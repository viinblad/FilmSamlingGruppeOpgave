package datasource;

import model.Movie;
import model.MovieCollection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class FileHandlerTest {
    private static final String TEST_FILE_NAME = "C:\\Users\\esben\\Documents\\Kea\\IdeaProjects\\FilmSamlingGruppeOpgave\\Test\\test_movies.txt";
    private FileHandler fileHandler;
    private MovieCollection movieCollection;

    @BeforeEach
    public void setUp() {
        // Arrange
        fileHandler = new FileHandler(TEST_FILE_NAME);
        movieCollection = new MovieCollection(); // Assuming a constructor that initializes an empty collection
    }

    @AfterEach
    public void tearDown() {
        // Clean up the test file after each test
        File testFile = new File(TEST_FILE_NAME);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    public void testSaveCollectionToFile() throws IOException {
        // Arrange
        Movie movie1 = new Movie("Inception", 2010, "Christopher Nolan", "Sci-Fi", 148, true);
        Movie movie2 = new Movie("The Matrix", 1999, "The Wachowskis", "Sci-Fi", 136, true);
        movieCollection.addMovie(movie1);
        movieCollection.addMovie(movie2);

        // Act
        fileHandler.saveCollectionToFile(movieCollection);

        // Assert
        try (BufferedReader reader = new BufferedReader(new FileReader(TEST_FILE_NAME))) {
            String line1 = reader.readLine();
            String line2 = reader.readLine();
            assertNotNull(line1);
            assertNotNull(line2);
            assertEquals("Inception;2010;Christopher Nolan;Sci-Fi;148;true", line1);
            assertEquals("The Matrix;1999;The Wachowskis;Sci-Fi;136;true", line2);
        }
    }

    @Test
    public void testLoadMoviesFromFile() throws IOException {
        // Arrange
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_FILE_NAME))) {
            writer.write("Inception;2010;Christopher Nolan;Sci-Fi;148;true");
            writer.newLine();
            writer.write("The Matrix;1999;The Wachowskis;Sci-Fi;136;true");
            writer.newLine();
        }

        // Act
        fileHandler.loadMoviesFromFile(movieCollection);

        // Assert
        assertEquals(2, movieCollection.getMovies().size());
        assertEquals("Inception", movieCollection.getMovies().get(0).getTitle());
        assertEquals("The Matrix", movieCollection.getMovies().get(1).getTitle());
    }

    @Test
    public void testLoadMoviesFromFileHandlesInvalidData() throws IOException {
        // Arrange
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TEST_FILE_NAME))) {
            writer.write("Invalid Movie Data"); // Writing invalid data
            writer.newLine();
        }

        // Act
        assertDoesNotThrow(() -> fileHandler.loadMoviesFromFile(movieCollection));

        // Assert
        assertEquals(2, movieCollection.getMovies().size()); // Expecting the collection to be empty
    }


    @Test
    public void testLoadMoviesFromFileFileNotFound() {
        // Arrange
        FileHandler nonExistentFileHandler = new FileHandler("non_existent_file.txt");

        // Act & Assert
        assertThrows(FileNotFoundException.class, () -> {
            nonExistentFileHandler.loadMoviesFromFile(movieCollection);
        });
    }
}
