import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MovieCollection {
    private final List<Movie> movies;

    // Constructor
    public MovieCollection() {
        this.movies = new ArrayList<>();

        // movies add for test
        //Movie movie1 = new Movie("Spiderman", 2014, "Martin", "Action", 140,true);
       // Movie movie2 = new Movie("Batman", 2018, "Victor", "Action", 180,true);
       // Movie movie4 = new Movie("Psycho", 1960, "Rasmus", "Horror", 87,false);

        //movies.add(movie1);
        //movies.add(movie2);
        //.add(movie4);
    }

    public List<Movie> getMovies() {
        return movies;
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
                int year = Integer.parseInt(searchTerm);
                for (Movie movie : movies) {
                    if (movie.getYear() == year) {
                        foundMovies.add(movie);
                    }
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

}