package datasource;

import model.Movie;
import model.MovieCollection;

import java.io.*;
import java.util.List;

public class FileHandler {
    private final String fileName;

    public FileHandler(String fileName) {
        this.fileName = fileName;
    }

    // Save the entire MovieCollection to a file
    public void saveCollectionToFile(MovieCollection collection) throws IOException {
        try (BufferedWriter writeToFile = new BufferedWriter(new FileWriter(fileName))) {
            List<Movie> moviesFromCollection = collection.getMovies(); // Get movies from the MovieCollection

            for (Movie movie : moviesFromCollection) {
                writeToFile.write(divideAttributes(movie)); // Format and write movie data to file
                writeToFile.newLine(); // Ensure a new line is written after each movie
            }
        } catch (IOException e) {
            System.err.println("Error saving collection to file: " + e.getMessage());
            throw e; // Rethrow exception after logging
        }
    }

    // Load movies from the file into a MovieCollection
    public void loadMoviesFromFile(MovieCollection collection) throws IOException {
        try (BufferedReader readFromFile = new BufferedReader(new FileReader(fileName))) {
            String lineData;

            while ((lineData = readFromFile.readLine()) != null) { // Read line-by-line until null
                Movie movie = undivideMovies(lineData); // Convert the line into a Movie object

                if (movie != null) {
                    collection.addMovie(movie); // Add the Movie object to the collection
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
            throw e; // Rethrow exception after logging
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
            throw e; // Rethrow exception after logging
        }
    }

    // Helper method to convert Movie attributes into a formatted string
    private String divideAttributes(Movie movie) {
        return String.join(";",
                movie.getTitle(),
                String.valueOf(movie.getYear()), // Convert int to String
                movie.getDirector(),
                movie.getGenre(),
                String.valueOf(movie.getLengthInMinutes()), // Convert int to String
                String.valueOf(movie.isColor()) // Convert boolean to String
        );
    }

    // Helper method to convert a formatted string into a Movie object
    private Movie undivideMovies(String movieData) {
        String[] data = movieData.split(";"); // Split data by ';'
        if (data.length != 6) {
            System.err.println("Invalid data format: " + movieData);
            return null; // Invalid data format
        }
        try {
            String title = data[0];
            int year = Integer.parseInt(data[1]);
            String director = data[2];
            String genre = data[3];
            int lengthInMinutes = Integer.parseInt(data[4]);
            boolean isColor = Boolean.parseBoolean(data[5]);
            return new Movie(title, year, director, genre, lengthInMinutes, isColor);
        } catch (NumberFormatException e) {
            System.err.println("Failed to parse movie data: " + movieData);
            return null; // Return null if parsing fails
        }
    }
}
