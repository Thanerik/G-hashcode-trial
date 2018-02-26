public class Slice {
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
