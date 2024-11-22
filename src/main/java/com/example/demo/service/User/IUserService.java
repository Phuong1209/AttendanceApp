package com.example.demo.service.User;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.model.Department;
import com.example.demo.model.Position;
import com.example.demo.model.User;
import com.example.demo.model.WorkTime;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.IGeneralService;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface IUserService extends IGeneralService<User> {
/*    //get list user
    List<UserDTO> getAllUser();
    //get list department by user
    List<Department> getDepartmentByUser(Long userId);
    //get list position by user
    List<Position> getPositionByUser(Long userId);
    //get list worktime by user
    List<WorkTime> getWorkTimeByUser(Long userId);
    //edit user
    UserDTO editUser(Long userId, String userName, String fullName, String password, Set<Long> departmentIds, Set<Long> positionIds);*/

}
