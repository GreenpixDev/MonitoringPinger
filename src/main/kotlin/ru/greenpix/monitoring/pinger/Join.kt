package ru.greenpix.monitoring.pinger

import kotlinx.coroutines.reactor.awaitSingleOrNull
import reactor.core.publisher.Mono

suspend fun Mono<Void>.join() {
    awaitSingleOrNull()
}