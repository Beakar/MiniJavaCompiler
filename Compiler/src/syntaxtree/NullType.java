package syntaxtree;
import java.io.PrintStream;

import prettyPrinter.PrettyPrinter;
import visitor.Visitor;

/**
 * the type of the expression 'null'
 */
public class NullType extends Type {

	/**
	 * constructor
	 * @param pos file position
	 */
	public NullType(int pos) {
		super(pos);
	}

	/**
	 * type equality
	 * @param the object tested for being equal to me
	 */
	@Override
	public boolean equals(Object obj) {
		return obj instanceof NullType;
	}

	/**
	 * hash code
	 * @return the object's hash code
	 */
	@Override
	public int hashCode() {
		return 7326834;
	}

	/*************** remaining methods are visitor- and display-related ****************/

	public Object accept(Visitor v) {
		return v.visitNullType(this);
	}

	public String toString2() {
		return "(null type)";
	}
	/*************** remaining methods are for pretty-print ****************/
	public void prettyPrint(PrettyPrinter pp, PrintStream ps) {
		ps.print("#null-type");
	}
	
}
