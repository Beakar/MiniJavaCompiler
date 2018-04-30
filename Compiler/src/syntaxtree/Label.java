package syntaxtree;
import treedisplay.TreeDisplayable;
import treedisplay.TreeDrawException;
import visitor.Visitor;

/**
 * a label in a 'switch' statement (abstract)
 */
public abstract class Label extends Statement {

	// instance variables filled in during later phases
	public Switch enclosingSwitch; // the switch-statement associated with this label

	/**
	 * constructor
	 * @param pos file position
	 */
	public Label(int pos) {
		super(pos);
		enclosingSwitch=null;
	}

	/**
	 * the value, if any, that is associated with the label
	 * @return the value associated with the label
	 */
	public Exp labelValue() {
		return null;
	}

	/*************** remaining methods are visitor- and display-related ****************/

	public Object accept(Visitor v) {
		return v.visitLabel(this);
	}

	//	method to give the elements we have links to
	public AstNode[] links() {
		return new AstNode[]{enclosingSwitch};
	}

	public TreeDisplayable getDrawTreeSubobj(int n) throws TreeDrawException {
		switch (n) {
		}
		throw new TreeDrawException();
	}
}

