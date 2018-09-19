/* Implementation of a class representing parser for the given input. */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

public class Parser {
    private LinkedList<Process> processList;
    private int[] qArr;
    private Scanner input;

    public Parser (String filePath) {
        this.processList = new LinkedList<>();
        qArr = null;

        if (filePath == null)
            input = new Scanner(System.in);

        else {
            File file = new File(filePath);

            try {
                input = new Scanner(file);
            }

            catch (FileNotFoundException e) {
                input = null;
            }
        }
    }

    public LinkedList<Process> getProcessList() {
        return processList;
    }

    public int[] getQArr() {
        return qArr;
    }

    public void parse () {
        if (input == null) {
            System.out.println("Plik z danymi nie jest dostępny.");
            processList = null;
        }

        else {
            int n;
            String s;
            int lineNumber = 0;

            if (input.hasNextLine()) {
                s = input.nextLine();
                lineNumber++;
            }

            else {
                processList = null;
                System.out.println("Błąd w wierszu 0: Puste wejście.");
                return;
            }

            if (s.matches("[0-9]+"))
                n = Integer.parseInt(s);

            else {
                processList = null;
                System.out.println("Błąd w wierszu 1: Wiersz zawiera znaki nie będące cyframi.");
                return;
            }

            if (n == 0) {
                processList = null;
                System.out.println("Błąd w wierszu 1: Liczba procesów jest równa 0.");
                return;
            }

            for (int i = 0; i < n; i++) {
                if (input.hasNextLine()) {
                    s = input.nextLine();
                    lineNumber++;
                }

                else {
                    processList = null;
                    System.out.println("Błąd w wierszu " +
                            Integer.toString(lineNumber) +
                            ": Koniec danych (za mało wierszy).");

                    return;
                }

                if (s.matches("[0-9]+ [0-9]+")) {
                    String[] num = s.split(" ");
                    int n1 = Integer.parseInt(num[0]);
                    int n2 = Integer.parseInt(num[1]);
                    processList.add(new Process(i, n1, n2));
                }

                else {
                    processList = null;

                    if (!s.replaceAll("[0-9]| ", "").isEmpty()) {
                        System.out.println("Błąd w wierszu " +
                                Integer.toString(lineNumber) +
                                ": Wiersz zawiera znaki nie będące cyframi lub spacjami.");
                    }

                    else {
                        System.out.println("Błąd w wierszu " +
                                Integer.toString(lineNumber) +
                                ": Wiersz zawiera za dużo liczb lub spacji.");
                    }

                    return;
                }
            }

            if (input.hasNextLine()) {
                s = input.nextLine();
                lineNumber++;
            }

            else {
                processList = null;
                System.out.println("Błąd w wierszu " +
                        Integer.toString(lineNumber) +
                        ": Koniec danych (za mało wierszy).");

                return;
            }

            if (s.matches("[0-9]"))
                n = Integer.parseInt(s);

            else {
                processList = null;
                System.out.println("Błąd w wierszu " +
                        Integer.toString(lineNumber) +
                        ": Wiersz zawiera znaki nie będące cyframi.");
                return;
            }

            if (n == 0) {
                processList = null;
                System.out.println("Błąd w wierszu " +
                        Integer.toString(lineNumber) +
                        ": Liczba strategii RR jest równa 0.");
                return;
            }

            if (input.hasNextLine()) {
                s = input.nextLine();
                lineNumber++;
            }

             else {
                processList = null;
                System.out.println("Błąd w wierszu " +
                        Integer.toString(lineNumber) +
                        ": Koniec danych (za mało wierszy).");

                return;
            }

            s += " ";

            if (s.matches("([0-9]+ ){" + Integer.toString(n) + "}")) {
                qArr = new int[n];
                String[] num = s.split(" ");

                for (int i = 0; i < n; i++) {
                    qArr[i] = Integer.parseInt(num[i]);
                }
            }

            else {
                processList = null;

                if (!s.replaceAll("[0-9]| ", "").isEmpty()) {
                    System.out.println("Błąd w wierszu " +
                            Integer.toString(lineNumber) +
                            ": Wiersz zawiera znaki nie będące cyframi lub spacjami.");
                }

                else {
                    System.out.println("Błąd w wierszu " +
                            Integer.toString(lineNumber) +
                            ": Wiersz nie zawiera dokładnie " +
                            Integer.toString(n) +
                            " liczb lub zawiera za dużo spacji.");
                }
            }
        }
    }
}
