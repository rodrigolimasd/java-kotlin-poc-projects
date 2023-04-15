package com.rodtech.kotlinpoprojects.kotlinwebflux.controllers

import com.rodtech.kotlinpoprojects.kotlinwebflux.model.Cep
import com.rodtech.kotlinpoprojects.kotlinwebflux.services.CepService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
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

}