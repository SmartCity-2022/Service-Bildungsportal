package com.smartcity.education.backend

import com.smartcity.education.backend.authentication.TokenVerifier
import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableRabbit
class RabbitMqConfig {
    @Autowired
    private val verifier: TokenVerifier? = null

    @Bean
    fun worldQueue(): Queue {
        return AnonymousQueue()
    }

    @Bean
    fun topicBinding(worldQueue: Queue): Declarables {
        val exchange = TopicExchange(Constants.exchange, false, false)

        val createdQueue = AnonymousQueue()
        val helloQueue = AnonymousQueue()

        return Declarables(
                exchange,
                createdQueue,
                helloQueue,
                worldQueue,
                BindingBuilder
                        .bind(createdQueue)
                        .to(exchange)
                        .with(Constants.RoutingKeys.created),
                BindingBuilder
                        .bind(helloQueue)
                        .to(exchange)
                        .with(Constants.RoutingKeys.hello),
                BindingBuilder
                        .bind(worldQueue)
                        .to(exchange)
                        .with(Constants.RoutingKeys.world)
        )
    }

    @Bean
    fun sendHello(template: RabbitTemplate): ApplicationRunner {
        return ApplicationRunner {
            template.convertAndSend(
                    Constants.exchange,
                    Constants.RoutingKeys.hello,
                    "Bildungsportal"
            )
        }
    }

    @RabbitListener(queues = ["#{worldQueue.name}"])
    fun listenWorld(message: String) {
        verifier?.setSecret(message)
    }
}