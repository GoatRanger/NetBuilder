package network;

import java.util.ArrayList;
import java.util.Iterator;

public class Link extends NetworkComponent {
  private NetworkComponent redComponent = null;
  private NetworkComponent blueComponent = null;

  public void connect(NetworkComponent red, NetworkComponent blue) {
    redComponent = red;
    blueComponent = blue;
  }

//  public boolean existsBetween(NetworkComponent comp1, NetworkComponent comp2) {
//    if ((redComponent.equals(comp1) && blueComponent.equals(comp2)) ||
//        (redComponent.equals(comp2) && blueComponent.equals(comp1)))
//      return true;
//    return false;
//  }
//  
//  public boolean pathExistsBetween(Link last, NetworkComponent start, NetworkComponent dest) {
//    boolean result = false;
//    if ((redComponent.equals(start) && blueComponent.equals(dest)) ||
//        (redComponent.equals(dest) && blueComponent.equals(start)) || start.equals(dest)) {
//      return true;
//    }
//    if ((redComponent.equals(start) && blueComponent instanceof Router) ||
//        (blueComponent.equals(start) && redComponent instanceof Router)) {
//
//      if (start instanceof Router) {
//        Iterator i = ((Router)start).getLinks().iterator();
//        while (i.hasNext()) {
//          Link nextLink = (Link)i.next();
//          if (!nextLink.equals(last)) {
//            NetworkComponent end1 = nextLink.getRedComponent();
//            NetworkComponent end2 = nextLink.getBlueComponent();
//            if (start.equals(end1)) {
//              result = nextLink.pathExistsBetween(nextLink, end2, dest);
//            } else {
//              result = false;
//            }
//          }
//          if (result==true) {
//            return result;
//          } 
//        }
//      }
//    }
//    return result;
//  }
  
  public int pathLength(int currentLength, int maxHops, ArrayList<Link> visited, NetworkComponent current, NetworkComponent end) {
    int length = currentLength + 1;
    if (length > maxHops) {
      return maxHops + 1;
    }
    if (redComponent.equals(end) || blueComponent.equals(end)) {
      return length;
    } else {
      visited.add(this);
      NetworkComponent nextNode;
      if (redComponent.equals(current)) {
        nextNode = blueComponent;
      } else {
        nextNode = redComponent;
      }
      if (nextNode instanceof Router) {
        Iterator<?> i = ((Router)nextNode).getLinks().iterator();
        int minLength = maxHops + 1;
        while (i.hasNext()) {
          Link nextLink = (Link)i.next();
          if (visited.contains(nextLink)) {
            // Already used that link
          } else {
            int path = nextLink.pathLength(length, maxHops, visited, nextNode, end);
            if (path < minLength) {
              minLength = path;
            }
          }
        }
        return minLength;
      }
      return maxHops + 1;
    }
  }

  public String toString() {
    StringBuffer sb = new StringBuffer("");
    sb.append("Link:");
    sb.append(getName());
    sb.append(" connects red:");
    if (redComponent != null)
      sb.append(redComponent.getName());
    else
      sb.append("none");
    sb.append(" to blue:");
    if (blueComponent != null)
      sb.append(blueComponent.getName());
    else
      sb.append("none");
    return sb.toString();
  }

  public NetworkComponent getBlueComponent() {
    return blueComponent;
  }

  public NetworkComponent getRedComponent() {
    return redComponent;
  }

  public void setBlueComponent(NetworkComponent component) {
    blueComponent = component;
  }

  public void setRedComponent(NetworkComponent component) {
    redComponent = component;
  }
}