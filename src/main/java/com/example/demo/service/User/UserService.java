package com.example.demo.service.User;

import com.example.demo.model.User;
//import com.example.demo.model.dto.WorkTimeDto;
import com.example.demo.repository.IUserRepository;
import com.example.demo.repository.IWorkTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;
    private IWorkTimeRepository workTimeRepository;

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }
    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(User model) {
        return userRepository.save(model);
    }

    @Override
    public void remove(Long id) {
        userRepository.deleteById(id);
    }

/*    @Override
    public List<UserDto> getAllUser() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for(User user : users){
            UserDto userDto = new UserDto();
            userDto.setUserName(user.getUserName());
            userDto.setFullName(user.getFullName());
            userDto.setPassword(user.getPassword());
            userDto.setId(user.getId());

            //get user's workTime list
            List<WorkTime> workTimes = workTimeRepository.findByUser(user);
            Set<WorkTimeDto> workTimeDtos = new HashSet<>();
            for (WorkTime workTime : workTimes){
                WorkTimeDto workTimeDto = new WorkTimeDto();
                workTimeDto.setId(workTime.getId());
                workTimeDtos.add(workTimeDto);
            }
            //set jobTypeDtos to departmentDto
            userDto.setWorkTimes(workTimeDtos);

            //add DepartmentDto to Dtos
            userDtos.add(userDto);
        }
        return userDtos;
    }*/


}
