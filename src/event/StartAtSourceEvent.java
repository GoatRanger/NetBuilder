package event;

import network.Packet;
import network.Source;

public class StartAtSourceEvent extends Event {
  
  private Source source = null;
  private int numPackets = 0;
  private int packetIds = 0;
  
  public void execute() {
    if (numPackets > 0) {
      if (source.getSink()==null) return;
      Packet packet = new Packet();
      packet.setName(source.getName() + "-" + ++packetIds);
      packet.setSource(source);
      packet.setSink(source.getSink());
      packet.setNextStop(source.getNextStop());
      packet.setCurrentLink(source.getLink());
      MoveOnLinkEvent event = new MoveOnLinkEvent(packet, MoveOnLinkEvent.STEPS_ON_LINK);
      event.setEventTime(eventTime + EventManager.eventLength);
      EventManager eventManager = EventManager.getInstance();
      eventManager.add(event);
      --numPackets;
//      eventTime += EventManager.eventLength;
      eventManager.add(this);
      if (packet.getSink() != null && EventManager.isVerbose()) {
        System.out.println("StartAtSourceEvent:" + packet.getName() + ", going to " + packet.getSink().getName());
      }
      source.eventExecuted(this);
    }
  }

  public Source getSource() {
    return source;
  }

  public void setSource(Source source) {
    numPackets = source.getNumberOfPacketsToGenerate();
    this.source = source;
  }
}