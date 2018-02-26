import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class pizzaSlice {

    private static final boolean DEBUG = true;
    private static final int TOMAT = 1;
    private static final int MUSHROOM = 0;

    static int[][] pizza;
    static int R;
    static int C;
    static int L;
    static int H;

    private static void parsePizza(String filename) {
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

    private static void printPizza() {
        for (int i = 0; i < R; i++) {
            System.out.println(Arrays.toString(pizza[i]));
        }
    }

    private static void outputPizza(String filename, List<Slice> slices) {
        try {
            final OutputStream os = new FileOutputStream(filename);
            final PrintStream printStream = new PrintStream(os);
            printStream.println(slices.size());
            for (Slice slice : slices) {
                printStream.println(slice.output());
            }
            printStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String file = "example";
        parsePizza("data/" + file + ".in");

        if(DEBUG) { printPizza(); }

        List<Slice> slices = makeSlices();

        outputPizza("output/" + file + ".out", slices);
    }

    private static List<Slice> makeSlices() {
        ArrayList<Slice> sliceList = new ArrayList<>();
        sliceList.add(new Slice(0, 0, 1, 1));
        sliceList.add(new Slice(2, 1, 2, 2));
        return sliceList;
    }

    private static class Slice {
        int r1;
        int c1;
        int r2;
        int c2;

        Slice(int r1, int c1, int r2, int c2) {
            this.r1 = r1;
            this.c1 = c1;
            this.r2 = r2;
            this.c2 = c2;
        }

        String output() {
            return r1 + " " + c1 + " " + r2 + " " + c2;
        }
    }
}
