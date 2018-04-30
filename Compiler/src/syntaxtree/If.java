package syntaxtree;
import java.io.PrintStream;

import prettyPrinter.PrettyPrinter;
import treedisplay.TreeDisplayable;
import treedisplay.TreeDrawException;
import visitor.Visitor;

/**
 * an 'if' statement
 */
public class If extends Statement {
	
	// instance variables filled in by constructor
	public Exp exp; // the test-expression
	public Statement trueStmt; // the statement that executes if the test is true
	public Statement falseStmt; // the statement that exeuctes if the test is false

	/**
	 * constructor
	 * @param pos file position
	 * @param aexp the if-statement's test-expression
	 * @param atrueStatement the statement that executes if test test is true
	 * @param afalseStatement the statement that executes if test test is false
	 */
	public If(int pos, Exp aexp, Statement atrueStmt, Statement afalseStmt) {
		super(pos);
		exp=aexp; trueStmt=atrueStmt; falseStmt=afalseStmt;
	}

	/*************** remaining methods are visitor- and display-related ****************/

	public Object accept(Visitor v) {
		return v.visitIf(this);
	}

	public TreeDisplayable getDrawTreeSubobj(int n) throws TreeDrawException {
		switch (n) {
		case 0: return exp;
		case 1: return trueStmt;
		case 2: return falseStmt;
		}
		throw new TreeDrawException();
	}
	
	/*************** remaining methods are for pretty-print ****************/
	public void prettyPrint(PrettyPrinter pp, PrintStream ps) {
		pp.tab(ps);
		ps.print("if (");
		pp.print(this.exp, ps);
		ps.println(")");
		pp.print(this.trueStmt, ps);
		pp.tab(ps);
		ps.println("else");
		pp.print(this.falseStmt, ps);
	}
}

