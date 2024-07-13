package com.kafka.orderservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kafka.baseDomain.dto.Order;
import com.kafka.baseDomain.dto.OrderEvent;
import com.kafka.orderservice.service.OrderProducerService;

@RestController
@RequestMapping("/kafka")
public class OrderController {

	@Autowired
	private OrderProducerService orderProducerService;

	public void setOrderProducerService(OrderProducerService orderProducerService) {
		this.orderProducerService = orderProducerService;
	}

	@PostMapping("/orderPublish")
	public String placeOrder(@RequestBody Order order) {

		OrderEvent event = OrderEvent.builder()
				.order(order).status("Pending").message("Order is in pending Stage")
				.build();

		orderProducerService.sendMessage(event);

		return "Order Placed Successfully";

	}

}
