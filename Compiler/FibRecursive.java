// Computes the fibonacci number of 14, using a (very inefficient) recursive
// algorithm.

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
	// set up for base case
	int rtnVal = n;
	if (n > 1) {
	    // not base case: make recursive call
	    rtnVal = fib(n-1) + fib(n-2);
	}
	return rtnVal;
    }
}

