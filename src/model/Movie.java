package model;


import java.time.Year;

public class Movie {
    private String title;
    private int year;
    private String director;
    private String genre;
    private int lengthInMinutes; // Attribute for length in minutes
    private boolean isColor; // Attribute for whether the movie is in color

    // Constructor
    public Movie(String title, int year, String director, String genre, int lengthInMinutes, boolean isColor) {
        this.title = title;
        setYear(year); // Use the setYear method for validation
        this.director = director;
        this.genre = genre;
        setLengthInMinutes(lengthInMinutes); // Validate length in the setter
        this.isColor = isColor;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty.");
        }
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        if (year > 1887 && year <= Year.now().getValue()) { // Simple validation
            this.year = year;
        } else {
            throw new IllegalArgumentException("Invalid year entered. Year must be between 1888 and the current year.");
        }
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        if (director == null || director.trim().isEmpty()) {
            throw new IllegalArgumentException("Director cannot be empty.");
        }
        this.director = director;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        if (genre == null || genre.trim().isEmpty()) {
            throw new IllegalArgumentException("Genre cannot be empty.");
        }
        this.genre = genre;
    }

    public int getLengthInMinutes() {
        return lengthInMinutes;
    }

    public void setLengthInMinutes(int lengthInMinutes) {
        if (lengthInMinutes > 0) { // Simple validation for positive length
            this.lengthInMinutes = lengthInMinutes;
        } else {
            throw new IllegalArgumentException("Invalid length entered. Length must be a positive number.");
        }
    }

    public boolean isColor() {
        return isColor;
    }

    public void setColor(boolean isColor) {
        this.isColor = isColor;
    }

    @Override
    public String toString() {
        return "model.Movie {" +
                "title='" + title + '\'' +
                ", year=" + year +
                ", director='" + director + '\'' +
                ", genre='" + genre + '\'' +
                ", length=" + lengthInMinutes + " minutes" +
                ", " + (isColor ? "In color" : "Black and white") +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Movie movie = (Movie) obj;
        return title.equalsIgnoreCase(movie.title) &&
                year == movie.year &&
                director.equalsIgnoreCase(movie.director) &&
                genre.equalsIgnoreCase(movie.genre);
    }
/* The number 31 is a prime number commonly used in the hashCode()
Multiplying by 31 before adding ensures that each component of the object (like title, year, etc.)
influences the final hash code. This helps reduce the likelihood of collisions when different
objects have the same hash code.
 */
    @Override
    public int hashCode() {
        int result = title.toLowerCase().hashCode();
        result = 31 * result + year;
        result = 31 * result + director.toLowerCase().hashCode();
        result = 31 * result + genre.toLowerCase().hashCode();
        return result;
    }
}
