package com.example.demo.controller;

import com.example.demo.csv.UserCSVExporter;
import com.example.demo.dto.*;
//import com.example.demo.dto.UserExcelDTO;
//import com.example.demo.excel.UserExcelExporter;
import com.example.demo.model.Department;
import com.example.demo.model.Position;
import com.example.demo.model.User;
import com.example.demo.model.WorkingTime;

import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.PositionRepository;
import com.example.demo.service.DepartmentService;
import com.example.demo.service.IDepartmentService;
import com.example.demo.service.MemberManagementService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("/user/member")
public class MemberManagementController {
    @Autowired
    private MemberManagementService memberManagementService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    PositionRepository positionRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    DepartmentService departmentService;

    @GetMapping

    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok().body(memberManagementService.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> getAllUserById(@PathVariable Long id) {
        Optional<User> userOptional = memberManagementService.findById(id);
        return userOptional.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("getPosition/{id}")
    public ResponseEntity<?> getPosition(@PathVariable Long id) {
        List<Position> positions = memberManagementService.getPositionByUser(id);
        return ResponseEntity.ok().body(positions);
    }

    @GetMapping("getDepartment/{id}")
    public ResponseEntity<?> getDepartment(@PathVariable Long id) {
        List<Department> departments = memberManagementService.getDepartmentByUser(id);
        return ResponseEntity.ok().body(departments);
    }

    @GetMapping("getWorkingTime/{id}")
    public ResponseEntity<?> getWorkingTime(@PathVariable Long id) {
        List<WorkingTime> workingTimes = memberManagementService.getWorkingTimebyUser(id);
        return ResponseEntity.ok().body(workingTimes);
    }

    //    @PostMapping("")
//    public ResponseEntity<User> createUser( @RequestBody UserDTO userDTO) {
//        User newUser = new User();
//        newUser.setUserName(userDTO.getUserName());
//        newUser.setUserFullName(userDTO.getUserFullName());
//        newUser.setUserPasswords(passwordEncoder.encode(userDTO.getPassword()));
//        Set<PositionDTO> positionDTOS = userDTO.getPositions();
//        Set<Position> positions = new HashSet<>();
//        for (PositionDTO positionDTO : positionDTOS) {
//            Position position;
//            Optional<Position> optionalPosition = positionRepository.findById(positionDTO.getId());
//            if (optionalPosition.isPresent()){
//                position = optionalPosition.get();
//            }
//            position = new Position();
//            position.setId(positionDTO.getId());
//            position.setPositionName(positionDTO.getPositionName());
//            positions.add(position);
//        }
//        newUser.setPositions(positions);
//        //Set department of departmentDTOs for department
//        Set<DepartmentDTO> departmentDTOS = userDTO.getDepartments();
//        Set<Department> departments = new HashSet<>();
//        for(DepartmentDTO departmentDTO : departmentDTOS){
//            Department department;
//            Optional<Department> optionalDepartment = departmentRepository.findById(departmentDTO.getId());
//            if (optionalDepartment.isPresent()){
//                department = optionalDepartment.get();
//            }
//            department = new Department();
//            department.setId(departmentDTO.getId());
//            department.setDepartmentName(departmentDTO.getDepartmentName());
//            departments.add(department);
//        }
//        newUser.setDepartments(departments);
//        memberManagementService.save(newUser);
//        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
//    }
    @PostMapping("")
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
        User newUser = new User();
        newUser.setUserName(userDTO.getUserName());
        newUser.setUserFullName(userDTO.getUserFullName());
        newUser.setUserPasswords(passwordEncoder.encode(userDTO.getPassword()));
        Set<PositionDTO> positionDTOS = userDTO.getPositions();
        Set<Position> positions = new HashSet<>();
        for (PositionDTO positionDTO : positionDTOS) {
            Position position;
            Optional<Position> optionalPosition = positionRepository.findById(positionDTO.getId());
            if (optionalPosition.isPresent()) {
                position = optionalPosition.get();
            } else {
                position = new Position();
                position.setPositionName(positionDTO.getPositionName());
            }
            positions.add(position);
        }
        newUser.setPositions(positions);
        //Set department of departmentDTOs for department
        Set<DepartmentDTO> departmentDTOS = userDTO.getDepartments();
        Set<Department> departments = new HashSet<>();
        for (DepartmentDTO departmentDTO : departmentDTOS) {
            Department department;
            Optional<Department> optionalDepartment = departmentRepository.findById(departmentDTO.getId());
            if (optionalDepartment.isPresent()) {
                department = optionalDepartment.get();
            } else {
                department = new Department();
                department.setDepartmentName(departmentDTO.getDepartmentName());
            }
            departments.add(department);
        }
        newUser.setDepartments(departments);
        memberManagementService.save(newUser);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> editUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        Optional<User> userOptional = memberManagementService.findById(id);
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        User existingUser = userOptional.get();
        if(userDTO.getId() != null){
            existingUser.setId(userDTO.getId());
        }
        if(userDTO.getUserName() != null){
            existingUser.setUserName(userDTO.getUserName());
        }
        if(userDTO.getUserFullName() != null){
            existingUser.setUserFullName(userDTO.getUserFullName());
        }
        if (userDTO.getPassword() != null){
            existingUser.setUserPasswords(passwordEncoder.encode(userDTO.getPassword()));
        }
        //Get old position list
        Set<Position> oldPositions = existingUser.getPositions();

        // 新しいポジションIDリストをDTOから取得
        Set<Long> newPositionIds = userDTO.getPositions().stream()
                .map(PositionDTO::getId)
                .collect(Collectors.toSet());
        Set<Position> newPositions = new HashSet<>();
        for (Long positionId : newPositionIds) {
            Position position;
            Optional<Position> optionalPosition = positionRepository.findById(positionId);
            if (optionalPosition.isPresent()) {
                newPositions.add(optionalPosition.get());
            } else {
                // ポジションが存在しない場合の処理（必要ならエラーを返す）
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            //get list of positions to delete
            Set<Position> positionsToRemove = new HashSet<>(oldPositions);
            positionsToRemove.removeAll(newPositions);

            //get list of positions to add
            Set<Position> positionsToAdd = new HashSet<>(newPositions);
            positionsToAdd.removeAll(oldPositions);

            // Update the user's positions
            existingUser.getPositions().removeAll(positionsToRemove);
            existingUser.getPositions().addAll(positionsToAdd);

            memberManagementService.save(existingUser);


            //Set department of departmentDTOs for department

            //get list of old departments
            Set<Department> oldDepartments = existingUser.getDepartments();
            //get new list of departmentIds from DTO
            Set<Long> newDepartmentIds = userDTO.getDepartments().stream()
                    .map(DepartmentDTO::getId)
                    .collect(Collectors.toSet());
            Set<Department> newDepartments = new HashSet<>();
            for (Long departmentId : newDepartmentIds) {
                Department department;
                Optional<Department> optionalDepartment = departmentRepository.findById(departmentId);
                if (optionalDepartment.isPresent()) {
                    newDepartments.add(optionalDepartment.get());
                } else {
                        // ポジションが存在しない場合の処理（必要ならエラーを返す）
                        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                    }

//               // get list of positions to delete
                Set<Department> departmentsToRemove = new HashSet<>(oldDepartments);
               departmentsToRemove.removeAll(newDepartments);

                //get list of positions to add
                Set<Department> departmentsToAdd = new HashSet<>(newDepartments);
                departmentsToAdd.removeAll(oldDepartments);

                // Update the user's positions
                existingUser.getDepartments().removeAll(departmentsToRemove);
                existingUser.getDepartments().addAll(departmentsToAdd);
            }

            memberManagementService.save(existingUser);
            return new ResponseEntity<>(existingUser, HttpStatus.OK);
        }
        return null;
    }
//    @PatchMapping("/{id}/positions")
//    public ResponseEntity<String> updateUserPositions(
//            @PathVariable Long id,
//            @RequestBody Set<Long> positionIds
//    ) {
//        Optional<User> optionalUser = memberManagementService.findById(id);
//        if (optionalUser.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        User user = optionalUser.get();
//        Set<Position> positions = new HashSet<>();
//
//        for (Long positionId : positionIds) {
//            Optional<Position> optionalPosition = memberManagementService.getPositionById(positionId); // Implement this method in your service
//            if (optionalPosition.isPresent()) {
//                positions.add(optionalPosition.get());
//            } else {
//                return ResponseEntity.badRequest().body("Position ID " + positionId + " not found");
//            }
//        }
//
//        user.setPositions(positions);
//        memberManagementService.save(user);
//
//        return ResponseEntity.ok("User positions updated successfully");
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        Optional<User> userOptional = memberManagementService.findById(id);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        memberManagementService.remove(id);
        return new ResponseEntity<>(userOptional.get(), HttpStatus.NO_CONTENT);
    }

//    @GetMapping("/export")
//    public void exportToExcel(HttpServletResponse response) throws IOException {
//        response.setContentType("application/octet-stream");
//        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
//        String currentDateTime = dateFormatter.format(new Date());
//
//        String headerKey = "Content-Disposition";
//        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
//        response.setHeader(headerKey, headerValue);
//
//        List<UserExcelDTO> listUsers = memberManagementService.getUsersExcel();
//
//        UserExcelExporter excelExporter = new UserExcelExporter(listUsers);
//
//        excelExporter.export(response);
//
//    }


//    @GetMapping("/generate-csv-user")
//    public void generateCSVUser(@RequestParam String param1, @RequestParam String param2) {
//        // your CSV report generation logic here
//        // using data from MongoDB with repository.findAll()
//        // once you have your CSV report, you can save it to file
//        // using FileWriter or any libraries like OpenCSV or Apache Commons CSV
//    }


    @GetMapping("/exportUserCSV")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("CSVpplication/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        List<UserCSVDTO> listUsers = memberManagementService.getUsersCSV();

        UserCSVExporter csvExporter = new UserCSVExporter(listUsers);

        csvExporter.generateCSV(response);
    }
}



