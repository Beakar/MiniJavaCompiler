package syntaxtree;
import treedisplay.TreeDisplayable;
import treedisplay.TreeDrawException;
import visitor.Visitor;

/**
 * a variable declaration (abstract)
 */
public abstract class VarDecl extends Decl {
	
	// instance variables filled in by constructor
	public Type type; // the type of the variable being declared

	// instance variables filled in during later phases
	public int offset; // the variable's stack (or object) offset

	/**
	 * constructor
	 * @param pos file position
	 * @param atype the type of the variable
	 * @param aname the name being declared
	 */
	public VarDecl(int pos, Type atype, String aname) {
		super(pos, aname);
		type=atype;
		offset = Integer.MIN_VALUE;
	}

	/*************** remaining methods are visitor- and display-related ****************/

	public Object accept(Visitor v) {
		return v.visitVarDecl(this);
	}

	public TreeDisplayable getDrawTreeSubobj(int n) throws TreeDrawException {
		switch (n) {
		case 0: return type;
		}
		throw new TreeDrawException();
	}

	protected String[]stringsInDescr() {
		if (offset == Integer.MIN_VALUE) {
			return super.stringsInDescr();
		}
		else {
			return strArrayPlus1(super.stringsInDescr(),
					"offset="+offset);
		}
	}
}
