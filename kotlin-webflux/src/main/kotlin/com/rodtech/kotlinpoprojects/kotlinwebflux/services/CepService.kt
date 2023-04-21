package com.rodtech.kotlinpoprojects.kotlinwebflux.services

import com.rodtech.kotlinpoprojects.kotlinwebflux.model.Cep
import com.rodtech.kotlinpoprojects.kotlinwebflux.model.GeoLocation
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

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
            .uri("https://viacep.com.br/ws/$_street/json/")
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
}