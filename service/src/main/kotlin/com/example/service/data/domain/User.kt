package com.example.service.data.domain

import org.springframework.data.annotation.CreatedDate
import java.time.Instant
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Table(name = "user")
@Entity
open class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    open val id: Long,

    @Column(name = "user_id", nullable = false)
    open var userId: String,

    @Column(name = "email", nullable = false)
    open var email: String,

    @Column(name = "password", nullable = false)
    open var password: String,

    @Column(name = "name", nullable = false)
    open var name: String,

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    open val createdAt: Instant,

)