package treedisplay;

import java.io.*;
import java.util.Vector;
import java.awt.Rectangle;

public class TreeDrawer {
	private TreeDisplayable target;
	private Vector<TDHelper> helpers;
	private Object auxData;
	private Vector<TDHelper> linkers;
	private Vector<TDHelper> linkees;
	private TDHelper linkObject;

	public void printToStream(PrintStream ps) {
		helpPrintToStream(ps, target, 0);
	}

	private static void indent(PrintStream ps, int n) {
		for (int i = 0; i < n; i++)
			ps.print(" ");
	}

	private void helpPrintToStream(PrintStream ps, TreeDisplayable tgt,
			int indentNum) {
		if (tgt == null) {
			/***new***/ indent(ps, indentNum); ps.println("****NULL****");
			return;
		}
		indent(ps, indentNum);
		ps.println(tgt.shortDescription(auxData));
		for (int subobjCount = 0;; subobjCount++) {
			try {
				TreeDisplayable subobj = tgt.getDrawTreeSubobj(subobjCount);
				if (subobj != null && subobj.nodeIsList()) {
					helpPrintToStream2(ps, subobj, indentNum + 1);
				} else {
					helpPrintToStream(ps, subobj, indentNum + 1);
				}
			} catch (TreeDrawException x) {
				break;
			}
		}
	}

	private void helpPrintToStream2(PrintStream ps, TreeDisplayable tgt,
			int indentNum) {
		if (tgt == null) {
			/***new***/ indent(ps, indentNum); ps.println("****NULL****");
			return;
		}
		indent(ps, indentNum);
		ps.println("*====list====*");
		for (int subobjCount = 0;; subobjCount++) {
			try {
				TreeDisplayable subobj = tgt.getDrawTreeSubobj(subobjCount);
				if (subobj != null && subobj.nodeIsList()) {
					helpPrintToStream2(ps, subobj, indentNum + 1);
				} else {
					helpPrintToStream(ps, subobj, indentNum + 1);
				}
			} catch (TreeDrawException x) {
				break;
			}
		}
	}



	public TreeDrawer(TreeDisplayable theTarget, Object auxDat) {
		target = theTarget;
		auxData = auxDat;
		helpers = new Vector<TDHelper>();
		for (int i = 0; i < helpers.size(); i++) {
			TDHelper h = helpers.elementAt(i);
		}
	}

	void setLinksFor(Rectangle r) {
		int limit = helpers.size();
		TDHelper theObject = null;
		for (int i = 0; i < limit; i++) {
			TDHelper h = helpers.elementAt(i);
			if (h.original != null
					&& h.drawRect != null
					&& r.contains(h.drawRect.x, h.drawRect.y)
					&& r.contains(h.drawRect.x + h.drawRect.width, h.drawRect.y
							+ h.drawRect.height)) {
				if (theObject != null) {
					linkObject = null;
					linkers = null;
					linkees = null;
					return; // not unique
				}
				theObject = h;
			}
		}
		if (theObject == null)
			return;
		linkObject = theObject;
		linkers = new Vector<TDHelper>();
		linkees = new Vector<TDHelper>();
		TreeDisplayable links[] = theObject.original.getDrawTreeLinks();
		for (int i = 0; i < limit; i++) {
			TDHelper h = helpers.elementAt(i);
			if (h.original != null) {
				TreeDisplayable links2[] = h.original.getDrawTreeLinks();
				if (links2 != null) {
					for (int j = 0; j < links2.length; j++) {
						if (links2[j] == theObject.original) {
							linkers.addElement(h);
							break;
						}
					}
				}
				if (links != null) {
					for (int j = 0; j < links.length; j++) {
						if (links[j] == h.original) {
							linkees.addElement(h);
							break;
						}
					}
				}
			}
		}
	}

}
