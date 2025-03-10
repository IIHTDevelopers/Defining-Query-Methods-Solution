package com.yaksha.assignment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yaksha.assignment.entity.Product;
import com.yaksha.assignment.repository.ProductRepository;

@RestController
public class ProductController {

	@Autowired
	private ProductRepository productRepository;

	// Create a product (POST)
	@PostMapping
	public Product createProduct(@RequestBody Product product) {
		return productRepository.save(product);
	}

	// Get products by name
	@GetMapping("/api/products/search/findByName")
	public List<Product> findByName(@RequestParam String name) {
		return productRepository.findByName(name);
	}

	// Get products by price range
	@GetMapping("/api/products/search/findByPriceBetween")
	public List<Product> findByPriceBetween(@RequestParam Double minPrice, @RequestParam Double maxPrice) {
		return productRepository.findByPriceBetween(minPrice, maxPrice);
	}
}
