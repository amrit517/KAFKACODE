package com.kafka.orderservice.service;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.kafka.baseDomain.dto.OrderEvent;

@Service
public class OrderProducerService {

	private static final Logger log = LoggerFactory.getLogger(OrderProducerService.class);

	private NewTopic newTopic;

	private KafkaTemplate<String, OrderEvent> kafkaTemplate;

	public OrderProducerService(NewTopic newTopic, KafkaTemplate<String, OrderEvent> kafkaTemplate) {
		super();
		this.newTopic = newTopic;
		this.kafkaTemplate = kafkaTemplate;
	}
	
	

	public OrderProducerService() {
		super();
	}


	public void sendMessage(OrderEvent event) {
		log.info(String.format("Order Event -> %s", event.toString()));

		// creating a message
		Message<OrderEvent> message = MessageBuilder.withPayload(event).setHeader(KafkaHeaders.TOPIC, newTopic.name())
				.build();

		kafkaTemplate.send(message);

	}

}
