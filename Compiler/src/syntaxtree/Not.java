package syntaxtree;
import java.io.PrintStream;

import prettyPrinter.PrettyPrinter;
import visitor.Visitor;

/**
 * a unary '!' expression
 */
public class Not extends UnExp {

	/**
	 * constructor
	 * @param pos file position
	 * @param ae the operand
	 */
	public Not(int pos, Exp ae) {
		super(pos, ae); 
	}
	
	/*************** remaining methods are visitor- and display-related ****************/
	
	public Object accept(Visitor v) {
		return v.visitNot(this);
	}
	
	/*************** remaining methods are for pretty-print ****************/
	public void prettyPrint(PrettyPrinter pp, PrintStream ps) {
		ps.print("(!"+typ(pp));
		pp.print(this.exp, ps);
		ps.print(")");
	}
}
