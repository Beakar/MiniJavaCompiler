package visitor;

import syntaxtree.*;

// The purpose of this class is to evaluate constant expressions at
// compile-time.  A ConstEvalVisitor, say 'ceVisitor', can be used to
// evaluate an expression, 'e' with:
//   Object obj = e.accept(ceVisitor);
// The object returned will be one of:
//   - null, which means that the expression did NOT evaluate to a constant
//   - An object of (wrapper-class) type Boolean or Integer, in which case
//     the evaluated.
public class ConstEvalVisitor extends ASTvisitor {
	
	public ConstEvalVisitor() {
	}

	public Object visitAnd(And n) {
		try {
			return new Boolean(getBool(n.left) && getBool(n.right));
		}
		catch (NullPointerException npx) {
			return null;
		}
	}
	
	public Object visitDivide(Divide n) {
		try {
			return new Integer(getInt(n.left) / getInt(n.right));
		}
		catch (NullPointerException npx) {
			return null;
		}
		catch (ArithmeticException dbzx) {
			return null;
		}
	}
	
	public Object visitEquals(Equals n) {
		Object left = n.left.accept(this);
		Object right = n.right.accept(this);
		if (left == null || right == null) return null;
		else return new Boolean(left.equals(right));
	}
	
	public Object visitFalse(False n) {
		return Boolean.FALSE;
	}
	
	public Object visitGreaterThan(GreaterThan n) {
		try {
			return new Boolean(getInt(n.left) > getInt(n.right));
		}
		catch (NullPointerException npx) {
			return null;
		}
	}
	
	public Object visitIntegerLiteral(IntegerLiteral n) {
		try {
			return new Integer(n.val);
		}
		catch (NullPointerException npx) {
			return null;
		}
	}
	
	public Object visitLessThan(LessThan n) {
		try {
			return new Boolean(getInt(n.left) < getInt(n.right));
		}
		catch (NullPointerException npx) {
			return null;
		}
	}
	
	public Object visitMinus(Minus n) {
		try {
			return new Integer(getInt(n.left) - getInt(n.right));
		}
		catch (NullPointerException npx) {
			return null;
		}
	}
	
	public Object visitNot(Not n) {
		try {
			return new Boolean(!getBool(n.exp));
		}
		catch (NullPointerException npx) {
			return null;
		}
	}
	
	public Object visitNull(Null n) {
		return "NULL";
	}

	public Object visitOr(Or n) {
		try {
			return new Boolean(getBool(n.left) || getBool(n.right));
		}
		catch (NullPointerException npx) {
			return null;
		}
	}
	
	public Object visitPlus(Plus n) {
		try {
			return new Integer(getInt(n.left) + getInt(n.right));
		}
		catch (NullPointerException npx) {
			return null;
		}
	}
	
	public Object visitRemainder(Remainder n) {
		try {
			return new Integer(getInt(n.left) % getInt(n.right));
		}
		catch (NullPointerException npx) {
			return null;
		}
		catch (ArithmeticException dbzx) {
			return null;
		}
	}
	
	public Object visitTimes(Times n) {
		try {
			return new Integer(getInt(n.left) * getInt(n.right));
		}
		catch (NullPointerException npx) {
			return null;
		}
	}
	
	public Object visitTrue(True n) {
		return Boolean.TRUE;
	}
	
	private boolean getBool(Exp e) throws NullPointerException {
		return ((Boolean)e.accept(this)).booleanValue();
	}
	
	private int getInt(Exp e) throws NullPointerException {
		return ((Integer)e.accept(this)).intValue();
	}
}

	
