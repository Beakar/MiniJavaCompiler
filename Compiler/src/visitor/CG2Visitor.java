package visitor;

import syntaxtree.*;

import java.util.*;

import errorMsg.*;
import java.io.*;
import java.awt.Point;
 
// the purpose here is to generate string literal data

public class CG2Visitor extends CG2VisitorSimple {
	
	// NOTE: the instance variagles errorMsg and code are defined in the
	// superclass (with "protected' visibility--no need to redefine them here

	// hash-table that keeps track of unique representative for
	// each string literal
	private Hashtable<String,StringLiteral> stringTable;
	
	public CG2Visitor(ErrorMsg e, PrintStream out) {
		super(e, out);
		initInstanceVars();
	}
	
	@Override
	public Object visitProgram(Program p) {
		code.emit(p, " .data");
		p.classDecls.accept(this);
		code.flush();
		return null;
	}
	
	private void initInstanceVars() {
		stringTable = new Hashtable<String,StringLiteral>();	
	}
	
}

	
