package com.Manisha.Ecom.model.dto;

import java.time.LocalDate;
import java.util.List;

public record orderResponse(
        String orderId,
        String customerName,
        String email,
        String status,
        LocalDate orderDate,
        List<OrderItemResponse> items
) {
}
