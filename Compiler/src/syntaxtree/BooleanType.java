package syntaxtree;
import java.io.PrintStream;

import prettyPrinter.PrettyPrinter;
import visitor.Visitor;

/**
 * the type 'boolean'
 */
public class BooleanType extends Type {

	/**
	 * constructor
	 * @param pos file position
	 */
	public BooleanType(int pos) {
		super(pos);
	}
	
	/**
	 * type equality
	 * @param the object tested for being equal to me
	 */
	@Override
	public boolean equals(Object obj) {
		return obj instanceof BooleanType;
	} 

	/**
	 * hash code
	 * @return the object's hash code
	 */
	@Override
	public int hashCode() {
		return 327236434;
	}

	/*************** remaining methods are visitor- and display-related ****************/

	public Object accept(Visitor v) {
		return v.visitBooleanType(this);
	}

	public String toString2() {
		return "boolean";
	}
	
	/*************** remaining methods are for pretty-print ****************/
	public void prettyPrint(PrettyPrinter pp, PrintStream ps) {
		ps.print("boolean");
	}
}
