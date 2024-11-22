package com.example.demo.service.User;

import com.example.demo.dto.*;
import com.example.demo.model.*;
import com.example.demo.repository.IDepartmentRepository;
import com.example.demo.repository.IPositionRepository;
import com.example.demo.repository.IUserRepository;
import com.example.demo.repository.IWorkTimeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor

public class UserService implements IUserService {

/*    private final IUserRepository userRepository;
    private final IWorkTimeRepository workTimeRepository;
    private final IDepartmentRepository departmentRepository;
    private final IPositionRepository positionRepository;
    //private final PasswodEncoder passwordEncoder;

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
    public User save(User model) { return userRepository.save(model);
    }

    //delete (old)
    *//*@Transactional
    @Override
    public void remove(Long id) {
        userRepository.deleteById(id);
    }*//*

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
                for (Position position : user.getPositions()) {
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


    //get list department of user
    public List<Department> getDepartmentByUser(Long userId) {
        if(userId != null){
            Optional<User>optionalUser = userRepository.findById(userId);
            if(optionalUser.isPresent()) {
                User foundUser=optionalUser.get();
                List<Department>departments = departmentRepository.findByUsers(foundUser);
                log.info("Department of user {}:{}",foundUser.getUserName(),departments);
                return departments;
            }
        }
        return Collections.emptyList();
    }

    //get list worktime of user
    public List<WorkTime> getWorkTimeByUser(Long userId) {
        if(userId != null){
            Optional<User>optionalUser = userRepository.findById(userId);
            if(optionalUser.isPresent()) {
                User foundUser=optionalUser.get();
                List<WorkTime> workTimes = workTimeRepository.findByUser(foundUser);
                log.info("Work times of user {}:{}",foundUser.getUserName(), workTimes);
                return workTimes;
            }
        }
        return Collections.emptyList();
    }

    //get list position of user
    public List<Position> getPositionByUser(Long userId) {
        if(userId != null){
            Optional<User>optionalUser = userRepository.findById(userId);
            if(optionalUser.isPresent()) {
                User foundUser=optionalUser.get();
                List<Position> positions = positionRepository.findByUsers(foundUser);
                log.info("Positions of user {}:{}",foundUser.getUserName(), positions);
                return positions;
            }
        }
        return Collections.emptyList();
    }

    @Override
    public List<UserDTO> getAllUser() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        for(User user : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setUserName(user.getUserName());
            userDTO.setFullName(user.getFullName());
            userDTO.setPassword(user.getPassword());

            //get department list
            List<Department> departments = departmentRepository.findByUsers(user);
            Set<DepartmentDTO> departmentDTOS = new HashSet<>();
            for(Department department: departments) {
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
            for(WorkTime workTime : workTimes) {
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

            //get position list
            List<Position> positions = positionRepository.findByUsers(user);
            Set<PositionDTO> positionDTOS = new HashSet<>();
            for(Position position : positions) {
                PositionDTO positionDTO = new PositionDTO();
                positionDTO.setId(position.getId());
                positionDTO.setName(position.getName());
                positionDTOS.add(positionDTO);
            }
            //add position list to user
            userDTO.setPositions(positionDTOS);

            //add user to user DTO
            userDTOS.add(userDTO);
        }
        return userDTOS;
    }

    //Edit
    @Transactional
    public UserDTO editUser(Long userId, String newUserName, String newFullName, String newPassword, Set<Long> newDepartmentIds, Set<Long> newPositionIds) {
        // Find the user by ID
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            throw new NoSuchElementException("User not found with ID: " + userId);
        }

        User user = optionalUser.get();

        // Update the user's username
        if (newUserName != null && !newUserName.trim().isEmpty()) {
            user.setUserName(newUserName);
        }
        if (newFullName != null && !newFullName.trim().isEmpty()) {
            user.setFullName(newFullName);
        }
        if (newPassword != null && !newPassword.trim().isEmpty()) {
            user.setPassword(newPassword);
        }

        // Update the user's departments
        if (newDepartmentIds != null && !newDepartmentIds.isEmpty()) {
            // Fetch the new Department entities by their IDs
            List<Department> newDepartments = departmentRepository.findAllById(newDepartmentIds);
            if (newDepartments.size() != newDepartmentIds.size()) {
                throw new IllegalArgumentException("One or more Department IDs are invalid.");
            }
            user.setDepartments(new HashSet<>(newDepartments));
        }

        // Update the user's positions
        if (newPositionIds != null && !newPositionIds.isEmpty()) {
            // Fetch the new Position entities by their IDs
            List<Position> newPositions = positionRepository.findAllById(newPositionIds);
            if (newPositions.size() != newPositionIds.size()) {
                throw new IllegalArgumentException("One or more Department IDs are invalid.");
            }
            user.setPositions(new HashSet<>(newPositions));
        }

        // Save the updated user
        User updatedUser = userRepository.save(user);

        // Map the updated user to UserDTO
        UserDTO userDTO = new UserDTO();
        userDTO.setId(updatedUser.getId());
        userDTO.setUserName(updatedUser.getUserName());
        userDTO.setFullName(updatedUser.getFullName());
        userDTO.setPassword(updatedUser.getPassword());

        // Map departments to DepartmentDTO
        Set<DepartmentDTO> departmentDTOS = new HashSet<>();
        for (Department department : updatedUser.getDepartments()) {
            DepartmentDTO departmentDTO = new DepartmentDTO();
            departmentDTO.setId(department.getId());
            departmentDTO.setName(department.getName());
            departmentDTOS.add(departmentDTO);
        }
        userDTO.setDepartments(departmentDTOS);

        // Map positions to PositionDTO
        Set<PositionDTO> positionDTOS = new HashSet<>();
        for (Position position : updatedUser.getPositions()) {
            PositionDTO positionDTO = new PositionDTO();
            positionDTO.setId(position.getId());
            positionDTO.setName(position.getName());
            positionDTOS.add(positionDTO);
        }
        userDTO.setPositions(positionDTOS);

        return userDTO;
    }*/


}
