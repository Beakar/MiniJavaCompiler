package visitor;

import syntaxtree.*;

public class ASTvisitor extends InhVisitor {
	
	@Override
	public Object visitAstList(AstList lst) {
		for (int i = 0; i < lst.size(); i++) {
			Object obj = lst.elementAt(i);
			if (obj != null && obj instanceof AstNode) {
				((AstNode)obj).accept(this);
			}
		}
		return null;
	}
	
	@Override
	public Object visitAstNode(AstNode n) {
		return null;
	}
	
	@Override
	public Object visitArrayLookup(ArrayLookup n) {
		/**/super.visitArrayLookup(n);
		n.arrExp.accept(this);
		n.idxExp.accept(this);
		return null;
	}
	
	@Override
	public Object visitArrayType(ArrayType n) {
		/**/super.visitArrayType(n);
		n.baseType.accept(this);
		return null;
	}
	
	@Override
	public Object visitAssign(Assign n) {
		/**/super.visitAssign(n);
		n.lhs.accept(this);
		n.rhs.accept(this);
		return null;
	}
	
	@Override
	public Object visitBinExp(BinExp n) {
		/**/super.visitBinExp(n);
		n.left.accept(this);
		n.right.accept(this);
		return null;
	}
	
	@Override
	public Object visitBlock(Block n) {
		/**/super.visitBlock(n);
		n.stmts.accept(this);
		return null;
	}
	
	@Override
	public Object visitCast(Cast n) {
		/**/super.visitCast(n);
		n.castType.accept(this);
		n.exp.accept(this);
		return null;
	}
	
	@Override
	public Object visitCall(Call n) {
		/**/super.visitCall(n);
		n.obj.accept(this);
		n.parms.accept(this);
		return null;
	}
	
	@Override
	public Object visitCase(Case n) {
		/**/super.visitCase(n);
		n.exp.accept(this);
		return null;
	}
	
	@Override
	public Object visitClassDecl(ClassDecl n) {
		/**/super.visitClassDecl(n);
		n.decls.accept(this);
		return null;
	}
	
	@Override
	public Object visitCallStatement(CallStatement n) {
		/**/super.visitCallStatement(n);
		n.callExp.accept(this);
		return null;
	}
	
	@Override
	public Object visitIf(If n) {
		/**/super.visitIf(n);
		n.exp.accept(this);
		n.trueStmt.accept(this);
		n.falseStmt.accept(this);
		return null;
	}

	@Override
	public Object visitInstanceOf(InstanceOf n) {
		/**/super.visitInstanceOf(n);
		n.exp.accept(this);
		n.checkType.accept(this);
		return null;
	}

	
	@Override
	public Object visitInstVarAccess(InstVarAccess n) {
		/**/super.visitInstVarAccess(n);
		n.exp.accept(this);
		return null;
	}
	
	@Override
	public Object visitLocalDeclStatement(LocalDeclStatement n) {
		/**/super.visitLocalDeclStatement(n);
		n.localVarDecl.accept(this);
		return null;
	}

	
	public Object visitLocalVarDecl(LocalVarDecl n) {
		/**/super.visitLocalVarDecl(n);
	    /*Object rtnVal = visitVarDecl(n);*/
		n.initExp.accept(this);
		return null;
	}
	
	@Override
	public Object visitMethodDecl(MethodDecl n) {
		/**/super.visitMethodDecl(n);
		n.formals.accept(this);
		n.stmts.accept(this);
		return null;
	}
	
	@Override
	public Object visitMethodDeclNonVoid(MethodDeclNonVoid n) {
		/**/super.visitMethodDeclNonVoid(n);
		n.rtnType.accept(this);
		//Object rtnVal = visitMethodDecl(n);
		n.rtnExp.accept(this);
		return null;
	}
	
	@Override
	public Object visitNewArray(NewArray n) {
		/**/super.visitNewArray(n);
		n.objType.accept(this);
		n.sizeExp.accept(this);
		return null;
	}
	
	@Override
	public Object visitNewObject(NewObject n) {
		/**/super.visitNewObject(n);
		n.objType.accept(this);
		return null;
	}
	
	@Override
	public Object visitProgram(Program n) {
		/**/super.visitProgram(n);
		n.mainStatement.accept(this);
		n.classDecls.accept(this);
		return null;
	}
	
	@Override
	public Object visitSwitch(Switch n) {
		/**/super.visitSwitch(n);
		n.exp.accept(this);
		n.stmts.accept(this);
		return null;
	}
	
	@Override
	public Object visitUnExp(UnExp n) {
		/**/super.visitUnExp(n);
		n.exp.accept(this);
		return null;
	}
	
	@Override
	public Object visitVarDecl(VarDecl n) {
		/**/super.visitVarDecl(n);
		n.type.accept(this);
		return null;
	}
	
	@Override
	public Object visitWhile(While n) {
		/**/super.visitWhile(n);
		n.exp.accept(this);
		n.body.accept(this);
		return null;
	}
	

}
