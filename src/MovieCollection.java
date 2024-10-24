import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MovieCollection {
    private final List<Movie> movies;

    // Constructor
    public MovieCollection() {
        this.movies = new ArrayList<>();
    }

    // Create - add a movie (with duplicate title check)
    public boolean addMovie(Movie movie) {
        if (findMovie(movie.getTitle()) == null) {
            movies.add(movie);
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
            return true;
        } else {
            return false;  // Movie not found
        }
    }

    // List all movies that can be sorted by title, year, director, or genre
    public void listMovies(String sortBy) {
        if (movies.isEmpty()) {
            System.out.println("No movies in the collection.");
            return;
        }

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
        movies.stream().sorted(comparator).forEach(System.out::println);
    }

    // Search for movies by title, year, director, or genre
    public void searchMovies(String searchTerm, String searchBy) {
        if (movies.isEmpty()) {
            System.out.println("No movies in the collection.");
            return;
        }

        List<Movie> result;
        switch (searchBy.toLowerCase()) {
            case "title":
                result = movies.stream()
                        .filter(m -> m.getTitle().toLowerCase().contains(searchTerm.toLowerCase()))
                        .collect(Collectors.toList());
                break;
            case "year":
                try {
                    int year = Integer.parseInt(searchTerm);
                    result = movies.stream()
                            .filter(m -> m.getYear() == year)
                            .collect(Collectors.toList());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid year format. Please enter a valid year.");
                    return;
                }
                break;
            case "director":
                result = movies.stream()
                        .filter(m -> m.getDirector().toLowerCase().contains(searchTerm.toLowerCase()))
                        .collect(Collectors.toList());
                break;
            case "genre":
                result = movies.stream()
                        .filter(m -> m.getGenre().toLowerCase().contains(searchTerm.toLowerCase()))
                        .collect(Collectors.toList());
                break;
            default:
                System.out.println("Invalid search option. Please choose title, year, director, or genre.");
                return;
        }

        // Check if there are results to display
        if (result.isEmpty()) {
            System.out.println("No movies found matching the criteria.");
        } else {
            System.out.println("Search results:");
            result.forEach(System.out::println); // Print each movie found
        }
    }
}