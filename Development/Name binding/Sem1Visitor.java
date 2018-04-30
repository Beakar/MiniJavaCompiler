package visitor;

import syntaxtree.*;
import java.util.*;
import errorMsg.*;
// The purpose of the Sem1Visitor class is to:
// - enter each class declaration into the global symbol table)
//   - duplciate class names are detected
// - enter each method declaration into the method symbol table
//   for its class
//   - duplicate method names for a class are detected
// - enter each instance variable declaration into the respective
//   instance-variable symbol table for its class
//   - duplicate instance variable names for a class are detected
// - all of the above are also done for the predefined classes
//   (Object, String and Lib)
public class Sem1Visitor extends ASTvisitor {
	
	public static final boolean doStringMethods = true;
	
	Hashtable<String,ClassDecl> globalSymTab;
	ClassDecl currentClass;
	ErrorMsg errorMsg;
	
	public Sem1Visitor(ErrorMsg e) {
		errorMsg = e;
		initInstanceVars();
		initGlobalSymTab();
	}
	
	public Hashtable<String,ClassDecl> getGlobalSymTab() {
		return globalSymTab;
	}

	protected void initGlobalSymTab() {
		ClassDecl classObjectDecl = createClass("Object", "");
		ClassDecl classStringDecl = createClass("String", "Object");
		ClassDecl classLibDecl = createClass("Lib", "Object");
		ClassDecl classRunMainDecl = createClass("RunMain", "Object");
		ClassDecl classDataArrayDecl = createClass("_DataArray", "Object");
		ClassDecl classObjectArrayDecl = createClass("_ObjectArray", "Object");
		
		addDummyMethod(classObjectDecl, "equals", "boolean", new String[]{"Object"});
	    addDummyMethod(classLibDecl, "readLine", "String", new String[]{});
	    addDummyMethod(classLibDecl, "readInt", "int", new String[]{});
	    addDummyMethod(classLibDecl, "readChar", "int", new String[]{});
		addDummyMethod(classLibDecl, "printStr", "void", new String[]{"String"});
		addDummyMethod(classLibDecl, "printBool", "void", new String[]{"boolean"});
		addDummyMethod(classLibDecl, "printInt", "void", new String[]{"int"});
		addDummyMethod(classLibDecl, "intToString", "String",
				new String[]{"int"});
		addDummyMethod(classLibDecl, "intToChar", "String",
				new String[]{"int"});
		addDummyMethod(classStringDecl, "equals", "boolean", new String[]{"Object"});
		addDummyMethod(classStringDecl, "concat", "String",
				new String[]{"String"});
		addDummyMethod(classStringDecl, "substring", "String",
				new String[]{"int","int"});
		addDummyMethod(classStringDecl, "length", "int", new String[]{});
		addDummyMethod(classStringDecl, "charAt", "int",
				new String[]{"int"});
		addDummyMethod(classStringDecl, "compareTo", "int",
				new String[]{"String"});

		this.visitClassDecl(classObjectDecl);
		this.visitClassDecl(classLibDecl);
		this.visitClassDecl(classStringDecl);
		this.visitClassDecl(classRunMainDecl);
		this.visitClassDecl(classDataArrayDecl);
		this.visitClassDecl(classObjectArrayDecl);

		Sem2Visitor s2 = new Sem2Visitor(globalSymTab, errorMsg);
		s2.visit(classObjectDecl);
		s2.visit(classLibDecl);
		s2.visit(classStringDecl);
		s2.visit(classRunMainDecl);
		s2.visitClassDecl(classDataArrayDecl);
		s2.visitClassDecl(classObjectArrayDecl);
		
		Sem3Visitor s3 = new Sem3Visitor(globalSymTab, errorMsg);
		s3.visit(classObjectDecl);
		s3.visit(classLibDecl);
		s3.visit(classStringDecl);
		s3.visit(classRunMainDecl);
		s3.visitClassDecl(classDataArrayDecl);
		s3.visitClassDecl(classObjectArrayDecl);
		
	}
	
	protected static ClassDecl createClass(String name, String superName) {
		return new ClassDecl(-1, name, superName, new DeclList());
	}
	protected static void addDummyMethod(ClassDecl dec, String methName,
				String rtnTypeName, String[] parmTypeNames) {
		VarDeclList parmDecls = new VarDeclList();
		for (int i = 0; i < parmTypeNames.length; i++) {
			Type t = convertToType(parmTypeNames[i]);
			String parmName = "parm"+i;
			VarDecl vd = new FormalDecl(-1, t, parmName);
			parmDecls.addElement(vd);
		}
		Type t = convertToType(rtnTypeName);
		StatementList sl = new StatementList(); // dummied up
		MethodDecl md;
		if (t == null) { // void return-type
			md = new MethodDeclVoid(-1, methName, parmDecls, sl);
		}
		else { // non-void return type
			Exp rtnExpr = new Null(-1);
			md = new MethodDeclNonVoid(-1, t, methName, parmDecls, sl, rtnExpr);
		}
		dec.decls.addElement(md);
	}
	
	private static Type convertToType(String s) {
		if (s.equals("void")) {
			return null;
		}
		else if (s.equals("boolean")) {
			return new BooleanType(-1);
		}
		else if (s.equals("int")) {
			return new IntegerType(-1);
		}
		else {
			return new IdentifierType(-1, s);
		}
	}

	private void initInstanceVars() {
		globalSymTab = new Hashtable<String,ClassDecl>();
		currentClass = null;
	}
	
	public Object visitClassDecl(ClassDecl n){
		if (globalSymTab.contains(n)){
			errorMsg.error(n.pos, "Class: " + n.name + " is defined multiple times");
			return null;
		}
		//put class into global system table
		globalSymTab.put(n.name, n);
		//set current class
		currentClass = n;
		return super.visitClassDecl(n);
	}
	
	public Object visitMethodDecl(MethodDecl n){
		//error checking
		if (currentClass.methodTable.contains(n)){
			errorMsg.error(n.pos, "Method: " + n.name + " is defined multiple times in class " + currentClass.name);
		}
		//put inst var into the correct table
		currentClass.methodTable.put(n.name, n);
		return null;
	}
	
	public Object visitMethodDeclNonVoid(MethodDeclNonVoid n){
		//error checking
		if (currentClass.methodTable.contains(n)){
			errorMsg.error(n.pos, "Method: " + n.name + " is defined multiple times in class " + currentClass.name);
		}
		//put inst var into the correct table
		currentClass.methodTable.put(n.name, n);
		return null;
	}
	
	public Object visitInstVarDecl(InstVarDecl n){
		//error checking
		if (currentClass.instVarTable.contains(n)){
			errorMsg.error(n.pos, "Instance variable: " + n.name + " is defined multiple times in class " + currentClass.name);
		}
		//put inst var into the correct table
		currentClass.instVarTable.put(n.name, n);
		return null;
	}
}
