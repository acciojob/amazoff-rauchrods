package com.driver;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("orders")
public class OrderController {

    @Autowired
    OrderService orderService = new OrderService();

    @PostMapping("/add-order")  //1
    public ResponseEntity<String> addOrder(@RequestBody Order order){

        String res= orderService.addOrder(order);

        return new ResponseEntity<>("New order added successfully", HttpStatus.CREATED);
    }


    @PostMapping("/add-partner/{partnerId}")  //2
    public ResponseEntity<String> addPartner(@PathVariable String partnerId){
        String res= orderService.addPartner(partnerId);
        return new ResponseEntity<>("New delivery partner added successfully", HttpStatus.CREATED);
    }



    @PutMapping("/add-order-partner-pair")  //3
    public ResponseEntity<String> addOrderPartnerPair(@RequestParam String orderId, @RequestParam String partnerId){

        String res= orderService.addOrderPartnerPair(orderId,partnerId);
        //This is basically assigning that order to that partnerId
        return new ResponseEntity<>("New order-partner pair added successfully", HttpStatus.CREATED);
    }



    @GetMapping("/get-order-by-id/{orderId}")  //4
    public ResponseEntity<Order> getOrderById(@PathVariable String orderId){

        Order order= null;
        //order should be returned with an orderId.
       order= orderService.getOrderById(orderId);

        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }



    @GetMapping("/get-partner-by-id/{partnerId}")  //5
    public ResponseEntity<DeliveryPartner> getPartnerById(@PathVariable String partnerId){

        DeliveryPartner deliveryPartner = null;

        //deliveryPartner should contain the value given by partnerId
        deliveryPartner = orderService.getPartnerById(partnerId);

        return new ResponseEntity<>(deliveryPartner, HttpStatus.CREATED);
    }



    @GetMapping("/get-order-count-by-partner-id/{partnerId}")  //6
    public ResponseEntity<Integer> getOrderCountByPartnerId(@PathVariable String partnerId){

        Integer orderCount = 0;
        //orderCount should denote the orders given by a partner-id

        orderCount= orderService.getOrderCountByPartnerId(partnerId);
        return new ResponseEntity<>(orderCount, HttpStatus.CREATED);
    }



    @GetMapping("/get-orders-by-partner-id/{partnerId}")  //7
    public ResponseEntity<List<String>> getOrdersByPartnerId(@PathVariable String partnerId){
        List<String> orders = null;

        //orders should contain a list of orders by PartnerId
          orders = orderService.getOrdersByPartnerId(partnerId);
        return new ResponseEntity<>(orders, HttpStatus.CREATED);
    }


    @GetMapping("/get-all-orders")  //8
    public ResponseEntity<List<String>> getAllOrders(){
        List<String> orders = null;
             orders =  orderService.getAllOrders();
        //Get all orders
        return new ResponseEntity<>(orders, HttpStatus.CREATED);
    }


    @GetMapping("/get-count-of-unassigned-orders")  //9
    public ResponseEntity<Integer> getCountOfUnassignedOrders(){
        Integer countOfOrders = 0;
        //Count of orders that have not been assigned to any DeliveryPartner
        countOfOrders = orderService.getCountOfUnassignedOrders();
        return new ResponseEntity<>(countOfOrders, HttpStatus.CREATED);
    }


    @GetMapping("/get-count-of-orders-left-after-given-time/{partnerId}") //10
    public ResponseEntity<Integer> getOrdersLeftAfterGivenTimeByPartnerId(@PathVariable String time, @PathVariable String partnerId){

        Integer countOfOrders = 0;
        //countOfOrders that are left after a particular time of a DeliveryPartner
        countOfOrders =  orderService.getOrdersLeftAfterGivenTimeByPartnerId(time,partnerId);
        return new ResponseEntity<>(countOfOrders, HttpStatus.CREATED);
    }


    @GetMapping("/get-last-delivery-time/{partnerId}")  //11
    public ResponseEntity<String> getLastDeliveryTimeByPartnerId(@PathVariable String partnerId){
        String time = null;

        //Return the time when that partnerId will deliver his last delivery order.

        return new ResponseEntity<>(time, HttpStatus.CREATED);
    }


    @DeleteMapping("/delete-partner-by-id/{partnerId}") //12
    public ResponseEntity<String> deletePartnerById(@PathVariable String partnerId){

        //Delete the partnerId
        //And push all his assigned orders to unassigned orders.
          String res = orderService.deletePartnerById(partnerId);
        return new ResponseEntity<>(partnerId + " removed successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-order-by-id/{orderId}") //13
    public ResponseEntity<String> deleteOrderById(@PathVariable String orderId){

        //Delete an order and also
        // remove it from the assigned order of that partnerId
        String str = orderService.deleteOrderById(orderId);
        return new ResponseEntity<>(orderId + " removed successfully", HttpStatus.CREATED);
    }
}
