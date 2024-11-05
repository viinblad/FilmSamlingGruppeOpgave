package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MovieTest {

    @Test
    public void testConstructorAndGetters() {
        Movie movie = new Movie("Inception", 2010, "Christopher Nolan", "Sci-Fi", 148, true);

        assertEquals("Inception", movie.getTitle());
        assertEquals(2010, movie.getYear());
        assertEquals("Christopher Nolan", movie.getDirector());
        assertEquals("Sci-Fi", movie.getGenre());
        assertEquals(148, movie.getLengthInMinutes());
        assertTrue(movie.isColor());
    }

    @Test
    public void testSetters() {
        Movie movie = new Movie("Inception", 2010, "Christopher Nolan", "Sci-Fi", 148, true);

        movie.setTitle("Interstellar");
        assertEquals("Interstellar", movie.getTitle());

        movie.setYear(2014);
        assertEquals(2014, movie.getYear());

        movie.setDirector("Christopher Nolan");
        assertEquals("Christopher Nolan", movie.getDirector());

        movie.setGenre("Adventure");
        assertEquals("Adventure", movie.getGenre());

        movie.setLengthInMinutes(169);
        assertEquals(169, movie.getLengthInMinutes());

        movie.setColor(false);
        assertFalse(movie.isColor());
    }

    @Test
    public void testSetTitleThrowsExceptionForEmptyTitle() {
        Movie movie = new Movie("Inception", 2010, "Christopher Nolan", "Sci-Fi", 148, true);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            movie.setTitle("");
        });
        assertEquals("Title cannot be empty.", exception.getMessage());
    }

    @Test
    public void testSetYearThrowsExceptionForInvalidYear() {
        Movie movie = new Movie("Inception", 2010, "Christopher Nolan", "Sci-Fi", 148, true);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            movie.setYear(1880); // Invalid year
        });
        assertEquals("Invalid year entered. Year must be between 1888 and the current year.", exception.getMessage());
    }

    @Test
    public void testSetLengthInMinutesThrowsExceptionForNonPositiveLength() {
        Movie movie = new Movie("Inception", 2010, "Christopher Nolan", "Sci-Fi", 148, true);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            movie.setLengthInMinutes(0); // Invalid length
        });
        assertEquals("Invalid length entered. Length must be a positive number.", exception.getMessage());
    }

    @Test
    public void testToString() {
        Movie movie = new Movie("Inception", 2010, "Christopher Nolan", "Sci-Fi", 148, true);
        String expectedString = "model.Movie {title='Inception', year=2010, director='Christopher Nolan', genre='Sci-Fi', length=148 minutes, In color}";
        assertEquals(expectedString, movie.toString());
    }

    @Test
    public void testEqualsAndHashCode() {
        Movie movie1 = new Movie("Inception", 2010, "Christopher Nolan", "Sci-Fi", 148, true);
        Movie movie2 = new Movie("Inception", 2010, "Christopher Nolan", "Sci-Fi", 148, true);
        Movie movie3 = new Movie("Interstellar", 2014, "Christopher Nolan", "Sci-Fi", 169, true);

        assertEquals(movie1, movie2); // should be equal
        assertNotEquals(movie1, movie3); // should not be equal
        assertEquals(movie1.hashCode(), movie2.hashCode()); // same hash code
        assertNotEquals(movie1.hashCode(), movie3.hashCode()); // different hash code
    }
}
