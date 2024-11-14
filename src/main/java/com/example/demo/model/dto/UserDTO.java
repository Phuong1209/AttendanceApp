package com.example.demo.model.dto;

public class UserDTO {
    private Long id;
    private String fullName;
    private String userName;
    private String password;
    //private Set<PositionDto> positions;
    //private Set<DepartmentDto> departments;
    //private Set<WorkTimeDto> workTimes;

    //constructor
    public UserDTO(Long id, String fullName, String userName, String password) {
        this.id = id;
        this.fullName = fullName;
        this.userName = userName;
        this.password = password;
    }

    //getter and setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /*public Set<PositionDto> getPositions() {
        return positions;
    }

    public void setPositions(Set<PositionDto> positions) {
        this.positions = positions;
    }*/

    /*public Set<DepartmentDto> getDepartments() {
        return departments;
    }

    public void setDepartments(Set<DepartmentDto> departments) {
        this.departments = departments;
    }

    public Set<WorkTimeDto> getWorkTimes() {
        return workTimes;
    }

    public void setWorkTimes(Set<WorkTimeDto> workTimes) {
        this.workTimes = workTimes;
    }*/
}
