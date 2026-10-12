
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class InputGenerator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.print("Number of rows: ");
        int rows = scanner.nextInt();

        System.out.print("Number of columns: ");
        int columns = scanner.nextInt();

        System.out.print("Percentage of mines (0-100): ");
        double minePercentage = scanner.nextDouble();

        scanner.nextLine(); // Consume the leftover newline

        System.out.print("Output filename: ");
        String filename = scanner.nextLine();

        if (rows < 1 || columns < 1
                || minePercentage < 0
                || minePercentage > 100) {
            System.out.println("Invalid input.");
            scanner.close();
            return;
        }

        char[][] minefield = new char[rows][columns];

        int totalCells = rows * columns;
        int numberOfMines =
                (int) Math.round(totalCells * minePercentage / 100.0);

        // Initialize every cell as empty.
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                minefield[row][column] = '.';
            }
        }

        // Place mines in randomly selected empty cells.
        int minesPlaced = 0;

        while (minesPlaced < numberOfMines) {
            int row = random.nextInt(rows);
            int column = random.nextInt(columns);

            if (minefield[row][column] != '*') {
                minefield[row][column] = '*';
                minesPlaced++;
            }
        }

        // Write the minefield to a file.
        try (PrintWriter writer = new PrintWriter(filename)) {
            writer.println(rows + " " + columns);

            for (int row = 0; row < rows; row++) {
                writer.println(new String(minefield[row]));
            }

            System.out.println("Minefield saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }

        scanner.close();
    }
}
