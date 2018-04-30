
public final class RunMain {
	
	// prevents anyone from creating an object of this class
	private RunMain() {
	
	}
	
	// main method, which runs creates a Main object, and then runs its main
	// method
	public static void main(String[] args) {
		try {
			new Main().main();
		}
		catch (ArrayIndexOutOfBoundsException aioobx) {
			reportError("array index out of bounds");
		}
		catch (NullPointerException npx) {
			reportError("null-pointer exception");
		}
		catch (StringIndexOutOfBoundsException sioobx) {
			reportError("string index out of bounds");
		}
		catch (ClassCastException ccx) {
			reportError("illegal cast");
		}
		catch (NegativeArraySizeException nasz) {
			reportError("array size out of bounds");
		}
		catch (ArithmeticException ax) {
			if (ax.getMessage().equals("/ by zero")) {
				reportError("divide by zero");
			}
			else {
				reportError("arithmetic exception");
			}
		}
	}
	
	private static void reportError(String msg) {
		System.out.print("ERROR: "+msg+"\n");
		System.out.print("Program terminated.\n");
	}
	
}