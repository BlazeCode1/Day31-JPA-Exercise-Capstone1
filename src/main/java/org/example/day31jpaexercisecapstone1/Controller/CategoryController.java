package org.example.day31jpaexercisecapstone1.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.day31jpaexercisecapstone1.Api.ApiResponse;
import org.example.day31jpaexercisecapstone1.Model.Category;
import org.example.day31jpaexercisecapstone1.Service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/get")
    public ResponseEntity<?> getCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@Valid @RequestBody Category category, Errors err){
        if(err.hasErrors()){
            return ResponseEntity.badRequest().body(err.getFieldError().getDefaultMessage());
        }

        categoryService.addCategory(category);
            return ResponseEntity.ok(new ApiResponse("Category Added Successfully"));

    }

    @PutMapping("/update/{ID}")
    public ResponseEntity<?> updateCategory(@PathVariable Integer ID,@Valid @RequestBody Category category,Errors err){
        if(err.hasErrors()){
            return ResponseEntity.badRequest().body(err.getFieldError().getDefaultMessage());
        }
        if(categoryService.updateCategory(ID,category)){
            return ResponseEntity.ok(new ApiResponse("Category Updated Successfully"));
        }
        return ResponseEntity.badRequest().body(new ApiResponse("Category ID Not Found"));
    }

    @DeleteMapping("/delete/{ID}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer ID){
        if(categoryService.deleteCategory(ID)){
            return ResponseEntity.ok(new ApiResponse("Category Deleted Successfully"));
        }
        return ResponseEntity.badRequest().body(new ApiResponse("Category With Given ID not Found"));
    }


}
