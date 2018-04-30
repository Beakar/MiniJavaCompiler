package syntaxtree;
import java.io.PrintStream;

import prettyPrinter.PrettyPrinter;
import treedisplay.TreeDisplayable;
import treedisplay.TreeDrawException;
import visitor.Visitor;

/**
 * an assignment statement
 */
public class Assign extends Statement {

	// instance variables filled in by constructor
	public Exp lhs; // the left operand
	public Exp rhs; // the right operand

	/**
	 * constructor
	 * @param pos file position
	 * @param alhs the left operand
	 * @param arhs the right operand
	 */
	public Assign(int pos, Exp alhs, Exp arhs) {
		super(pos);
		lhs = alhs;
		rhs = arhs;
	}

	/*************** remaining methods are visitor- and display-related ****************/
	
	public Object accept(Visitor v) {
		return v.visitAssign(this);
	}

	public TreeDisplayable getDrawTreeSubobj(int n) throws TreeDrawException {
		switch (n) {
		case 0: return lhs;
		case 1: return rhs;
		}
		throw new TreeDrawException();
	}

	
	/*************** remaining methods are for pretty-print ****************/
	public void prettyPrint(PrettyPrinter pp, PrintStream ps) {
		pp.tab(ps);
		pp.print(this.lhs, ps);
		ps.print(" = ");
		pp.print(this.rhs, ps);
		ps.println(";");
	}
}

