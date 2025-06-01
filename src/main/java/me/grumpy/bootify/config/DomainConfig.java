package me.grumpy.bootify.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("me.grumpy.bootify")
@EnableJpaRepositories("me.grumpy.bootify")
@EnableTransactionManagement
public class DomainConfig {
}
