package com.yaksha.assignment.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.yaksha.assignment.entity.Product;

public class MasterData {

	// Create a sample Product instance
	public static Product getProduct() {
		Product product = new Product("Product A", "A sample product", 100.0);
		product.setId(1L); // Setting the ID for the product
		return product;
	}

	// Create a list of products
	public static List<Product> getProductList() {
		List<Product> productList = new ArrayList<>();
		productList.add(getProduct()); // Adding a sample product
		return productList;
	}

	// Create a page of products
	public static Page<Product> getProductPage(int page, int size) {
		List<Product> productList = getProductList(); // Get list of products
		PageRequest pageRequest = PageRequest.of(page, size); // Set pagination
		return new PageImpl<>(productList, pageRequest, productList.size()); // Return page
	}

	// Convert an object to a JSON string
	public static String asJsonString(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule()); // Register the JavaTimeModule
			mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Disable writing dates as timestamps
			final String jsonContent = mapper.writeValueAsString(obj); // Convert object to JSON string
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e); // Handle exceptions during JSON conversion
		}
	}

	// Generate a random string of the given size (not used directly in this class)
	public static String randomStringWithSize(int size) {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < size; i++) {
			s.append("A");
		}
		return s.toString(); // Return the generated string
	}
}
