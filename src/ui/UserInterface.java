package ui;

import controller.MovieController;
import model.Movie;
import model.MovieCollection;



import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserInterface {
    public static void startProgram() {
        MovieCollection collection = new MovieCollection();
        MovieController controller = new MovieController(collection);  // use controlleren
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            int choice = getChoice(scanner, 8);

            switch (choice) {
                case 1:
                    findMovie(scanner, controller);
                    break;
                case 2:
                    addMovie(scanner, controller);
                    break;
                case 3:
                    updateMovie(scanner, controller);
                    break;
                case 4:
                    deleteMovie(scanner, controller);
                    break;
                case 5:
                    viewAllMovies(controller, collection);
                    break;
                case 6:
                    listMovies(scanner, controller, collection);
                    break;
                case 7:
                    searchMovies(scanner, controller);
                    break;
                case 8:
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
        System.out.println("1) Find Movie");
        System.out.println("2) Add Movie");
        System.out.println("3) Update Movie");
        System.out.println("4) Delete Movie");
        System.out.println("5) View All Movies");
        System.out.println("6) List All Movies by");
        System.out.println("7) Search Movies by keywords");
        System.out.println("8) Exit");
        System.out.print("Choose an option: ");
    }

    private static int getChoice(Scanner scanner, int maxOption) {
        int choice;
        while (true) {
            System.out.print("Enter your choice (1-" + maxOption + "): ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (choice >= 1 && choice <= maxOption) {
                    break;  // Valid choice
                } else {
                    System.out.println("Invalid choice. Please select a number between 1 and " + maxOption + ".");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Discard invalid input
            }
        }
        return choice;
    }

    private static void addMovie(Scanner scanner, MovieController controller) {
        System.out.print("Enter title: ");
        String title = scanner.nextLine();

        int year = getYearInput(scanner, "Enter year (must be between 1888 and current year): ");
        System.out.print("Enter director: ");
        String director = scanner.nextLine();
        System.out.print("Enter genre: ");
        String genre = scanner.nextLine();
        int lengthInMinutes = getPositiveIntInput(scanner, "Enter length in minutes (must be positive): ");
        boolean isColor = getBooleanInput(scanner, "Is the movie in color? (true/false): ");

        boolean added = controller.addMovie(title, year, director, genre, lengthInMinutes, isColor);

        if (added) {
            System.out.println("Movie added successfully.");
        } else {
            System.out.println("Movie with this title already exists.");
        }
    }

    private static void findMovie(Scanner scanner, MovieController controller) {
        System.out.print("Enter title of movie to find: ");
        String title = scanner.nextLine();
        Movie movie = controller.findMovie(title);
        if (movie != null) {
            System.out.println("Found: " + movie);
        } else {
            System.out.println("Movie not found.");
        }
    }

    private static void updateMovie(Scanner scanner, MovieController controller) {
        System.out.print("Enter title of movie to update: ");
        String title = scanner.nextLine();
        Movie existingMovie = controller.findMovie(title);
        if (existingMovie != null) {
            System.out.print("Enter new title (leave blank to keep unchanged): ");
            String newTitle = scanner.nextLine();

            int newYear = getYearInput(scanner, "Enter new year (0 to keep unchanged): ");
            System.out.print("Enter new director (leave blank to keep unchanged): ");
            String newDirector = scanner.nextLine();

            System.out.print("Enter new genre (leave blank to keep unchanged): ");
            String newGenre = scanner.nextLine();

            int newLength = getPositiveIntInput(scanner, "Enter new length in minutes (0 to keep unchanged): ");
            boolean newIsColor = getBooleanInput(scanner, "Is the movie in color? (leave blank to keep unchanged, true/false): ");

            // Update the movie
            Movie updatedMovie = new Movie(
                    newTitle.isEmpty() ? existingMovie.getTitle() : newTitle,
                    newYear == 0 ? existingMovie.getYear() : newYear,
                    newDirector.isEmpty() ? existingMovie.getDirector() : newDirector,
                    newGenre.isEmpty() ? existingMovie.getGenre() : newGenre,
                    newLength == 0 ? existingMovie.getLengthInMinutes() : newLength,
                    newIsColor
            );
            controller.updateMovie(title, updatedMovie);
        } else {
            System.out.println("Movie not found.");
        }
    }

    // delete movie method
    private static void deleteMovie(Scanner scanner, MovieController controller) {
        System.out.print("Enter title of movie to delete: ");
        String title = scanner.nextLine();

        boolean deleted = controller.deleteMovie(title);
        if (deleted) {
            System.out.println("Movie \"" + title + "\" deleted successfully.");
        } else {
            System.out.println("Movie not found.");
        }
    }




    // show movie list methods

    private static void viewAllMovies(MovieController controller, MovieCollection collection) {
        if (collection.getMovies().isEmpty()) {
            System.out.println("No movies in the collection.");
            return;
        }

        // specify both primary and secondary sorting parameters.
        String primarySortBy = "title"; // Default sorting by title
        String secondarySortBy = "year"; // Default secondary sorting by year

        // Pass both parameters to the controller's listMovies method
        List<Movie> allMovies = controller.listMovies(primarySortBy, secondarySortBy);
        System.out.println("\nAll Movies in Collection (Sorted by " + primarySortBy + " and then by " + secondarySortBy + "):");
        allMovies.forEach(System.out::println);
        System.out.println();
    }

    private static void listMovies(Scanner scanner, MovieController controller, MovieCollection collection) {
        if (collection.getMovies().isEmpty()) {
            System.out.println("No movies in the collection.");
            return;
        }

        System.out.println("Choose primary sorting option:");
        System.out.println("1) Title");
        System.out.println("2) Year");
        System.out.println("3) Director");
        System.out.println("4) Genre");

        int choice = getChoice(scanner, 4);  // Get user's choice with validation
        String primarySortBy;

        // Map the user's choice to a primary sorting field
        switch (choice) {
            case 1:
                primarySortBy = "title";
                break;
            case 2:
                primarySortBy = "year";
                break;
            case 3:
                primarySortBy = "director";
                break;
            case 4:
                primarySortBy = "genre";
                break;
            default:
                System.out.println("Invalid choice. Defaulting to title.");
                primarySortBy = "title";
        }

        // Now ask for the secondary sorting option
        System.out.println("Choose secondary sorting option:");
        System.out.println("1) Title");
        System.out.println("2) Year");
        System.out.println("3) Director");
        System.out.println("4) Genre");

        int secondaryChoice = getChoice(scanner, 4);  // Get user's choice for secondary sort
        String secondarySortBy;

        // Map the user's choice to a secondary sorting field
        switch (secondaryChoice) {
            case 1:
                secondarySortBy = "title";
                break;
            case 2:
                secondarySortBy = "year";
                break;
            case 3:
                secondarySortBy = "director";
                break;
            case 4:
                secondarySortBy = "genre";
                break;
            default:
                System.out.println("Invalid choice. Defaulting to title.");
                secondarySortBy = "title";
        }

        // Get sorted list from the controller
        List<Movie> sortedMovies = controller.listMovies(primarySortBy, secondarySortBy);
        System.out.println("\nMovies sorted by " + primarySortBy + " and then by " + secondarySortBy + ":");
        sortedMovies.forEach(System.out::println);  // Display each movie
        System.out.println();
    }


    // search movie method by specific
    private static void searchMovies(Scanner scanner, MovieController controller) {
        System.out.print("Enter search term: ");
        String searchTerm = scanner.nextLine();

        System.out.println("Choose search option:");
        System.out.println("1) Title");
        System.out.println("2) Year");
        System.out.println("3) Director");
        System.out.println("4) Genre");

        int choice = getChoice(scanner, 4);  // Get user's choice with validation
        String searchBy;

        // Map the user's choice to a search field
        switch (choice) {
            case 1:
                searchBy = "title";
                break;
            case 2:
                searchBy = "year";
                break;
            case 3:
                searchBy = "director";
                break;
            case 4:
                searchBy = "genre";
                break;
            default:
                System.out.println("Invalid choice. Defaulting to title.");
                searchBy = "title";
        }

        ArrayList<Movie> searchResults = controller.searchMovies(searchTerm, searchBy); // Use controller

        if (!searchResults.isEmpty()) {
            System.out.println("The following results were found: ");
            searchResults.forEach(System.out::println);
        } else {
            System.out.println("No movies found.");
        }
    }



    // user input year rule
    private static int getYearInput(Scanner scanner, String prompt) {
        int year = 0;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                year = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (year == 0 || (year > 1887 && year <= java.time.Year.now().getValue())) {
                    break; // Valid year
                } else {
                    System.out.println("Invalid year.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Discard invalid input
            }
        }
        return year;
    }

    // rule for no negative number input from user
    private static int getPositiveIntInput(Scanner scanner, String prompt) {
        int value;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (value >= 0) {
                    return value;
                } else {
                    System.out.println("Invalid input. Must be a positive number.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Discard invalid input
            }
        }
    }

    // color input true or false method - boolean
    private static boolean getBooleanInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("true") || input.equals("yes")) {
                return true;
            } else if (input.equals("false") || input.equals("no")) {
                return false;
            } else {
                System.out.println("Invalid input. Please enter 'true', 'false', 'yes', or 'no'.");
            }
        }
    }
}