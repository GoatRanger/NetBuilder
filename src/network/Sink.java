package network;

public class Sink extends NetworkComponent {
  private Link link = null;
  
  public String toString() {
    StringBuffer sb = new StringBuffer("");
    sb.append("Sink:");
    sb.append(getName());
    sb.append(" is on link:");
    if (link != null)
      sb.append(link.getName());
    else
      sb.append("none");
    return sb.toString();
  }

  public Link getLink() {
    return link;
  }

  public void setLink(Link link) {
    this.link = link;
  }
}