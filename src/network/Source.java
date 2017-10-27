package network;

public class Source extends NetworkComponent {
  private int numberOfPacketsToGenerate = 0;
  private NetworkComponent nextStop = null;
  private Sink sink = null;
  private Link link = null;

  public Sink getSink() {
    return sink;
  }

  public void setSink(Sink component) {
    sink = component;
  }
  
  public int getNumberOfPacketsToGenerate() {
    return numberOfPacketsToGenerate;
  }

  public void setNumberOfPacketsToGenerate(int i) {
    numberOfPacketsToGenerate = i;
  }
  
  public String toString() {
    StringBuffer sb = new StringBuffer("");
    sb.append("Source:");
    sb.append(getName());
    sb.append(" connects to sink:");
    if (sink != null)
      sb.append(sink.getName());
    else
      sb.append("none");
    sb.append(" by starting out on link:");
    if (link != null)
      sb.append(link.getName());
    else
      sb.append("none");
    sb.append(" packets:");
    sb.append(numberOfPacketsToGenerate);
    return sb.toString();
  }

  public Link getLink() {
    return link;
  }

  public void setLink(Link link) {
    this.link = link;
  }

  public NetworkComponent getNextStop() {
    return nextStop;
  }

  public void setNextStop(NetworkComponent component) {
    nextStop = component;
  }
}