package com.anselmojr.desafioanotaai.domain.product;

import org.json.JSONObject;
import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product {
	@Id
	private String id;
	private String title;
	private String description;
	private String ownerId;
	private Integer price;
	private String categoryId;
	
	public Product(ProductDTO data) {
		this.title = data.title();
		this.description = data.description();
		this.ownerId = data.ownerId();
		this.price = data.price();
		this.categoryId = data.categoryId();
	}
	
	@Override
	public String toString() {
		JSONObject json = new JSONObject();
		json.put("title", this.title);
		json.put("description", this.description);
		json.put("ownerId", this.ownerId);
		json.put("price", this.price);
		json.put("categoryId", this.categoryId);
		json.put("type", "products");
		return json.toString();
	}

}
