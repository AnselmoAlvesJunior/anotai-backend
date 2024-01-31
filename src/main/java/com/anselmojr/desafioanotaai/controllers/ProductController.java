package com.anselmojr.desafioanotaai.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anselmojr.desafioanotaai.domain.product.Product;
import com.anselmojr.desafioanotaai.domain.product.ProductDTO;
import com.anselmojr.desafioanotaai.service.ProductService;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api/product")
public class ProductController {
	private ProductService service;
	public ProductController(ProductService service) {
		this.service =service;
	}
	
	@PostMapping
	public ResponseEntity<Product> create(@RequestBody ProductDTO productData){
		Product newProduct = this.service.create(productData);
		return ResponseEntity.ok().body(newProduct);
		
	}
	
	@GetMapping
	public ResponseEntity<List<Product>> getAllProducts(){
		List<Product> products = this.service.getAll();
		return ResponseEntity.ok().body(products);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Product> update(@PathVariable("id") String id, @RequestBody ProductDTO productData){
		Product updatedProduct = this.service.update(id,productData);
		return ResponseEntity.ok().body(updatedProduct);
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Product> update(@PathVariable("id") String id){
		this.service.delete(id);
		return ResponseEntity.noContent().build();
		
	}
}
