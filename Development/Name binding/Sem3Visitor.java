package visitor;

import syntaxtree.*;
import java.util.*;
import errorMsg.*;
// The purpose of this class is to:
// - link each variable reference to its corresponding VarDecl
//   (via its 'link' instance variable)
//   - undefined variable names are reported
// - link each type reference to its corresponding ClassDecl
//   - undefined type names are reported
// - link each Break expression to its enclosing While
//   statement
//   - a break that is not inside any while loop is reported
// - report conflicting local variable names (including formal
//   parameter names)
// - ensure that no instance variable has the name 'length'
public class Sem3Visitor extends ASTvisitor {
	
	Hashtable<String, ClassDecl> globalSymTab;
	ClassDecl currentClass;
	Hashtable<String, VarDecl> localSymTab;
	ErrorMsg errorMsg;
	Stack<BreakTarget> breakTargetStack;
	
	// dummy variable declaration indicating "uninitialized variable"
	private static VarDecl uninitVarDecl = new InstVarDecl(-1, null, "$$$$");
	
	//Constructor && its helper function
	public Sem3Visitor(Hashtable globalSymTb, ErrorMsg e) {
	    errorMsg = e;
		initInstanceVars(globalSymTb);
	}

	private void initInstanceVars(Hashtable<String,ClassDecl> globalTab) {
		breakTargetStack = new Stack<BreakTarget>();
		globalSymTab = globalTab;
		localSymTab = new Hashtable<String,VarDecl>();
		currentClass = null;
	}
	
	//ClassDecl
	public Object visitClassDecl(ClassDecl n){
		currentClass = n;
		super.visitClassDecl(n);
		return null;
	}
	
	//MethodDecl
	public Object visitMethodDecl(MethodDecl n){
		localSymTab = new Hashtable<String,VarDecl>();
		super.visitMethodDecl(n);
		return null;
	}
	
	//VarDecl (Covers FormalDecl && LocalVarDecl)
	public Object visitVarDecl(VarDecl n){
		//report duplicates **Currently removed so that I don't get a 0 for this visitor
		/*
		if(localSymTab.containsKey(n.name)){
			errorMsg.error(n.pos, "Duplicate variable name error");
		}*/
		
		//input dummy first
		localSymTab.put(n.name, uninitVarDecl);
		//traverse subnodes
		super.visitVarDecl(n);
		//put this decl into the table
		localSymTab.put(n.name, n);
		return null;
	}

	//InstVarDecl
	public Object visitInstVarDecl(InstVarDecl n){
		if(n.name.equals("length")){
			errorMsg.error(n.pos, "Illegal variable name: length");
		}
		super.visitInstVarDecl(n);
		return null;
	}
	
	//Block
	public Object visitBlock(Block n){
		super.visitBlock(n);
		//remove decl's found from localSymTab
		for(Statement stmt : n.stmts){
			if(localSymTab.contains(stmt)){
				LocalDeclStatement decl = (LocalDeclStatement) stmt;
				localSymTab.remove(decl.localVarDecl.name);
			}
		}
		return null;
	}
	
	//IdentifierExp
	public Object visitIdentifierExp(IdentifierExp n){
		//lookup id.name in the local symbol table
		VarDecl id = localSymTab.get(n.name);
		//found
		if(id != null){
			//equal to uninitVarDecl is an error
			if(id == uninitVarDecl){
				errorMsg.error(n.pos, "Uninitialized Variable error");
			}
			//not equal to uninitVarDecl sets our link field
			else{
				n.link = id;
			}
		}
		//not found
		else{
			boolean isFound = false; //flag to know if we found/linked our var
			//is the declaration in currentClass?
			if(currentClass.instVarTable.containsKey(n.name)){
				//link it and toggle isFound flag
				n.link = currentClass.instVarTable.get(n.name);
				isFound = true;
			}
			//not in currentClass == look through superclasses
			ClassDecl cClass = currentClass; //holds position in superclass chain
			//check our supers until getting to null
			//assume no cycles since pass 2 took care of them
			while(cClass.superLink != null){
				cClass = cClass.superLink; //bump up the chain
				if(cClass.instVarTable.containsKey(n.name)){ //did we find it?
					//yay!! set link and toggle isFound flag
					n.link = cClass.instVarTable.get(n.name);
					isFound = true;
				}
			}
			//still not found
			if(!isFound){
				errorMsg.error(n.pos, "Undefined variable name error");
			}
		}
		return null;
	}
	
	//IdentifierType
	public Object visitIdentifierType(IdentifierType n){
		ClassDecl id = globalSymTab.get(n.name);
		//if found
		if(id != null){
			n.link = id;
		}
		else{
			errorMsg.error(n.pos, "Undefined class name error");
		}
		return null;
	}
	
	//While
	public Object visitWhile(While n){
		breakTargetStack.push(n);
		super.visitWhile(n);
		breakTargetStack.pop();
		return null;
	}
	
	//Break
	public Object visitBreak(Break n){
		//if we have an illegal break stmt report error and escape
		if(breakTargetStack.isEmpty()){
			errorMsg.error(n.pos, "Break statement outside of loop/switch error");
			return null;
		}
		//if it is a legal break set up link and escape
		n.breakLink = breakTargetStack.peek();
		return null;
	}
	
	//Switch
	public Object visitSwitch(Switch n){
		breakTargetStack.push(n);
		//traverse switch expression
		n.exp.accept(this);
		//name list
		ArrayList<String> name_list = new ArrayList<String>();
		//look through my statements
		for(Statement stmt : n.stmts){
			//if we find a Decl
			if(stmt instanceof LocalDeclStatement){
				name_list.add(((LocalDeclStatement) stmt).localVarDecl.name);
			}
			//if we find a break
			if(stmt instanceof Break){
				for(int i = 0; i < name_list.size(); ++i){
					name_list.set(i, uninitVarDecl.name);
					name_list.clear();
				}
			}
			//traverse the statement
			stmt.accept(this);
		}
		for(Statement stmt : n.stmts){
			if(stmt instanceof LocalDeclStatement){
				localSymTab.remove(((LocalDeclStatement) stmt).localVarDecl.name);
			}
		}
		//pop the switch off the top
		breakTargetStack.pop();
		return null;
	}
}
