package com.anselmojr.desafioanotaai.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anselmojr.desafioanotaai.domain.category.exceptions.CategoryNotFoundException;
import com.anselmojr.desafioanotaai.domain.product.Product;
import com.anselmojr.desafioanotaai.domain.product.ProductDTO;
import com.anselmojr.desafioanotaai.domain.product.exceptions.ProductNotFoundException;
import com.anselmojr.desafioanotaai.repositories.ProductRepository;
import com.anselmojr.desafioanotaai.service.aws.MessageDTO;

@Service
public class ProductService {

	private CategoryService categoryService;
	
	private ProductRepository productRepository;
	
	private AwsSnsService snsService;
	
	public ProductService(ProductRepository productRepository, CategoryService categoryService, AwsSnsService snsService) {
		this.categoryService = categoryService;
		this.productRepository = productRepository;
		this.snsService = snsService;
	}
	
	public Product create(ProductDTO productData) {
		this.categoryService.getById(productData.categoryId()).orElseThrow(CategoryNotFoundException::new);
		Product newProduct = new Product(productData);

		this.productRepository.save(newProduct);
		this.snsService.publish(new MessageDTO(newProduct.getOwnerId()));

		return newProduct;
	}
	
	public List<Product> getAll() {
		return this.productRepository.findAll();
	}
	
	public Product update(String id, ProductDTO productData) {
		Product product = this.productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
		if(productData.categoryId() != null) {
			this.categoryService.getById(productData.categoryId()).orElseThrow(CategoryNotFoundException::new);
			product.setCategoryId(productData.categoryId());
		}
		if(!productData.title().isEmpty()) product.setTitle(productData.title());
		if(!productData.description().isEmpty()) product.setDescription(productData.description()); 
		if(!(productData.price() == null)) product.setPrice(productData.price()); 

		this.productRepository.save(product);
		this.snsService.publish(new MessageDTO(product.toString()));
		return product;
	}
	public void delete(String id) {
		Product product = this.productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
		this.productRepository.delete(product);
	}
}
