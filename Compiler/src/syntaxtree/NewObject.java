package syntaxtree;
import java.io.PrintStream;

import prettyPrinter.PrettyPrinter;
import treedisplay.TreeDisplayable;
import treedisplay.TreeDrawException;
import visitor.Visitor;

/**
 * a expression that creates a new object, as in "new Tree()"
 */
public class NewObject extends Exp {

	// instance variables filled in by constructor
	public IdentifierType objType; // the type of the object being created

	/**
	 * constructor
	 * @param pos file position
	 * @param atype the type of object being created
	 */
	public NewObject(int pos, IdentifierType atype) {
		super(pos);
		objType=atype;
	}

	/*************** remaining methods are visitor- and display-related ****************/

	public Object accept(Visitor v) {
		return v.visitNewObject(this);
	}

	public TreeDisplayable getDrawTreeSubobj(int n) throws TreeDrawException {
		switch (n) {
		case 0: return objType;
		}
		throw new TreeDrawException();
	}
	
	/*************** remaining methods are for pretty-print ****************/
	public void prettyPrint(PrettyPrinter pp, PrintStream ps) {
		ps.print("new"+" ");
		pp.print(this.objType, ps);
		ps.print("()"+typ(pp));
	}

}
