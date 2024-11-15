package com.example.demo.service.User;

import com.example.demo.model.Department;
import com.example.demo.model.User;
//import com.example.demo.model.dto.WorkTimeDto;
import com.example.demo.model.WorkTime;
import com.example.demo.model.dto.DepartmentDTO;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.model.dto.WorkTimeDTO;
import com.example.demo.repository.IDepartmentRepository;
import com.example.demo.repository.IUserRepository;
import com.example.demo.repository.IWorkTimeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor

public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final IWorkTimeRepository workTimeRepository;
    private final IDepartmentRepository departmentRepository;

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
    public User save(User model) {
        return userRepository.save(model);
    }

    @Transactional
    @Override
    public void remove(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserDTO> getAllUser() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        for(User user : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserName(user.getUserName());
            userDTO.setFullName(user.getFullName());
            userDTO.setId(user.getId());
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
            userDTO.setWorkTimes(workTimeDTOS);

            //add user to user DTO
            userDTOS.add(userDTO);
        }
        return userDTOS;
    }

    //list department
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

    //list worktime
    public List<WorkTime> getWorkTimeByUser(Long userId) {
        if(userId != null){
            Optional<User>optionalUser = userRepository.findById(userId);
            if(optionalUser.isPresent()) {
                User foundUser=optionalUser.get();
                List<WorkTime> workTimes = workTimeRepository.findByUser(foundUser);
                log.info("Worktimes of user {}:{}",foundUser.getUserName(), workTimes);
                return workTimes;
            }
        }
        return Collections.emptyList();
    }

}
