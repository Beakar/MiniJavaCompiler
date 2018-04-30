/*
 * Created on Jun 18, 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package syntaxtree;

import visitor.Visitor;

/**
 * a binary '>' expression
 */
public class GreaterThan extends BinExp {

	/**
	 * constructor
	 * @param pos file position
	 * @param ae1 left operand
	 * @param ae2 right operand
	 */
	public GreaterThan(int pos, Exp ae1, Exp ae2) { 
		super(pos, ae1, ae2);
	}

	/*************** remaining methods are visitor- and display-related ****************/

	public Object accept(Visitor v) {
		return v.visitGreaterThan(this);
	}


	/*************** remaining methods are for pretty-print ****************/
	public  String opString() { return ">";}

}