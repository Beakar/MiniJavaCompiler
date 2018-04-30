package syntaxtree;

import java.util.List;
import java.util.Vector;
import java.io.PrintStream;

import prettyPrinter.PrettyPrinter;
import treedisplay.*;
import visitor.Visitor;

/**
 * a list of a given AST node type (abstract)
 * @param <T> the base type of the list
 */
public abstract class AstList<T> extends Vector<T> implements TreeDisplayable {

	/**
	 * constructor -- initializes to an empty list
	 */
	public AstList() {
		super();
	}

	/**
	 * constructor -- initializes with values pass in parameter
	 * @
	 * @param lst the list's initial elements
	 */
	public AstList(List<T> lst) {
		this();
		this.addAll(lst);
	}

	/**
	 * adds an element to the end of the list
	 * @param n the element to add
	 */
	public void addElement(T n) {
		super.addElement(n);
	}

	/**
	 * adds an element to the front of the list
	 * @param n the element to add
	 */
	public void addElementAtFront(T n) {
		super.insertElementAt(n, 0);
	}
	
	/*************** remaining methods are visitor- and display-related ****************/

	public abstract Object accept(Visitor v);

	public TreeDisplayable getDrawTreeSubobj(int n) throws TreeDrawException {
		try {
			return (TreeDisplayable)this.elementAt(n);
		}
		catch (Exception x) {
			throw new TreeDrawException();
		}
	}

	public TreeDisplayable[] getDrawTreeLinks() {
		return null;
	}

	public String shortDescription(Object auxData) {
		return "";
	}

	public String longDescription(Object auxData) {
		return shortDescription(auxData);
	}
	public boolean nodeIsList() {
		return true;
	}

	/*************** remaining methods are for pretty-print ****************/
	public void prettyPrint(PrettyPrinter pp, PrintStream ps) {
		for (Object node : this) {
			if (node instanceof AstNode) {
				pp.print((AstNode)node, ps);
			}
			else {
				ps.println("***???***");
			}
		}
	}
}
