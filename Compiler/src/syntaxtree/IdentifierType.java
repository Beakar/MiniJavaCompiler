package syntaxtree;
import java.io.PrintStream;

import prettyPrinter.PrettyPrinter;
import visitor.Visitor;

/**
 * a named type (i.e., a class name)
 */
public class IdentifierType extends Type {
	
	// instance variables filled in by constructor
	public String name; // the name of the type

	// instance variables filled in during later phases
	public ClassDecl link; // the type declaration

	/**
	 * constructor
	 * @param pos file position
	 * @param aname the name of the type
	 */
	public IdentifierType(int pos, String aname) {
		super(pos);
		name=aname;
		link = null;
	}
	
	/**
	 * type equality
	 * @param the object tested for being equal to me
	 */
	@Override
	public boolean equals(Object obj) {
		return obj instanceof IdentifierType &&
				this.link != null &&
				this.link == ((IdentifierType)obj).link;
	} 

	/**
	 * hash code
	 * @return the object's hash code
	 */
	@Override
	public int hashCode() {
		if (name == null) {
			return 23977;
		}
		else return 826427*name.hashCode()+83473;
	}

	/*************** remaining methods are visitor- and display-related ****************/

	public Object accept(Visitor v) {
		return v.visitIdentifierType(this);
	}

	protected String[]stringsInDescr() {
		return strArrayPlus1(super.stringsInDescr(),name);
	}

	public AstNode[] links() {
		return new AstNode[]{link};
	}

	public String toString2() {
		return name;
	}
	/*************** remaining methods are for pretty-print ****************/
	public void prettyPrint(PrettyPrinter pp, PrintStream ps) {
 		String xtn = this.link != null && pp.printLinks() ? "@"+link.uniqueId : "";
 		ps.print(this.name+xtn);
	}
}
