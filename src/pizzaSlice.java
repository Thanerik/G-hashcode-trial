public class pizzaSlice {

    private static final boolean DEBUG = true;

    public static void main(String[] args) {
        String file = "example";

        Pizza pizza = new Pizza();
        pizza.parsePizza("data/" + file + ".in");

        pizza.makeSlices();

        if (DEBUG) {
            pizza.printPizza();
            pizza.printSlices();
        }

        pizza.validateSlices();
        pizza.validatePizza();
        //pizza.outputPizza("output/" + file + ".out");
    }

}
