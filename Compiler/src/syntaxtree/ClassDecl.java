package syntaxtree;
import treedisplay.TreeDisplayable;
import treedisplay.TreeDrawException;
import visitor.Visitor;

import java.io.PrintStream;
import java.util.*;

import prettyPrinter.PrettyPrinter;

/**
 * a class declaration
 */
public class ClassDecl extends Decl {
	// instance variables filled in by constructor
	public String superName; // superclass name
	public DeclList decls; // list of declarations

	// instance variables filled in during later phases
	public ClassDecl superLink; // link to superclass declaration
	public ClassDeclList subclasses; // list of subclasses
	public Hashtable<String,InstVarDecl> instVarTable; // symbol table for instance variables
	public Hashtable<String,MethodDecl> methodTable; // symbol table for methods
	public int numDataInstVars; // number of non-object instance variables
	public int numObjInstVars; // number of object instance variables

	/**
	 * constructor
	 * @param pos file position
	 * @param aname the name being declared
	 * @param asuperName the name of the superclass
	 * @param aDeclList the list of declarations inside the class
	 */
	public ClassDecl(int pos, String aname, String asuperName, 
			DeclList aDeclList) {
		super(pos, aname);
		superName=asuperName; decls = aDeclList;
		superLink = null;
		subclasses = new ClassDeclList();
		instVarTable = new Hashtable<String,InstVarDecl>();
		methodTable = new Hashtable<String,MethodDecl>();
	}

	/*************** remaining methods are visitor- and display-related ****************/
	
	public Object accept(Visitor v) {
		return v.visitClassDecl(this);
	}

	public TreeDisplayable getDrawTreeSubobj(int n) throws TreeDrawException {
		switch (n) {
		case 0: return decls;
		}
		throw new TreeDrawException();
	}

	protected String[]stringsInDescr() {
		return strArrayPlus1(super.stringsInDescr(), superName);
	}

	public AstNode[] links() {
		return new AstNode[]{superLink};
	}
	


	/*************** remaining methods are for pretty-print ****************/
	public void prettyPrint(PrettyPrinter pp, PrintStream ps) {
		pp.tab(ps);
 		if (pp.printLinks()) {
 			String xtnStr = this.superLink == null ? "" : "@"+superLink.uniqueId;
 			ps.println("class "+this.name+"#"+this.uniqueId+" extends "+superName+xtnStr+" {");
 		}
 		else {
 			ps.println("class "+this.name+" extends "+superName+ " {");
 		}
		pp.indent();
		pp.print(decls, ps);
		pp.unindent();
		pp.tab(ps);
		ps.println("}");
	}
}
