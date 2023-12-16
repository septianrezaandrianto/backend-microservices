package com.service.becatalog.controller;

import com.service.becatalog.dto.CategoryRequest;
import com.service.becatalog.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping(value = "/create")
    public ResponseEntity<Object> create(@Valid @RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.ok(categoryService.create(categoryRequest));
    }

    @GetMapping(value = "/getCategoryList")
    public ResponseEntity<Object> getCategoryList() {
        return ResponseEntity.ok(categoryService.getCategoryList());
    }

    @GetMapping(value = "/getById")
    public ResponseEntity<Object> getById(@RequestParam(value = "id", defaultValue = "")String id) {
        return ResponseEntity.ok(categoryService.getById(id));
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Object> update(@PathVariable String id, @Valid @RequestBody CategoryRequest categoryRequest) {
        return ResponseEntity.ok(categoryService.update(id, categoryRequest));
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<Object> delete(@RequestParam(value = "id", defaultValue = "")String id,
                                         @RequestParam(value = "username", defaultValue = "")String username) {
        return ResponseEntity.ok(categoryService.delete(id, username));
    }

}
