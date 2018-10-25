package com.rootsolution;

import com.rootsolution.display.UiKt;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BurgerQuizzApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = new SpringApplicationBuilder(BurgerQuizzApplication.class).headless(false).run(args);
		UiKt.display();

	}
}
