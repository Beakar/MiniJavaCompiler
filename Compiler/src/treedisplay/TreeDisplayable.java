package treedisplay;

public interface TreeDisplayable {
  public TreeDisplayable getDrawTreeSubobj(int n) throws TreeDrawException;
  public TreeDisplayable[] getDrawTreeLinks();
  public String shortDescription(Object obj);
  public String longDescription(Object obj);
  public boolean nodeIsList();
}
