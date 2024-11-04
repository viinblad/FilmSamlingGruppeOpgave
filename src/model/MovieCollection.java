package model;

import datasource.FileHandler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MovieCollection {
    private final List<Movie> movies;
    private final FileHandler fileHandler;

    // Constructor
    public MovieCollection() {
        this.movies = new ArrayList<>();
        this.fileHandler = new FileHandler("resources/moviedatabase.txt"); // Update the path as necessary
        loadMoviesFromFile(); // Load movies from file on initialization
    }

    public List<Movie> getMovies() {
        return new ArrayList<>(movies); // Return a copy to prevent external modification
    }

    // Create - add a movie (with duplicate title check)
    public boolean addMovie(Movie movie) {
        if (findMovie(movie.getTitle()) == null) {
            movies.add(movie);
            saveMoviesToFile(); // Save to file after adding
            return true;
        } else {
            return false;  // Movie already exists
        }
    }

    // Read - find a movie by title
    public Movie findMovie(String title) {
        return movies.stream()
                .filter(m -> m.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

    // Update - update individual movie fields
    public boolean updateMovie(String title, Movie updatedMovie) {
        Movie existingMovie = findMovie(title);
        if (existingMovie != null) {
            // Update fields only if they are valid
            if (updatedMovie.getTitle() != null && !updatedMovie.getTitle().isEmpty()) {
                existingMovie.setTitle(updatedMovie.getTitle());
            }
            if (updatedMovie.getYear() > 1887 && updatedMovie.getYear() <= java.time.Year.now().getValue()) {
                existingMovie.setYear(updatedMovie.getYear());
            }
            if (updatedMovie.getDirector() != null && !updatedMovie.getDirector().isEmpty()) {
                existingMovie.setDirector(updatedMovie.getDirector());
            }
            if (updatedMovie.getGenre() != null && !updatedMovie.getGenre().isEmpty()) {
                existingMovie.setGenre(updatedMovie.getGenre());
            }
            if (updatedMovie.getLengthInMinutes() > 0) {
                existingMovie.setLengthInMinutes(updatedMovie.getLengthInMinutes());
            }
            existingMovie.setColor(updatedMovie.isColor()); // Color is a boolean, so we update it always

            saveMoviesToFile(); // Save to file after updating
            return true;
        } else {
            return false;  // Movie not found
        }
    }

    // Delete - remove a movie
    public boolean deleteMovie(String title) {
        Movie movie = findMovie(title);
        if (movie != null) {
            movies.remove(movie);
            saveMoviesToFile(); // Save to file after deletion
            return true;
        } else {
            return false;  // Movie not found
        }
    }

    // List all movies that can be sorted by title, year, director, or genre
    public List<Movie> listMovies(String sortBy) {
        // Create a new list to avoid modifying the original list
        List<Movie> sortedMovies = new ArrayList<>(movies);

        // Sort the movies based on the specified criteria
        Comparator<Movie> comparator;
        switch (sortBy.toLowerCase()) {
            case "year":
                comparator = Comparator.comparingInt(Movie::getYear);
                break;
            case "director":
                comparator = Comparator.comparing(Movie::getDirector, String.CASE_INSENSITIVE_ORDER);
                break;
            case "genre":
                comparator = Comparator.comparing(Movie::getGenre, String.CASE_INSENSITIVE_ORDER);
                break;
            case "title":
            default:
                comparator = Comparator.comparing(Movie::getTitle, String.CASE_INSENSITIVE_ORDER);
                break;
        }

        // Sort the list
        sortedMovies.sort(comparator);

        // Return the sorted list of movies
        return sortedMovies;
    }

    // Search for movies by title, year, director, or genre
    public ArrayList<Movie> searchMovies(String searchTerm, String searchBy) {
        ArrayList<Movie> foundMovies = new ArrayList<>();

        if (movies.isEmpty()) {
            return foundMovies;
        }

        switch (searchBy.toLowerCase()) {
            case "title":
                for (Movie movie : movies) {
                    if (movie.getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
                        foundMovies.add(movie);
                    }
                }
                break;

            case "year":
                int year;
                try {
                    year = Integer.parseInt(searchTerm);
                    for (Movie movie : movies) {
                        if (movie.getYear() == year) {
                            foundMovies.add(movie);
                        }
                    }
                } catch (NumberFormatException e) {
                    // Handle the case where the search term is not a valid number
                    System.out.println("Invalid year format.");
                }
                break;

            case "director":
                for (Movie movie : movies) {
                    if (movie.getDirector().toLowerCase().contains(searchTerm.toLowerCase())) {
                        foundMovies.add(movie);
                    }
                }
                break;

            case "genre":
                for (Movie movie : movies) {
                    if (movie.getGenre().toLowerCase().contains(searchTerm.toLowerCase())) {
                        foundMovies.add(movie);
                    }
                }
                break;

            default:
                return foundMovies;
        }

        return foundMovies;
    }

    // Load movies from file
    public void loadMoviesFromFile() {
        try {
            fileHandler.loadMoviesFromFile(this); // Pass this MovieCollection instance
        } catch (IOException e) {
            System.err.println("Failed to load movies from file: " + e.getMessage());
        }
    }

    // Save movies to file
    private void saveMoviesToFile() {
        try {
            fileHandler.saveCollectionToFile(this); // Pass this MovieCollection instance
        } catch (IOException e) {
            System.err.println("Error saving movies to file: " + e.getMessage());
        }
    }
}
