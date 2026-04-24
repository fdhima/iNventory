package com.tutorial.inventory.orders;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/orders")
public class OrdersController {
    private final OrdersRepository ordersRepo;

    public OrdersController(OrdersRepository ordersRepo) {
        this.ordersRepo = ordersRepo;
    }

    @GetMapping
    public List<Orders> getAllOrders() {
        return ordersRepo.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Orders> getOrderById(@PathVariable UUID id) {
        return ordersRepo.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Orders createOrder(@RequestBody Orders order) {
        return ordersRepo.save(order);
    }

    @PutMapping("/{id}")
    public Orders updateOrder(@PathVariable UUID id, @RequestBody Orders updatedOrder) {
        return ordersRepo.findById(id)
        .map(order -> {
            order.setProduct(updatedOrder.getProduct());
            order.setQuantity(updatedOrder.getQuantity());
            order.setLocation(updatedOrder.getLocation());
            order.setExpectedArrivalAt(updatedOrder.getExpectedArrivalAt());
            return ordersRepo.save(order);
        })
        .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable UUID id) {
        ordersRepo.deleteById(id);
    }
}
