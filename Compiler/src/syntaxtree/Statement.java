package syntaxtree;
import visitor.Visitor;

/**
 * a statement (abstract)
 */
public abstract class Statement extends AstNode {

	/**
	 * constructor
	 * @param pos file position
	 */
public Statement(int pos) {
		super(pos);
	}

/*************** remaining methods are visitor- and display-related ****************/

	public Object accept(Visitor v) {
		return v.visitStatement(this);
	}

}
