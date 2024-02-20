package com.finexti.pocdynamodb.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.finexti.pocdynamodb.domain.ProductEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final DynamoDBMapper dynamoDBMapper;

    public ProductService(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public List<ProductEntity> getAllProducts() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        List<ProductEntity> products = dynamoDBMapper.scan(ProductEntity.class, scanExpression);
        return products;
    }

    public ProductEntity getProductById(String id) {
        ProductEntity product = dynamoDBMapper.load(ProductEntity.class, id);
        return product;
    }

    public ProductEntity createNewProduct(ProductEntity entity) {
        dynamoDBMapper.save(entity);
        return entity;
    }

}
