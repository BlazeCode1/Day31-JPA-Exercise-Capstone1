package org.example.day31jpaexercisecapstone1.Service;

import lombok.RequiredArgsConstructor;
import org.example.day31jpaexercisecapstone1.Model.Category;
import org.example.day31jpaexercisecapstone1.Repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public void addCategory(Category category){
       categoryRepository.save(category);
    }


    public Boolean updateCategory(Integer ID,Category category){
        Category oldCategory = categoryRepository.getById(ID);
        if(oldCategory==null){
            return false;
        }
        oldCategory.setName(category.getName());
        categoryRepository.save(oldCategory);
        return true;
    }

    public Boolean deleteCategory(Integer ID){
        Category category = categoryRepository.getById(ID);
        if(category == null){
            return false;
        }
        categoryRepository.delete(category);
        return true;
    }

    public Category findByID(Integer ID){
        Category category = categoryRepository.getById(ID);
        if(category == null)
            return null;
        return category;
    }




}
