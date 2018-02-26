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

    public static void main(String[] args) {
        String file = "example";
        parsePizza("data/" + file + ".in");

        if (DEBUG) {
            printPizza();
        }

        List<Slice> slices = makeSlices();

        validateSlices(slices);
        outputPizza("output/" + file + ".out", slices);
    }

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

    private static List<Slice> makeSlices() {
        ArrayList<Slice> sliceList = new ArrayList<>();
        sliceList.add(new Slice(0, 0, 1, 1));
        sliceList.add(new Slice(2, 1, 2, 2));
        return sliceList;
    }

    private static void validateSlices(List<Slice> slices) {
        boolean[][] testPizza = new boolean[R][C];

        for (Slice slice :
                slices) {
            if (slice.c1 > slice.c2) {
                slice.swapC();
            }
            if (slice.r1 > slice.r2) {
                slice.swapR();
            }

            boolean tomato = false;
            boolean mushroom = false;

            for (int c = slice.c1; c < slice.c2; c++) {
                for (int r = slice.r1; r < slice.r2; r++) {
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

        public void swapC() {
            int temp = c1;
            this.c1 = c2;
            this.c2 = temp;
        }

        public void swapR() {
            int temp = r1;
            this.r1 = r2;
            this.r2 = temp;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Slice{");
            sb.append("r1=").append(r1);
            sb.append(", c1=").append(c1);
            sb.append(", r2=").append(r2);
            sb.append(", c2=").append(c2);
            sb.append('}');
            return sb.toString();
        }
    }
}
