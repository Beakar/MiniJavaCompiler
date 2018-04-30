package syntaxtree;

import visitor.Visitor;
import java.util.List;

/**
 * a list of class declaration
 */
public class ClassDeclList extends AstList<ClassDecl> {
	
	public ClassDeclList() {
		super();
	}
	
	public ClassDeclList(List<ClassDecl> lst) {
		super(lst);
	}
	
	public Object accept(Visitor v) {
		return v.visitClassDeclList(this);
	}
	
}
