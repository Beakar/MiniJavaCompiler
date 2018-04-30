package syntaxtree;

import java.io.PrintStream;
import visitor.Visitor;
import treedisplay.*;
import errorMsg.*;
import prettyPrinter.PrettyPrinter;

/**
 * an AST node (abstract)
 */
public abstract class AstNode implements TreeDisplayable {

	// instance variables filled in by constructor
	public int pos; // the character-position of this construct in the source file
	public int uniqueId; // the object's unique ID

	// static variable used to generate unique IDs
	private static int uniqueIdCounter = 0;

	/**
	 * constructor
	 * @param poss file position
	 */
	public AstNode(int poss) {
		pos = poss;
		uniqueId = uniqueIdCounter++;
	}
	
	/*************** remaining methods are visitor- and display-related ****************/

	public abstract Object accept(Visitor v);

	//method to give the elements we have links to
	public AstNode[] links() {
		return null;
	}

	public TreeDisplayable getDrawTreeSubobj(int n) throws TreeDrawException {
		throw new TreeDrawException();
	}

	public TreeDisplayable[] getDrawTreeLinks() {
		AstNode helper[] = this.links();
		if (helper == null) return null;
		return (TreeDisplayable[])helper;
	}

	String shortClassName() {
		String fullClassName = this.getClass().getName();
		int lastDotPosition = fullClassName.lastIndexOf((int)'.');
		if (lastDotPosition >= 0) {
			return fullClassName.substring(lastDotPosition+1);
		}
		else {
			return fullClassName;
		}
	}

	protected String[]stringsInDescr() {
		return null;
	}

	protected static String[] strArrayPlus1(String[] arr, String s) {
		if (arr == null) return new String[]{s};
		else if (s == null) return arr;
		else {
			String[] rtnVal = new String[arr.length+1];
			for (int i = 0; i < arr.length; i++) {
				rtnVal[i] = arr[i];
			}
			rtnVal[arr.length] = s;
			return rtnVal;
		}
	}
	protected static String[] strArrayPlus1(String s, String[] arr) {
		if (arr == null) return new String[]{s};
		else if (s == null) return arr;
		else {
			String[] rtnVal = new String[arr.length+1];
			for (int i = 0; i < arr.length; i++) {
				rtnVal[i+1] = arr[i];
			}
			rtnVal[0] = s;
			return rtnVal;
		}
	}
	public String[] tempStrs() { return stringsInDescr();}
	public String shortDescription(Object auxData) {
		String idStr = "#"+uniqueId;
		String posField = "";
		String[] strings = this.stringsInDescr();
		String stringDesc = "";
		if (strings != null && strings.length > 0) {
			stringDesc = "["+strings[0];
			for (int i = 1; i < strings.length; i++) {
				stringDesc += "," + strings[i];
			}
			stringDesc += "]";
		}
		if (auxData != null) {
			posField += ":"+((ErrorMsg)auxData).lineAndChar(pos);
		}
		String linkString = "";
		AstNode[] theLinks = this.links();
		if (theLinks != null) {
			for (int i = 0; i < theLinks.length; i++) {
				AstNode obj = theLinks[i];
				if (obj == null) {
					linkString += ";";
				}
				else {
					linkString += ";@"+obj.uniqueId;
				}
			}
		}
		return this.shortClassName()+stringDesc+idStr+posField+linkString;
	}

	public String longDescription(Object auxData) {
		return shortDescription(auxData);
	}
	public boolean nodeIsList() {
		return false;
	}

	public String toString() {
		return this.toString(1);
	}
	private String toString(int depth) {
		return "["+shortDescription(null)+":"+argsAsString(depth)+"]";
	}
	private String argsAsString(int depth) {
		if (depth <= 0) return "...";
		String rtnVal = "";
		for (int i = 0; ; i++) {
			try {
				TreeDisplayable td = getDrawTreeSubobj(i);
				if (td == null) break;
				if (td.nodeIsList()) {
					rtnVal += "[";
					if (td instanceof AstList) {
						AstList<AstNode> al = (AstList<AstNode>)td;
						for (int j = 0; j < al.size(); j++) {
							AstNode an = al.elementAt(j);
							rtnVal += an.argsAsString(depth-1)+" ";
						}
					}
					else {
						rtnVal += "?? ";
					}
					rtnVal += "]";
				}
				else if (td instanceof AstNode) {
					rtnVal += ((AstNode)td).toString(depth-1)+" ";
				}
				else {
					rtnVal += "?? ";
				}
			}
			catch (TreeDrawException tdx){
				break;
			}
		}
		return rtnVal;
	}

	/*************** remaining methods are for pretty-print ****************/
	public abstract void prettyPrint(PrettyPrinter pp, PrintStream ps);
//	{
//		ps.println("[to be implemented]");
//	}
}
