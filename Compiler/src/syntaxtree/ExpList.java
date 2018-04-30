package syntaxtree;

import java.util.List;

import visitor.Visitor;

/**
 * a list of expressions
 */
public class ExpList extends AstList<Exp> {
	
	public ExpList() {
		super();
	}
	
	public ExpList(List<Exp> lst) {
		super(lst);
	}
	
	public Object accept(Visitor v) {
		return v.visitExpList(this);
	}
	
}
