package com.rodtech.kotlinpoprojects.kotlinwebflux.services

import com.rodtech.kotlinpoprojects.kotlinwebflux.model.Cep
import com.rodtech.kotlinpoprojects.kotlinwebflux.model.GeoLocation
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.util.function.component1
import reactor.kotlin.core.util.function.component2
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

@Service
class CepService(
        private val webClient: WebClient
) {

    fun getCep(cep: String) : Mono<Cep> {
        return webClient.get()
                .uri("$cep/json/")
                .retrieve()
                .bodyToMono(Cep::class.java)
                .flatMap { fetchGeolocation(it) }
    }

    fun searchCepsByStreet(street: String): Flux<Cep> {
        val _street = street.trim().replace(" ", "+")
        return webClient.get()
            .uri("$_street/json/")
            .retrieve()
            .bodyToFlux(Cep::class.java)
            .flatMap { fetchGeolocation(it) }
    }

    private fun fetchGeolocation(cep: Cep): Mono<Cep> {
        return  webClient.get()
            .uri("https://maps.googleapis.com/maps/api/geocode/json")
            .accept(MediaType.APPLICATION_JSON)
            .attribute("address", "${cep.logradouro}, ${cep.localidade} - ${cep.uf}")
            .retrieve()
            .bodyToMono(GeoLocation::class.java)
            .map { geoLocation ->
                cep.copy(geoLocation = geoLocation)
            }
    }

    fun getCepsDistance(cep1: String, cep2: String): Mono<Double> {
        val monoCep1 = getCep(cep1)
        val monoCep2 = getCep(cep2)

        return monoCep1.zipWith(monoCep2)
            .map { tuple ->
                val (cep1, cep2) = tuple
                calculateDistance(cep1, cep2)
            }
    }

    private fun calculateDistance(cep1: Cep, cep2: Cep): Double {
        val lat1 = cep1.geoLocation?.lat?.toDouble() ?: 0.0
        val lon1 = cep1.geoLocation?.lng?.toDouble() ?: 0.0
        val lat2 = cep2.geoLocation?.lat?.toDouble() ?: 0.0
        val lon2 = cep2.geoLocation?.lng?.toDouble() ?: 0.0

        //form Haversine
        val R = 6371.0 // earth's average radius in km
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2) * sin(dLon / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        val d = R * c

        return d

    }
}