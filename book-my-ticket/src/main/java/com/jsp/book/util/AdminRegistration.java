package com.jsp.book.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.jsp.book.entity.User;
import com.jsp.book.entity.Movie;
import com.jsp.book.repository.UserRepository;
import com.jsp.book.repository.MovieRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminRegistration implements CommandLineRunner {

	@Value("${admin.email}")
	private String adminEmail;

	@Value("${admin.password}")
	private String adminPassword;

	private final UserRepository userRepository;
	private final MovieRepository movieRepository;

	@Override
	public void run(String... args) {

		if (userRepository.existsByEmail(adminEmail)) {
			log.info("Admin already exists");
		} else {
			User adminUser = new User(null, "ADMIN", adminEmail, 0L, AES.encrypt(adminPassword), "ADMIN", false);
			userRepository.save(adminUser);
			log.info("Admin registration successful");
		}

		// Add or update KGF movie image
		java.util.List<Movie> movies = movieRepository.findAll();
		boolean found = false;
		for(Movie m : movies) {
			if(m.getName().toLowerCase().contains("kgf")) {
				m.setImageLink("/images/kgf.png");
				movieRepository.save(m);
				log.info("Updated image for KGF");
				found = true;
			}
		}
		if (!found) {
			Movie kgf = new Movie();
			kgf.setName("K.G.F Chapter 2");
			kgf.setLanguages("Kannada, Hindi, Telugu, Tamil, Malayalam");
			kgf.setGenre("Action/Drama");
			kgf.setDuration(LocalTime.of(2, 48));
			kgf.setImageLink("/images/kgf.png");
			kgf.setTrailerLink("https://www.youtube.com/embed/Qah9sSIXJqk");
			kgf.setDescription("In the blood-soaked Kolar Gold Fields, Rocky's name strikes fear into his foes.");
			kgf.setReleaseDate(LocalDate.of(2022, 4, 14));
			kgf.setCast("Yash, Sanjay Dutt, Raveena Tandon, Srinidhi Shetty");
			movieRepository.save(kgf);
			log.info("Created K.G.F Chapter 2 Movie with image");
		}
	}
}
