package com.example.demo.service;

import com.example.demo.dto.UserDTO;
import com.example.demo.dto.UserExcelDTO;
import com.example.demo.model.*;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.MemberManagementRepository;
import com.example.demo.repository.PositionRepository;
import com.example.demo.repository.WorkingTimeRepository;
import com.example.demo.security.MyUserPrincipal;
import com.example.demo.utils.AppUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor

public class MemberManagementService implements IMemberManagementService {
//
//    @Autowired
//    private Session session;


    private final MemberManagementRepository memberManagementRepository;
    private final PositionRepository positionRepository;
    private final DepartmentRepository departmentRepository;
    private final WorkingTimeRepository workingTimeRepository;
    private final PasswordEncoder passwordEncoder;
//    public MemberManagementService(MemberManagementRepository memberManagementRepository, PositionRepository positionRepository, DepartmentRepository departmentRepository, WorkingTimeRepository workingTimeRepository) {
//        this.memberManagementRepository = memberManagementRepository;
//        this.positionRepository = positionRepository;
//        this.departmentRepository = departmentRepository;
//        this.workingTimeRepository = workingTimeRepository;
//    }


    @Override
    public Iterable<User> findAll() {
        return memberManagementRepository.findAll();

    }

    @Override
    public  Optional<User> findById(Long id) {
        Optional<User> user=memberManagementRepository.findById(id);
        if(user.isPresent()){
            List<WorkingTime>workingTimes = workingTimeRepository.findByUser(user.get());
            user.get().setWorkingTimes(new HashSet<>(workingTimes));
        }
        return user;
    }

    @Transactional
    @Override
    public User save(User user) {
        user.setUserPasswords(passwordEncoder.encode(user.getUserPasswords()));
        return memberManagementRepository.save(user);
    }


    @Transactional
    @Override
    public void remove(Long id) {
        memberManagementRepository.deleteById(id);

    }
    @Transactional
    @Override
    public List<Position> getPositionByUser(Long userId) {
        if (userId!= null) {
            Optional<User> optionalUser = memberManagementRepository.findById(userId);
            if (optionalUser.isPresent()) {
                User foundUser = optionalUser.get();
                List<Position> positions = positionRepository.findByUsers(foundUser);
                log.info("Position of user {}: {}", foundUser.getUserName(), positions);
                return positions;
            }
        }
        return Collections.emptyList();
    }

    @Override
    public List<UserDTO> getAllUser() {
        List<User> users = memberManagementRepository.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        for(User user : users){
            UserDTO userDTO = new UserDTO();
            userDTO.setUserName(user.getUserName());
            userDTO.setUserFullName(user.getUserFullName());
            userDTO.setId(user.getId());

            //get position list by user and set to position for user
            List<Position> positions = positionRepository.findByUsers(user);
            Set<String> positionNames = positions.stream().map(Position::getPositionName).collect(Collectors.toSet());
            userDTO.setPositions(positionNames);

            //get department list by user and set to department for user
            List<Department> departments = departmentRepository.findByUsers(user);
            userDTO.setDepartments(departments.stream().map(Department::getDepartmentName).collect(Collectors.toSet()));

//          //get workingTime by user and set to workingTime for user
//            List<WorkingTime> workingTimes = workingTimeRepository.findByUser(user);
//            userDTO.setWorkingTime(workingTimes.stream().map(WorkingTime::get).collect(Collectors.toSet()));

            userDTOS.add(userDTO);
        }
        return userDTOS;

    }


    public List<Department>getDepartmentByUser(Long userId){
        if(userId!=null){
            Optional<User>optionalUser=memberManagementRepository.findById(userId);
            if(optionalUser.isPresent()){
                User foundUser=optionalUser.get();
                List<Department>departments=departmentRepository.findByUsers(foundUser);
                log.info("Department of user {}:{}",foundUser.getUserName(),departments);
                return departments;
            }
        }
        return Collections.emptyList();
    }
    public List<WorkingTime>getWorkingTimebyUser(Long userId){
        if(userId!=null){
            Optional<User>optionalUser=memberManagementRepository.findById(userId);
            if(optionalUser.isPresent()){
                User foundUser=optionalUser.get();
                List<WorkingTime> workingTimes= workingTimeRepository.findByUser(foundUser);
                for(WorkingTime workingTime:workingTimes){
                    log.info("WorkingTime for user{}:Date,{}Checkin_time:{},Checkout_time{},Breaktime{},Overtime{},Worktime{}",foundUser.getUserName(),
                    workingTime.getDate(), workingTime.getCheckin_time(), workingTime.getCheckout_time(),
                            workingTime.getWorktime(), workingTime.getBreaktime(), workingTime.getOvertime());

                }
                return workingTimes;
            }
        }
        return Collections.emptyList();

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = memberManagementRepository.findByUserName(username);
        String pass = passwordEncoder.encode("120901");
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new MyUserPrincipal(user);
    }

    public List<UserExcelDTO> getUsersExcel() {
        List<User> users = memberManagementRepository.findAll();
        List<UserExcelDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            UserExcelDTO userDTO = new UserExcelDTO();
            userDTO.setUserName(user.getUserName());

            userDTO.setUserFullName(user.getUserFullName());


            //get position list by user and set to position for user
            List<Position> positions = positionRepository.findByUsers(user);
            Set<String> positionNames = positions.stream().map(Position::getPositionName).collect(Collectors.toSet());
            userDTO.setPositions(String.join(", ", positionNames));

            //get department list by user and set to department for user
            List<Department> departments = departmentRepository.findByUsers(user);
            Set<String> departmentsNames = departments.stream().map(Department::getDepartmentName).collect(Collectors.toSet());
            userDTO.setDepartments(String.join(", ", departmentsNames));

            userDTOS.add(userDTO);
        }
        return userDTOS;
    }



    }
