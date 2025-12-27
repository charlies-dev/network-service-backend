package com.infy.user;

// import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserServiceApplication {

	public static void main(String[] args) {

		// String[] directories = {
		// 		"config",
		// 		"controller",
		// 		"dto",
		// 		"enum",
		// 		"exception",
		// 		"entity",
		// 		"repository",
		// 		"request",
		// 		"response",
		// 		"security/config",
		// 		"security/entrypoint",
		// 		"security/filter",
		// 		"security/model",
		// 		"security/service",
		// 		"security/util",
		// 		"service",
		// 		"util",
		// };

		// // Create each directory
		// for (String dir : directories) {
		// 	File directory = new File("dir/"+dir);
		// 	if (directory.mkdirs()) {
		// 		System.out.println("Created: " + dir);
		// 	} else {
		// 		System.out.println("Failed to create or already exists: " + dir);
		// 	}
		// }

		// System.out.println("Directory structure creation complete!");

		SpringApplication.run(UserServiceApplication.class, args);
	}
	

}
