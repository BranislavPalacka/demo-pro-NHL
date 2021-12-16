package com.example.demo;

import com.opencsv.exceptions.CsvException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) throws IOException, CsvException {

		ApplicationContext context =  SpringApplication.run(DemoApplication.class, args);

//		AppRun appRun = context.getBean(AppRun.class);
//		appRun.run();

//		StringToSQLDateExample appRun = context.getBean(StringToSQLDateExample.class);
//		appRun.parse();
	}


}