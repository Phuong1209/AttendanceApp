//package com.example.demo.model;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//import java.util.Set;
//@Data
//@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//@Table(name = "jobtype")
//public class JobType {
//    @Id
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
//    private Long id;
//    private String jobTypeName;
//    private LocalDateTime create_at;
//    private LocalDateTime update_at;
//
//    @ManyToMany(mappedBy = "jobtypes")
//    private Set<Department> departments;
//
//    @OneToMany(mappedBy = "jobtype",cascade = CascadeType.ALL)
//    private Set<Task> tasks;
//}
//
//package com.example.demo.model;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDateTime;
//import java.util.Set;
//
//@Data
//@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//@Table(name = "jobtype")
//public class JobType {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String jobTypeName;
//
//    @Column(name = "created_at")
//    private LocalDateTime createdAt;
//
//    @Column(name = "updated_at")
//    private LocalDateTime updatedAt;
//
//    @ManyToMany(mappedBy = "jobtypes")
//    private Set<Department> departments;
//
//    @OneToMany(mappedBy = "jobtype", cascade = CascadeType.ALL)
//    private Set<Task> tasks;
//}

package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "jobtype") // Matches SQL table name
public class JobType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jobtype_name") // Matches SQL column name
    private String jobTypeName;

    @Column(name = "create_at")
    private LocalDateTime createAt;

    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @ManyToMany(mappedBy = "jobtypes")
    private Set<Department> departments;

    @OneToMany(mappedBy = "jobtype", cascade = CascadeType.ALL)
    private Set<Task> tasks;
}
