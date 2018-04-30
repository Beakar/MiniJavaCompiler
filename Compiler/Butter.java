// A test file that exercises several MiniJava features by doing
// some random things.
class Main {
    public void main() {
        new Jam().run();
    }
}

class Jam extends Toast {
    int x;
    String z;
    public void test(int q) {
        x = 3;
        z = "fun";
        for (int i = 0; i < q; i++) {
            boolean x = true;
            int y = 78;
            x = y > 0 && x;
            printInt(i+y);
            printStr(" ");
        }
        printStr("\n");
        x++;
        y++;
    }
}

class Toast extends Lib {
    int y;
    public void run() {
        Jam j = new Jam();
        j.test(33);
        y = j.y;
        y++;
        this.printInt(y);
        this.printStr("\n");
    }
}
