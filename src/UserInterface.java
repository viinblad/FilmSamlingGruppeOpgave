import java.util.Scanner;

public class UserInterface {
    public static void startProgram() {
        MovieCollection collection = new MovieCollection();
        MovieController controller = new MovieController(collection);  // Brug controlleren
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            int choice = getChoice(scanner);
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addMovie(scanner, controller);
                    break;
                case 2:
                    findMovie(scanner, controller);
                    break;
                case 3:
                    updateMovie(scanner, controller);
                    break;
                case 4:
                    deleteMovie(scanner, controller);
                    break;
                case 5:
                    listMovies(scanner, controller);
                    break;
                case 6:
                    searchMovies(scanner, controller);
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
        System.out.println("6) Search Movies by keywords");
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

        controller.addMovie(title, year, director, genre, lengthInMinutes, isColor); // use controller
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
            controller.updateMovie(title, updatedMovie); // Brug controlleren
        } else {
            System.out.println("Movie not found.");
        }
    }

    private static void deleteMovie(Scanner scanner, MovieController controller) {
        System.out.print("Enter title of movie to delete: ");
        String title = scanner.nextLine();
        controller.deleteMovie(title); // Brug controlleren
    }

    private static void listMovies(Scanner scanner, MovieController controller) {
        System.out.println("Choose sorting option: title, year, director, or genre");
        String sortBy = scanner.nextLine();
        controller.listMovies(sortBy); // Brug controlleren
    }

    private static void searchMovies(Scanner scanner, MovieController controller) {
        System.out.print("Enter search term: ");
        String searchTerm = scanner.nextLine();

        System.out.println("Choose search option: title, year, director, or genre");
        String searchBy = scanner.nextLine();
        controller.searchMovies(searchTerm, searchBy); // use controller
    }

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
