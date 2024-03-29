package com.rodtech.kotlinpoprojects.kotlinwebflux.controllers

import com.rodtech.kotlinpoprojects.kotlinwebflux.model.Cep
import com.rodtech.kotlinpoprojects.kotlinwebflux.services.CepService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

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

    @GetMapping("/{cep}/nearest")
    fun getCepsProximos(@PathVariable("cep") cep: String, @RequestParam("maxDistance") maxDistance: Double): Flux<Cep> {
        return cepService.getCep(cep)
            .flatMapMany { cep ->
                cepService.searchCepsByState(cep.uf)
                    .filter { it.cep != cep.cep }
                    .filter { cepService.calculateDistance(it, cep) <= maxDistance }
                    .switchIfEmpty(Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found")))
            }
            .switchIfEmpty(Mono.error(ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found")))
    }

    @GetMapping("/cep/merged/{cep1}/{cep2}")
    fun getMergedCepData(@PathVariable cep1: String, @PathVariable cep2: String): Flux<Cep> {
        return cepService.mergeCepData(listOf(cep1, cep2))
    }

    @GetMapping("/ceps/coordinates")
    fun getCepsCoordinates(@RequestParam("ceps") ceps: List<String>): Flux<Pair<Double, Double>> {
        return cepService.getCepsCoordinates(ceps)
    }

    @GetMapping("/ceps/location/{latitude}/{longitude}/{radius}")
    fun searchCepsByLocation(
        @PathVariable latitude: Double,
        @PathVariable longitude: Double,
        @PathVariable radius: Double
    ): Flux<Cep> {
        return cepService.searchCepsByLocation(latitude, longitude, radius)
    }

}