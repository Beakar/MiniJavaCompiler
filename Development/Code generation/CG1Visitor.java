package visitor;

import syntaxtree.*;

import java.util.*;

import errorMsg.*;
import java.io.*;
import java.awt.Point;

//the purpose here is to annotate things with their offsets:
// - formal parameters, with respect to the (callee) frame
// - instance variables, with respect to their slot in the object
// - methods, with respect to their slot in the v-table
public class CG1Visitor extends ASTvisitor {
	
	// Error message object
	ErrorMsg errorMsg;
	
	// IO stream to which we will emit code
	CodeStream code;
	
	// v-table offset of next method we encounter
	int currentMethodOffset;
	
	// offset in object of next "object" instance variable we encounter
	int currentObjInstVarOffset;
	
	// offset in object of next "data" instance variable we encounter
	int currentDataInstVarOffset;
	
	// stack-offset of next formal parameter we encounter
	int currentFormalVarOffset;
	
	// stack method tables for current class and all superclasses
	Stack<Vector<String>> superclassMethodTables;
	
	// current method table
	Vector<String> currentMethodTable;
	
	public CG1Visitor(ErrorMsg e, PrintStream out) {
		errorMsg = e;
		initInstanceVars(e, out);
	}
	
	private void initInstanceVars(ErrorMsg e, PrintStream out) {
		errorMsg = e;
		currentMethodOffset = 0;
		currentObjInstVarOffset = 0;
		currentDataInstVarOffset = 0;
		code = new CodeStream(out, e);
		superclassMethodTables = new Stack<Vector<String>>();
		superclassMethodTables.addElement(new Vector<String>());
	}
	
	//////////////////////////////////////////////
	///			Helper Functions			   ///
	//////////////////////////////////////////////
	
	//computes the total number of methods that cd has
	private int numMethods(ClassDecl cd){
		//base case
		if(cd.superLink == null){
			return cd.methodTable.size();
		}
		// count the methods that do not override an existing method
		Collection<MethodDecl> methods = cd.methodTable.values();
		int numTopLevelMethods = 0;
		for(MethodDecl item : methods){
			if(item.superMethod == null)
				numTopLevelMethods++;
		}
		//recurse the super classes to count all of the methods
		return numMethods(cd.superLink) + numTopLevelMethods;
	}
	
	//stores the name of md in the currentMethodTable at position md.vtableOffset
	//if any value is
	private void registerMethodInTable(MethodDecl md, String label){
		//if the size of currentMethodTable is to small, increase it to cover md.vtableOffset + 20 (arbitrary number)
		if(currentMethodTable.size() < md.vtableOffset){
			currentMethodTable.setSize(md.vtableOffset + 20);
		}
		currentMethodTable.add(md.vtableOffset - 1, label);
	}
	
	//computes the number of words that vdl will add to the stack if pushed
	private int wordsOnStackFrame(VarDeclList vdl){
		int words = 0;
		for(VarDecl var : vdl){
			words = wordsOnStackFrame(var.type);
		}
		return words;
	}
	
	// computes the number of words that an object of type t would grow the stack if it was pushed
	// Void==0, int==2, Object/array/boolean==1
	private int wordsOnStackFrame(Type t){
		if(t instanceof VoidType){
			return 0;
		}
		else if(t instanceof IntegerType){
			return 2;
		}
		//t is neither void nor int, so it must be an object/array/boolean
		return 1;
	}
	
	// returns true iff t represents the int or boolean type
	private boolean isDataType(Type t){
		if(t instanceof IntegerType || t instanceof BooleanType){
			return true;
		}
		return false;
	}
	
	// returns true unless t represents boolean, int, or void
	private boolean isObjectType(Type t){
		if(t instanceof BooleanType || t instanceof IntegerType || t instanceof VoidType){
			return false;
		}
		return true;
	}
	
	//////////////////////////////////////////////////////////
	///				Visiting functions					   ///
	//////////////////////////////////////////////////////////

	//Program
	public Object visitProgram(Program n){
		code.emit(n, ".data");
		ClassDecl objClassDecl = n.classDecls.elementAt(0);
		while(objClassDecl.superLink != null){
			objClassDecl = objClassDecl.superLink;
		}
		objClassDecl.accept(this);
		code.flush();
		return null;
	}
	
	//ClassDecl
	public Object visitClassDecl(ClassDecl n){
		currentMethodTable = (Vector<String>) superclassMethodTables.peek().clone();
		//currentMethodOffset = 1 + # methods in superclass
		if(n.superLink == null){
			currentMethodOffset = 1;
		}
		else{
			currentMethodOffset = 1 + numMethods(n.superLink);
		}
		//step 3 in notes
		//init in case we have null super link
		currentDataInstVarOffset = -16;
		currentObjInstVarOffset = 0;
		//corrects the above init if we have a valid super link
		if (n.superLink != null){	
			currentDataInstVarOffset = -16 - 4*(n.superLink.numDataInstVars);
			currentObjInstVarOffset = 4 * n.superLink.numObjInstVars;
		}
		//step 4 in notes
		super.visitClassDecl(n);
		//step 5 in notes
		n.numDataInstVars = (-16 - currentDataInstVarOffset)/4;
		//step 6 in notes
		n.numObjInstVars = currentObjInstVarOffset/4;
		//step 7 in notes
		//emit CLASS_XXX
		code.emit(n, "CLASS_" + n.name + ":");
		//emit superclass's vtable address
		if(n.superLink == null){
			code.emit(n, ".word 0");
		}
		else{
			code.emit(n, ".word CLASS_" + n.superName);
		}
		//emit each method address
		for(String item : currentMethodTable){
			if(item != null){
				code.emit(n, ".word " + item);
			}
		}
		//step 11
		superclassMethodTables.push(currentMethodTable);
		//visit all my subclasses
		n.subclasses.accept(this);
		//2nd to last step
		superclassMethodTables.pop();
		code.emit(n, "CLASS_END_" + n.name + ":");
		return null;
	}

	//MethodDecl
	public Object visitMethodDecl(MethodDecl n){
		//step 1
		n.thisPtrOffset = 4 * (1+ wordsOnStackFrame(n.formals));
		//step 2
		currentFormalVarOffset = n.thisPtrOffset;
		//step 3
		super.visitMethodDecl(n);
		//set vtableOffset
		//if superMethod exists
		if(n.superMethod != null){
			n.vtableOffset = n.superMethod.vtableOffset;
		}
		//else use currentMethodOffset and increment currentMethodOffset
		else{
			n.vtableOffset = currentMethodOffset;
			currentMethodOffset++;
		}
		//register label in the method table
		//if it is a predefined method
		if(n.pos < 0){
			registerMethodInTable(n, n.name + "_" + n.classDecl.name);
		}
		//otherwise the label is fcn_n.uniqueId_n.name
		else{
			registerMethodInTable(n, "fcn_" + n.uniqueId + "_" + n.name);
		}
		return null;
	}

	//InstVarDecl
	public Object visitInstVarDecl(InstVarDecl n){
		super.visitInstVarDecl(n);
		//if type == int || bool
		if(isDataType(n.type)){
			n.offset = currentDataInstVarOffset;
			currentDataInstVarOffset -= 4;
		}
		//else
		else{
			n.offset = currentObjInstVarOffset;
			currentObjInstVarOffset += 4;
		}
		return null;
	}
	
	//FormalDecl
	public Object visitFormalDecl(FormalDecl n){
		super.visitFormalDecl(n);
		//if type == int, subtract 8
		if(n.type instanceof IntegerType){
			currentFormalVarOffset -= 8;
		}
		//else subtract 4
		else{
			currentFormalVarOffset -= 4;
		}
		//set n.offset to the updated currentFormalVarOffset
		n.offset = currentFormalVarOffset;
		return null;
	}
	
}
