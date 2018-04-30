package syntaxtree;
import java.io.PrintStream;

import prettyPrinter.PrettyPrinter;
import visitor.Visitor;

/**
 * the expression 'super'
 */
public class Super extends Exp {

	/**
	 * constructor
	 * @param pos file position
	 */
	public Super(int pos) {
		super(pos);
	}
	
	/*************** remaining methods are visitor- and display-related ****************/

	public Object accept(Visitor v) {
		return v.visitSuper(this);
	}
	
	/*************** remaining methods are for pretty-print ****************/
	public void prettyPrint(PrettyPrinter pp, PrintStream ps) {
		ps.print("super");
	}
}
