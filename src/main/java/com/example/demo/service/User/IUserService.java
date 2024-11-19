package com.example.demo.service.User;

import com.example.demo.model.Department;
import com.example.demo.model.User;
import com.example.demo.model.WorkTime;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.IGeneralService;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserService extends IGeneralService<User> {
    List<UserDTO> getAllUser();
    List<Department> getDepartmentByUser(Long userId);
    List<WorkTime> getWorkTimeByUser(Long userId);
}
