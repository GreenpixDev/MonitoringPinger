package ru.greenpix.monitoring.pinger

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.web.reactive.config.EnableWebFlux

@SpringBootApplication
@ConfigurationPropertiesScan
@EnableWebFlux
class MonitoringPingerApplication

fun main(args: Array<String>) {
    runApplication<MonitoringPingerApplication>(*args)
}
