package com.dtt.organization;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@SpringBootApplication
public class OrganizationServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrganizationServicesApplication.class, args);
	}


	@Value("${app.redis.hosts}")
	private String redisHosts;

	@Value("${app.redis.password}")
	private String redisPassword;

	@Value("${app.redis.username}")
	private String redisUsername;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}



	@Bean
	public RedisConnectionFactory redisConnectionFactory() {

		if (redisHosts == null || redisHosts.isBlank()) {
			throw new IllegalStateException("redis.hosts must be provided");
		}

		String[] hosts = redisHosts.split(",");

		if (hosts.length == 1) {
			String[] hostPort = hosts[0].trim().split(":");
			String host = hostPort[0];
			int port = Integer.parseInt(hostPort[1]);

			RedisStandaloneConfiguration config =
					new RedisStandaloneConfiguration(host, port);

			if (redisUsername != null && !redisUsername.isBlank()) {
				config.setUsername(redisUsername);
			}

			if (redisPassword != null && !redisPassword.isBlank()) {
				config.setPassword(RedisPassword.of(redisPassword));
			}

			return new LettuceConnectionFactory(config);
		}

		RedisClusterConfiguration clusterConfig =
				new RedisClusterConfiguration(
						Arrays.stream(hosts)
								.map(String::trim)
								.toList()
				);


			clusterConfig.setUsername(redisUsername);


		clusterConfig.setPassword(RedisPassword.of(redisPassword));

		return new LettuceConnectionFactory(clusterConfig);
	}


	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(factory);
		template.setKeySerializer(template.getStringSerializer());
		return template;
	}
}
