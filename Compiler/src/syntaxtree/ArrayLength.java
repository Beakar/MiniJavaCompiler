package syntaxtree;
import java.io.PrintStream;

import prettyPrinter.PrettyPrinter;
import treedisplay.TreeDisplayable;
import treedisplay.TreeDrawException;
import visitor.Visitor;

/**
 * a array-length expression, as in "a.length"
 */
public class ArrayLength extends UnExp {

	/**
	 * constructor
	 * @param pos file position
	 * @param arrExp the operand
	 */
	public ArrayLength(int pos, Exp arrExp) {
		super(pos, arrExp);
	}

	/*************** remaining methods are visitor- and display-related ****************/
	
	public Object accept(Visitor v) {
		return v.visitArrayLength(this);
	}
	
	/*************** remaining methods are for pretty-print ****************/
	public void prettyPrint(PrettyPrinter pp, PrintStream ps) {
		pp.print(this.exp, ps);
		ps.print(".length"+typ(pp));
	}

}
