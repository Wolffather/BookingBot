package ru.hey_savvy.bookingBot.models

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "time_slot")
class TimeSlot(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name = "start_at", nullable = false)
    val startAt: LocalDateTime,
    @Column(name = "is_booked")
    val isBooked: Boolean = false,
    @JoinColumn(name = "master_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    val master: Master,
)