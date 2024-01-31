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

import com.anselmojr.desafioanotaai.domain.category.Category;
import com.anselmojr.desafioanotaai.domain.category.CategoryDTO;
import com.anselmojr.desafioanotaai.service.CategoryService;


@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private CategoryService service;

    public CategoryController(CategoryService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Category> create(@RequestBody CategoryDTO categoryData){
        Category newCategory = this.service.create(categoryData);
        return ResponseEntity.ok().body(newCategory);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAll(){
        List<Category> categories = this.service.getAll();
        return ResponseEntity.ok().body(categories);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable("id") String id, @RequestBody CategoryDTO categoryData){
        Category updatedCategory = this.service.update(id, categoryData);
        return ResponseEntity.ok().body(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Category> delete(@PathVariable("id") String id){
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }
}