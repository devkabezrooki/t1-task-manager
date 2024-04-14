package com.example.t1taskmanager;

import com.example.t1taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class T1TaskManagerApplication {

	@Autowired
	TaskService taskService;

	public static void main(String[] args) {
		SpringApplication.run(T1TaskManagerApplication.class, args);
	}

}
