// Computes the fibonacci number of 14, using an iterative algorithm.

// main class -- contains method that performs insertions and deletions
class Main {
    
    // main method
    public void main() {

	// create lib object
	Lib lib = new Lib();
	
	// our input number
	int val = 14;

	// compute fibonacci number of 14
	int result = fib(val);

	// print the result
	lib.printStr("The fibonacci number of ");
	lib.printInt(val);
	lib.printStr(" is ");
	lib.printInt(result);
	lib.printStr(".\n");

    }

    // the fibonacci method
    public int fib(int n) {
	int prev = 1; // previous value
	int current = 0; // current value
	for (int i = 0; i < n; i++) { // iterate n times
	    int temp = current;
	    current = current + prev; // new current is sum
	    prev = temp; // new prev is previous current
	}
	return current; // return the value
    }
}

