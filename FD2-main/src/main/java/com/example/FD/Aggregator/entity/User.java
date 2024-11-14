//package com.example.FD.Aggregator.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import java.time.LocalDate;
//
//@Data
//@Entity
//@Table(name = "users")
//public class User {
//
//    @Id
//    @Column(name = "ref_id", unique = true, nullable = false)
//    private String refNo;
//
//    private String mobile;
//    private String email;
//    private String firstName;
//    private String lastName;
//
//    @Column(name = "date_of_birth")
//    private LocalDate dob;
//
//    @Column(name = "created_date", updatable = false)
//    private LocalDate createdDate;
//
//    @Column(name = "updated_date")
//    private LocalDate updatedDate;
//
//    @PrePersist
//    protected void onCreate() {
//        createdDate = LocalDate.now();
//        updatedDate = LocalDate.now();
//    }
//
//    @PreUpdate
//    protected void onUpdate() {
//        updatedDate = LocalDate.now();
//    }
//}
//


package com.example.FD.Aggregator.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "ref_id", unique = true, nullable = false)
    private String refNo;

    private String mobile;
    private String email;
    private String firstName;
    private String lastName;
    private String preferredLang; // Preferred Language

    @Column(name = "date_of_birth")
    private LocalDate dob;

    @Column(name = "created_date", updatable = false)
    private LocalDate createdDate;

    @Column(name = "updated_date")
    private LocalDate updatedDate;

    @PrePersist
    protected void onCreate() {
        createdDate = LocalDate.now();
        updatedDate = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedDate = LocalDate.now();
    }
}
