package syntaxtree;

import java.io.PrintStream;

import prettyPrinter.PrettyPrinter;
import treedisplay.TreeDisplayable;
import treedisplay.TreeDrawException;
import visitor.Visitor;

/**
 * a binary expression (abstract)
 */
public abstract class BinExp extends Exp {
	
	// instance variables filled in by constructor
	public Exp left; // the left operand
	public Exp right; // the right operand

	/**
	 * constructor
	 * @param pos file position
	 * @param ae1 left operand
	 * @param ae2 right operand
	 */
	public BinExp(int pos, Exp aleft, Exp aright) { 
		super(pos);
		left=aleft; right = aright;
	}

	/*************** remaining methods are visitor- and display-related ****************/

	public Object accept(Visitor v) {
		return v.visitBinExp(this);
	}

	public TreeDisplayable getDrawTreeSubobj(int n) throws TreeDrawException {
		switch (n) {
		case 0: return left;
		case 1: return right;
		}
		throw new TreeDrawException();
	}
	/*************** remaining methods are for pretty-print ****************/
	public abstract String opString();
	public void prettyPrint(PrettyPrinter pp, PrintStream ps) {
		ps.print("(");
		pp.print(this.left, ps);
		ps.print(this.opString());
		pp.print(this.right, ps);
		ps.print(")"+typ(pp));
	}
}