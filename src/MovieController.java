public class MovieController {
    private MovieCollection collection;

    public MovieController(MovieCollection collection) {
        this.collection = collection;
    }

    // Add movie
    public void addMovie(String title, int year, String director, String genre, int lengthInMinutes, boolean isColor) {
        Movie movie = new Movie(title, year, director, genre, lengthInMinutes, isColor);
        collection.addMovie(movie);
    }

    // Find movie
    public Movie findMovie(String title) {
        return collection.findMovie(title);
    }

    // Update movie
    public void updateMovie(String title, Movie updatedMovie) {
        collection.updateMovie(title, updatedMovie);
    }

    // Delete movie
    public void deleteMovie(String title) {
        collection.deleteMovie(title);
    }

    // List movies
    public void listMovies(String sortBy) {
        collection.listMovies(sortBy);
    }

    // Search movies
    public void searchMovies(String searchTerm, String searchBy) {
        collection.searchMovies(searchTerm, searchBy);
    }
}
