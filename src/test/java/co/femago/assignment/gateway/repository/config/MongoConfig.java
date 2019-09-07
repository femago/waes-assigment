package co.femago.assignment.gateway.repository.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableAutoConfiguration // create MongoTemplate and MongoOperations
@EnableMongoRepositories(basePackages = "co.femago.assignment.gateway.repository") // Create your repos
@ComponentScan(basePackages = "co.femago.assignment.gateway.repository")
public class MongoConfig {

}
