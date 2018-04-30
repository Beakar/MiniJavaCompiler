package syntaxtree;
import visitor.Visitor;

/**
 * a binary '-' expression
 */
public class Minus extends BinExp {

	/**
	 * constructor
	 * @param pos file position
	 * @param ae1 left operand
	 * @param ae2 right operand
	 */
	public Minus(int pos, Exp ae1, Exp ae2) {
		super(pos, ae1, ae2);
	}

	/*************** remaining methods are visitor- and display-related ****************/

	public Object accept(Visitor v) {
		return v.visitMinus(this);
	}

	/*************** remaining methods are for pretty-print ****************/
	public  String opString() { return "-";}
}
