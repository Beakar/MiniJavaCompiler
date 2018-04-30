package visitor;

import syntaxtree.*;

public interface Visitor {
	
	//public Object visit(AstNode n);
	
	public Object visitAstNode(AstNode n);
	public Object visitAstList(AstList n);
	
	public Object visitAnd(And n);
	public Object visitArrayLength(ArrayLength n);
	public Object visitArrayLookup(ArrayLookup n);
	public Object visitArrayType(ArrayType n);
	public Object visitAssign(Assign n);
	public Object visitBinExp(BinExp n);
	public Object visitBlock(Block n);
	public Object visitBooleanType(BooleanType n);
	public Object visitBreak(Break n);
	public Object visitBreakTarget(BreakTarget n);
	public Object visitCall(Call n);
	public Object visitCallStatement(CallStatement n);
	public Object visitCast(Cast n);
	public Object visitClassDecl(ClassDecl n);
	public Object visitCase(Case n);
	public Object visitDecl(Decl n);
	public Object visitDefault(Default n);
	public Object visitDivide(Divide n);
	public Object visitEquals(Equals n);
	public Object visitExp(Exp n);
	public Object visitFalse(False n);
  	public Object visitFormalDecl(FormalDecl n);
	public Object visitGreaterThan(GreaterThan n);
	public Object visitIdentifierExp(IdentifierExp n);
	public Object visitIdentifierType(IdentifierType n);
	public Object visitIf(If n);
	public Object visitInstVarAccess(InstVarAccess n);
	public Object visitInstanceOf(InstanceOf n);
  	public Object visitInstVarDecl(InstVarDecl n);
	public Object visitIntegerLiteral(IntegerLiteral n);
	public Object visitIntegerType(IntegerType n);
	public Object visitLabel(Label n);
	public Object visitLessThan(LessThan n);
  	public Object visitLocalDeclStatement(LocalDeclStatement n);
  	public Object visitLocalVarDecl(LocalVarDecl n);
	public Object visitMethodDecl(MethodDecl n);
	public Object visitMethodDeclNonVoid(MethodDeclNonVoid n);
	public Object visitMethodDeclVoid(MethodDeclVoid n);
  	public Object visitMinus(Minus n);
  	public Object visitNewArray(NewArray n);
  	public Object visitNewObject(NewObject n);
  	public Object visitNot(Not n);
  	public Object visitNull(Null n);
  	public Object visitNullType(NullType n);
  	public Object visitOr(Or n);
  	public Object visitPlus(Plus n);
  	public Object visitProgram(Program n);
  	public Object visitRemainder(Remainder n);
  	public Object visitStatement(Statement n);
  	public Object visitStringLiteral(StringLiteral n);
  	public Object visitSuper(Super n);
	public Object visitSwitch(Switch n);
  	public Object visitThis(This n);
  	public Object visitTimes(Times n);
  	public Object visitTrue(True n);
  	public Object visitType(Type n);
  	public Object visitUnExp(UnExp n);
  	public Object visitVarDecl(VarDecl n);
	public Object visitVoidType(VoidType n);
  	public Object visitWhile(While n);

  	public Object visitClassDeclList(ClassDeclList n);
  	public Object visitExpList(ExpList n);
  	public Object visitDeclList(DeclList n);
  	public Object visitStatementList(StatementList n);
  	public Object visitVarDeclList(VarDeclList n);
  	
}
