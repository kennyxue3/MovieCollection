import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MovieCollection {
    private ArrayList<Movie> movies;
    private Scanner scanner;

    public MovieCollection(String fileName) {
        importMovieList(fileName);
        scanner = new Scanner(System.in);
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void menu() {
        String menuOption = "";

        System.out.println("Welcome to the movie collection!");
        System.out.println("Total: " + movies.size() + " movies");

        while (!menuOption.equals("q")) {
            System.out.println("------------ Main Menu ----------");
            System.out.println("- search (t)itles");
            System.out.println("- search (k)eywords");
            System.out.println("- search (c)ast");
            System.out.println("- see all movies of a (g)enre");
            System.out.println("- list top 50 (r)ated movies");
            System.out.println("- list top 50 (h)igest revenue movies");
            System.out.println("- (q)uit");
            System.out.print("Enter choice: ");
            menuOption = scanner.nextLine();

            if (!menuOption.equals("q")) {
                processOption(menuOption);
            }
        }
    }

    private void processOption(String option) {
        if (option.equals("t")) {
            searchTitles();
        } else if (option.equals("c")) {
            searchCast();
        } else if (option.equals("k")) {
            searchKeywords();
        } else if (option.equals("g")) {
            listGenres();
        } else if (option.equals("r")) {
            listHighestRated();
        } else if (option.equals("h")) {
            listHighestRevenue();
        } else {
            System.out.println("Invalid choice!");
        }
    }

    private void searchTitles() {
        System.out.print("Enter a title search term: ");
        String searchTerm = scanner.nextLine();

        // prevent case sensitivity
        searchTerm = searchTerm.toLowerCase();

        // arraylist to hold search results
        ArrayList<Movie> results = new ArrayList<>();

        // search through ALL movies in collection
        for (Movie movie : movies) {
            String movieTitle = movie.getTitle();
            movieTitle = movieTitle.toLowerCase();

            if (movieTitle.contains(searchTerm)) {
                //add the Movie objest to the results list
                results.add(movie);
            }
        }

        // sort the results by title
        sortResults(results);

        // now, display them all to the user
        for (int i = 0; i < results.size(); i++) {
            String title = results.get(i).getTitle();

            // this will print index 0 as choice 1 in the results list; better for user!
            int choiceNum = i + 1;

            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Movie selectedMovie = results.get(choice - 1);

        displayMovieInfo(selectedMovie);

        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void sortResults(ArrayList<Movie> listToSort) {
        for (int j = 1; j < listToSort.size(); j++) {
            Movie temp = listToSort.get(j);
            String tempTitle = temp.getTitle();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempTitle.compareTo(listToSort.get(possibleIndex - 1).getTitle()) < 0) {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void sortStrings(ArrayList<String> listToSort) {
        for (int j = 1; j < listToSort.size(); j++) {
            String itemToSort = listToSort.get(j);

            int possibleIndex = j;
            while (possibleIndex > 0 && itemToSort.compareTo(listToSort.get(possibleIndex - 1)) < 0) {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, itemToSort);
        }
    }

    private void sortByUserRating(ArrayList<Movie> listToSort) {
        for (int j = 1; j < listToSort.size(); j++) {
            Movie temp = listToSort.get(j);
            double tempNum = temp.getUserRating();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempNum > listToSort.get(possibleIndex - 1).getUserRating()) {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }

    private void sortByRevenue(ArrayList<Movie> listToSort) {
        for (int j = 1; j < listToSort.size(); j++) {
            Movie temp = listToSort.get(j);
            double tempNum = temp.getRevenue();

            int possibleIndex = j;
            while (possibleIndex > 0 && tempNum > listToSort.get(possibleIndex - 1).getRevenue()) {
                listToSort.set(possibleIndex, listToSort.get(possibleIndex - 1));
                possibleIndex--;
            }
            listToSort.set(possibleIndex, temp);
        }
    }


    private void displayMovieInfo(Movie movie) {
        System.out.println();
        System.out.println("Title: " + movie.getTitle());
        System.out.println("Tagline: " + movie.getTagline());
        System.out.println("Runtime: " + movie.getRuntime() + " minutes");
        System.out.println("Year: " + movie.getYear());
        System.out.println("Directed by: " + movie.getDirector());
        System.out.println("Cast: " + movie.getCast());
        System.out.println("Overview: " + movie.getOverview());
        System.out.println("User rating: " + movie.getUserRating());
        System.out.println("Box office revenue: " + movie.getRevenue());
    }

    private void searchCast() {
        System.out.print("Enter a person to search for (first or last name): ");
        String searchTerm = scanner.nextLine();
        searchTerm = searchTerm.toLowerCase();
        ArrayList<String> everyCast = new ArrayList<>();
        for (Movie movie : movies) {
            String cast = movie.getCast();
            String[] castArray = cast.split("\\|");
            for (String castMember : castArray) {
                if (!everyCast.contains(castMember)) {
                    everyCast.add(castMember);
                }
            }
        }
        ArrayList<String> results = new ArrayList<>();
        for (String castMember : everyCast) {
            if (castMember.toLowerCase().contains(searchTerm)) {
                results.add(castMember);
            }
        }
        sortStrings(results);
        for (int i = 0; i < results.size(); i++) {
            String castMember = results.get(i);
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + castMember);
        }
        System.out.println("Which person would you like to learn more about?");
        System.out.print("Enter number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        ArrayList<Movie> moviesList = new ArrayList<>();

        for (Movie movie : movies) {
            String cast = movie.getCast();
            String[] castArray = cast.split("\\|");
            for (String castMember : castArray) {
                if (castMember.equals(results.get(choice - 1))) {
                    moviesList.add(movie);
                }
            }
        }
        sortResults(moviesList);

        for (int i = 0; i < moviesList.size(); i++) {
            String title = moviesList.get(i).getTitle();
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + title);
        }
        System.out.println("Which movie that includes your actor would you like to learn more about?");
        System.out.print("Enter number: ");
        choice = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = moviesList.get(choice - 1);
        displayMovieInfo(selectedMovie);
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void searchKeywords() {
        System.out.print("Enter a keyword search term: ");
        String searchTerm = scanner.nextLine();
        searchTerm = searchTerm.toLowerCase();
        ArrayList<Movie> results = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getKeywords().toLowerCase().contains(searchTerm)) {
                results.add(movie);
            }
        }
        sortResults(results);
        for (int i = 0; i < results.size(); i++) {
            String title = results.get(i).getTitle();
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + title);
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = results.get(choice - 1);
        displayMovieInfo(selectedMovie);
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listGenres() {
        ArrayList<String> genres = new ArrayList<>();
        for (Movie movie : movies) {
            String movieGenre = movie.getGenres();
            String[] genreArray = movieGenre.split("\\|");
            for (String genre : genreArray) {
                if (!genres.contains(genre)) {
                    genres.add(genre);
                }
            }
        }
        sortStrings(genres);

        for (int i = 0; i < genres.size(); i++) {
            String title = genres.get(i);
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which would you like to see all movies for?");
        System.out.print("Enter number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        String selectedGenre = genres.get(choice - 1).toLowerCase();

        ArrayList<Movie> moviesList = new ArrayList<>();
        for (Movie movie : movies) {
            String movieGenre = movie.getGenres();
            String[] genreArray = movieGenre.split("\\|");
            for (String genre : genreArray) {
                if (genre.toLowerCase().equals(selectedGenre)) {
                    moviesList.add(movie);
                }
            }
        }

        sortResults(moviesList);
        for (int i = 0; i < moviesList.size(); i++) {
            String title = moviesList.get(i).getTitle();
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + title);
        }

        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");
        choice = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = moviesList.get(choice - 1);
        displayMovieInfo(selectedMovie);
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRated() {
        ArrayList<Movie> moviesClone = new ArrayList<>(movies);
        sortByUserRating(moviesClone);
        moviesClone.subList(50, moviesClone.size()).clear();
        for (int i = 0; i < moviesClone.size(); i++) {
            String title = moviesClone.get(i).getTitle();
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + title + ": " + moviesClone.get(i).getUserRating());
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = moviesClone.get(choice - 1);
        displayMovieInfo(selectedMovie);
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void listHighestRevenue() {
        ArrayList<Movie> moviesClone = new ArrayList<>(movies);
        sortByRevenue(moviesClone);
        moviesClone.subList(50, moviesClone.size()).clear();
        for (int i = 0; i < moviesClone.size(); i++) {
            String title = moviesClone.get(i).getTitle();
            int choiceNum = i + 1;
            System.out.println("" + choiceNum + ". " + title + ": $" + moviesClone.get(i).getRevenue());
        }
        System.out.println("Which movie would you like to learn more about?");
        System.out.print("Enter number: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        Movie selectedMovie = moviesClone.get(choice - 1);
        displayMovieInfo(selectedMovie);
        System.out.println("\n ** Press Enter to Return to Main Menu **");
        scanner.nextLine();
    }

    private void importMovieList(String fileName) {
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            movies = new ArrayList<Movie>();

            while ((line = bufferedReader.readLine()) != null) {
                // import all cells for a single row as an array of Strings,
                // then convert to ints as needed
                String[] movieFromCSV = line.split(",");

                // pull out the data for this cereal
                String title = movieFromCSV[0];
                String cast = movieFromCSV[1];
                String director = movieFromCSV[2];
                String tagline = movieFromCSV[3];
                String keywords = movieFromCSV[4];
                String overview = movieFromCSV[5];
                int runtime = Integer.parseInt(movieFromCSV[6]);
                String genres = movieFromCSV[7];
                double userRating = Double.parseDouble(movieFromCSV[8]);
                int year = Integer.parseInt(movieFromCSV[9]);
                int revenue = Integer.parseInt(movieFromCSV[10]);

                // create Cereal object to store values
                Movie nextMovie = new Movie(title, cast, director, tagline, keywords, overview, runtime, genres, userRating, year, revenue);

                // adding Cereal object to the arraylist
                movies.add(nextMovie);
            }
            bufferedReader.close();
        } catch (IOException exception) {
            // Print out the exception that occurred
            System.out.println("Unable to access " + exception.getMessage());
        }

    }

    // ADD ANY ADDITIONAL PRIVATE HELPER METHODS you deem necessary

}