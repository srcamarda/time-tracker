package com.dev.tptimetracker;

import com.dev.tptimetracker.view.MenuPrincipal;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TpTimeTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TpTimeTrackerApplication.class, args);
		MenuPrincipal.mainMenu();
	}
}