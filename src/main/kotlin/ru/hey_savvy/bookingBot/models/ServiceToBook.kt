package ru.hey_savvy.bookingBot.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table

@Entity
@Table(name = "service")
class ServiceToBook(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name = "title")
    val title: String,
    @Column(name = "duration_inMinutes")
    val durationInMinutes: Long,
    @Column(name = "price")
    val price: Long,
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "master_service",
        joinColumns = [JoinColumn(name = "service_id")],
        inverseJoinColumns = [JoinColumn(name = "master_id")]
    )
    val masters: List<Master> = emptyList()
)