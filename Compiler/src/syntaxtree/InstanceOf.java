package syntaxtree;
import java.io.PrintStream;

import prettyPrinter.PrettyPrinter;
import treedisplay.TreeDisplayable;
import treedisplay.TreeDrawException;
import visitor.Visitor;

/**
 * a 'instanceof' expression
 */
public class InstanceOf extends Exp {
	
	// instance variables filled in by constructor
	public Exp exp; // the expression being tested
	public Type checkType; // the type being checked against

	/**
	 * constructor
	 * @param pos file position
	 * @param aexp the expression being tested
	 * @param atype the type being tested against
	 */
	public InstanceOf(int pos, Exp aexp, Type atype) {
		super(pos);
		exp=aexp;
		checkType=atype;
	}

	/*************** remaining methods are visitor- and display-related ****************/

	public Object accept(Visitor v) {
		return v.visitInstanceOf(this);
	}

	public TreeDisplayable getDrawTreeSubobj(int n) throws TreeDrawException {
		switch (n) {
		case 0: return exp;
		case 1: return checkType;
		}
		throw new TreeDrawException();
	}
	/*************** remaining methods are for pretty-print ****************/
	public void prettyPrint(PrettyPrinter pp, PrintStream ps) {
		ps.print("(");
		pp.print(this.exp, ps);
		ps.print(" instanceof "+typ(pp));
		pp.print(this.checkType, ps);
		ps.print(")");
	}
}
