package syntaxtree;
import visitor.Visitor;

/**
 * a MiniJava type (abstract)
 */
public abstract class Type extends AstNode {

	/**
	 * constructor
	 * @param pos file position
	 */
	public Type(int pos) {
		super(pos);
	}
	
	/*************** remaining methods are visitor- and display-related ****************/

	public Object accept(Visitor v) {
		return v.visitType(this);
	}
	public String toString() {
		return toString2();
	}
	public abstract String toString2();
}
