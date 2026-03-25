package ru.hey_savvy.bookingBot.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.hey_savvy.bookingBot.models.Master

@Repository
interface MasterRepository : JpaRepository<Master, Long> {
}