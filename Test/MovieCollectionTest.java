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
        Movie movie1 = new Movie("Batman", 2014, "Martin", "Action", 140, true);
        Movie movie2 = new Movie("Spiderman 2", 2018, "Victor", "Action", 180, true);
        Movie movie3 = new Movie("Batman 2", 2013, "Rasmus", "Action", 83, true);

        collection.addMovie(movie1);
        collection.addMovie(movie2);
        collection.addMovie(movie3);

        // Act
        ArrayList<Movie> actualResult = collection.searchMovies("Batman", "title");

        // Assert
        assertEquals(2, actualResult.size());
        assertFalse(actualResult.contains(movie1));
        assertTrue(actualResult.contains(movie3));
    }





}