package com.finexti.pocdynamodb.controller;

import com.finexti.pocdynamodb.domain.ProductEntity;
import com.finexti.pocdynamodb.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductEntity> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductEntity getProductById(@PathVariable String id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public ProductEntity createProduct(@RequestBody ProductEntity productDTO) {
        return productService.createNewProduct(productDTO);
    }
}
