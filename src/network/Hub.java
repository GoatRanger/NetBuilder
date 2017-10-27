package network;

import java.util.Iterator;

public class Hub extends Router {

  public String toString() {
    StringBuffer sb = new StringBuffer("");
    sb.append("Hub:");
    sb.append(getName());
    sb.append("\n");
    for (Iterator<Link> i = links.iterator(); i.hasNext(); ) {
      Link l = (Link) i.next();
      sb.append("   ");
      sb.append(l.toString());
      sb.append("\n");
    }
    return sb.toString();
  }
 
}