package com.example.demo.service;

import com.example.demo.dto.DepartmentDTO;
import com.example.demo.dto.PositionDTO;
import com.example.demo.dto.UserCSVDTO;
import com.example.demo.dto.UserDTO;

import com.example.demo.model.*;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.MemberManagementRepository;
import com.example.demo.repository.PositionRepository;
import com.example.demo.repository.WorkingTimeRepository;
import com.example.demo.security.MyUserPrincipal;
//import com.opencsv.CSVWriter;
import com.opencsv.CSVWriter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor

public class MemberManagementService implements IMemberManagementService {


    private final MemberManagementRepository memberManagementRepository;
    private final PositionRepository positionRepository;
    private final DepartmentRepository departmentRepository;
    private final WorkingTimeRepository workingTimeRepository;

    @Transactional
    @Override
    public Iterable<User> findAll() {
        return memberManagementRepository.findAll();

    }
    @Transactional
    @Override
    public  Optional<User> findById(Long id) {
        return memberManagementRepository.findById(id);
    }

    @Transactional
    @Override
    public User save( User user) {
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
        for(User user : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setUserName(user.getUserName());
            userDTO.setUserFullName(user.getUserFullName());
            userDTO.setId(user.getId());
            userDTO.setPassword(user.getUserPasswords());

            //get position list by user and set to position for user
            List<Position> positions = positionRepository.findByUsers(user);
            Set<PositionDTO> positionDTOS = new HashSet<>();
            for (Position position : positions) {
                PositionDTO positionDTO = new PositionDTO();
                positionDTO.setId(position.getId());
                positionDTO.setPositionName(position.getPositionName());
                positionDTOS.add(positionDTO);
            }

            userDTO.setPositions(positionDTOS);

            //get department list by user and set to department for user
            List<Department> departments = departmentRepository.findByUsers(user);
            Set<DepartmentDTO> departmentDTOS = new HashSet<>();
            for(Department department: departments) {
                DepartmentDTO departmentDTO = new DepartmentDTO();
                departmentDTO.setId(department.getId());
                departmentDTO.setDepartmentName(department.getDepartmentName());
                departmentDTOS.add(departmentDTO);
            }
            userDTO.setDepartments(departmentDTOS);

            userDTOS.add(userDTO);
        }
        return userDTOS;
    }
    public List<Department> getDepartmentByUser(Long userId) {
        if(userId != null){
            Optional<User>optionalUser = memberManagementRepository.findById(userId);
            if(optionalUser.isPresent()) {
                User foundUser=optionalUser.get();
                List<Department>departments = departmentRepository.findByUsers(foundUser);
                log.info("Department of user {}:{}",foundUser.getUserName(),departments);
                return departments;
            }
        }
        return Collections.emptyList();
    }
    public List<WorkingTime> getWorkingTimebyUser(Long userId) {
        if(userId != null) {
            Optional<User>optionalUser=memberManagementRepository.findById(userId);
            if(optionalUser.isPresent()) {
                User foundUser = optionalUser.get();
                List<WorkingTime> workingTimes = workingTimeRepository.findByUser(foundUser);
                for(WorkingTime workingTime:workingTimes) {
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
        Optional<User> user = memberManagementRepository.findByUserName(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User is not exist!");
        }
        return new MyUserPrincipal(user.get());
    }

//    public List<UserExcelDTO> getUsersExcel() {
//        List<User> users = memberManagementRepository.findAll();
//        List<UserExcelDTO> userDTOS = new ArrayList<>();
//        for (User user : users) {
//            UserExcelDTO userDTO = new UserExcelDTO();
//            userDTO.setUserName(user.getUserName());
//
//            userDTO.setUserFullName(user.getUserFullName());
//
//
//            //get position list by user and set to position for user
//            List<Position> positions = positionRepository.findByUsers(user);
//            Set<String> positionNames = positions.stream().map(Position::getPositionName).collect(Collectors.toSet());
//            userDTO.setPositions(String.join(", ", positionNames));
//
//            //get department list by user and set to department for user
//            List<Department> departments = departmentRepository.findByUsers(user);
//            Set<String> departmentsNames = departments.stream().map(Department::getDepartmentName).collect(Collectors.toSet());
//            userDTO.setDepartments(String.join(", ", departmentsNames));
//
//            userDTOS.add(userDTO);
//        }
//        return userDTOS;
////    }

    public void generateCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; file=report.csv");

        List<User> userList = memberManagementRepository.findAll();
        CSVWriter writer = new CSVWriter(response.getWriter());

        // Write header to CSV file
        writer.writeNext(new String[] {"ID", "Name", "FullName", "Position", "Department"});

        for (User user : userList) {
            String positionNames = getPositionNamesByUser(user.getId());
            String departmentNames = getDepartmentNamesByUser(user.getId());
            writer.writeNext(new String[] {String.valueOf(user.getId()), user.getUserName(), user.getUserFullName() , positionNames, departmentNames});
        }

        writer.close();
    }
    private String getPositionNamesByUser(Long userId) {
        List<Position> positions = getPositionByUser(userId);
        return positions.stream()
                .map(Position::getPositionName)  // Positionの名前を取得
                .collect(Collectors.joining(", "));
    }

    // Department情報をカンマ区切りで取得するメソッド
    private String getDepartmentNamesByUser(Long userId) {
        List<Department> departments = getDepartmentByUser(userId);
        return departments.stream()
                .map(Department::getDepartmentName)  // Departmentの名前を取得
                .collect(Collectors.joining(", "));
    }
}
