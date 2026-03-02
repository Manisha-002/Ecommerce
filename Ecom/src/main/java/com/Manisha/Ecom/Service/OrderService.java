
package com.Manisha.Ecom.Service;

import com.Manisha.Ecom.model.*;
import com.Manisha.Ecom.model.dto.*;
import com.Manisha.Ecom.repo.OrderRepo;
import com.Manisha.Ecom.repo.Productrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class OrderService {

    @Autowired
    private Productrepo productrepo;

    @Autowired
    private OrderRepo orderRepo;

    // ================= PLACE ORDER =================
    public OrderResponse placeOrder(OrderRequest request) {

        Order order = new Order();
        order.setOrderId("ORD-" + UUID.randomUUID().toString().substring(0,8).toUpperCase());
        order.setCustomerName(request.customerName());
        order.setEmail(request.email());
        order.setStatus("PLACED");
        order.setLocalDate(LocalDate.now());

        List<OrderItem> items = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemRequest req : request.items()) {

            Product product = productrepo.findById(req.productId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getStockQuantity() < req.quantity())
                throw new RuntimeException("Not enough stock");

            product.setStockQuantity(product.getStockQuantity() - req.quantity());
            productrepo.save(product);

            BigDecimal itemTotal =
                    product.getPrice().multiply(BigDecimal.valueOf(req.quantity()));

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(req.quantity());
            item.setTotalPrice(itemTotal);
            item.setOrder(order);

            items.add(item);
            totalAmount = totalAmount.add(itemTotal);
        }

        order.setOrderItems(items);
        order.setTotalAmount(totalAmount);

        Order saved = orderRepo.save(order);

        List<OrderItemResponse> responseItems = new ArrayList<>();
        for (OrderItem i : saved.getOrderItems()) {
            responseItems.add(
                    new OrderItemResponse(
                            i.getProduct().getName(),
                            i.getQuantity(),
                            i.getTotalPrice()
                    )
            );
        }

        return new OrderResponse(
                saved.getOrderId(),
                saved.getCustomerName(),
                saved.getEmail(),
                saved.getStatus(),
                saved.getLocalDate(),
                saved.getTotalAmount(),
                responseItems
        );
    }

    // ================= GET ALL ORDERS =================
    public List<OrderResponse> getAllOrder() {

        List<OrderResponse> list = new ArrayList<>();

        for (Order order : orderRepo.findAll()) {

            List<OrderItemResponse> items = new ArrayList<>();

            for (OrderItem i : order.getOrderItems()) {
                items.add(new OrderItemResponse(
                        i.getProduct().getName(),
                        i.getQuantity(),
                        i.getTotalPrice()
                ));
            }

            list.add(new OrderResponse(
                    order.getOrderId(),
                    order.getCustomerName(),
                    order.getEmail(),
                    order.getStatus(),
                    order.getLocalDate(),
                    order.getTotalAmount(),
                    items
            ));
        }

        return list;
    }
}