package parse;
import java.util.List;

import errorMsg.*;

public class TokenGrammar implements wrangLR.runtime.MessageObject {

	public TokenGrammar(ErrorMsg em) {
		errorMsg = em;
	}
	private ErrorMsg errorMsg;
	
	public void error(int pos, String msg) {
		errorMsg.error(pos, msg);
	}
	
	public void warning(int pos, String msg) {
		errorMsg.warning(pos, msg);
	}
	
	public void reportTok(int pos, String s) {
		System.out.println(errorMsg.lineAndChar(pos)+": "+s);
	}


//: start ::= ws* token*
	

//: token ::= # `boolean =>
public void sawBoolean(int pos) {
       reportTok(pos, "`boolean");
}
//: token ::= # `class =>
public void sawClass(int pos) {
       reportTok(pos, "`class");
}
//: token ::= # `extends =>
public void sawExtends(int pos) {
       reportTok(pos, "`extends");
}
//: token ::= # `void =>
public void sawVoid(int pos) {
       reportTok(pos, "`void");
}
//: token ::= # `int =>
public void sawInt(int pos) {
       reportTok(pos, "`int");
}
//: token ::= # `while =>
public void sawWhile(int pos) {
       reportTok(pos, "`while");
}
//: token ::= # `if =>
public void sawIf(int pos) {
       reportTok(pos, "`if");
}
//: token ::= # `else =>
public void sawElse(int pos) {
       reportTok(pos, "`else");
}
//: token ::= # `for =>
public void sawFor(int pos) {
       reportTok(pos, "`for");
}
//: token ::= # `break =>
public void sawBreak(int pos) {
       reportTok(pos, "`break");
}
//: token ::= # `this =>
public void sawThis(int pos) {
       reportTok(pos, "`this");
}
//: token ::= # `false =>
public void sawFalse(int pos) {
       reportTok(pos, "`false");
}
//: token ::= # `true =>
public void sawTrue(int pos) {
       reportTok(pos, "`true");
}
//: token ::= # `super =>
public void sawSuper(int pos) {
       reportTok(pos, "`super");
}
//: token ::= # `null =>
public void sawNull(int pos) {
       reportTok(pos, "`null");
}
//: token ::= # `return =>
public void sawReturn(int pos) {
       reportTok(pos, "`return");
}
//: token ::= # `instanceof =>
public void sawInstanceof(int pos) {
       reportTok(pos, "`instanceof");
}
//: token ::= # `new =>
public void sawNew(int pos) {
       reportTok(pos, "`new");
}
//: token ::= # `abstract =>
public void sawAbstract(int pos) {
       reportTok(pos, "`abstract");
}
//: token ::= # `assert =>
public void sawAssert(int pos) {
       reportTok(pos, "`assert");
}
//: token ::= # `byte =>
public void sawByte(int pos) {
       reportTok(pos, "`byte");
}
//: token ::= # `case =>
public void sawCase(int pos) {
       reportTok(pos, "`case");
}
//: token ::= # `catch =>
public void sawCatch(int pos) {
       reportTok(pos, "`catch");
}
//: token ::= # `char =>
public void sawChar(int pos) {
       reportTok(pos, "`char");
}
//: token ::= # `const =>
public void sawConst(int pos) {
       reportTok(pos, "`const");
}
//: token ::= # `continue =>
public void sawContinue(int pos) {
       reportTok(pos, "`continue");
}
//: token ::= # `default =>
public void sawDefault(int pos) {
       reportTok(pos, "`default");
}
//: token ::= # `do =>
public void sawDo(int pos) {
       reportTok(pos, "`do");
}
//: token ::= # `double =>
public void sawDouble(int pos) {
       reportTok(pos, "`double");
}
//: token ::= # `enum =>
public void sawEnum(int pos) {
       reportTok(pos, "`enum");
}
//: token ::= # `final =>
public void sawFinal(int pos) {
       reportTok(pos, "`final");
}
//: token ::= # `finally =>
public void sawFinally(int pos) {
       reportTok(pos, "`finally");
}
//: token ::= # `float =>
public void sawFloat(int pos) {
       reportTok(pos, "`float");
}
//: token ::= # `goto =>
public void sawGoto(int pos) {
       reportTok(pos, "`goto");
}
//: token ::= # `implements =>
public void sawImplements(int pos) {
       reportTok(pos, "`implements");
}
//: token ::= # `import =>
public void sawImport(int pos) {
       reportTok(pos, "`import");
}
//: token ::= # `interface =>
public void sawInterface(int pos) {
       reportTok(pos, "`interface");
}
//: token ::= # `long =>
public void sawLong(int pos) {
       reportTok(pos, "`long");
}
//: token ::= # `native =>
public void sawNative(int pos) {
       reportTok(pos, "`native");
}
//: token ::= # `package =>
public void sawPackage(int pos) {
       reportTok(pos, "`package");
}
//: token ::= # `private =>
public void sawPrivate(int pos) {
       reportTok(pos, "`private");
}
//: token ::= # `protected =>
public void sawProtected(int pos) {
       reportTok(pos, "`protected");
}
//: token ::= # `public =>
public void sawPublic(int pos) {
       reportTok(pos, "`public");
}
//: token ::= # `short =>
public void sawShort(int pos) {
       reportTok(pos, "`short");
}
//: token ::= # `static =>
public void sawStatic(int pos) {
       reportTok(pos, "`static");
}
//: token ::= # `strictfp =>
public void sawStrictfp(int pos) {
       reportTok(pos, "`strictfp");
}
//: token ::= # `switch =>
public void sawSwitch(int pos) {
       reportTok(pos, "`switch");
}
//: token ::= # `synchronized =>
public void sawSynchronized(int pos) {
       reportTok(pos, "`synchronized");
}
//: token ::= # `throw =>
public void sawThrow(int pos) {
       reportTok(pos, "`throw");
}
//: token ::= # `throws =>
public void sawThrows(int pos) {
       reportTok(pos, "`throws");
}
//: token ::= # `transient =>
public void sawTransient(int pos) {
       reportTok(pos, "`transient");
}
//: token ::= # `try =>
public void sawTry(int pos) {
       reportTok(pos, "`try");
}
//: token ::= # `volatile =>
public void sawVolatile(int pos) {
       reportTok(pos, "`volatile");
}

//: token ::= # `! =>
public void sawNot(int pos) {
     reportTok(pos, "`!");
}
//: token ::= # `!= =>
public void sawNotEqual(int pos) {
     reportTok(pos, "`!=");
}
//: token ::= # `% =>
public void sawRemainder(int pos) {
     reportTok(pos, "`%");
}
//: token ::= # `&& =>
public void sawAnd(int pos) {
     reportTok(pos, "`&&");
}
//: token ::= # `* =>
public void sawTimes(int pos) {
     reportTok(pos, "`*");
}
//: token ::= # `( =>
public void sawLpar(int pos) {
     reportTok(pos, "`(");
}
//: token ::= # `) =>
public void sawRpar(int pos) {
     reportTok(pos, "`)");
}
//: token ::= # `{ =>
public void sawLbrace(int pos) {
     reportTok(pos, "`{");
}
//: token ::= # `} =>
public void sawRbrace(int pos) {
     reportTok(pos, "`}");
}
//: token ::= # `- =>
public void sawMinus(int pos) {
     reportTok(pos, "`-");
}
//: token ::= # `+ =>
public void sawPlus(int pos) {
     reportTok(pos, "`+");
}
//: token ::= # `= =>
public void sawAssign(int pos) {
     reportTok(pos, "`=");
}
//: token ::= # `== =>
public void sawEqual(int pos) {
     reportTok(pos, "`==");
}
//: token ::= # `[ =>
public void sawLbrack(int pos) {
     reportTok(pos, "`[");
}
//: token ::= # `] =>
public void sawRbrack(int pos) {
     reportTok(pos, "`]");
}
//: token ::= # `|| =>
public void sawOr(int pos) {
     reportTok(pos, "`||");
}
//: token ::= # `< =>
public void sawLess(int pos) {
     reportTok(pos, "`<");
}
//: token ::= # `<= =>
public void sawLessEq(int pos) {
     reportTok(pos, "`<=");
}
//: token ::= # `, =>
public void sawComma(int pos) {
     reportTok(pos, "`,");
}
//: token ::= # `> =>
public void sawGreater(int pos) {
     reportTok(pos, "`>");
}
//: token ::= # `>= =>
public void sawGreaterEq(int pos) {
     reportTok(pos, "`>=");
}
//: token ::= # `. =>
public void sawDot(int pos) {
     reportTok(pos, "`.");
}
//: token ::= # `; =>
public void sawSemi(int pos) {
     reportTok(pos, "`;");
}
//: token ::= # `++ =>
public void sawPlusPlus(int pos) {
     reportTok(pos, "`++");
}
//: token ::= # `-- =>
public void sawMinusMinus(int pos) {
     reportTok(pos, "`--");
}
//: token ::= # `/ =>
public void sawSlash(int pos) {
     reportTok(pos, "`/");
}
	
//: token ::= # ID =>
public void identifier(int pos, String s) {
	reportTok(pos, "identifier: "+s);
}
	
//: token ::= # INTLIT =>
public void intLit(int pos, int n) {
	reportTok(pos, "integer literal: "+n);
}
	
//: token ::= # STRINGLIT =>
public void stringLit(int pos, String s) {
	reportTok(pos, "string literal: "+s);
}
	
//: token ::= # CHARLIT =>
public void charLit(int pos, int n) {
	reportTok(pos, "chracter literal with ASCII value: "+n);
}

/////////////////////////////////////////////////////////////////
///////////// Your modifications should start here //////////////
/////////////////////////////////////////////////////////////////

//================================================================
// the actual tokens
//================================================================

// reserved words
//: `class ::= "class" !idChar ws*
//: reserved ::= `class
//: `else ::= "else" !idChar ws*
//: reserved ::= `else

//special-token characters
//: `!= ::= "!=" ws*
//: `* ::= "*" ws*

// A numeric literal
//: INTLIT ::= # intLit2 ws* =>
public int convertToInt(int pos, String s) {
	try {
		return new Integer(s).intValue();
	}
	catch (NumberFormatException nfx) {
		error(pos, "Character literal value "+s+" is out of range.");
		return 0;
	}
}

//hexadecimal-formatted integers
//: INTLIT ::= # hexint ws* =>
public int hexToInt(int pos, String s) {
	try {
		return Integer.parseInt(s.substring(2), 16);
	} catch (NumberFormatException nfx) {
		error(pos, "Invalid hex number format for " + s);
		return 0;
	}
}

//octal-formatted integers
//: INTLIT ::= # octint ws* =>
public int octToInt(int pos, String s) {
	try {
		return Integer.parseInt(s.substring(1), 8);
	} catch (NumberFormatException nfx) {
		error(pos, "Invalid oct number format for " + s);
		return 0;
	}
}

//================================================================
// character patterns -- "helper symbols"
//================================================================

// pattern that represents an integer literal (without trailing whitespace)
//: intLit2 ::= !"0" digit++ => text

//: hexint ::= "0" x hexdigit++ => text
//: hexdigit ::= {"0".."9" "a".."f" "A".."F"} => pass
//: x ::= {"x" "X"} => pass

//: octint ::= "0" octdigit++ => text
//: octdigit ::= {"0".."7"} => pass

// any character except for ' and \
//: anyChar ::= {32..38 35..91 93..126} => pass

//any character except for " and \
//: anyString ::= {32 33 35..91 93..126} => pass
//: anyString ::= escapeChars => pass

//any printable character
//: commentChar ::= {9 12 32..126} => pass

//: allEscapeChars ::= '\\' => pass
//: allEscapeChars ::= '\"' => pass
//: allEscapeChars ::= "\'" => pass
//: allEscapeChars ::= '\n' => pass
//: allEscapeChars ::= '\t' => pass
//: allEscapeChars ::= '\f' => pass
//: allEscapeChars ::= '\r' => pass

//: escapeChars ::= allEscapeChars =>
/**
 * Converts an escape sequence into its ascii representation
 * @param backslash toss this
 * @param c
 * @return ascii character representation of characer in escape sequence
 */
public char escapeToChar(char backslash, char c) {
	switch(c) { 
	
		//double quote
		case 34:
			return c;
			
		//single quote
		case 39:
			return c;
		
		//backslash
		case 92:
			return c;
			
		//form feed
		case 102:
			return 12;
		
		//newline
		case 110:
			return 10;
			
		//tab
		case 116:
			return 9;
		
		//carriage return
		case 114:
			break;
	}
	
	return 0;
}



//: multilineChar ::= {9 32..41 43..126}
//: multilineChar ::= "*" !"/"
//: multilineChar ::= eol

// a character that can be a non-first character in an identifier
//: idChar ::= letter => pass
//: idChar ::= digit => pass
//: idChar ::= "_" => pass

// a letter
//: letter ::= {"a".."z" "A".."Z"} => pass

// a digit
//: digit ::= {"0".."9"} => pass

//================================================================
// whitespace
//================================================================

//: ws ::= {" " 9} // space or tab
//: ws ::= eol
//: ws ::= COMMENT


// to handle the common end-of-line sequences on different types
// of systems, we treat the sequence CR+LF as an end of line.
// Otherwise, we treat CR or LF appearing separately each as an
// end of line.
//: eol ::= {10} registerNewline
//: eol ::= {13} {10} registerNewline
//: eol ::= {13} !{10} registerNewline // CR alone only if not followed by LF

// empty symbol which registers a new line at the position reduced
//: registerNewline ::= # =>
public void registerNewline(int pos) {
	errorMsg.newline(pos-1);
}

//================================================================
// dummied-up (and incorrect) definitions, so that this starter-file
// will compile. These definitions use unprintable characters.
//================================================================

/*start literals*/
//: CHARLIT ::= # charlit2 ws* =>
public int charToInt(int pos, String s) {
	return s.toCharArray()[1];
}
//: charlit2 ::= "'" anyChar "'" => text

//: ID ::= !reserved letter idChar** ws* => text

//: STRINGLIT ::= '"' anyString* '"' ws* => text


/*start comments*/
//: COMMENT ::= singleline
//: COMMENT ::= multiline

//: singleline ::= slashes commentChar* eol => void
//: multiline ::= slashstar multilineChar* starslash => void

//: slashstar ::= "/*" => pass
//: starslash ::= "*/" => pass
//: slashes ::= "//" => pass


/*start operators*/
//: `! ::= "!" !"=" ws*
//: `% ::= "%" ws*
//: `&& ::= "&&" ws*
//: `( ::= "(" ws*
//: `) ::= ")" ws*
//: `+ ::= "+" !"+" ws*
//: `++ ::= "++" ws*
//: `, ::= "," ws*
//: `- ::= "-" !"-" ws*
//: `-- ::= "--" ws*
//: `. ::= "." ws*
//: `/ ::= !slashes !slashstar "/" !{"/" "*"} ws*
//: `; ::= ";" ws*
//: `< ::= "<" !"=" ws*
//: `<= ::= "<=" ws*
//: `= ::= "=" !"=" ws*
//: `== ::= "==" ws*
//: `> ::= ">" !"=" ws*
//: `>= ::= ">=" ws*
//: `[ ::= "[" ws*
//: `] ::= "]" ws*
//: `{ ::= "{" ws*
//: `|| ::= "||" ws*
//: `} ::= "}" ws*


/*start reserved*/
//: `abstract ::= "abstract" !idChar ws* => void
//: reserved ::= `abstract
//: `assert ::= "assert" !idChar ws* => void
//: reserved ::= `assert
//: `boolean ::= "boolean" !idChar ws* => void
//: reserved ::= `boolean
//: `break ::= "break" !idChar ws* => void
//: reserved ::= `break
//: `byte ::= "byte" !idChar ws* => void
//: reserved ::= `byte
//: `case ::= "case" !idChar ws* => void
//: reserved ::= `case
//: `catch ::= "catch" !idChar ws* => void
//: reserved ::= `catch
//: `char ::= "char" !idChar ws* => void
//: reserved ::= `char
//: `const ::= "const" !idChar ws* => void
//: reserved ::= `const
//: `continue ::= "continue" !idChar ws* => void
//: reserved ::= `continue
//: `default ::= "default" !idChar ws* => void
//: reserved ::= `default
//: `do ::= "do" !idChar ws* => void
//: reserved ::= `do
//: `double ::= "double" !idChar ws* => void
//: reserved ::= `double
//: `enum ::= "enum" !idChar ws* => void
//: reserved ::= `enum
//: `extends ::= "extends" !idChar ws* => void
//: reserved ::= `extends
//: `false ::= "false" !idChar ws* => void
//: reserved ::= `false
//: `final ::= "final" !idChar ws* => void
//: reserved ::= `final
//: `finally ::= "finally" !idChar ws* => void
//: reserved ::= `finally
//: `float ::= "float" !idChar ws* => void
//: reserved ::= `float
//: `for ::= "for" !idChar ws* => void
//: reserved ::= `for
//: `goto ::= "goto" !idChar ws* => void
//: reserved ::= `goto
//: `if ::= "if" !idChar ws* => void
//: reserved ::= `if
//: `implements ::= "implements" !idChar ws* => void
//: reserved ::= `implements
//: `import ::= "import" !idChar ws* => void
//: reserved ::= `import
//: `instanceof ::= "instanceof" !idChar ws* => void
//: reserved ::= `instanceof
//: `int ::= "int" !idChar ws* => void
//: reserved ::= `int
//: `interface ::= "interface" !idChar ws* => void
//: reserved ::= `interface
//: `long ::= "long" !idChar ws* => void
//: reserved ::= `long
//: `native ::= "native" !idChar ws* => void
//: reserved ::= `native
//: `new ::= "new" !idChar ws* => void
//: reserved ::= `new
//: `null ::= "null" !idChar ws* => void
//: reserved ::= `null
//: `package ::= "package" !idChar ws* => void
//: reserved ::= `package
//: `private ::= "private" !idChar ws* => void
//: reserved ::= `private
//: `protected ::= "protected" !idChar ws* => void
//: reserved ::= `protected
//: `public ::= "public" !idChar ws* => void
//: reserved ::= `public
//: `return ::= "return" !idChar ws* => void
//: reserved ::= `return
//: `short ::= "short" !idChar ws* => void
//: reserved ::= `short
//: `static ::= "static" !idChar ws* => void
//: reserved ::= `static
//: `strictfp ::= "strictfp" !idChar ws* => void
//: reserved ::= `strictfp
//: `super ::= "super" !idChar ws* => void
//: reserved ::= `super
//: `switch ::= "switch" !idChar ws* => void
//: reserved ::= `switch
//: `synchronized ::= "synchronized" !idChar ws* => void
//: reserved ::= `synchronized
//: `this ::= "this" !idChar ws* => void
//: reserved ::= `this
//: `throw ::= "throw" !idChar ws* => void
//: reserved ::= `throw
//: `throws ::= "throws" !idChar ws* => void
//: reserved ::= `throws
//: `transient ::= "transient" !idChar ws* => void
//: reserved ::= `transient
//: `true ::= "true" !idChar ws* => void
//: reserved ::= `true
//: `try ::= "try" !idChar ws* => void
//: reserved ::= `try
//: `void ::= "void" !idChar ws* => void
//: reserved ::= `void
//: `volatile ::= "volatile" !idChar ws* => void
//: reserved ::= `volatile
//: `while ::= "while" !idChar ws* => void
//: reserved ::= `while


}
