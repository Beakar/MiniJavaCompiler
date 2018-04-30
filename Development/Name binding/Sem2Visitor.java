package visitor;

import syntaxtree.*;
import java.util.*;
import errorMsg.*;

// the purpose of this class is to
// - link each ClassDecl to the ClassDecl for its superclass (via
//   its 'superLink'
// - link each ClassDecl to each of its subclasses (via the
//   'subclasses' instance variable
// - ensure that there are no cycles in the inheritance hierarchy
// - ensure that no class has 'String' or 'RunMain' as its superclass
public class Sem2Visitor extends ASTvisitor {
	
	Hashtable<String,ClassDecl> globalSymTab;
	ErrorMsg errorMsg;
	
	public Sem2Visitor(Hashtable<String,ClassDecl> globalSymTb, ErrorMsg e) {
		errorMsg = e;
		initInstanceVars(globalSymTb);
	}

	private void initInstanceVars(Hashtable<String,ClassDecl> globalTab) {
		globalSymTab = globalTab;
	}
	
	public Object visitProgram(Program n){
		super.visitProgram(n);
		//define things here to save allocation space
		ClassDecl currentClass;
		int counter;
		//look through the Classes to find super link errors
		for( ClassDecl cd : n.classDecls){
			//checks for illegal super classes
			if(cd.superName == "String" || cd.superName == "RunMain"){
				errorMsg.error(n.pos, "Illegal super class: " +cd.superName+ " for class: " +cd.name);
			}
			//init counter && holder object so that we don't mess stuff up
			currentClass = cd;
			counter = 0;
			//loop through cd's superlinks to check for cycles
			while(currentClass.superLink != null){
				currentClass = currentClass.superLink;
				counter++;
				if(counter > n.classDecls.size()){
					errorMsg.error(n.pos, "Illegal super link cycle detected");
					break;
				}
				if(currentClass.superLink == cd){
					errorMsg.error(n.pos, "Illegal super link cycle detected");
					break;
				}
			}
		}
		return null;
	}
	
	public Object visitClassDecl(ClassDecl n){
		//test in case: n is Object
		if(n.superName == ""){
			return null;
		}
		//lookup superclass name
		ClassDecl mySuper = globalSymTab.get(n.superName);
		
		//if superClass is null, report error and return
		if(mySuper == null)
		{
			errorMsg.error(n.pos, "Undefined super class name for class:" +n.name);
			return null;
		}
		//link n to its superclass
		n.superLink = mySuper;
		//add n to its superclass's list of subclasses
		mySuper.subclasses.add(n);
		return null;
	}
	
}

	
