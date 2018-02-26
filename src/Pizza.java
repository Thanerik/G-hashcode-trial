import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Pizza {
    private static final int TOMAT = 1;
    private static final int MUSHROOM = 0;

    List<Slice> sliceList = new ArrayList<>();
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
        for (Slice slice : sliceList) {
            if(!validateSlice(slice)) {
                throw new IllegalStateException(slice + " not validated!");
            }
        }
    }

    boolean validateSlice(Slice slice) {
        int tomato = 0;
        int mushroom = 0;

        // H slice validator!
        if (slice.getSize() > H) {
            return false;
        }

        for (int c = slice.c1; c <= slice.c2; c++) {
            for (int r = slice.r1; r <= slice.r2; r++) {
                if (pizza[r][c] == TOMAT) tomato++;
                if (pizza[r][c] == MUSHROOM) mushroom++;
            }
            if(tomato >= L && mushroom >= L) return true;
        }

        return false;
    }

    void validatePizza() {
        boolean[][] validationPizza = new boolean[R][C];

        for (Slice slice :
                sliceList) {
            for (int c = slice.c1; c <= slice.c2; c++) {
                for (int r = slice.r1; r <= slice.r2; r++) {
                    if(validationPizza[r][c]) {
                        throw new IllegalStateException("Pizza is triggered twice in ["+r+", "+c+"] with "+slice);
                    }
                    validationPizza[r][c] = true;
                }
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

    void makeSlices() {
        Slice slice = new Slice(0,0,R-1,C-1);
        List<Slice> sliceList1 = cut(slice, true);
        List<Slice> sliceList2 = cut(slice, false);
        if(calcValue(sliceList1) > calcValue(sliceList2)) sliceList = sliceList1;
        else sliceList = sliceList2;
    }

    void printPizza() {
        for (int i = 0; i < R; i++) {
            System.out.println(Arrays.toString(pizza[i]));
        }
    }


    public List<Slice> cut(Slice slice, boolean horizontal) {
        if (this.validateSlice(slice)) {
            List<Slice> sliceList = new ArrayList<>();
            sliceList.add(slice);
            return sliceList;
        }
        if (slice.getSize() < H) return new ArrayList<>();

        int cuts;
        if (horizontal) cuts = slice.getNumberOfRows()-1;
        else cuts = slice.getNumberOfColumns()-1;

        int max = 0;
        List<Slice> best = new ArrayList<>();
        for (int i = 0; i < cuts; i++) {
            List<Slice> slices = slice.slice(i, horizontal);
            for (Slice smallSlice : slices) {
                List<Slice> recursiveResult = cut(smallSlice, !horizontal);
                int recursiveValue = calcValue(recursiveResult);
                if (recursiveValue > max) {
                    max = recursiveValue;
                    best = recursiveResult;
                }
            }


        }
        return best;
    }

    int calcValue(List<Slice> slices) {
        if (slices.size() == 0) return 0;
        int value = 0;
        for (Slice slice : slices) value += slice.getSize();
        return value;
    }

}
