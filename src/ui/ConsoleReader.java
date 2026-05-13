package ui;

import java.util.Scanner;

public class ConsoleReader {
    private final Scanner scanner = new Scanner(System.in);

    public String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите целое число.");
            }
        }
    }

    public boolean readBoolean(String prompt) {
        while (true) {
            String input = readLine(prompt + " (да/нет): ").toLowerCase();
            if (input.equals("да") || input.equals("д")) return true;
            if (input.equals("нет") || input.equals("н")) return false;
            System.out.println("Введите 'да' или 'нет'.");
        }
    }

    public void close() {
        scanner.close();
    }
}
