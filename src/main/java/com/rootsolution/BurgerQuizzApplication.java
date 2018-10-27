package com.rootsolution;

import com.rootsolution.burgerquizz.display.UiKt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;

@SpringBootApplication
public class BurgerQuizzApplication {

	private static Logger logger = LoggerFactory.getLogger(BurgerQuizzApplication.class);

	public static void main(String[] args) {

		if(args.length > 0 ){
			File videoFolder = new File( args[0].replace("\"",""));
			if(videoFolder.isDirectory()){
				ConfigurableApplicationContext context = new SpringApplicationBuilder(BurgerQuizzApplication.class).headless(false).run(args);
				UiKt.display(videoFolder.getPath());
			}else{
				logger.error("Error with burgerquizz videos path");
			}
		}else{
			logger.error("Please pass the burgerquizz videos path");
		}

	}
}
