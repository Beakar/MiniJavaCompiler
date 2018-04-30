package syntaxtree;
import java.io.PrintStream;

import prettyPrinter.PrettyPrinter;
import treedisplay.TreeDisplayable;
import treedisplay.TreeDrawException;
import visitor.Visitor;

/**
 * a local variable declaration
 */
public class LocalVarDecl extends VarDecl {
	
	// instance variables filled in by constructor
	public Exp initExp; // the initializer expression

	/**
	 * constructor
	 * @param pos file position
	 * @param at the type of the variable
	 * @param as the name being declared
	 * @param initX the variable's initializer-expression
	 */
	public LocalVarDecl(int pos, Type at, String as, Exp initX) {
		super(pos, at, as);
		initExp = initX;
	}

	/*************** remaining methods are visitor- and display-related ****************/

	public Object accept(Visitor v) {
		return v.visitLocalVarDecl(this);
	}

	public TreeDisplayable getDrawTreeSubobj(int n) throws TreeDrawException {
		switch (n) {
		case 0: return type;
		case 1: return initExp;
		}
		throw new TreeDrawException();
	}
	/*************** remaining methods are for pretty-print ****************/
	public void prettyPrint(PrettyPrinter pp, PrintStream ps) {
		pp.print(this.type, ps);
 		String xtn = pp.printLinks() ? "#"+this.uniqueId : "";
 		ps.print(" "+this.name+xtn+" = ");
		pp.print(this.initExp, ps);
	}
}
