package syntaxtree;
import java.io.PrintStream;

import prettyPrinter.PrettyPrinter;
import visitor.Visitor;

/**
 * a 'break' statement
 */
public class Break extends Statement {


	// instance variables filled in during later phases
	public BreakTarget breakLink; // link to the while or switch the statement breaks out of

	/**
	 * constructor
	 * @param pos file position
	 */
	public Break(int pos) {
		super(pos);
		breakLink = null;
	}

	/*************** remaining methods are visitor- and display-related ****************/
	
	public Object accept(Visitor v) {
		return v.visitBreak(this);
	}

	//	method to give the elements we have links to
	public AstNode[] links() {
		return new AstNode[]{breakLink};
	}

	/*************** remaining methods are for pretty-print ****************/
	public void prettyPrint(PrettyPrinter pp, PrintStream ps) {
		pp.tab(ps);
		String xtn = this.breakLink != null && pp.printLinks() ? "@"+breakLink.uniqueId : "";
		ps.println("break"+xtn+";");
	}
}
