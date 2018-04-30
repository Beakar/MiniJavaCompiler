package treedisplay;

import java.awt.*;
import java.io.*;
import java.util.Vector;

public class TreeDrawCanvas extends Canvas {
	static Font ourFont;
	static int FontHeight;
	static FontMetrics FM;
	private TreeDisplayable target;
	private Vector<TDHelper> helpers;
	private Object auxData;
	private Vector<TDHelper> linkers;
	private Vector<TDHelper> linkees;
	private TDHelper linkObject;
	private Color backgroundColor;

	private static boolean doWindow;
	
	private static final TreeDisplayable dummy = new TreeDisplayable() {

		@Override
		public TreeDisplayable getDrawTreeSubobj(int n)
				throws TreeDrawException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public TreeDisplayable[] getDrawTreeLinks() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String shortDescription(Object obj) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String longDescription(Object obj) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean nodeIsList() {
			// TODO Auto-generated method stub
			return false;
		}
		
	};

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
		/***NEW***/	ps.println("****NULL****");
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
		if (tgt == null)
			return;
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

	public TreeDrawCanvas(TreeDisplayable theTarget, Object auxDat, Color c) {
		this(theTarget, auxDat, true, 10, c);
	}

	public TreeDrawCanvas(TreeDisplayable theTarget, Object auxDat,
			boolean doWin, int fontSize, Color c) {
		doWindow = doWin;
		if (doWindow) {
			ourFont = new Font("MonoSpaced", Font.PLAIN, fontSize);
			FM = getFontMetrics(ourFont);
			FontHeight = FM.getMaxAscent() + FM.getMaxDescent();
			//setBackground(Color.yellow.brighter());
			setBackground(c);
		}

		target = theTarget;
		auxData = auxDat;
		helpers = new Vector<TDHelper>();
		createHelperStruct(target, helpers, 0, auxDat);
		for (int i = 0; i < helpers.size(); i++) {
			TDHelper h = helpers.elementAt(i);
		}
		if (doWindow) {
			layoutRects();
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

	private Rectangle layoutRects() {
		int limit = helpers.size();
		int visiCount = 0;
		int maxHeight = 50;
		int maxWidth = 50;
		int nextVisibleCol = Integer.MAX_VALUE;
		for (int i = 0; i < limit; i++) {
			TDHelper thisHelper = helpers.elementAt(i);
			if (thisHelper.indentation > nextVisibleCol) {
				thisHelper.drawRect = null;
				continue;
			}
			if (thisHelper.subsVisible) {
				nextVisibleCol = Integer.MAX_VALUE;
			} else {
				nextVisibleCol = thisHelper.indentation;
			}
			thisHelper.layoutRect(visiCount, thisHelper.indentation, auxData);
			visiCount++;
			maxWidth = Math.max(maxWidth, thisHelper.drawRect.x
					+ thisHelper.drawRect.width);
			maxHeight = Math.max(maxHeight, thisHelper.drawRect.y
					+ thisHelper.drawRect.height);
		}
		Rectangle rtnVal = new Rectangle(maxWidth + TDHelper.rowPixelBase,
				maxHeight + TDHelper.colPixelBase);
		this.setSize(Math.max(500, rtnVal.width), Math.max(500, rtnVal.height));
		return rtnVal;

	}

	private static void createHelperStruct(TreeDisplayable tgt, Vector<TDHelper> helpers,
			int indent, Object auxDat) {
		TDHelper helper = new TDHelper(tgt, indent, false, doWindow);
		if (doWindow)
			helper.layoutRect(helpers.size(), indent, auxDat);
		helpers.addElement(helper);
		if (tgt == null) {
			return;
		}
		for (int subobjCount = 0;; subobjCount++) {
			try {
				TreeDisplayable subobj = tgt.getDrawTreeSubobj(subobjCount);
				if (subobj != null && subobj.nodeIsList()) {
					createHelperStruct2(subobj, helpers, indent + 1, auxDat);
				} else {
					createHelperStruct(subobj, helpers, indent + 1, auxDat);
				}
			} catch (TreeDrawException x) {
				break;
			}
		}
	}

	private static void createHelperStruct2(TreeDisplayable tgt, Vector<TDHelper> helpers,
			int indent, Object auxData) {
		TDHelper helper = new TDHelper(dummy, indent, true, doWindow);
		if (doWindow)
			helper.layoutRect(helpers.size(), indent, auxData);
		helpers.addElement(helper);
		if (tgt == null)
			return;
		for (int subobjCount = 0;; subobjCount++) {
			try {
				TreeDisplayable subobj = tgt.getDrawTreeSubobj(subobjCount);
				if (subobj != null && subobj.nodeIsList()) {
					createHelperStruct2(subobj, helpers, indent + 1, auxData);
				} else {
					createHelperStruct(subobj, helpers, indent + 1, auxData);
				}
			} catch (TreeDrawException x) {
				break;
			}
		}
	}

	static Color lightGray = new Color(160, 160, 160);
	static Color lightBlue = new Color(190, 240, 255);
	static Color darkGreen = Color.green.darker().darker();

	public void paint(Graphics g) {
		if (!doWindow)
			return;
		int limit = helpers.size();
		for (int i = 0; i < limit; i++) {
			TDHelper thisHelper = helpers.elementAt(i);
			Rectangle rect = thisHelper.drawRect;
			if (rect == null)
				continue;
			String boxString = "";
			Color boxColor = Color.white;
			if (thisHelper.original == null) {
				boxColor = Color.orange;
				boxString = "(null)";
			}
			else if (thisHelper.original == dummy) {
				boxColor = thisHelper.subsVisible ? Color.black : lightGray;
			}
			else {
				boxString = thisHelper.original.shortDescription(auxData);
				if (!thisHelper.subsVisible) {
					boxColor = lightBlue;
				}
			}
			Color textColor = Color.white;
			if (thisHelper == linkObject) {
				boxColor = Color.red;
			} else if (linkers != null && linkers.contains(thisHelper)) {
				boxColor = Color.blue;
			} else if (linkees != null && linkees.contains(thisHelper)) {
				boxColor = darkGreen;
			} else {
				textColor = Color.black;
			}

			drawBox(boxString, rect, boxColor, textColor, g);

			TDHelper target = null;
			for (int j = i + 1; j < limit; j++) {
				TDHelper other = helpers.elementAt(j);
				if (other.indentation <= thisHelper.indentation)
					break;
				if (other.indentation == thisHelper.indentation + 1) {
					target = other;
				}
			}
			if (target != null) {
				g.setColor(Color.black);
				drawVerticalLine(rect, target.drawRect, g);
			}
		}
	}

	private static void drawBox(String str, Rectangle r, Color fillColor,
			Color textColor, Graphics g) {
		g.setColor(fillColor);
		g.fillRect(r.x, r.y, r.width, r.height);
		g.setColor(Color.black);
		g.drawRect(r.x, r.y, r.width, r.height);
		int xbase = r.x;
		int ybase = r.y;
		int thisBoxHeight = r.height;
		g.drawLine(xbase - TDHelper.minBoxWidth / 2, ybase + thisBoxHeight / 2,
				xbase, ybase + thisBoxHeight / 2);
		g.setFont(ourFont);
		if (str != null) {
			g.setColor(textColor);
			g.drawString(str, xbase + 3, ybase + thisBoxHeight
					- TDHelper.descenderPixels);
		}
	}

	private static void drawVerticalLine(Rectangle rStart, Rectangle rEnd,
			Graphics g) {
		if (rStart == null || rEnd == null)
			return;
		int yStart = rStart.y + rStart.height;
		int yEnd = rEnd.y + (rEnd.height / 2);
		if (yStart < yEnd) {
			int xbase = rStart.x + TDHelper.minBoxWidth / 2;
			g.drawLine(xbase, yStart, xbase, yEnd);
		}
	}

	void processClick(int x, int y) {
		int idx = findHelperAt(x, y);
		if (idx < 0)
			return;
		TDHelper h = helpers.elementAt(idx);
		h.subsVisible = !h.subsVisible;
		layoutRects();
		repaint();
	}

	private int findHelperAt(int xpixel, int ypixel) {
		int limit = helpers.size();
		for (int i = 0; i < limit; i++) {
			TDHelper h = helpers.elementAt(i);
			if (h.drawRect != null && h.drawRect.contains(xpixel, ypixel)) {
				return i;
			}
		}
		return -1;
	}
}