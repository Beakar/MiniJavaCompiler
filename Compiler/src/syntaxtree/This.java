package syntaxtree;
import java.io.PrintStream;

import prettyPrinter.PrettyPrinter;
import visitor.Visitor;

/**
 * the expression, 'this'
 */
public class This extends Exp {

	/**
	 * constructor
	 * @param pos file position
	 */
	public This(int pos) {
		super(pos);
	}

	/*************** remaining methods are visitor- and display-related ****************/

	public Object accept(Visitor v) {
		return v.visitThis(this);
	}
	
	/*************** remaining methods are for pretty-print ****************/
	public void prettyPrint(PrettyPrinter pp, PrintStream ps) {
		ps.print("this"+typ(pp));
	}
}
