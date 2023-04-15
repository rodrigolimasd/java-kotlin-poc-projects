package com.rodtech.kotlinpoprojects.kotlinwebflux.services

import com.rodtech.kotlinpoprojects.kotlinwebflux.model.Cep
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class CepService(
        private val webClient: WebClient
) {
    fun getCep(cep: String) : Mono<Cep> {
        return webClient.get()
                .uri("$cep/json/")
                .retrieve()
                .bodyToMono(object : ParameterizedTypeReference<Cep>() {})
    }
}