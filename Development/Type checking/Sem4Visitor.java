package visitor;

import syntaxtree.*;

import java.util.*;

import errorMsg.*;
// The purpose of this class is to do type-checking and related
// actions.  These include:
// - evaluate the type for each expression, filling in the 'type'
//   link for each
// - ensure that each expression follows MiniJava's type rules (e.g.,
//   that the arguments to '*' are both integer, the argument to
//   '.length' is an array, etc.)
// - ensure that each method-call follows Java's type rules:
//   - there exists a method for the given class (or a superclass)
//     for the receiver's object type
//   - the method has the correct number of parameters
//   - the types of each actual parameter is compatible with that
//     of its corresponding formal parameter
// - ensure that for each instance variable access (e.g., abc.foo),
//   there is an instance variable defined for the given class (or
//   in a superclass
//   - sets the 'varDec' link in the InstVarAccess to refer to the
//     method
// - ensure that the RHS expression in each assignment statement is
//   type-compatible with its corresponding LHS
//   - also checks that the LHS an lvalue
// - ensure that if a method with a given name is defined in both
//   a subclass and a superclass, that they have the same parameters
//   (with identical types) and the same return type
// - ensure that the declared return-type of a method is compatible
//   with its "return" expression
// - ensuring that the type of the control expression for an if- or
//   while-statement is boolean
public class Sem4Visitor extends ASTvisitor {
	
	ClassDecl currentClass;
	IdentifierType currentClassType;
	IdentifierType currentSuperclassType;
	ErrorMsg errorMsg;
	Hashtable<String,ClassDecl> globalSymTab;
	ConstEvalVisitor conEval;
	
	BooleanType theBoolType;
	IntegerType theIntType;
	NullType theNullType;
	VoidType theVoidType;
	IdentifierType theStringType;
	
	public Sem4Visitor(Hashtable<String,ClassDecl> globalSymTb, ErrorMsg e) {
		globalSymTab = globalSymTb;
		errorMsg = e;
		initInstanceVars();
	}

	private void initInstanceVars() {
		currentClass = null;
		conEval = new ConstEvalVisitor();
		
		theBoolType = new BooleanType(-1);
		theIntType = new IntegerType(-1);
		theNullType = new NullType(-1);
		theVoidType = new VoidType(-1);
		if (globalSymTab != null) {
			theStringType = new IdentifierType(-1, "String");
			theStringType.link = globalSymTab.get("String");
		}
	}
	
	
	///////////////////////////////////////////////////////////
	///				Visit Functions							///
	///////////////////////////////////////////////////////////
	
	
	//integerliteral
	public Object visitIntegerLiteral(IntegerLiteral n) {
		super.visitIntegerLiteral(n); //traverse subnodes
		n.type = theIntType;
		return null;
	}
	
	//null
	public Object visitNull(Null n){
		super.visitNull(n); //traverse subnodes
		n.type = theNullType;
		return null;
	}
	
	//true
	public Object visitTrue(True n){
		super.visitTrue(n); //traverse subnodes
		n.type = theBoolType;
		return null;
	}
	
	//false
	public Object visitFalse(False n){
		super.visitFalse(n); //traverse subnodes
		n.type = theBoolType;
		return null;
	}

	//this
	public Object visitThis(This n){
		super.visitThis(n); //traverse subnodes
		n.type = currentClassType;
		return null;
	}

	//super
	public Object visitSuper(Super n){
		super.visitSuper(n); //traverse subnodes
		n.type = currentSuperclassType;
		return null;
	}
	
	//stringliteral
	public Object visitStringLiteral(StringLiteral n){
		super.visitStringLiteral(n); //traverse subnodes
		n.type = theStringType;
		return null;
	}
	
	//identifierexp
	public Object visitIdentifierExp(IdentifierExp n){
		super.visitIdentifierExp(n); //traverse subnodes
		n.type = n.link.type;
		return null;
	}
	
	//plus
	public Object visitPlus(Plus n){
		super.visitPlus(n); //traverse subnodes
		//if stmt here to save computing load in some situations
		if(matchTypesExact(n.left.type, theIntType, n.left.pos)) {
			matchTypesExact(n.right.type, theIntType, n.right.pos);
		}
		n.type = theIntType;
		return null;
	}
	
	//minus
	public Object visitMinus(Minus n){
		super.visitMinus(n); //traverse subnodes
		//if stmt here to save computing load in some situations
		if(matchTypesExact(n.left.type, theIntType, n.left.pos)) {
			matchTypesExact(n.right.type, theIntType, n.right.pos);
		}
		n.type = theIntType;
		return null;
	}

	//times
	public Object visitTimes(Times n){
		super.visitTimes(n); //traverse subnodes
		//if stmt here to save computing load in some situations
		if(matchTypesExact(n.left.type, theIntType, n.left.pos)) {
			matchTypesExact(n.right.type, theIntType, n.right.pos);
		}
		n.type = theIntType;
		return null;
	}
	
	//divide
	public Object visitDivide(Divide n){
		super.visitDivide(n); //traverse subnodes
		//if stmt here to save computing load in some situations
		if(matchTypesExact(n.left.type, theIntType, n.left.pos)) {
			matchTypesExact(n.right.type, theIntType, n.right.pos);
		}
		n.type = theIntType;
		return null;
	}
	
	//remainder
	public Object visitRemainder(Remainder n){
		super.visitRemainder(n); //traverse subnodes
		//if stmt here to save computing load in some situations
		if(matchTypesExact(n.left.type, theIntType, n.left.pos)) {
				matchTypesExact(n.right.type, theIntType, n.right.pos);
		}
		n.type = theIntType;
		return null;
	}
	
	//greaterthan
	public Object visitGreaterThan(GreaterThan n){
		super.visitGreaterThan(n); //traverse subnodes
		//if stmt here to save computing load in some situations
		if(matchTypesExact(n.left.type, theIntType, n.left.pos)) {
			matchTypesExact(n.right.type, theIntType, n.right.pos);
		}
		n.type = theBoolType;
		return null;
	}
	
	//lessthan
	public Object visitLessThan(LessThan n){
		super.visitLessThan(n); //traverse subnodes
		//if stmt here to save computing load in some situations
		if(matchTypesExact(n.left.type, theIntType, n.left.pos)) {
			matchTypesExact(n.right.type, theIntType, n.right.pos);
		}
		n.type = theBoolType;
		return null;
	}
	
	//equals
	public Object visitEquals(Equals n){
		super.visitEquals(n); //traverse subnodes
		matchTypesEqCompare(n.left.type, n.right.type, n.pos);
		n.type = theBoolType;
		return null;
	}
	
	//not
	public Object visitNot(Not n){
		super.visitNot(n); //traverse subnodes
		matchTypesExact(n.exp.type, theBoolType, n.exp.pos);
		n.type = theBoolType;
		return null;
	}
	
	//and
	public Object visitAnd(And n){
		super.visitAnd(n); //traverse subnodes
		//if stmt here to save computing load in some situations
		if(matchTypesExact(n.left.type, theBoolType, n.left.pos)) {
			matchTypesExact(n.right.type, theBoolType, n.right.pos);
		}
		n.type = theBoolType;
		return null;
	}
	
	//or
	public Object visitOr(Or n){
		super.visitOr(n); //traverse subnodes
		//if stmt here to save computing load in some situations
		if(matchTypesExact(n.left.type, theBoolType, n.left.pos)) {
			matchTypesExact(n.right.type, theBoolType, n.right.pos);
		}
		n.type = theBoolType;
		return null;
	}
	
	//arraylength
	public Object visitArrayLength(ArrayLength n){
		super.visitArrayLength(n); //traverse subnodes
		if (n.exp.type == null || !(n.exp.type instanceof ArrayType)){
			errorMsg.error(n.pos, "Unable to call length function on non-array type variables");
		}
		n.type = theIntType;
		return null;
	}
	
	//arraylookup
	public Object visitArrayLookup(ArrayLookup n){
		super.visitArrayLookup(n); //traverse subnodes
		if(!matchTypesExact(n.idxExp.type, theIntType, n.idxExp.pos)) {
			return null;
		}
		//report an error and return if @arrExp is not an ArrayType
		if (n.arrExp.type == null || !(n.arrExp.type instanceof ArrayType)) {
			errorMsg.error(n.pos, "Illegal array access");
			return null;
		}
		//cast to ArrayType and set the type field
		ArrayType temp = (ArrayType) n.arrExp.type;
		n.type = temp.baseType;
		return null;
	}
	
	//instvaraccess
	public Object visitInstVarAccess(InstVarAccess n){
		super.visitInstVarAccess(n); //traverse subnodes
		if (n.exp.type == null){
			return null;
		}
		n.varDec = this.instVarLookup(n.varName, n.exp.type, n.pos, "Illegal instance variable access");
		if (n.varDec != null){
			n.type = n.varDec.type;
		}
		return null;
	}
	
	//cast
	public Object visitCast(Cast n){
		super.visitCast(n); //traverse subnodes
		matchTypesAssign(n.exp.type, n.castType, n.exp.pos);
		n.type = n.castType;
		return null;
	}
	
	//instanceof
	public Object visitInstanceOf(InstanceOf n){
		super.visitInstanceOf(n); //traverse subnodes
		matchTypesAssign(n.exp.type, n.checkType, n.exp.pos);
		n.type = theBoolType;
		return null;
	}
	
	//newobject
	public Object visitNewObject(NewObject n){
		super.visitNewObject(n); //traverse subnodes
		n.type = n.objType;
		return null;
	}
	
	//newarray
	public Object visitNewArray(NewArray n){
		super.visitNewArray(n); //traverse subnodes
		matchTypesExact(n.sizeExp.type, theIntType, n.pos);
		n.type = n.objType;
		return null;
	}
	
	//call
	public Object visitCall(Call n){
		super.visitCall(n); //traverse subnodes
		if(n.obj == null){
			return null;
		}
		//check if the method exists in our table
		n.methodLink = methodLookup(n.methName, n.obj.type, n.pos, "Method " +n.methName + " not found", true);
		//exit after printing error message if we don't find the method
		if(n.methodLink == null){
			return null;
		}
		//check that our parameters match up correctly
		if (n.parms.size() != n.methodLink.formals.size()){
			errorMsg.error(n.pos, "Incorrect number of parameters");
			return null;
		}
		for(int i = 0; i < n.parms.size(); ++i){
			if (!matchTypesAssign(n.parms.get(i).type, n.methodLink.formals.get(i).type, n.parms.get(i).pos)){
				return null; // only get here if there is a type mismatch error
			}
		}
		n.type = returnTypeFor(n.methodLink);
		return null;
	}
	
	//assign
	public Object visitAssign(Assign n){
		super.visitAssign(n); //traverse subnodes
		if (n.lhs instanceof IdentifierExp || n.lhs instanceof ArrayLookup || n.lhs instanceof InstVarAccess){
			matchTypesAssign(n.rhs.type, n.lhs.type, n.pos);
			return null;
		}
		errorMsg.error(n.pos, "Attempting to assign to non-l-value");
		return null;
	}
	
	//localvardecl
	public Object visitLocalVarDecl(LocalVarDecl n){
		super.visitLocalVarDecl(n); //traverse subnodes
		matchTypesAssign(n.initExp.type, n.type, n.pos);
		return null;
	}
	
	//if
	public Object visitIf(If n){
		super.visitIf(n); //traverse subnodes
		matchTypesExact(n.exp.type, theBoolType, n.pos);
		return null;
	}
	
	//while
	public Object visitWhile(While n){
		super.visitWhile(n); //traverse subnodes
		matchTypesExact(n.exp.type, theBoolType, n.pos);
		return null;
	}

	//case
	public Object visitCase(Case n){
		super.visitCase(n);
		matchTypesExact(n.exp.type, theIntType, n.pos);
		return null;
	}
	
	//MethodDeclVoid
	public Object visitMethodDeclVoid(MethodDeclVoid n){
		super.visitMethodDeclVoid(n);
		MethodDecl superMethod = methodLookup(n.name, currentClass.superLink, n.pos, "", false);
		if(superMethod == null){
			return null; //return quietly if there is no superclass for method n
		}
		if(!(superMethod instanceof MethodDeclVoid)){
			errorMsg.error(n.pos, "Non-void returning superclass method" +superMethod.name+ " found for void returning method " +n.name);
		}
		if(n.formals.size() != superMethod.formals.size()){
			errorMsg.error(n.pos, "Signature mismatch with super class");
			return null;
		}
		for(int i = 0; i < n.formals.size(); ++i){
			if(!matchTypesExact(n.formals.get(i).type, superMethod.formals.get(i).type, n.pos)){
				errorMsg.error(n.pos, "Type mismatch: " + n.formals.get(i).type.toString2() + " should be " + superMethod.formals.get(i).type.toString2());
			}
		}
		n.superMethod = superMethod;
		return null;
	}

	//methoddeclnonvoid
	public Object visitMethodDeclNonVoid(MethodDeclNonVoid n){
		super.visitMethodDeclNonVoid(n);
		//make sure the return exp is assignment compatible with the return type
		if(!matchTypesAssign(n.rtnExp.type, n.rtnType, n.pos)){
			return null;
		}
		//if a superclass defines a method by the same name
		MethodDecl superMethod = methodLookup(n.name, currentClass.superLink, n.pos, "", false);
		if(superMethod != null){ //if this passes the method exists
			//check that superclass is a MethodDeclNonVoid
			if(!(superMethod instanceof MethodDeclNonVoid)){
				errorMsg.error(n.pos, "Void returning superclass method" +superMethod.name+ " found for non-void returning method " +n.name);
			}
			//check formals
			if(n.formals.size() != superMethod.formals.size()){
				errorMsg.error(n.pos, "Signature mismatch with super class");
				return null;
			}
			for(int i = 0; i < n.formals.size(); ++i){
				if(!matchTypesExact(n.formals.get(i).type, superMethod.formals.get(i).type, n.pos)){
					errorMsg.error(n.pos, "Type mismatch: " + n.formals.get(i).type.toString2() + " should be " + superMethod.formals.get(i).type.toString2());
				}
			}
			//check return types
			if(!matchTypesExact(n.rtnType, ((MethodDeclNonVoid) superMethod).rtnType, n.pos)){
				errorMsg.error(n.pos, "Return type mismatch: " +n.rtnType.toString2()+ "should be " + ((MethodDeclNonVoid) superMethod).rtnType.toString2());
			}
			//set link field and exit
			n.superMethod = superMethod;
		}
		return null;
	}
	
	//ClassDecl
	public Object visitClassDecl(ClassDecl n){
		currentClass = n;
		currentClassType = new IdentifierType(n.pos, n.name);
		currentClassType.link = n;
		currentSuperclassType = new IdentifierType(n.pos, n.superName);
		currentSuperclassType.link = n.superLink;
		super.visitClassDecl(n);
		return null;
	}
	
	//Switch
	public Object visitSwitch(Switch n){
		super.visitSwitch(n); //Visit subnodes first
		//check switch-exp  type compatibility with Int
		if(!matchTypesExact(n.exp.type, theIntType, n.pos)){
			errorMsg.error(n.pos, "Illegal switch expression type: Switch expression type must be Int");
		}
		//check switch-block initial statement
		if(!(n.stmts.get(0) instanceof Label)){
			if(!(n.stmts.size() == 0)){
				errorMsg.error(n.pos, "Unreachable code error: Switch statment must begin with a 'case' or 'default' label");
			}
		}
		//check last statement in switch-block
		if(!(n.stmts.get(n.stmts.size()-1) instanceof Break)){
			if(!(n.stmts.size() == 0)){
				errorMsg.error(n.pos, "Switch-block error: Last statement in switch-block is not a break");
			}
		}
		//time to look through the cases
		//init setup
		Hashtable<Case,Object> switchTable = new Hashtable<Case,Object>();
		Default fDefault = null;
		//loop through each top-level statement for eval
		for(int i  = 0; i < n.stmts.size(); i++){
			//get the stmt we are looking at this round
			Statement stmt = n.stmts.get(i);
			if(!(stmt instanceof Label) && !(stmt instanceof Break)){ //Case 1:  stmt is not a Label or Break
				if(i != n.stmts.size()-1 && n.stmts.get(i+1) instanceof Label){ //make sure the next stmt is not a Label
					errorMsg.error(n.pos, "Fall-through in switch is not allowed"); //if it is a Label, we are falling through which is illegal
				}
			}
			if(stmt instanceof Break && (i != n.stmts.size()-1)){ //Case 2: stmt is a Break that is not the end of the Switch-exp block
				if(!(n.stmts.get(i+1) instanceof Label)){
					errorMsg.error(n.pos, "Illegally place Break statement in switch-expression block: Unreachable code found");
				}
			}
			if(stmt instanceof Default){ //Case 3: stmt is a Default
				if(fDefault == null){
					fDefault = (Default) stmt;
				}
				else{
					errorMsg.error(n.pos, "Illegal Switch statement: Switch block cannot have two default labels");
				}
			}
			if(stmt instanceof Case){ //Case 4: stmt is a Case
				Case myCase = (Case) stmt;
				Object obj = myCase.exp.accept(conEval);
				if(obj == null){
					errorMsg.error(n.pos, "Illegal Case expression: Case expression not constant");
				}
				else{
					if(switchTable.contains(obj)){
						errorMsg.error(n.pos, "Two case labels with same value error");
					}
					else{
						switchTable.put(myCase, obj);
					}
				}
			}
		}
		return null;
	}
	
	///////////////////////////////////////////////////////////
	//				Helper Functions						///
	///////////////////////////////////////////////////////////
	
	
	/*
	 * checks whether two type objects represent the EXACT same type (strict equivalence)
	 */
	private boolean matchTypesExact(Type have, Type need, int pos) {
		if (have == null || need == null) {
			return false;
		}
		else if (have.equals(need)) {
			return true;
		}
		if(pos > -1){
			errorMsg.error(pos, "Type mismatch: " + have.toString2() + " should be " + need.toString2());
		}
		return false;
	}
	
	/*
	 * Checks whether @src is assignment compatible with @target
	 */
	private boolean matchTypesAssign(Type src, Type target, int pos) {
		if (src == null || target == null) {
			return false;
		}
		else if (src.equals(theVoidType) || target.equals(theVoidType)) {
			errorMsg.error(pos, "Cannot assign void type to variable");
			return false;
		}
		else if (src.equals(target)) {
			return true;
		}
		
		if (src.equals(theNullType) && (target instanceof IdentifierType || target instanceof ArrayType)) {
			return true;
		}
		
		if (src instanceof ArrayType && target instanceof IdentifierType) {
			IdentifierType temp = (IdentifierType) target;
			if (temp.name.equals("Object")){
				return true;
			}
		}
		
		if (src instanceof IdentifierType && target instanceof IdentifierType) {
			IdentifierType tempSrc = (IdentifierType) src;
			IdentifierType tempTarget = (IdentifierType) target;
			if(isSuperClass(tempSrc.link, tempTarget.link)){
				return true;
			}
		}
		
		if (pos > -1) {
			errorMsg.error(pos, "Incompatible types: " + src.toString2() + " does not conform to " +target.toString2());
		}
		return false;
	}
	
	/*
	 * Recursively checks if @target is a (direct or indirect) superclass of @src
	 */
	private boolean isSuperClass(ClassDecl src, ClassDecl target){
		if(src.superLink == null){
			return false;
		}
		if(src.superLink.equals(target)){
			return true;
		}
		return isSuperClass(src.superLink, target);
	}
	
	/*
	 * checks whether @t1 and @t2 can be compared using == or !=
	 */
	private boolean matchTypesEqCompare(Type t1, Type t2, int pos) {
		if (t1 == null || t2 == null) {
			return false;
		}
		boolean t1First = matchTypesAssign(t1, t2, pos);
		boolean t2First = matchTypesAssign(t2, t1, pos);
		if (t1First == true || t2First == true) {
			return true;
		}
		errorMsg.error(pos, "Incompatible types: " + t1.toString2() + " does not conform to " +t2.toString2());
		return false;
	}
	
	/*
	 * Checks if @clas has the instance variable corresponding to @name
	 */
	private InstVarDecl instVarLookup(String name, ClassDecl clas, int pos, String msg) {
		// Case A: inst var not found
		if (clas == null) {
			errorMsg.error(pos, msg);
			return null;
		}
		// Case B: We find the inst var
		else if (clas.instVarTable.containsKey(name)) {
			return clas.instVarTable.get(name);
		}
		//recursively check each of super class until we come across either case A or B
		else {
			return instVarLookup(name, clas.superLink, pos, msg);
		}
	}
	
	/*
	 * Checks if @t has the instance variable corresponding to @name
	 */
	private InstVarDecl instVarLookup(String name, Type t, int pos, String msg) {
		if (t == null) {
			return null;
		}
		if (!(t instanceof IdentifierType)) {
			errorMsg.error(pos, msg);
			return null;
		}
		IdentifierType temp = (IdentifierType) t;
		return instVarLookup(name, temp.link, pos, msg);
	}
	
	/*
	 * Checks if class @clas contains a method with name @name
	 */
	private MethodDecl methodLookup(String name, ClassDecl clas, int pos, String msg, boolean reportErrors) {
		// Case A: method not found
		if (clas == null) {
			if(reportErrors){
				errorMsg.error(pos, msg);
			}
			return null;
		}
		// Case B: We find the method
		else if (clas.methodTable.containsKey(name)) {
			return clas.methodTable.get(name);
		}
		//recursively check each of super class until we come across either case A or B
		else {
			return methodLookup(name, clas.superLink, pos, msg, reportErrors);
		}
	}
	
	/*
	 * Checks if classType @t contains a method with name @name
	 */
	private MethodDecl methodLookup(String name, Type t, int pos, String msg, boolean reportErrors) {
		if (t == null) {
			return null;
		}
		if (!(t instanceof IdentifierType)) {
			errorMsg.error(pos, msg);
			return null;
		}
		IdentifierType temp = (IdentifierType) t;
		return methodLookup(name, temp.link, pos, msg, reportErrors);
	}
	
	/*
	 * returns the declared return type of the method @md
	 */
	private Type returnTypeFor(MethodDecl md){
		if(md instanceof MethodDeclVoid) {
			return theVoidType;
		}
		MethodDeclNonVoid temp = (MethodDeclNonVoid) md;
		return temp.rtnType;
	}
}
	
