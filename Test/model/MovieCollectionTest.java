package model;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Movie;
import model.MovieCollection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MovieCollectionTest {

    private MovieCollection movieCollection;
    private final String testFilePath = "C:\\Users\\esben\\Documents\\Kea\\IdeaProjects\\FilmSamlingGruppeOpgave\\resources\\moviedatabase.txt";

    @BeforeEach
    public void setUp() {
        // Clear the test file before each test
        try {
            Files.deleteIfExists(Paths.get(testFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Initialize a new MovieCollection instance
        movieCollection = new MovieCollection();
    }

    @AfterEach
    public void tearDown() {
        // Clean up test file after each test
        try {
            Files.deleteIfExists(Paths.get(testFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAddMovie() {
        Movie movie = new Movie("Inception", 2010, "Christopher Nolan", "Sci-Fi", 148, true);
        assertTrue(movieCollection.addMovie(movie), "Movie should be added successfully");
        assertFalse(movieCollection.addMovie(movie), "Duplicate movie should not be added");
    }

    @Test
    public void testFindMovie() {
        Movie movie = new Movie("The Matrix", 1999, "The Wachowskis", "Sci-Fi", 136, true);
        movieCollection.addMovie(movie);
        Movie foundMovie = movieCollection.findMovie("The Matrix");
        assertNotNull(foundMovie, "Movie should be found");
        assertEquals("The Matrix", foundMovie.getTitle(), "Found movie title should match");
    }

    @Test
    public void testUpdateMovie() {
        Movie movie = new Movie("The Dark Knight", 2008, "Christopher Nolan", "Action", 152, true);
        movieCollection.addMovie(movie);

        Movie updatedMovie = new Movie("The Dark Knight Rises", 2012, "Christopher Nolan", "Action", 164, true);
        assertTrue(movieCollection.updateMovie("The Dark Knight", updatedMovie), "Movie should be updated successfully");

        Movie foundMovie = movieCollection.findMovie("The Dark Knight Rises");
        assertNotNull(foundMovie, "Updated movie should be found");
        assertEquals("The Dark Knight Rises", foundMovie.getTitle(), "Updated movie title should match");
    }

    @Test
    public void testDeleteMovie() {
        Movie movie = new Movie("Interstellar", 2014, "Christopher Nolan", "Sci-Fi", 169, true);
        movieCollection.addMovie(movie);
        assertTrue(movieCollection.deleteMovie("Interstellar"), "Movie should be deleted successfully");
        assertNull(movieCollection.findMovie("Interstellar"), "Movie should not be found after deletion");
    }

    @Test
    public void testListMovies() {
        Movie movie1 = new Movie("Inception", 2010, "Christopher Nolan", "Sci-Fi", 148, true);
        Movie movie2 = new Movie("The Shawshank Redemption", 1994, "Frank Darabont", "Drama", 142, true);
        movieCollection.addMovie(movie1);
        movieCollection.addMovie(movie2);

        List<Movie> sortedMovies = movieCollection.listMovies("title");
        assertEquals(2, sortedMovies.size(), "There should be 2 movies in the collection");
        assertEquals("Inception", sortedMovies.get(0).getTitle(), "First movie should be Inception");
        assertEquals("The Shawshank Redemption", sortedMovies.get(1).getTitle(), "Second movie should be The Shawshank Redemption");
    }

    @Test
    public void testSearchMoviesByTitle() {
        Movie movie = new Movie("Pulp Fiction", 1994, "Quentin Tarantino", "Crime", 154, true);
        movieCollection.addMovie(movie);
        List<Movie> foundMovies = movieCollection.searchMovies("Pulp", "title");
        assertEquals(1, foundMovies.size(), "Should find 1 movie");
        assertEquals("Pulp Fiction", foundMovies.get(0).getTitle(), "Found movie title should match");
    }
}
