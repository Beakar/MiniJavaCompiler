package syntaxtree;
import visitor.Visitor;

import java.io.PrintStream;

import prettyPrinter.PrettyPrinter;
import treedisplay.*;

/**
 * a MiniJava program
 */
public class Program extends AstNode {
	
	// instance variables filled in by constructor
	public ClassDeclList classDecls; // the list of class declarations
	public Statement mainStatement; // the (call) statement that starts the execution

	/**
	 * constructor
	 * @param pos file position
	 * @param acl the program's list of class declarations
	 */
	public Program(int pos, ClassDeclList acl) {
		super(pos);
		IdentifierType mainType = new IdentifierType(-1, "Main"); 
		Exp newExp = new NewObject(-1, mainType);
		Call callExp = new Call(-1, newExp, "main", new ExpList());
		mainStatement = new CallStatement(-1, callExp);
		classDecls=acl; 
	}

	/*************** remaining methods are visitor- and display-related ****************/
	
	public Object accept(Visitor v) {
		return v.visitProgram(this);
	}

	public TreeDisplayable getDrawTreeSubobj(int n) throws TreeDrawException {
		switch (n) {
		case 0: return classDecls;
		case 1: return mainStatement;
		}
		throw new TreeDrawException();
	}
	
	/*************** remaining methods are for pretty-print ****************/
	public void prettyPrint(PrettyPrinter pp, PrintStream ps) {
		if (classDecls == null) {
			pp.tab(ps);
			System.out.println("??null??");
		}
		else {
			pp.print(classDecls, ps);
		}
	}

}
