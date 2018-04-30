package syntaxtree;
import java.io.PrintStream;

import prettyPrinter.PrettyPrinter;
import treedisplay.TreeDisplayable;
import treedisplay.TreeDrawException;
import visitor.Visitor;

/**
 * an array-access expression, as in a[i]
 */
public class ArrayLookup extends Exp {

	// instance variables filled in by constructor
	public Exp arrExp; // the array-expression
	public Exp idxExp; // the index-expression

	/**
	 * constructor
	 * @param pos file position
	 * @param aarrExp the expression before the brackets
	 * @param aidxExp the expression inside the brackets
	 */
	public ArrayLookup(int pos, Exp aarrExp, Exp aidxExp) {
		super(pos);
		arrExp=aarrExp; idxExp=aidxExp;
	}

	/*************** remaining methods are visitor- and display-related ****************/

	public Object accept(Visitor v) {
		return v.visitArrayLookup(this);
	}

	public TreeDisplayable getDrawTreeSubobj(int n) throws TreeDrawException {
		switch (n) {
		case 0: return arrExp;
		case 1: return idxExp;
		}
		throw new TreeDrawException();
	}

	
	/*************** remaining methods are for pretty-print ****************/
	public void prettyPrint(PrettyPrinter pp, PrintStream ps) {
		pp.print(this.arrExp, ps);
		ps.print("[");
		pp.print(this.idxExp, ps);
		ps.print("]"+typ(pp));
	}

}
