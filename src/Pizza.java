import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Pizza {
    private static final int TOMAT = 1;
    private static final int MUSHROOM = 0;

    ArrayList<Slice> sliceList = new ArrayList<>();
    static int[][] pizza;
    int R;
    int C;
    int L;
    int H;

    public void parsePizza(String filename) {
        File file = new File(filename);
        try {
            Scanner sc = new Scanner(file);

            R = sc.nextInt();
            C = sc.nextInt();
            L = sc.nextInt();
            H = sc.nextInt();

            pizza = new int[R][C];
            sc.nextLine();

            for (int r = 0; r < R; r++) {
                String pizzaRow = sc.nextLine();
                for (int c = 0; c < C; c++) {
                    char type = pizzaRow.charAt(c);
                    if (type == 'T') {
                        pizza[r][c] = TOMAT;
                    } else if (type == 'M') {
                        pizza[r][c] = MUSHROOM;
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    void validateSlices() {
        boolean[][] testPizza = new boolean[R][C];

        for (Slice slice :
                sliceList) {
            if (slice.c1 > slice.c2) {
                slice.swapC();
            }
            if (slice.r1 > slice.r2) {
                slice.swapR();
            }

            boolean tomato = false;
            boolean mushroom = false;

            for (int c = slice.c1; c <= slice.c2; c++) {
                for (int r = slice.r1; r <= slice.r2; r++) {
                    // Pizza at most one slice validator!
                    if (testPizza[r][c]) {
                        throw new IllegalStateException("Pizza [" + r + "," + c + "] is triggered twice!");
                    }
                    testPizza[r][c] = true;
                    if (pizza[r][c] == TOMAT) tomato = true;
                    if (pizza[r][c] == MUSHROOM) mushroom = true;
                }
            }

            // Tomato and mushroom validator!
            if (!tomato || !mushroom) {
                throw new IllegalStateException("There are not at least one tomato and one mushroom in " + slice);
            }

            // H slice validator!
            int size = (1+(slice.c2-slice.c1)) * (1+(slice.r2-slice.r1));
            if(size > H) {
                throw new IllegalStateException(slice+" is bigger than H!");
            }
        }
    }

    void printSlices() {
        for (Slice slice : sliceList) {
            System.out.println(slice);
        }
    }

    void outputPizza(String filename) {
        try {
            final OutputStream os = new FileOutputStream(filename);
            final PrintStream printStream = new PrintStream(os);
            printStream.println(sliceList.size());
            for (Slice slice : sliceList) {
                printStream.println(slice.output());
            }
            printStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    List<Slice> makeSlices() {
        return new ArrayList<>();
    }

    void printPizza() {
        for (int i = 0; i < R; i++) {
            System.out.println(Arrays.toString(pizza[i]));
        }
    }
}
