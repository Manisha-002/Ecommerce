
package com.Manisha.Ecom.Controller;

import com.Manisha.Ecom.Service.OrderService;
import com.Manisha.Ecom.model.dto.OrderRequest;
import com.Manisha.Ecom.model.dto.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest req){
        return new ResponseEntity<>(orderService.placeOrder(req), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAll(){
        return ResponseEntity.ok(orderService.getAllOrder());
    }
}