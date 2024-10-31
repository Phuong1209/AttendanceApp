//
//package com.example.demo.model;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.Set;
//
//@Entity
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//@Table(name="department")
//public class Department {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String departmentName;
//
//    @ManyToMany
//    @JoinTable(
//            name = "department_jobtype",
//            joinColumns = @JoinColumn(name = "department_id"),
//            inverseJoinColumns = @JoinColumn(name = "jobtype_id")
//    )
//    private Set<JobType> jobtypes;
//
//    @ManyToMany(mappedBy = "departments")
//    private Set<User> users;
//
//
//
//
//}

package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // You can keep this as is if you prefer

    @Column(name = "department_name") // Match the column name to your SQL
    private String departmentName;

    @ManyToMany
    @JoinTable(
            name = "department_jobtype",
            joinColumns = @JoinColumn(name = "department_id"),
            inverseJoinColumns = @JoinColumn(name = "jobtype_id")
    )
    private Set<JobType> jobtypes;

    @ManyToMany(mappedBy = "departments")
    private Set<User> users;
}

