package com.rodtech.kotlinpoprojects.kotlinwebflux.controllers

import com.rodtech.kotlinpoprojects.kotlinwebflux.model.Cep
import com.rodtech.kotlinpoprojects.kotlinwebflux.services.CepService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/cep")
class CepController(
    private val cepService: CepService
) {

    @GetMapping("/{cep}")
    fun getCep(@PathVariable cep:String): Mono<Cep> {
        return cepService.getCep(cep)
    }

    @GetMapping("/cep/street/{street}")
    fun searchCepsByStreet(@PathVariable street: String): Flux<Cep> {
        return cepService.searchCepsByStreet(street)
    }

    @GetMapping("/distance")
    fun getCepsDistance(@RequestParam cep1: String, @RequestParam cep2: String): Mono<Double> {
        return cepService.getCepsDistance(cep1, cep2)
    }

}