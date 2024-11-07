package controller;

import model.Movie;
import model.MovieCollection;

import java.util.ArrayList;
import java.util.List;

public class MovieController {
    private final MovieCollection collection;

    public MovieController(MovieCollection collection){
        this.collection = collection;
    }

    public boolean addMovie(String title, int year, String director, String genre, int lengthInMinutes, boolean isColor) {
        if (collection.findMovie(title) != null) {
            return false; // Duplicate found
        }

        Movie movie = new Movie(title, year, director, genre, lengthInMinutes, isColor);
        collection.addMovie(movie);
        return true; // Movie added successfully
    }

    public Movie findMovie(String title){
        return collection.findMovie(title);
    }

    public void updateMovie(String title, Movie updateMovie){
        collection.updateMovie(title, updateMovie);
    }

    public boolean deleteMovie(String title){
        return collection.deleteMovie(title); // Use the result from the collection to determine if the movie was deleted

    }

    // Return the sorted list of movies based on the provided sorting option
    public List<Movie> listMovies(String primarySortBy, String secondarySortBy) {
        // Assuming listMovies in MovieCollection returns a List<Movie>
        return collection.listMovies(primarySortBy, secondarySortBy);
    }

    public ArrayList<Movie> searchMovies(String searchTerm, String searchBy){
        return collection.searchMovies(searchTerm, searchBy);
    }
}