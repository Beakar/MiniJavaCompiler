package syntaxtree;

import visitor.Visitor;

/**
 * a statement that a break statement can break out of (e.g.,
 * 'switch' or 'while' (abstract)
 */
public abstract class BreakTarget extends Statement {
	
	// instance variables filled in during later phases
	public int stackHeight; // the height of the stack at the time the statement is reached

	/**
	 * constructor
	 * @param pos file position
	 */
	public BreakTarget(int pos) {
		super(pos);
		stackHeight = Integer.MIN_VALUE;
	}

	/*************** remaining methods are visitor- and display-related ****************/

	public Object accept(Visitor v) {
		return v.visitBreakTarget(this);
	}
	
	protected String[]stringsInDescr() {
		if (stackHeight == Integer.MIN_VALUE) {
			return super.stringsInDescr();
		}
		else {
			return strArrayPlus1(super.stringsInDescr(),
					"stackHeight="+stackHeight);
		}
	}
}
