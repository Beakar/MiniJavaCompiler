package syntaxtree;

import java.util.List;

import visitor.Visitor;

/**
 * a list of declarations
 */
public class DeclList extends AstList<Decl> {
	
	public DeclList() {
		super();
	}
		
	public DeclList(List<Decl> lst) {
		super(lst);
	}
	
	public Object accept(Visitor v) {
		return v.visitDeclList(this);
	}
	
	
}
