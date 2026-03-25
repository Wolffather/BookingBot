package ru.hey_savvy.bookingBot.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "booking")
class Booking(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @Column(name = "client_telegram_id")
    val clientTelegramId: Long,
    @Column(name = "client_name")
    val clientName: String,
    @JoinColumn(name = "service_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val serviceToBook: ServiceToBook,
    @JoinColumn(name = "time_slot_id")
    @OneToOne(fetch = FetchType.EAGER)
    val timeSlot: TimeSlot,
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    val status: Status,
    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now()
)