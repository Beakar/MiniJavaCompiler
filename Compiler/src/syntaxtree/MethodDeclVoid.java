package syntaxtree;
import java.io.PrintStream;

import prettyPrinter.PrettyPrinter;
import visitor.Visitor;

/**
 * a method declaration that does not return a value
 */
public class MethodDeclVoid extends MethodDecl {

	/**
	 * constructor
	 * @param pos file position
	 * @param as the name being declared
	 * @param afl the list of formal parameter declarations
	 * @param asl the statements that are the method's body
	 */
	public MethodDeclVoid(int pos, String as, VarDeclList afl,
			StatementList asl) {
		super(pos, as, afl, asl);
	}

	/*************** remaining methods are visitor- and display-related ****************/

	public Object accept(Visitor v) {
		return v.visitMethodDeclVoid(this);
	}

	/*************** remaining methods are for pretty-print ****************/
	public void prettyPrint(PrettyPrinter pp, PrintStream ps) {
		pp.tab(ps);
 		String xtn = pp.printLinks() ? "#"+this.uniqueId : "";
 		ps.print("public void "+this.name+xtn+"(");
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
		pp.unindent();
		pp.tab(ps);
		ps.println("}");
	}
}
