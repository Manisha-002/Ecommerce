package com.Manisha.Ecom.Controller;

import com.Manisha.Ecom.Service.ProductService;
import com.Manisha.Ecom.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(){

        return new ResponseEntity(productService.getAllProducts(), HttpStatus.FOUND);
    }
    @GetMapping("/product/{id}")
    public ResponseEntity<Product>getProductById(@PathVariable int id){
        Product product=productService.getProductById(id);
    }
}
