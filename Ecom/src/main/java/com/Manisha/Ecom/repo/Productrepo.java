package com.Manisha.Ecom.repo;

import com.Manisha.Ecom.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Productrepo extends JpaRepository<Product, Integer> {
}
