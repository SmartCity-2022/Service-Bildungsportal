package com.smartcity.education.backend

import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Declarables
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class BackendApplication {

	@Bean
	fun topicBinding(): Declarables {
		val exchange = TopicExchange(Constants.Exchanges.education, false, false)

		val createdQueue = Queue("", true, true, false)

		return Declarables(
			exchange,
			createdQueue,
			BindingBuilder
				.bind(createdQueue)
				.to(exchange)
				.with(Constants.RoutingKeys.created)
		)
	}
}

fun main(args: Array<String>) {
	runApplication<BackendApplication>(*args)
}
