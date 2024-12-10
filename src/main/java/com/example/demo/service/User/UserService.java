package com.example.demo.service.User;

import com.example.demo.model.Department;
import com.example.demo.model.User;
import com.example.demo.model.WorkTime;
import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.PositionDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.dto.WorkTimeDTO;
import com.example.demo.repository.IDepartmentRepository;
import com.example.demo.repository.IPositionRepository;
import com.example.demo.repository.IUserRepository;
import com.example.demo.repository.IWorkTimeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.dto.request.UserCreationRequest;
import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.enums.Position;
import com.example.demo.exception.AppException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.mapper.UserMapper;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final IWorkTimeRepository workTimeRepository;
    private final IDepartmentRepository departmentRepository;
    private final IPositionRepository positionRepository;
    PasswordEncoder passwordEncoder;
    UserMapper userMapper;

    //    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse createRequest(UserCreationRequest request) {
        if (userRepository.existsByUserName(request.getUsername())) {
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
        }
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        HashSet<String> position = new HashSet<>();
        position.add(Position.USER.name());
        return userMapper.toUserResponse(userRepository.save(user));
    }

    //    @PostAuthorize("returnObject.userName == authentication.name")
    public UserResponse getMyInfo() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUserName(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );
        return userMapper.toUserResponse(user);
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse).toList();
    }

    //    @PostAuthorize("returnObject.userName == authentication.name")
    public UserResponse getUserById(Long id) {
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
    }

    //    @PostAuthorize("returnObject.userName == authentication.name")
    public UserResponse updateUser(Long userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPositions(new HashSet<>(positionRepository.findAllById(Collections.singleton(userId))));
        return userMapper.toUserResponse(userRepository.save(user));
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Transactional
    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void remove(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Clear associations with departments
            if (user.getDepartments() != null) {
                for (Department department : user.getDepartments()) {
                    department.getUsers().remove(user);
                }
                user.getDepartments().clear();
            }

            // Clear associations with positions
            if (user.getPositions() != null) {
                for (com.example.demo.model.Position position : user.getPositions()) {
                    position.getUsers().remove(user);
                }
                user.getPositions().clear();
            }

            // Delete the user
            userRepository.delete(user);
        } else {
            throw new EntityNotFoundException("User with ID " + id + " not found");
        }
    }

    @Transactional
    public void delete(User user) {
//        List<WorkingTime> workingTimes = workingTimeRepository.findByUser(user);
//        workingTimeRepository.deleteAll(workingTimes);
        userRepository.deleteById(user.getId());
    }

    @Override
    public List<UserDTO> getAllUser() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserName(user.getUserName());
            userDTO.setFullName(user.getFullName());
            userDTO.setId(user.getId());
            userDTO.setPassword(user.getPassword());

            //get position list
            List<com.example.demo.model.Position> positions = positionRepository.findByUsers(user);
            Set<PositionDTO> positionDTOS = new HashSet<>();
            for (com.example.demo.model.Position position : positions) {
                PositionDTO positionDTO = new PositionDTO();
                positionDTO.setId(position.getId());
                positionDTO.setName(position.getName());
                positionDTOS.add(positionDTO);
            }

            userDTO.setPositions(positionDTOS);

            //get department list
            List<Department> departments = departmentRepository.findByUsers(user);
            Set<DepartmentDTO> departmentDTOS = new HashSet<>();
            for (Department department : departments) {
                DepartmentDTO departmentDTO = new DepartmentDTO();
                departmentDTO.setId(department.getId());
                departmentDTO.setName(department.getName());
                departmentDTOS.add(departmentDTO);
            }
            //add department list to user
            userDTO.setDepartments(departmentDTOS);

            //get worktime list
            List<WorkTime> workTimes = workTimeRepository.findByUser(user);
            Set<WorkTimeDTO> workTimeDTOS = new HashSet<>();
            for (WorkTime workTime : workTimes) {
                WorkTimeDTO workTimeDTO = new WorkTimeDTO();
                workTimeDTO.setId(workTime.getId());
                workTimeDTO.setDate(workTime.getDate());
                workTimeDTO.setCheckinTime(workTime.getCheckinTime());
                workTimeDTO.setCheckoutTime(workTime.getCheckoutTime());
                workTimeDTO.setBreakTime(workTime.getBreakTime());
                workTimeDTO.setWorkTime(workTime.getWorkTime());
                workTimeDTO.setOverTime(workTime.getOverTime());
                workTimeDTOS.add(workTimeDTO);
            }
            //add worktime list to user
            userDTO.setWorkTimes(workTimeDTOS);

            //add user to user DTO
            userDTOS.add(userDTO);
        }
        return userDTOS;
    }

    //list position
    @Transactional
    @Override
    public List<com.example.demo.model.Position> getPositionByUser(Long userId) {
        if (userId != null) {
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isPresent()) {
                User foundUser = optionalUser.get();
                List<com.example.demo.model.Position> positions = positionRepository.findByUsers(foundUser);
                log.info("Position of user {}: {}", foundUser.getUserName(), positions);
                return positions;
            }
        }
        return Collections.emptyList();
    }

    //list department
    public List<Department> getDepartmentByUser(Long userId) {
        if (userId != null) {
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isPresent()) {
                User foundUser = optionalUser.get();
                List<Department> departments = departmentRepository.findByUsers(foundUser);
                log.info("Department of user {}:{}", foundUser.getUserName(), departments);
                return departments;
            }
        }
        return Collections.emptyList();
    }

    //list worktime
    public List<WorkTime> getWorkTimeByUser(Long userId) {
        if (userId != null) {
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isPresent()) {
                User foundUser = optionalUser.get();
                List<WorkTime> workTimes = workTimeRepository.findByUser(foundUser);
                log.info("Worktimes of user {}:{}", foundUser.getUserName(), workTimes);
                return workTimes;
            }
        }
        return Collections.emptyList();
    }
}

////    @Override
////    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
////        Optional<User> user = userRepository.findByUserName(username);
////        if (user.isEmpty()) {
////            throw new UsernameNotFoundException("User is not exist!");
////        }
////        return new MyUserPrincipal(user.get());
////    }
////    @Transactional
////    @Override
////    public void register(UserRegisterDTO userRegisterDTO){
////        User newUser = new User();
////        newUser.setUserName(userRegisterDTO.getUserName());
////        newUser.setPassword(userRegisterDTO.getPassword());
////        newUser.setFullName(userRegisterDTO.getFullName());
////        userRepository.save(newUser);
//    }
