package treedisplay;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class TreeDisplayFrame extends Frame
    implements WindowListener, MouseListener, MouseMotionListener {

  ScrollPane p2;
  TreeDrawCanvas tdc;

  public TreeDisplayFrame(TreeDisplayable t, String title) {
    this(t, title, null, 10);
  }
  public TreeDisplayFrame(TreeDisplayable t, String title, Object auxData,
        int fontSize) {
  	this(t,title,auxData,fontSize,Color.yellow.brighter());
  }
 
  public TreeDisplayFrame(TreeDisplayable t, String title, Object auxData,
                 int fontSize, Color c) {
    if (title != null) {
      this.setTitle(title);
      this.setSize(500,500);

      this.addWindowListener(this);
      p2 = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
      p2.setSize(500,500);
      tdc = new TreeDrawCanvas(t, auxData, true, fontSize, c);
      tdc.addMouseListener(this);
      tdc.addMouseMotionListener(this);
      p2.add(tdc);
      this.add(p2);
      this.pack();
    }
    else {
      tdc = new TreeDrawCanvas(t, auxData, false, fontSize, c);
    }
  }

  public void printToStream(PrintStream ps) {
    tdc.printToStream(ps);
  }

  public void mouseClicked(MouseEvent e) {
    if (e.getSource() == tdc) {
      int x = e.getX();
      int y = e.getY();
      tdc.processClick(x, y);
    }
  }

  private Point point1;
  private Point lastPoint2;

  private Rectangle effectiveRectangle(Point p1, Point p2) {
    int x1 = p1.x;
    int x2 = p2.x;
    int y1 = p1.y;
    int y2 = p2.y;
    if (x1 > x2) {
      x1 = p2.x;
      x2 = p1.x;
    }
    if (y1 > y2) {
      y1 = p2.y;
      y2 = p1.y;
    }
    return new Rectangle(x1,y1,x2-x1,y2-y1);
  }

  public void mouseEntered(MouseEvent e) {}
  public void mouseExited(MouseEvent e) {}
  public void mousePressed(MouseEvent e) {
    point1 = new Point(e.getX(), e.getY());
  }
  public void mouseReleased(MouseEvent e) {
    if (point1 == null || lastPoint2 == null) return;
    Graphics g = tdc.getGraphics();
    g.setXORMode(Color.white);
    Rectangle r = effectiveRectangle(point1,lastPoint2);
    g.drawRect(r.x,r.y,r.width,r.height);
    g.setPaintMode();
    Point temp = lastPoint2;
    lastPoint2 = null;
    tdc.setLinksFor(r);
    tdc.repaint();
  }

  public void mouseDragged(MouseEvent e) {
    if (point1 == null) return;
    Graphics g = tdc.getGraphics();
    g.setXORMode(Color.white);
    if (lastPoint2 != null) {
      Rectangle r = effectiveRectangle(point1,lastPoint2);
      g.drawRect(r.x,r.y,r.width,r.height);
    }
    lastPoint2 = new Point(e.getX(), e.getY());
    Rectangle r = effectiveRectangle(point1,lastPoint2);
    g.drawRect(r.x,r.y,r.width,r.height);
    g.setPaintMode();
  }
  public void mouseMoved(MouseEvent e) {}
  
  public void windowClosing(WindowEvent e) {
    System.exit(0);
  }

  public void windowOpened(WindowEvent e) {}
  public void windowClosed(WindowEvent e) {}
  public void windowIconified(WindowEvent e) {}
  public void windowDeiconified(WindowEvent e) {}
  public void windowActivated(WindowEvent e) {}
  public void windowDeactivated(WindowEvent e) {}
}
