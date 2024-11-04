package controller;

import model.Movie;
import model.MovieCollection;

import java.util.ArrayList;
import java.util.List;

public class MovieController {
    private MovieCollection collection;

    public MovieController(MovieCollection collection){
        this.collection = collection;
    }

    public void addMovie(String title, int year, String director, String genre, int lengthInMinutes, boolean isColor){
        Movie movie = new Movie(title, year, director, genre, lengthInMinutes, isColor);
        collection.addMovie(movie);
    }

    public Movie findMovie(String title){
        return collection.findMovie(title);
    }

    public void updateMovie(String title, Movie updateMovie){
        collection.updateMovie(title, updateMovie);
    }

    public void deleteMovie(String title){
        collection.deleteMovie(title);
    }

    // Return the sorted list of movies based on the provided sorting option
    public List<Movie> listMovies(String sortBy) {
        List<Movie> sortedMovies = collection.listMovies(sortBy); // Assuming listMovies in MovieCollection returns a List<Movie>
        return sortedMovies; // Return the sorted list
    }

    public ArrayList<Movie> searchMovies(String searchTerm, String searchBy){
        return collection.searchMovies(searchTerm, searchBy);
    }
}
