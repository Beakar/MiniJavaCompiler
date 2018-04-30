package syntaxtree;
import java.io.PrintStream;

import prettyPrinter.PrettyPrinter;
import treedisplay.TreeDisplayable;
import treedisplay.TreeDrawException;
import visitor.Visitor;

/**
 * a cast expression, as in "(Car)myVehicle"
 */
public class Cast extends Exp {
	
	// instance variables filled in by constructor
	public Type castType; // the type being cast to
	public Exp exp; // the expression being cast

	/**
	 * constructor
	 * @param pos file position
	 * @param atype the type being cast to
	 * @param aexp the expression being cast
	 */
	public Cast(int pos, Type atype, Exp aexp) {
		super(pos);
		castType=atype;
		exp=aexp;
	}

	/*************** remaining methods are visitor- and display-related ****************/

	public Object accept(Visitor v) {
		return v.visitCast(this);
	}

	public TreeDisplayable getDrawTreeSubobj(int n) throws TreeDrawException {
		switch (n) {
		case 0: return castType;
		case 1: return exp;
		}
		throw new TreeDrawException();
	}
	
	/*************** remaining methods are for pretty-print ****************/
	public void prettyPrint(PrettyPrinter pp, PrintStream ps) {
		ps.print("((");
		pp.print(this.castType, ps);
		ps.print(")"+typ(pp));
		pp.print(this.exp, ps);
		ps.print(")");
	}
}
