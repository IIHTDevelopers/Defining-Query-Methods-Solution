package com.yaksha.assignment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yaksha.assignment.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	// Custom query method to find products by name
	List<Product> findByName(String name);

	// Custom query method to find products by price range
	List<Product> findByPriceBetween(Double minPrice, Double maxPrice);
}
