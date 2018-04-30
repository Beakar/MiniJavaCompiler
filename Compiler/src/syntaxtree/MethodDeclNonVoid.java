package syntaxtree;

import java.io.PrintStream;

import prettyPrinter.PrettyPrinter;
import treedisplay.TreeDisplayable;
import treedisplay.TreeDrawException;
import visitor.Visitor;

/**
 * a method declaration that returns a value
 */
public class MethodDeclNonVoid extends MethodDecl {
	
	// instance variables filled in by constructor
	public Type rtnType; // the method's return-type
	public Exp rtnExp; // the method's return-expression

	/**
	 * constructor
	 * @param pos file position
	 * @param at the method's return type
	 * @param as the name being declared
	 * @param afl the list of formal parameter declarations
	 * @param asl the statements that are the method's body
	 * @param ae the method's return-expression
	 */
	public MethodDeclNonVoid(int pos, Type at, String as, VarDeclList afl,
			StatementList asl, Exp ae) {
		super(pos, as, afl, asl);
		rtnType=at; rtnExp=ae;
	}

	/*************** remaining methods are visitor- and display-related ****************/

	public Object accept(Visitor v) {
		return v.visitMethodDeclNonVoid(this);
	}

	public TreeDisplayable getDrawTreeSubobj(int n) throws TreeDrawException {
		switch (n) {
		case 0: return rtnType;
		case 1: return formals;
		case 2: return stmts;
		case 3: return rtnExp;
		}
		throw new TreeDrawException();
	}

	
	/*************** remaining methods are for pretty-print ****************/
	public void prettyPrint(PrettyPrinter pp, PrintStream ps) {
		pp.tab(ps);
		ps.print("public ");
		pp.print(this.rtnType, ps);
 		String xtn = pp.printLinks() ? "#"+this.uniqueId : "";
 		ps.print(" "+this.name+xtn+"(");
		String sep = "";
		if (this.formals == null) {
			ps.print("??null??");
		}
		else {
			for (VarDecl d : this.formals) {
				ps.print(sep);
				sep = ",";
				pp.print(d, ps);
			}
		}
		ps.println(") {");
		pp.indent();
		pp.print(this.stmts, ps);
		pp.tab(ps);
		ps.print("return ");
		pp.print(this.rtnExp, ps);
		ps.println(";");
		pp.unindent();
		pp.tab(ps);
		ps.println("}");
	}
}
