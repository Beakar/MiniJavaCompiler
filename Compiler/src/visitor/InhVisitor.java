package visitor;

import syntaxtree.*;

public abstract class InhVisitor implements Visitor {
	
	public final Object visit(AstNode n) {
		return n.accept(this);
	}

	public abstract Object visitAstNode(AstNode n);
	
	public abstract Object visitAstList(AstList n);
	
	@Override
	public Object visitAnd(And n) {
		return visitBinExp(n);
	}
	@Override
	public Object visitArrayLength(ArrayLength n) {
		return visitUnExp(n);
	}
	@Override
	public Object visitArrayLookup(ArrayLookup n) {
		return visitExp(n);
	}
	@Override
	public Object visitArrayType(ArrayType n) {
		return visitType(n);
	}
	@Override
	public Object visitAssign(Assign n) {
		return visitStatement(n);
	}
	@Override
	public Object visitBlock(Block n) {
		return visitStatement(n);
	}
	@Override
	public Object visitBinExp(BinExp n) {
		return visitExp(n);
	}
	@Override
	public Object visitBooleanType(BooleanType n) {
		return visitType(n);
	}
	@Override
	public Object visitBreak(Break n) {
		return visitStatement(n);
	}
	@Override
	public Object visitBreakTarget(BreakTarget n) {
		return visitStatement(n);
	}
	@Override
	public Object visitCall(Call n) {
		return visitExp(n);
	}
	@Override
	public Object visitCast(Cast n) {
		return visitExp(n);
	}
	@Override
	public Object visitCase(Case n) {
		return visitLabel(n);
	}
	@Override
	public Object visitClassDecl(ClassDecl n) {
		return visitDecl(n);
	}
	@Override
	public Object visitDecl(Decl n) {
		return visitAstNode(n);
	}
	@Override
	public Object visitDefault(Default n) {
		return visitLabel(n);
	}
	@Override
	public Object visitDivide(Divide n) {
		return visitBinExp(n);
	}
	@Override
	public Object visitEquals(Equals n) {
		return visitBinExp(n);
	}
	@Override
	public Object visitExp(Exp n) {
		return visitAstNode(n);
	}
	@Override
	public Object visitCallStatement(CallStatement n) {
		return visitStatement(n);
	}
	@Override
	public Object visitFalse(False n) {
		return visitExp(n);
	}
	@Override
  	public Object visitFormalDecl(FormalDecl n) {
		return visitVarDecl(n);
	}
	@Override
	public Object visitGreaterThan(GreaterThan n) {
		return visitBinExp(n);
	}
	@Override
	public Object visitIdentifierExp(IdentifierExp n) {
		return visitExp(n);
	}
	@Override
	public Object visitIdentifierType(IdentifierType n) {
		return visitType(n);
	}
	@Override
	public Object visitIf(If n) {
		return visitStatement(n);
	}
	@Override
	public Object visitInstVarAccess(InstVarAccess n) {
		return visitExp(n);
	}
	@Override
  	public Object visitInstVarDecl(InstVarDecl n) {
		return visitVarDecl(n);
	}
	@Override
  	public Object visitInstanceOf(InstanceOf n) {
		return visitExp(n);
	}
	@Override
	public Object visitIntegerLiteral(IntegerLiteral n) {
		return visitExp(n);
	}
	@Override
	public Object visitIntegerType(IntegerType n) {
		return visitType(n);
	}
	@Override
	public Object visitLabel(Label n) {
		return visitStatement(n);
	}
	@Override
	public Object visitLessThan(LessThan n) {
		return visitBinExp(n);
	}
	@Override
  	public Object visitLocalDeclStatement(LocalDeclStatement n) {
		return visitStatement(n);
	}
	@Override
  	public Object visitLocalVarDecl(LocalVarDecl n) {
		return visitVarDecl(n);
	}
	@Override
	public Object visitMethodDecl(MethodDecl n) {
		return visitDecl(n);
	}
	@Override
	public Object visitMethodDeclNonVoid(MethodDeclNonVoid n) {
		return visitMethodDecl(n);
	}
	@Override
	public Object visitMethodDeclVoid(MethodDeclVoid n) {
		return visitMethodDecl(n);
	}
	@Override
  	public Object visitMinus(Minus n) {
		return visitBinExp(n);
	}
	@Override
  	public Object visitNewArray(NewArray n) {
		return visitExp(n);
	}
	@Override
  	public Object visitNewObject(NewObject n) {
		return visitExp(n);
	}
	@Override
  	public Object visitNot(Not n) {
		return visitUnExp(n);
	}
	@Override
  	public Object visitNull(Null n) {
		return visitExp(n);
	}
	@Override
	public Object visitNullType(NullType n) {
		return visitType(n);
	}
	@Override
  	public Object visitOr(Or n) {
		return visitBinExp(n);
	}
	@Override
  	public Object visitPlus(Plus n) {
		return visitBinExp(n);
	}
	@Override
  	public Object visitProgram(Program n) {
		return visitAstNode(n);
	}
	@Override
  	public Object visitRemainder(Remainder n) {
		return visitBinExp(n);
	}
	@Override
  	public Object visitStatement(Statement n) {
		return visitAstNode(n);
	}
	@Override
  	public Object visitStringLiteral(StringLiteral n) {
		return visitExp(n);
	}
	@Override
  	public Object visitSuper(Super n) {
		return visitExp(n);
	}
	@Override
	public Object visitSwitch(Switch n) {
		return visitBreakTarget(n);
	}
	@Override
  	public Object visitThis(This n) {
		return visitExp(n);
	}
	@Override
  	public Object visitTimes(Times n) {
		return visitBinExp(n);
	}
	@Override
  	public Object visitTrue(True n) {
		return visitExp(n);
	}
	@Override
  	public Object visitType(Type n) {
		return visitAstNode(n);
	}
	@Override
  	public Object visitUnExp(UnExp n) {
		return visitExp(n);
	}
	@Override
  	public Object visitVarDecl(VarDecl n) {
		return visitDecl(n);
	}
	@Override
	public Object visitVoidType(VoidType n) {
		return visitType(n);
	}
	@Override
  	public Object visitWhile(While n) {
		return visitBreakTarget(n);
	}
	@Override
  	public Object visitClassDeclList(ClassDeclList n) {
		return visitAstList(n);
	}
	@Override
  	public Object visitExpList(ExpList n) {
		return visitAstList(n);
	}
	@Override
  	public Object visitDeclList(DeclList n) {
		return visitAstList(n);
	}
	@Override
  	public Object visitStatementList(StatementList n) {
		return visitAstList(n);
	}
	@Override
  	public Object visitVarDeclList(VarDeclList n) {
		return visitAstList(n);
	}
}

