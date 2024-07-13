package com.kafka.stockservice.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.kafka.baseDomain.dto.OrderEvent;

@Service
public class OrderConsumerService {

	private static final Logger log = LoggerFactory.getLogger(OrderConsumerService.class);

	@KafkaListener(topics = "${spring.kafka.topic.name}", groupId = "${spring.kafka.consumer.group-id}")
	public void consume(OrderEvent event) {

		log.info(String.format("Order Received is %s", event.toString()));

		// save the order into DataBase

	}

}
