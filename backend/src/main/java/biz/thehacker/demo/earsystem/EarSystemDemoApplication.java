package biz.thehacker.demo.earsystem;

import biz.thehacker.demo.earsystem.config.EarsystemConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(EarsystemConfigProperties.class)
public class EarSystemDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EarSystemDemoApplication.class, args);
	}
}
