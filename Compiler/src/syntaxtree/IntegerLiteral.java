package syntaxtree;
import java.io.PrintStream;

import prettyPrinter.PrettyPrinter;
import visitor.Visitor;

/**
 * a expression that is an integer constant, as in "34"
 */
public class IntegerLiteral extends Exp {

	// instance variables filled in by constructor
	public int val; // the value denoted by the integer literal

	/**
	 * constructor
	 * @param pos file position
	 * @param aval the value denoted by this integer literal
	 */
	public IntegerLiteral(int pos, int aval) {
		super(pos);
		val=aval;
	}

	/*************** remaining methods are visitor- and display-related ****************/

	public Object accept(Visitor v) {
		return v.visitIntegerLiteral(this);
	}

	protected String[]stringsInDescr() {
		return strArrayPlus1(""+val,super.stringsInDescr());
	}
	
	/*************** remaining methods are for pretty-print ****************/
	public void prettyPrint(PrettyPrinter pp, PrintStream ps) {
		ps.print(this.val+typ(pp));
	}
}
