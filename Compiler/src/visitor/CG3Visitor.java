package visitor;

import syntaxtree.*;

import errorMsg.*;
import java.io.*;

public class CG3Visitor extends ASTvisitor {

	// the purpose here is to annotate things with their offsets:
	// - formal parameters, with respect to the (callee) frame
	// - local variables, with respect to the frame
	// - instance variables, with respect to their slot in the object
	// - methods, with respect to their slot in the v-table
	// - while statements, with respect to the stack-size at the time
	//   of loop-exit
	
	// Error message object
	ErrorMsg errorMsg;
	
	// IO stream to which we will emit code
	CodeStream code;

	// current stack height
	int stackHeight;
	
	// for constant evaluation
	ConstEvalVisitor conEvalVis;
	
	public CG3Visitor(ErrorMsg e, PrintStream out) {
		errorMsg = e;
		initInstanceVars(out);
		conEvalVis = new ConstEvalVisitor();
	}
	
	private void initInstanceVars(PrintStream out) {
		code = new CodeStream(out, errorMsg);
		stackHeight = 0;
	}
	
	//Integer Literal
	public Object visitIntegerLiteral(IntegerLiteral n){
		code.emit(n, "subu $sp,$sp,8"); //subtract 8 from $sp, effectively growing $sp by 8
		stackHeight += 8;				//update stackHeight to reflect the growth
		code.emit(n, "sw $s5,4($sp)");
		code.emit(n, "li $t0,"+n.val);	//load our integers value into memory
		code.emit(n, "sw $t0,($sp)");	//store the value of the integer on the top of the stack
		return null;
	}

	//Null
	public Object visitNull(Null n){
		code.emit(n, "subu $sp,$sp,4");
		stackHeight += 4;
		code.emit(n, "sw $zero,($sp)");
		return null;
	}
	
	//True
	public Object visitTrue(True n){
		code.emit(n, "subu $sp,$sp,4");
		stackHeight += 4;
		code.emit(n, "sw 1,($sp)");
		return null;
	}	

	//False
	public Object visitFalse(False n){
		code.emit(n, "subu $sp,$sp,4");
		stackHeight += 4;
		code.emit(n, "sw 1,($sp)");
		return null;
	}	

	//String Literal
	public Object visitStringLiteral(StringLiteral n){
		code.emit(n, "subu $sp,$sp,4");	//subtract 4 from $sp, effectively growing $sp by 4
		stackHeight += 4;				//update stackHeight to reflect the growth
		code.emit(n, "la $t0,strLit_"+n.uniqueCgRep.uniqueId);	//place n's uniqueID in memory
		code.emit(n, "sw $t0,($sp)");					//store the uniqueID on the stack
		return null;
	}
	
	//This
	public Object visitThis(This n){
		code.emit(n, "subu $sp,$sp,4");	//subtract 4 from $sp, effectively growing $sp by 4
		stackHeight += 4;				//update stackHeight to reflect the growth
		code.emit(n, "sw $s2,($sp)");	//store the value at $s2 on top of the stack
		return null;
	}
	
	//Super
	public Object visitSuper(Super n){
		code.emit(n, "subu $sp,$sp,4");	//subtract 4 from $sp, effectively growing $sp by 4
		stackHeight += 4;				//update stackHeight to reflect the growth
		code.emit(n, "sw $s2,($sp)");	//store the value at $s2 on top of the stack
		return null;
	}
	
	//Identifer Exp
	public Object visitIdentifierExp(IdentifierExp n){
		//first
		if(n.link instanceof InstVarDecl){
			code.emit(n, "lw $t0," + n.link.offset + "($s2)");
		}
		else{
			int stackDepth = n.link.offset + stackHeight;
			code.emit(n, "lw $t0," +stackDepth+ "($sp)");
		}
		//then
		if(n.type instanceof IntegerType){
			code.emit(n, "subu $sp,$sp,8");
			stackHeight += 8;
			code.emit(n, "sw $s5,4($sp)");
			code.emit(n, "sw $t0,($sp)");
		}
		else{
			code.emit(n, "subu $sp,$sp,4");
			stackHeight += 4;
			code.emit(n, "sw $t0,($sp)");
		}
		return null;
	}
	
	//Not
	public Object visitNot(Not n){
		n.exp.accept(this);
		code.emit(n, "lw $t0,($sp)");
		code.emit(n, "xor $t0,$t0,1");
		code.emit(n, "sw $t0,($sp)");
		return null;
	}

	//Plus
	public Object visitPlus(Plus n){
		//generate code for my left and right subexpressions
		n.left.accept(this);
		n.right.accept(this);
		code.emit(n, "lw $t0,($sp)");		//load the first integer which is on top of the stack into $t0
		code.emit(n, "lw $t1,8($sp)");		//load the second integer which is 8 below the $sp into $t1
		code.emit(n, "addu $t0,$t0,$t1");	//$t0 = $t0 + $t1
		code.emit(n, "addu $sp,$sp,8");		//add 8 to the $sp, effectively reducing the stackheight by 8, so that our new integer goes on top of the stack
		stackHeight -= 8;					//update stackHeight to reflect the movement
		code.emit(n, "sw $t0,($sp)");		//place our value on top of the stack
		return null;
	}
	
	//Minus
	public Object visitMinus(Minus n){
		n.right.accept(this);				//generate right sub-expression first so that the subu is left - right instead of right - left
		n.left.accept(this);				//generate left sub-expression second so that the subu is left - right instead of right - left
		code.emit(n, "lw $t0,($sp)");		//load the first integer which is on top of the stack into $t0
		code.emit(n, "lw $t1,8($sp)");		//load the second integer which is 8 below the $sp into $t1
		code.emit(n, "subu $t0,$t0,$t1");	//$t0 = $t0 - $t1
		code.emit(n, "addu $sp,$sp,8");		//add 8 to the $sp, effectively reducing the stackheight by 8, so that our new integer goes on top of the stack
		stackHeight -= 8;					//update stackHeight to reflect the movement
		code.emit(n, "sw $t0,($sp)");		//place our value on top of the stack
		return null;
	}
	
	//Times
	public Object visitTimes(Times n){
		n.left.accept(this);
		n.right.accept(this);
		code.emit(n, "lw $t0,($sp)");
		code.emit(n, "lw $t1,8($sp)");
		code.emit(n, "mult $t0,$t1");
		code.emit(n, "mflo $t0");
		code.emit(n, "addu $sp,$sp,8");
		stackHeight -= 8;
		code.emit(n, "sw $t0,($sp)");
		return null;
	}

	//Divide
	public Object visitDivide(Divide n){
		n.left.accept(this);
		n.right.accept(this);
		code.emit(n, "jal divide");  //this millicode ftn shrinks the stack by 8
		stackHeight += 8;			 //shrink stack by 8. Use + since stack grows negative direction
		return null;
	}
	
	//Remainder
	public Object visitRemainder(Remainder n){
		n.left.accept(this);
		n.right.accept(this);
		code.emit(n, "jal remainder");  //this millicode ftn shrinks the stack by 8
		stackHeight += 8;				//shrink stack by 8. Use + since stack grows negative direction
		return null;
	}

	//Equals
	public Object visitEquals(Equals n){
		n.left.accept(this);
		n.right.accept(this);
		//if both operands are Integer Type
		if(n.left.type instanceof IntegerType && n.right.type instanceof IntegerType){
			code.emit(n, "lw $t0,($sp)");
			code.emit(n, "lw $t1,8($sp)");
			code.emit(n, "seq $t0,$t0,$t1");
			code.emit(n, "addu $sp,$sp,12");
			stackHeight -= 12;
			code.emit(n, "sw $t0,($sp)");
		}
		else{
			code.emit(n, "lw $t0,($sp)");
			code.emit(n, "lw $t1,4($sp)");
			code.emit(n, "seq $t0,$t0,$t1");
			code.emit(n, "addu $sp,$sp,4");
			stackHeight -= 4;
			code.emit(n, "sw $t0,($sp)");
		}
		return null;
	}

	//Greater Than
	public Object visitGreaterThan(GreaterThan n){
		n.left.accept(this);
		n.right.accept(this);
		code.emit(n, "lw $t0,($sp)");
		code.emit(n, "lw $t1,8($sp)");
		code.emit(n, "sgt $t0,$t1,$t0");
		code.emit(n, "addu $sp,$sp,12");
		stackHeight -= 12;
		code.emit(n, "sw $t0,($sp)");
		return null;
	}

	//Less Than
	public Object visitLessThan(LessThan n){
		n.left.accept(this);
		n.right.accept(this);
		code.emit(n, "lw $t0,($sp)");
		code.emit(n, "lw $t1,8($sp)");
		code.emit(n, "sgt $t0,$t1,$t0");
		code.emit(n, "addu $sp,$sp,12");
		stackHeight -= 12;
		code.emit(n, "sw $t0,($sp)");
		return null;
	}
	
	//And
	public Object visitAnd(And n){
		n.left.accept(this);
		code.emit(n, "lw $t0,($sp)");
		code.emit(n, "beq $t0,$zero, skip_" + n.uniqueId);
		code.emit(n, "addu $sp,$sp,4");
		stackHeight -= 4;
		n.right.accept(this);
		code.emit(n, "skip_" + n.uniqueId + ":");
		return null;
	}
	
	//Or
	public Object visitOr(Or n){
		n.left.accept(this);
		code.emit(n, "lw $t0,($sp)");
		code.emit(n, "beq $t0,$zero, skip_" + n.uniqueId);
		code.emit(n, "addu $sp,$sp,4");
		stackHeight -= 4;
		n.right.accept(this);
		code.emit(n, "skip_" + n.uniqueId);
		return null;
	}

	//Array Length
	public Object visitArrayLength(ArrayLength n){
		n.exp.accept(this);
		code.emit(n, "lw $t0,($sp)");
		code.emit(n, "beq $t0,$zero,nullPtrException");
		code.emit(n, "$t0,-4($t0)");
		code.emit(n, "sw $s5,($sp)");
		code.emit(n, "subu $sp,4");
		stackHeight += 4;
		code.emit(n, "sw $t0,($sp)");
		return null;
	}
	
	//Array Lookup
	public Object visitArrayLookup(ArrayLookup n){
		n.arrExp.accept(this);
		n.idxExp.accept(this);
		code.emit(n, "lw $t0,8($sp)");
		code.emit(n, "beq $t0,$zero,nullPtrException");
		code.emit(n, "lw $t1,-4($t0)");
		code.emit(n, "lw $t2,($sp)");
		code.emit(n, "bgeu $t2,$t1,arrayIndexOutOfBounds");
		code.emit(n, "sll $t2,$t2,2");
		code.emit(n, "addu $t2,$t2,$t0");
		code.emit(n, "lw $t0,($t2)");
		if(n.arrExp.type instanceof IntegerType){
			code.emit(n, "sw $t0,4($sp)");
			code.emit(n, "sw $s5,8($sp)");
			code.emit(n, "addu $sp,$sp,4");
			stackHeight -= 4;
		}
		else{
			code.emit(n, "sw $t0,8($sp)");
			code.emit(n, "addu $sp,$sp,8");
			stackHeight -= 8;
		}
		return null;
	}

	//InstVarAccess
	public Object visitInstVarAccess(InstVarAccess n){
		n.exp.accept(this);
		int offset = n.varDec.offset;
		code.emit(n, "lw $t0,($sp)");
		code.emit(n, "beq $t0,$zero,nullPtrException");
		code.emit(n, "lw $t0," + offset + "($t0)");
		if(n.type instanceof IntegerType){
			code.emit(n, "subu $sp,$sp,4");
			code.emit(n, "sw $s5,4($sp)");
			code.emit(n, "sw $t0,($sp)");
			stackHeight += 4;
		}
		else{
			code.emit(n, "sw $t0,($sp)");
		}
		return null;
	}

	//InstanceOf
	public Object visitInstanceOf(InstanceOf n){
		n.exp.accept(this);
		code.emit(n, "la $t0,CLASS_"+n.checkType.toString2());
		code.emit(n, "la $t1,CLASS_END_"+n.checkType.toString2());
		code.emit(n, "jal instanceof");
		return null;
	}

	//Cast
	public Object visitCast(Cast n){
		n.exp.accept(this);
		code.emit(n, "la $t0,CLASS_"+n.castType.toString2());
		code.emit(n, "la $t1,CLASS_END_"+n.castType.toString2());
		code.emit(n, "jal checkCast");
		return null;
	}

	//New Object
	public Object visitNewObject(NewObject n){
		//determine # of obj instance variables
		int objInstVars = n.objType.link.numObjInstVars;
		//determine # of data instance variables + 1
		int dataInstVars = n.objType.link.numDataInstVars + 1;
		code.emit(n, "li $s6," + dataInstVars);
		code.emit(n, "li $s7,"+ objInstVars);
		code.emit(n, "jal newObject");
		//jal newObject pushes a word onto the stack so grow the stack by 4
		stackHeight -= 4;
		code.emit(n, "la $t0,CLASS_"+ n.objType.link.name);
		code.emit(n, "sw $t0,-12($s7)");
		return null;
	}

	//New Array
	public Object visitNewArray(NewArray n){
		n.sizeExp.accept(this);
		String arrayTypeString = "ObjectArray";
		code.emit(n, "lw $s7,($sp)");
		code.emit(n, "addu $sp,$sp,8");
		stackHeight -= 8;
		if(n.objType instanceof IntegerType || n.objType instanceof BooleanType){
			code.emit(n, "li $s6,-1");
			arrayTypeString = "DataArray";
		}
		else{
			code.emit(n, "li $s6,1");
		}
		code.emit(n, "jal newObject");
		//jal newObject pushes a word onto the stack so grow the stack by 4
		stackHeight -= 4;
		code.emit(n, "la $t0,CLASS_"+arrayTypeString);
		code.emit(n, "sw $t0,-12($s7)");
		return null;
	}

	//Call (Super and non-super)
	public Object visitCall(Call n){
		int startingStackHeight = stackHeight;
		n.obj.accept(this);
		n.parms.accept(this);
		//obj is a super
		if(n.obj instanceof Super){
			//if method is a library method
			if(n.methodLink.pos < 0){
				code.emit(n, "jal "+n.methName+"_"+n.methodLink.classDecl.name);
			}
			else{
				code.emit(n, "jal fcn_"+n.uniqueId+"_"+n.methName);
			}
		}
		//obj is NOT a super
		else{
			int mmm = n.methodLink.thisPtrOffset - 4;
			int nnn = 4 * n.methodLink.vtableOffset;
			code.emit(n, "lw $t0," +mmm+"($sp)");
			code.emit(n, "beq $t0,$zero,nullPtrException");
			code.emit(n, "lw $t0,-12($t0)");
			code.emit(n, "lw $t0,"+nnn+"($t0)");
			code.emit(n, "jalr $t0");
		}
		//update stackHeight using saved Height + related int value
		if(n.type instanceof VoidType){ 
			stackHeight = startingStackHeight;	//if the exp type is void, stackHeight = saved stackHeight + 0
		}
		else if(n.type instanceof IntegerType){
			stackHeight = startingStackHeight + 8; //if exp type is int, stackHeight = saved stackHeight + 8
		}
		else{
			stackHeight = startingStackHeight + 4;	//if exp is type object/boolean, stackHeight = saved stackHeight + 4
		}
		return null;
	}
	
	//Local VarDecl
	public Object visitLocalVarDecl(LocalVarDecl n){
		n.initExp.accept(this);		//generate code for the init exp
		n.offset = 0 - stackHeight;
		return null;
	}
	
	//CallStatement
	public Object visitCallStatement(CallStatement n){
		n.callExp.accept(this);
		if(n.callExp.type instanceof IntegerType){
			code.emit(n, "addu $sp,$sp,8");
			stackHeight -= 8;
		}
		else if(n.callExp.type instanceof VoidType){}
		else {
			code.emit(n, "addu $sp,$sp,4");
			stackHeight -=4;
		}
		return null;
	}
	
	//Block
	public Object visitBlock(Block n){
		int savedStackHeight = stackHeight;
		n.stmts.accept(this);
		if(stackHeight != savedStackHeight){
			int ddd = stackHeight - savedStackHeight;
			code.emit(n, "addu $sp," +ddd);
		}
		stackHeight = savedStackHeight;
		return null;
	}

	//If
	public Object visitIf(If n){
		n.exp.accept(this);
		code.emit(n, "lw $t0,($sp)");
		code.emit(n, "addu $sp,$sp,4");
		stackHeight -= 4;
		code.emit(n, "beq $t0,$zero,if_else_"+n.uniqueId);
		//gen code for true side
		n.trueStmt.accept(this);
		code.emit(n, "j if_done_"+n.uniqueId);
		code.emit(n, "if_else_" + n.uniqueId + ":");
		//gen code for false side
		n.falseStmt.accept(this);
		code.emit(n, "if_done_" + n.uniqueId + ":");
		return null;
	}
	
	//While
	public Object visitWhile(While n){
		n.stackHeight = stackHeight;
		code.emit(n, "j while_enter_" + n.uniqueId);
		code.emit(n, "while_top_" + n.uniqueId + ":");
		//gen code for body
		n.body.accept(this);
		code.emit(n, "while_enter_" + n.uniqueId + ":");
		//gen code for test exp
		n.exp.accept(this);
		code.emit(n, "lw $t0,($sp)");
		code.emit(n, "addu $sp,$sp,4");
		stackHeight -= 4;
		code.emit(n, "bne $t0,$zero,while_top_" + n.uniqueId);
		code.emit(n, "break_target_" + n.uniqueId + ":");
		return null;
	}

	//Break
	public Object visitBreak(Break n){
		int diffHeight = stackHeight - n.breakLink.stackHeight;
		if (diffHeight != 0){
			code.emit(n, "addu $sp," + diffHeight);
		}
		//use n.breakLink.uniqueId instead of n.uniqueId so that we jump to the right spot
		code.emit(n, "j break_target_"+ n.breakLink.uniqueId);
		return null;
	}
	
	//Assign
	public Object visitAssign(Assign n){
		//IdentifierExp
		if(n.lhs instanceof IdentifierExp){
			n.rhs.accept(this);
			IdentifierExp leftHandSide = (IdentifierExp) n.lhs;
			code.emit(n, "lw $t0,($sp)");
			//if LHS is InstVarDecl
			if(leftHandSide.link instanceof InstVarDecl){
				int offset = leftHandSide.link.offset;
				code.emit(n, "sw $t0,"+offset+"($s2)");
			}
			//if LHS is not InstVarDecl
			else{
				int offset = stackHeight + leftHandSide.link.offset;
				code.emit(n, "sw $t0,"+offset+"($sp)");
			}
			//fix up the stack height
			if(n.lhs.type instanceof IntegerType){
				code.emit(n, "addu $sp,$sp,8");
				stackHeight -= 8;
			}
			else{
				code.emit(n, "addu $sp,$sp,4");
				stackHeight -= 4;
			}
		}
		//InstVarAccess
		else if(n.lhs instanceof InstVarAccess){
			InstVarAccess leftHandSide = (InstVarAccess) n.lhs;
			leftHandSide.exp.accept(this);	//gen code for exp expression
			n.rhs.accept(this);				//gen code for RHS
			code.emit(n, "lw $t0,($sp)");
			//default to non-int LHS type
			int sss = 4;
			int finalFix = 8;
			//if LHS is IntegerType, update the above vars
			if(n.lhs.type instanceof IntegerType){
				sss = 8;
				finalFix = 12;
			}
			code.emit(n, "lw $t1,"+sss+"($sp)");
			code.emit(n, "beq $t1,$zero,nullPtrException");
			int nnn = leftHandSide.varDec.offset;
			code.emit(n, "sw $t0,"+nnn+"($t1)");
			code.emit(n, "addu $sp,$sp,"+finalFix);
			stackHeight -= finalFix;
		}
		//ArrayLookup
		else if(n.lhs instanceof ArrayLookup){
			ArrayLookup leftHandSide = (ArrayLookup) n.lhs;
			leftHandSide.arrExp.accept(this);
			leftHandSide.idxExp.accept(this);
			n.rhs.accept(this);
			//if RHS is type IntegerType, add 4 to various lines of code as defined by slides
			if(n.rhs.type instanceof IntegerType){
				code.emit(n, "lw, $t0,($sp)");
				code.emit(n, "lw $t1,16($sp)");
				code.emit(n, "beq $t1,$zero,nullPtrException");
				code.emit(n, "lw $t2,8($sp)");
				code.emit(n, "lw $t3,-4($t1)");
				code.emit(n, "bgeu $t2,$t3,arrayIndexOutOfBounds");
				code.emit(n, "sll $t2,$t2,2");
				code.emit(n, "addu $t2,$t2,$t1");
				code.emit(n, "sw $t0,($t2)");
				code.emit(n, "addu $sp,$sp,20");
				stackHeight -= 20;
			}
			//if RHS is not IntegerType, keep identical to slides
			else{
				code.emit(n, "lw, $t0,($sp)");
				code.emit(n, "lw $t1,12($sp)");
				code.emit(n, "beq $t1,$zero,nullPtrException");
				code.emit(n, "lw $t2,4($sp)");
				code.emit(n, "lw $t3,-4($t1)");
				code.emit(n, "bgeu $t2,$t3,arrayIndexOutOfBounds");
				code.emit(n, "sll $t2,$t2,2");
				code.emit(n, "addu $t2,$t2,$t1");
				code.emit(n, "sw $t0,($t2)");
				code.emit(n, "addu $sp,$sp,16");
				stackHeight -= 16;
			}
		}
		return null;
	}
	
	//Label
	public Object visitLabel(Label n){
		stackHeight = n.enclosingSwitch.stackHeight;
		code.emit(n, "case_label_" +n.uniqueId + ":");
		return null;
	}
	
	//Switch  ~~ TODO
	/*
	public Object visitSwitch(Switch n){
		n.stackHeight = stackHeight;
		n.exp.accept(this);
		code.emit(n, "lw $t1,($sp)");
		code.emit(n, "addu $sp,8");
		stackHeight -= 8;	//adjust stackHeight according to above emit code
		//gen code for conditional branching
		Default defaultHolder = null;
		
		
		
		n.stmts.accept(this);
		code.emit(n, "break_target_" + n.uniqueId + ":");
		stackHeight = n.stackHeight;
		return null;
	}*/
	
	//MethodDeclVoid
	public Object visitMethodDeclVoid(MethodDeclVoid n){
		code.emit(n, ".global fcn_"+n.uniqueId+"_"+n.name);
		code.emit(n, "fcn_"+n.uniqueId+"_"+n.name+":");
		code.emit(n, "subu $sp,$sp,4");
		code.emit(n, "sw $s2,($sp)");
		//determine stack-top-relative location, nnn, of method's this-pointer
		int nnn = n.thisPtrOffset;
		code.emit(n, "lw $s2,"+nnn+"($sp)");	//put address of return address into $s2
		code.emit(n, "sw $ra,"+nnn+"($sp)");	//put return addresss onto stack
		stackHeight = 0;
		n.stmts.accept(this);	//Generate code for the body
		//determine offset of saved return address, JJJ, and saved this-pointer, KKK, relative to current stack height
		int jjj = stackHeight + 4;	// saved return address relative to current stack height -- Return address is 1 word past all of the methods body stmts
		int kkk = nnn + stackHeight;
		code.emit(n, "lw $ra,"+jjj+"($sp)");
		code.emit(n, "lw $s2,"+kkk+"($sp)");
		//computer amount to pop stack, MMM
		int spaceForParameters = 0;
		for(VarDecl vd : n.formals){
			//catch our formals having null spaces in it
			//most likely won't happen but better safe than sorry
			if(vd == null){
				continue;
			}
			if(vd.type instanceof IntegerType){
				spaceForParameters += 8;
			}
			else if(vd.type instanceof VoidType){
				spaceForParameters += 0;
			}
			else{
				spaceForParameters += 4;
			}
		}

		int mmm = stackHeight + 4 + 4 + spaceForParameters;
		code.emit(n, "addu $sp,$sp,"+mmm);
		code.emit(n, "jr $ra");
		return null;
	}
	
	//MethodDeclNonVoid
	public Object visitMethodDeclNonVoid(MethodDeclNonVoid n){
		code.emit(n, ".globl fcn_" + n.uniqueId + "_" + n.name);
		code.emit(n, "fcn_" + n.uniqueId + "_" + n.name);
		code.emit(n, "subu $sp,$sp,4");
		code.emit(n, "sw $s2,($sp)");
		//determine stack-top-relative location
		int nnn = n.thisPtrOffset;
		code.emit(n, "lw $s2," + nnn + "($sp)");
		code.emit(n, "sw $ra," + nnn + "($sp)");
		stackHeight = 0;
		//generate code for method's body
		n.stmts.accept(this);
		//gen code for method's return exp
		n.rtnExp.accept(this);
		//offset of saved return addr relative to current stackHeight
		int jjj = stackHeight + 4;
		//saved this-ptr relative to current stackHeight
		int kkk = nnn + stackHeight;
		code.emit(n, "lw $ra," + jjj + "($sp)");
		code.emit(n, "lw $s2," + kkk + "($sp)");
		//offset on stack for return-value relative to curr stackHeight
		int yyy = kkk - 4;
		int spaceForRtnValue = 4;
		code.emit(n, "lw $t0,($sp)");
		code.emit(n, "sw $t0," +yyy+ "($sp)");
		if(n.rtnType instanceof IntegerType){
			int zzz = stackHeight +n.thisPtrOffset;
			spaceForRtnValue = 8;
			code.emit(n, "sw $s5," +zzz+ "($sp)");
		}
		//compute ammount to pop stack, MMM
		int spaceForParameters = 0;
		for(VarDecl vd : n.formals){
			//catch our formals having null spaces in it
			//most likely won't happen but better safe than sorry
			if(vd == null){
				continue;
			}
			if(vd.type instanceof IntegerType){
				spaceForParameters += 8;
			}
			else if(vd.type instanceof VoidType){
				spaceForParameters += 0;
			}
			else{
				spaceForParameters += 4;
			}
		}
		int mmm = stackHeight + 4 + spaceForParameters + 4 - spaceForRtnValue;
		code.emit(n, "addu $sp,$sp," + mmm);
		code.emit(n, "jr $ra");
		return null;
	}
	
	//Program
	public Object visitProgram(Program n){
		//generate code for the main label
		code.emit(n, ".text");
		code.emit(n, ".globl main");
		code.emit(n, "main:");
		code.emit(n, "# initialize registers, etc.");
		code.emit(n, "jal vm_init");
		stackHeight = 0; 				//set stackHeight to 0
		n.mainStatement.accept(this);	//generate code for the mainStatement
		code.emit(n, "# exit program");
		code.emit(n, "li $v0,10");
		code.emit(n, "syscall");
		n.classDecls.accept(this);			//generate code for all the class declarations
		code.flush();					//flush the code stream
		return null;
	}
}


	
