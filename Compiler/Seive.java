// computes prime numbers using the Sieve of Eratosthenes, after prompting
// user for a number

// main class - gets computation going
class Main {
    public void main() {
	new Seive().exec();
    }
}

// class that computes the primes
class Seive extends Lib {
    
    // main method for computing primes
    public void exec() {
	
	// prompt user for limit, and read what is typed
	printStr("Find primes through: ");
	int num = readInt();
	
	// ensure that number is not negative
	int n = num;
	if (n < 0) {
	    n = 0;
	}
	
	////////////////////////////////
	// compute the primes
	////////////////////////////////
	
	// array that tells whether number is composite
	boolean[] seive = new boolean[n+1];
	
	// run through all candidates "seive-ing" when we find a prime
	for (int i = 2; i <= n; i++) {
	    if (!seive[i]) { // prime, so need to "seive"
		for (int j = 2*i; j < n; j = j + i) {
		    seive[j] = true; // mark composite
		}
	    }
	}
	
	////////////////////////////////
	// print the primes
	////////////////////////////////
	
	// print initial line
	printStr("primes up through ");
	printInt(n);
	printStr(":\n");
	
	// print primes, 20 per line
	int elementsOnLine = 0; // number of elements on current line
	for (int i = 2; i < n; i++) {
	    if (!seive[i]) {
		// found prime, so print it
		printInt(i);
		elementsOnLine++; // bump elements on line
		if (elementsOnLine >= 20) {
		    // reached line limit; print newline and reset
		    printStr("\n");
		    elementsOnLine = 0;
		}
		else {
		    // did not reach line limit; print space-separator
		    printStr(" ");
		}
	    }
	}
	
	// print final newline
	printStr("\n");
    }
}
