
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * Generates random Minesweeper input files for testing.
 */
public class InputGenerator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.print("Number of minefields: ");
        int fieldCount = scanner.nextInt();

        System.out.print("Rows per field: ");
        int rows = scanner.nextInt();

        System.out.print("Columns per field: ");
        int columns = scanner.nextInt();

        System.out.print("Mine percentage (0-100): ");
        double minePercentage = scanner.nextDouble();

        if (fieldCount < 1 || rows < 1 || columns < 1
                || minePercentage < 0 || minePercentage > 100) {
            System.out.println("Invalid input.");
            scanner.close();
            return;
        }

        try (PrintWriter writer = new PrintWriter("minefields.txt")) {

            for (int field = 0; field < fieldCount; field++) {
                writer.println(rows + " " + columns);

                char[][] minefield = new char[rows][columns];

                int totalCells = rows * columns;
                int mines = (int) Math.round(
                        totalCells * minePercentage / 100.0);

                // Initialize every cell as empty.
                for (int r = 0; r < rows; r++) {
                    for (int c = 0; c < columns; c++) {
                        minefield[r][c] = '.';
                    }
                }

                // Place mines randomly without duplicates.
                int placed = 0;
                while (placed < mines) {
                    int r = random.nextInt(rows);
                    int c = random.nextInt(columns);

                    if (minefield[r][c] != '*') {
                        minefield[r][c] = '*';
                        placed++;
                    }
                }

                // Write each row.
                for (int r = 0; r < rows; r++) {
                    writer.println(new String(minefield[r]));
                }
            }

            // Signal the end of input for Minesweeper.
            writer.println("0 0");

            System.out.println("Generated minefields.txt successfully.");

        } catch (IOException e) {
            System.out.println("Could not write file: " + e.getMessage());
        }

        scanner.close();
    }
}
