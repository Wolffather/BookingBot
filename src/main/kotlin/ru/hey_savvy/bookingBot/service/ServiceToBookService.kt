package ru.hey_savvy.bookingBot.service

import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import ru.hey_savvy.bookingBot.models.Master
import ru.hey_savvy.bookingBot.models.ServiceToBook
import ru.hey_savvy.bookingBot.repository.ServiceToBookRepository

@Service
class ServiceToBookService(
    private val serviceToBookRepository: ServiceToBookRepository
) {

    fun findAvailableServices(): List<ServiceToBook> {
        return serviceToBookRepository.findAll(Sort.by("title"))
    }

    fun getMastersForService(serviceId: Long): List<Master> {
        val service = serviceToBookRepository.findById(serviceId)
            .orElseThrow { NoSuchElementException("Service not found: $serviceId") }
        return service.masters
    }
}