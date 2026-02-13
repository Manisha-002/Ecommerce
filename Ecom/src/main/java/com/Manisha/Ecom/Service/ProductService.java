package com.Manisha.Ecom.Service;

import com.Manisha.Ecom.model.Product;
import com.Manisha.Ecom.repo.Productrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private Productrepo productrepo;

    public List<Product> getAllProducts() {
        return productrepo.findAll();
    }

    public Product getProductById(int id) {
        return productrepo.findById(id).orElse(null);
    }

    public Product addProduct(Product product, MultipartFile image) throws IOException {
        product.setImageName(image.getOriginalFilename());
        product.setImageType(image.getContentType());
        product.setImageData(image.getBytes());
         return productrepo.save(product);
    }
}
