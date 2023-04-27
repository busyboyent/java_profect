package com.dev.javadevproject.resources;

import com.dev.javadevproject.dto.orders.CancelOrderRequest;
import com.dev.javadevproject.dto.orders.ChangeOrderStatusRequest;
import com.dev.javadevproject.dto.orders.CreateOrderRequest;
import com.dev.javadevproject.entities.OrderEntity;
import com.dev.javadevproject.entities.OrderStatus;
import com.dev.javadevproject.entities.ProductEntity;
import com.dev.javadevproject.entities.UserEntity;
import com.dev.javadevproject.repositories.GoodsRepo;
import com.dev.javadevproject.repositories.OrdersRepo;
import com.dev.javadevproject.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/orders")
public class OrdersResource {
    @Autowired
    UserServiceImpl userService;

    @Autowired
    OrdersRepo ordersRepo;

    @Autowired
    GoodsRepo goodsRepo;

    @GetMapping(value="", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getOrders() throws IOException {
        UserEntity currentUser = userService.getCurrentUser();
        List<OrderEntity> orders = ordersRepo.findByUserId(currentUser.Id);
        return ResponseEntity.ok(orders);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest request) throws IOException {
        HashMap<String, String> response = new HashMap<>();
        UserEntity currentUser = userService.getCurrentUser();
        List<ProductEntity> result = new ArrayList<>();
        if(request.goodsId == null) request.goodsId = new ArrayList<>();
        for(Integer id : request.goodsId){
            var product = goodsRepo.findById(id);
            if(product.isEmpty()) {
                response.put("status", "error");
                response.put("message", "Продукт с таким id не найден");
                return ResponseEntity.ok(response);
            }
            product.ifPresent(result::add);
        }
        var order = new OrderEntity();
        order.setStatus(OrderStatus.InProgress);
        order.goods = result;
        order.userId = currentUser.Id;
        if(currentUser.getAddresses().isEmpty()) {
            response.put("status", "error");
            response.put("message", "У Вас не указан адрес доставки");
            return ResponseEntity.ok(response);
        }
        order.toAddress = currentUser.getAddresses().get(0);
        order.creationDateTime = new Date();
        ordersRepo.save(order);
        response.put("status", "ok");
        response.put("message", "Заказ создан");
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/cancel", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> cancelOrder(@RequestBody CancelOrderRequest cancelOrderRequest){
        HashMap<String, String> response = new HashMap<>();
        Optional<OrderEntity> orderMaybe = ordersRepo.findById(cancelOrderRequest.orderId);
        if(!orderMaybe.isPresent()) {
            response.put("status", "error");
            response.put("message", "Не найден заказ с таким id");
            return ResponseEntity.ok(response);
        }
        OrderEntity order = orderMaybe.get();
        order.setStatus(OrderStatus.Cancelled);
        ordersRepo.save(order);
        response.put("message", "Заказ отменен");
        return ResponseEntity.ok(response);
    }

    @PatchMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeStatus(@RequestBody ChangeOrderStatusRequest request){
        HashMap<String, String> response = new HashMap<>();
        if(UserServiceImpl.getCurrentUser().getRoles().stream().noneMatch(o -> o.getName().equals("ROLE_ADMIN"))){
            response.put("status", "error");
            response.put("message", "Не хватает прав на изменение статуса");
            return ResponseEntity.ok(response);
        }
        Optional<OrderEntity> orderMaybe = ordersRepo.findById(request.orderId);
        if(!orderMaybe.isPresent()) {
            response.put("status", "error");
            response.put("message", "Не найден заказ с таким id");
            return ResponseEntity.ok(response);
        }
        OrderEntity order = orderMaybe.get();
        order.setStatus(request.newStatus);
        ordersRepo.save(order);
        response.put("message", "Статус изменен");
        return ResponseEntity.ok(response);
    }
}
