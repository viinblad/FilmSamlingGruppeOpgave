import java.util.ArrayList;

public class MovieController {
    private MovieCollection collection;

    public MovieController(MovieCollection collection){
        this.collection = collection;
    }

    public void addMovie(String title, int year, String director, String genre, int lengthInMinutes, boolean isColor){
        Movie movie = new Movie(title, year,director,genre,lengthInMinutes,isColor);
        collection.addMovie(movie);
    }
    public Movie findMovie(String title){
        return collection.findMovie(title);
    }
    public void updateMovie(String title, Movie updateMovie){
        collection.updateMovie(title,updateMovie);
    }

    public void deleteMovie(String title){
        collection.deleteMovie(title);
    }
    public void listMovies(String sortBy){
        collection.listMovies(sortBy);
    }
    public ArrayList<Movie> searchMovies(String searchTerm, String searchBy){
        return collection.searchMovies(searchTerm, searchBy);
    }

}
