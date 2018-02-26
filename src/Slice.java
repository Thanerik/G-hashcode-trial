import java.util.ArrayList;
import java.util.List;

public class Slice {
    int r1;
    int c1;
    int r2;
    int c2;
    int size;

    Slice(int r1, int c1, int r2, int c2) {
        this.r1 = r1;
        this.c1 = c1;
        this.r2 = r2;
        this.c2 = c2;
        this.size = computeSize();

        if(r1 > r2) { swapR(); }
        if(c1 > c2) { swapC(); }
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
        sb.append(", size=").append(size);
        sb.append('}');
        return sb.toString();
    }


    List<Slice> slice(int where, boolean horizontal) {
        ArrayList<Slice> slicelist = new ArrayList<>();
        if(horizontal) {
            slicelist.add(new Slice(r1, c1, where + r1, c2));
            slicelist.add(new Slice(where+r1+1, c1, r2, c2));
        }
        else {
            slicelist.add(new Slice(r1, c1, r2, where+c1));
            slicelist.add(new Slice(r1, where+c1+1, r2, c2));
        }

        return slicelist;
    }

    private int computeSize() {
       return getNumberOfRows() * getNumberOfColumns();
    }

    int getSize() {
        return size;
    }

    public int getNumberOfRows() {
        return r2-r1+1;
    }

    public int getNumberOfColumns() {
        return c2-c1+1;
    }
}
