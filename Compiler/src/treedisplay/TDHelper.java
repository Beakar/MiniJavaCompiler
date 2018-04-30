package treedisplay;

import java.awt.*;

class TDHelper {
  static final int rowPixelBase = 5;
  static final int colPixelBase = 5;
  static final int verticalSpread = 2;
  static final int minBoxWidth = 8;
  static int charAllowance;
  static int descenderPixels;

  TreeDisplayable original;
  int indentation;
  private boolean isList;
  boolean subsVisible;
  Rectangle drawRect;

  public TDHelper(TreeDisplayable orig, int indent,
			      boolean isAList, boolean doWindow) {
    charAllowance = doWindow ? TreeDrawCanvas.FM.charWidth('W') : 8;
    descenderPixels = doWindow ? TreeDrawCanvas.FM.getMaxDescent() : 4;
    original = orig;
    isList = isAList;
    indentation = indent;
    drawRect = null;
    subsVisible = true;
  }

  public void layoutRect(int row, int col, Object auxData) {
    int xbase = xBase(col);
    int ybase = yBase(row);
    int thisBoxWidth = minBoxWidth;
    String str = "(null)";
    if (original != null) {
    	str = original.shortDescription(auxData);
    }
    if (str == null) str = "";
    thisBoxWidth = 6+charAllowance*str.length();
    if (thisBoxWidth < minBoxWidth) thisBoxWidth = minBoxWidth;
//    if (original != null) {
//      String str = original.shortDescription(auxData);
//      if (str == null) str = "";
//      thisBoxWidth = 6+charAllowance*str.length();
//      if (thisBoxWidth < minBoxWidth) thisBoxWidth = minBoxWidth;
//    }
    drawRect = new Rectangle(xbase,ybase,thisBoxWidth, TreeDrawCanvas.FontHeight);
  }
  public Rectangle rect() { return drawRect; }
  private static int xBase(int col) { return colPixelBase+col*minBoxWidth; }
  private static int yBase(int row) {
    return rowPixelBase+row*(verticalSpread+TreeDrawCanvas.FontHeight);
  }
}
