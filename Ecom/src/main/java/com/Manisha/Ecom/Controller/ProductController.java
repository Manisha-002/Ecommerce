package com.Manisha.Ecom.Controller;

import com.Manisha.Ecom.Service.ProductService;
import com.Manisha.Ecom.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        Product product = productService.getProductById(id);

        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/product/{ProductId}/image")
    public ResponseEntity<byte[]> getImageByProduct(@PathVariable int ProductId){
        Product productImage= productService.getProductById(ProductId);
        if (productImage != null) {
            return ResponseEntity.ok(productImage.getImageData());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/product")
    public ResponseEntity<Product> addProduct(@RequestPart Product product , @RequestPart MultipartFile imageFile){
        Product savedproduct= null;
        try {
            savedproduct = productService.addProduct(product,imageFile);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedproduct);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

        }


    }
}
