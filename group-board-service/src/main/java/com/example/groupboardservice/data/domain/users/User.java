package com.example.groupboardservice.data.domain.users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "user_seq")
    private Long userSeq;

    @Column
    private String id;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String roles;

    @CreatedDate
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
}
