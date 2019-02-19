package com.wallmart.tiket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
/***
 *  Application start point. Not expecting any input parameter as command line  
 * @author Anish Jose
 * @since 2/11/2018
 */
@SpringBootApplication
@EnableScheduling
public class TiketApplication {

	public static void main(String[] args) {
		SpringApplication.run(TiketApplication.class, args);
	}

}

