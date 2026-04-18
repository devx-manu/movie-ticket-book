package com.jsp.book.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jsp.book.dto.MovieDto;
import com.jsp.book.entity.Movie;
import com.jsp.book.repository.MovieRepository;

@Controller
public class MovieController {

	@Autowired
	private MovieRepository movieRepository;

	public String loadMain(ModelMap model) {

	    List<Movie> movies = movieRepository.findAll();

	    System.out.println("Movies on main page: " + movies.size());

	    model.addAttribute("movies", movies);

	    return "main";
	}

    @PostMapping("/add-movie")
    public String addMovie(@ModelAttribute MovieDto movieDto,
                           RedirectAttributes attributes) {

        System.out.println("Controller hit");

        Movie movie = new Movie();
        movie.setName(movieDto.getName());
        movie.setGenre(movieDto.getGenre());
        movie.setLanguages(movieDto.getLanguages());
        movie.setDuration(movieDto.getDuration());
        movie.setDescription(movieDto.getDescription());
        movie.setReleaseDate(movieDto.getReleaseDate());
        movie.setCast(movieDto.getCast());
        movie.setTrailerLink(movieDto.getTrailerLink());

     // 🔥 IMPORTANT (TEMP FIX)
        movie.setImageLink("https://via.placeholder.com/150");

        movieRepository.save(movie);

        System.out.println("Movie saved");

        attributes.addFlashAttribute("pass", "Movie Added Successfully");

        return "redirect:/manage-movies";
    }
}
