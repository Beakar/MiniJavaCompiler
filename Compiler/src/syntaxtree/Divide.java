package syntaxtree;
import visitor.Visitor;

/**
 * a binary '/' expression
 */
public class Divide extends BinExp {

	/**
	 * constructor
	 * @param pos file position
	 * @param ae1 left operand
	 * @param ae2 right operand
	 */
	public Divide(int pos, Exp ae1, Exp ae2) { 
		super(pos, ae1, ae2);
	}

	/*************** remaining methods are visitor- and display-related ****************/

	public Object accept(Visitor v) {
		return v.visitDivide(this);
	}


	/*************** remaining methods are for pretty-print ****************/
	public  String opString() { return "/";}
}
