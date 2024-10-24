import java.time.Year;

public class Movie {
    private String title;
    private int year;
    private String director;
    private String genre;
    private int lengthInMinutes; // attribute for length in minutes
    private boolean isColor; // attribute for whether the movie is in color

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
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        if (year > 1887 && year <= Year.now().getValue()) { // Simple validation
            this.year = year;
        } else {
            throw new IllegalArgumentException("Invalid year entered. Year must be between 1888 and current year.");
        }
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getLengthInMinutes() {
        return lengthInMinutes;
    }

    public void setLengthInMinutes(int lengthInMinutes) {
        if (lengthInMinutes > 0) { // Simple validation for positive length
            this.lengthInMinutes = lengthInMinutes;
        } else {
            throw new IllegalArgumentException("Invalid length entered. Length must be positive.");
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
        return "Movie{" +
                "title='" + title + '\'' +
                ", year=" + year +
                ", director='" + director + '\'' +
                ", genre='" + genre + '\'' +
                ", length=" + lengthInMinutes + " minutes" +
                ", " + (isColor ? "In color" : "Black and white") + // For displaying whether the movie is in color
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Movie movie = (Movie) obj;
        return title.equalsIgnoreCase(movie.title); // Compare based on title (case-insensitive)
    }

    @Override
    public int hashCode() {
        return title.toLowerCase().hashCode(); // Use title for hash code (case-insensitive)
    }
}
