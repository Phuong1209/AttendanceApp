package com.example.demo.service.User;

import com.example.demo.dto.UserDTO;
import com.example.demo.dto.UserRegisterDTO;
import com.example.demo.model.Department;
import com.example.demo.model.Position;
import com.example.demo.model.User;
import com.example.demo.model.WorkTime;
import com.example.demo.dto.UserDTO;
import com.example.demo.service.IGeneralService;
import jakarta.transaction.Transactional;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserService extends IGeneralService<User> {
    List<UserDTO> getAllUser();
    List<Position> getPositionByUser(Long userId);
    List<Department> getDepartmentByUser(Long userId);
    List<WorkTime> getWorkTimeByUser(Long userId);

    void register(UserRegisterDTO userRegisterDTO);

//    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
