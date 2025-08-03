package org.example.day31jpaexercisecapstone1.Service;

import lombok.RequiredArgsConstructor;
import org.example.day31jpaexercisecapstone1.Model.Category;
import org.example.day31jpaexercisecapstone1.Model.Product;
import org.example.day31jpaexercisecapstone1.Model.User;
import org.example.day31jpaexercisecapstone1.Repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final CategoryService categoryService;
    private final UserService userService;
    private final ProductRepository productRepository;


    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public int addProduct(Product product){
        if(categoryService.getAllCategories().isEmpty()){
            return 1;
        }
//        for (Product p : products){
//            if(product.getID().equals(p.getID())){
//                return 0;
//            }
//        }
        Product foundProduct = productRepository.getById(product.getID());
        if(foundProduct.equals(product)){
            return 0;
        }

//        for (Category c : categoryService.getAllCategories()){
//            if(product.getCategoryID().equals(c.getID())){
//                products.add(product);
//                return 3;
//            }
//        }
        Category foundCategory = categoryService.findByID(foundProduct.getCategory_id());
        if(foundCategory == null){
        return 2;
        }
        productRepository.save(product);
       return 3;
    }

    public int updateProduct(Product product){


        if(categoryService.getAllCategories().isEmpty()){
            return 0; // category list empty
        }
        Category category = categoryService.findByID(product.getCategory_id());

        if(category == null){
            return 1; // if category not found
        }

        Product p = productRepository.getById(product.getID());
        if(p == null){
            return 2;
        }
        p.setCategory_id(product.getCategory_id());
        p.setProduct_code(product.getProduct_code());
        p.setName(product.getName());
        p.setPrice(product.getPrice());
        return 3;

    }

    public boolean deleteProduct(Integer productID){
        Product product = productRepository.getById(productID);
        if(product== null){
            return false;
        }
        productRepository.delete(product);
        return true;
    }

    //Second Method
    public List<Product> topProductsPurchased(String start, String end,int userLimit){
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        ArrayList<Product> productsWithinRange = new ArrayList<>();

        for (Product p : productRepository.findAll()){
            if(p.getLast_sold_date() != null &&
                    (p.getLast_sold_date().isEqual(startDate) || p.getLast_sold_date().isAfter(startDate)) &&
                    (p.getLast_sold_date().isEqual(endDate) || p.getLast_sold_date().isBefore(endDate))){
                productsWithinRange.add(p);
            }
        }
        //sorting in descending order to get last 5
        productsWithinRange.sort((p1,p2) -> Integer.compare(p2.getTotal_sold(),p1.getTotal_sold()));

        int limit = Math.min(userLimit,productsWithinRange.size());
        //then sub listing to see last Five products
        return productsWithinRange.subList(0,limit);
    }

    public Product findById(Integer ID){
        Product foundProduct = productRepository.getById(ID);
        return foundProduct;
    }




}
