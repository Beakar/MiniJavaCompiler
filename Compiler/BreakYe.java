// simple test of the break statement
class Main extends Lib {
    public void main() {
	for (int j = 0; j < 20; j++) {
	    if (j == 12) break;
	    super.printInt(j);
	    super.printStr("\n");
	}
    }
}
