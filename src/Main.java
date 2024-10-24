import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        MovieCollection collection = new MovieCollection();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            int choice = getChoice(scanner);
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addMovie(scanner, collection);
                    break;
                case 2:
                    findMovie(scanner, collection);
                    break;
                case 3:
                    updateMovie(scanner, collection);
                    break;
                case 4:
                    deleteMovie(scanner, collection);
                    break;
                case 5:
                    listMovies(scanner, collection);
                    break;
                case 6:
                    searchMovies(scanner, collection);
                    break;
                case 7:
                    running = false;
                    System.out.println("Exiting program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n--- Movie Collection Menu ---");
        System.out.println("1) Add Movie");
        System.out.println("2) Find Movie");
        System.out.println("3) Update Movie");
        System.out.println("4) Delete Movie");
        System.out.println("5) List All Movies");
        System.out.println("6) Search Movies");
        System.out.println("7) Exit");
        System.out.print("Choose an option: ");
    }

    private static int getChoice(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); // Discard invalid input
            System.out.print("Choose an option: ");
        }
        return scanner.nextInt();
    }

    private static void addMovie(Scanner scanner, MovieCollection collection) {
        System.out.print("Enter title: ");
        String title = scanner.nextLine();

        int year = 0; // Initialize year
        while (true) {
            System.out.print("Enter year (must be between 1888 and current year): ");
            if (scanner.hasNextInt()) {
                year = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                try {
                    if (year > 1887 && year <= java.time.Year.now().getValue()) {
                        break; // Valid year entered
                    } else {
                        System.out.println("Invalid year. Please enter a valid year between 1888 and " + java.time.Year.now().getValue());
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Discard invalid input
            }
        }

        System.out.print("Enter director: ");
        String director = scanner.nextLine();

        System.out.print("Enter genre: ");
        String genre = scanner.nextLine();

        int lengthInMinutes = 0; // Initialize length
        while (true) {
            System.out.print("Enter length in minutes (must be positive): ");
            if (scanner.hasNextInt()) {
                lengthInMinutes = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                try {
                    if (lengthInMinutes > 0) {
                        break; // Valid length entered
                    } else {
                        System.out.println("Invalid length. Please enter a positive number.");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Discard invalid input
            }
        }

        boolean isColor = false; // Initialize color
        while (true) {
            System.out.print("Is the movie in color? (true/false): ");
            String colorInput = scanner.nextLine().trim().toLowerCase(); // Read input and normalize case

            // Check for valid input or spelling mistakes
            if (colorInput.equals("true") || colorInput.equals("t") || colorInput.equals("yes")) {
                isColor = true;
                break; // Valid input given
            } else if (colorInput.equals("false") || colorInput.equals("f") || colorInput.equals("no")) {
                isColor = false;
                break; // Valid input given
            } else {
                System.out.println("Invalid input. Please enter 'true', 'false', 't', 'f', 'yes', or 'no'.");
            }
        }

        // Create a new Movie object with the collected information
        Movie movie = new Movie(title, year, director, genre, lengthInMinutes, isColor);
        collection.addMovie(movie); // Add the movie to the collection
        System.out.println("Movie added: " + movie); // Print success message only
    }


    private static void findMovie(Scanner scanner, MovieCollection collection) {
        System.out.print("Enter title of movie to find: ");
        String title = scanner.nextLine();
        Movie movie = collection.findMovie(title);
        if (movie != null) {
            System.out.println("Found: " + movie);
        } else {
            System.out.println("Movie not found.");
        }
    }

    private static void updateMovie(Scanner scanner, MovieCollection collection) {
        System.out.print("Enter title of movie to update: ");
        String title = scanner.nextLine();
        Movie existingMovie = collection.findMovie(title);
        if (existingMovie != null) {
            System.out.print("Enter new title (leave blank to keep unchanged): ");
            String newTitle = scanner.nextLine();
            if (!newTitle.isEmpty()) {
                existingMovie.setTitle(newTitle);
            }

            // Update year with validation
            int newYear = 0; // Initialize new year
            while (true) {
                System.out.print("Enter new year (0 to keep unchanged): ");
                if (scanner.hasNextInt()) {
                    newYear = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    if (newYear == 0 || (newYear > 1887 && newYear <= java.time.Year.now().getValue())) {
                        if (newYear != 0) {
                            existingMovie.setYear(newYear);
                        }
                        break; // Exit loop if valid year is entered
                    } else {
                        System.out.println("Invalid year. Please enter a valid year between 1888 and " + java.time.Year.now().getValue());
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next(); // Discard invalid input
                }
            }

            System.out.print("Enter new director (leave blank to keep unchanged): ");
            String newDirector = scanner.nextLine();
            if (!newDirector.isEmpty()) {
                existingMovie.setDirector(newDirector);
            }

            System.out.print("Enter new genre (leave blank to keep unchanged): ");
            String newGenre = scanner.nextLine();
            if (!newGenre.isEmpty()) {
                existingMovie.setGenre(newGenre);
            }

            // Update length with validation
            int newLength = 0; // Initialize new length
            while (true) {
                System.out.print("Enter new length in minutes (0 to keep unchanged): ");
                if (scanner.hasNextInt()) {
                    newLength = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    if (newLength == 0 || newLength > 0) {
                        if (newLength != 0) {
                            existingMovie.setLengthInMinutes(newLength);
                        }
                        break; // Exit loop if valid length is entered
                    } else {
                        System.out.println("Invalid length. Please enter a positive number.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.next(); // Discard invalid input
                }
            }

            // Update color with validation
            boolean newIsColor = existingMovie.isColor(); // Default to existing value
            while (true) {
                System.out.print("Is the movie in color? (leave blank to keep unchanged, true/false): ");
                String colorInput = scanner.nextLine();
                if (colorInput.isEmpty()) {
                    break; // Keep unchanged
                }
                if (colorInput.equalsIgnoreCase("true") || colorInput.equalsIgnoreCase("yes")) {
                    newIsColor = true;
                    break;
                } else if (colorInput.equalsIgnoreCase("false") || colorInput.equalsIgnoreCase("no")) {
                    newIsColor = false;
                    break;
                } else {
                    System.out.println("Invalid input. Please enter 'true', 'false', 'yes', or 'no'.");
                }
            }
            existingMovie.setColor(newIsColor);

            collection.updateMovie(title, existingMovie);
            System.out.println("Movie updated: " + existingMovie);
        } else {
            System.out.println("Movie not found.");
        }
    }

    private static void deleteMovie(Scanner scanner, MovieCollection collection) {
        System.out.print("Enter title of movie to delete: ");
        String title = scanner.nextLine();
        collection.deleteMovie(title);
        System.out.println("Movie deleted: " + title);
    }

    private static void listMovies(Scanner scanner, MovieCollection collection) {
        System.out.println("Choose sorting option: title, year, director, or genre");
        String sortBy = scanner.nextLine();
        collection.listMovies(sortBy);
    }

    private static void searchMovies(Scanner scanner, MovieCollection collection) {
        System.out.print("Enter search term: movie: ");
        String searchTerm = scanner.nextLine();

        System.out.println("Choose search option: title, year, director, or genre");
        String searchBy = scanner.nextLine();
        collection.searchMovies(searchTerm, searchBy);
    }

    private static int getIntInput(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); // Discard invalid input
            System.out.print("Enter a number: ");
        }
        return scanner.nextInt();
    }
}
