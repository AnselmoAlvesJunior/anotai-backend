package com.anselmojr.desafioanotaai.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.anselmojr.desafioanotaai.domain.category.Category;
import com.anselmojr.desafioanotaai.domain.category.CategoryDTO;
import com.anselmojr.desafioanotaai.domain.category.exceptions.CategoryNotFoundException;
import com.anselmojr.desafioanotaai.repositories.CategoryRepository;
import com.anselmojr.desafioanotaai.service.aws.MessageDTO;
@Service
public class CategoryService {

    private CategoryRepository repository;
	
    private AwsSnsService snsService;

    public CategoryService(CategoryRepository repository, AwsSnsService snsService){
        this.repository = repository;
        this.snsService = snsService;
    }

    public Category create(CategoryDTO categoryData){
        Category newCategory = new Category(categoryData);
        this.repository.save(newCategory);
		this.snsService.publish(new MessageDTO(newCategory.toString()));

        return newCategory;
    }

    public Category update(String id, CategoryDTO categoryData){
        Category category = this.repository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);

        if(!categoryData.title().isEmpty()) category.setTitle(categoryData.title());
        if(!categoryData.description().isEmpty()) category.setDescription(categoryData.description());

        this.repository.save(category);
		this.snsService.publish(new MessageDTO(category.toString()));

        return category;
    }

    public void delete(String id){
        Category category = this.repository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);

        this.repository.delete(category);
    }

    public List<Category> getAll(){
        return this.repository.findAll();
    }

    public Optional<Category> getById(String id){
        return this.repository.findById(id);
    }
}
