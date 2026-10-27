package biz.thehacker.demo.earsystem;

import biz.thehacker.demo.earsystem.config.EarsystemConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties(EarsystemConfigProperties.class)
@EnableScheduling
public class EarSystemDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EarSystemDemoApplication.class, args);
	}
}
