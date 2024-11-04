import model.Movie;
import model.MovieCollection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MovieCollectionTest {

    @DisplayName("Test for enkelt film")
    @Test
    void addMovie() {
        //Arrange
        MovieCollection collection = new MovieCollection();
        Movie movie1 = new Movie("Spiderman", 2014, "Martin", "Action", 204,true);
        //Act
        collection.addMovie(movie1);
        //Assert
        assertNotNull(collection);


    }


    @DisplayName("Test for addmovie med flere film.")
    @Test
    void addMovie2(){
        //Arrange
        MovieCollection collection = new MovieCollection();
        Movie movie1 = new Movie("Spiderman", 2014, "Martin", "Action", 140,true);
        Movie movie2 = new Movie("Batman", 2018, "Victor", "Action", 180,true);
        Movie movie3 = new Movie("Interstellar", 2014, "Christopher Nolan", "Sci-fi", 169,true);
        Movie movie4 = new Movie("Psycho", 1960, "Rasmus", "Horror", 87,false);
        //Act
        collection.addMovie(movie1);
        collection.addMovie(movie2);
        collection.addMovie(movie3);
        collection.addMovie(movie4);
        int actualResult = collection.getMovies().size();
        int expectedResult = 4;

        //Assert
        assertNotNull(collection);
        assertEquals(expectedResult,actualResult);
    }

    @Test
    void searchMoviesByTitle() {
        // Arrange
        MovieCollection collection = new MovieCollection();
        Movie movie1 = new Movie("Interstellar", 2014, "Martin", "Action", 140, true);
        Movie movie2 = new Movie("Spiderman 2", 2018, "Victor", "Action", 180, true);
        Movie movie3 = new Movie("Interstellar 2", 2013, "Rasmus", "Action", 83, true);

        collection.addMovie(movie1);
        collection.addMovie(movie2);
        collection.addMovie(movie3);

        // Act
        ArrayList<Movie> actualResult = collection.searchMovies("Interstellar", "title");

        // Debug: print resultatet af søgningen for at verificere indholdet
        System.out.println("Search result: " + actualResult);

        // Assert
        assertEquals(2, actualResult.size());
        assertTrue(actualResult.contains(movie1));
        assertTrue(actualResult.contains(movie3));
    }

    @DisplayName("Test for deleting an existing movie")
    @Test
    void deleteExistingMovie() {
        // Arrange
        MovieCollection collection = new MovieCollection();
        Movie movie1 = new Movie("Inception", 2010, "Christopher Nolan", "Sci-Fi", 148, true);
        Movie movie2 = new Movie("The Matrix", 1999, "The Wachowskis", "Sci-Fi", 136, true);
        collection.addMovie(movie1);
        collection.addMovie(movie2);

        // Act
        boolean isDeleted = collection.deleteMovie("Inception");

        // Debug: print the size of the collection after deletion
        System.out.println("Collection size after deletion: " + collection.getMovies().size());

        // Assert
        assertTrue(isDeleted, "The movie should be successfully deleted.");
        assertEquals(1, collection.getMovies().size(), "There should be only one movie left after deletion.");
        assertFalse(collection.getMovies().contains(movie1), "The deleted movie should no longer be in the collection.");
    }





}