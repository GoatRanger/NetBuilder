package event;

import network.Hub;
import network.Link;
import network.NetworkComponent;
import network.Packet;
import network.Router;
import network.RouterFirewall;
import network.Sink;

import gui.MessagePanel;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
 */
public class MoveOnLinkEvent extends Event {
  /** DOCUMENT ME! */
  public static final int STEPS_ON_LINK = 10;

  /** DOCUMENT ME! */
  Packet packet = null;

  /** DOCUMENT ME! */
  int numberOfTimesToExecuteAgain = 0;

  /** DOCUMENT ME! */
  int packetIds = 0;

  /**
   * Creates a new MoveOnLinkEvent object.
   *
   * @param packet DOCUMENT ME!
   * @param numberOfTimesToExecuteAgain DOCUMENT ME!
   */
  public MoveOnLinkEvent(Packet packet, int numberOfTimesToExecuteAgain) {
    this.packet = packet;
    this.numberOfTimesToExecuteAgain = numberOfTimesToExecuteAgain;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public Packet getPacket() {
    return packet;
  }

  /**
   * DOCUMENT ME!
   *
   * @return DOCUMENT ME!
   */
  public int getStepsRemaining() {
    return numberOfTimesToExecuteAgain;
  }

  /**
   * DOCUMENT ME!
   */
  public void execute() {
    if (packet.getTTL() <= 0) {
      MessagePanel.appendLine("Packet " + packet.getName() + "timed out.");
      return;
    }
    if (numberOfTimesToExecuteAgain > 0) {
      --numberOfTimesToExecuteAgain;

      EventManager eventManager = EventManager.getInstance();
      eventTime += EventManager.eventLength;
      eventManager.add(this);
      if (EventManager.isVerbose())
        System.out.println("MoveOnLinkEvent: packet#" + packet.getName()
          + " moving on link#" + packet.getCurrentLink().getName());
      if (packet.getCurrentLink()!= null) packet.getCurrentLink().eventExecuted(this);
    } else {
      if (packet.getCurrentLink()!= null) packet.getCurrentLink().eventComplete(this);

      if (packet.getNextStop().equals(packet.getSink())) {
        if (EventManager.isVerbose())
          System.out.println("MoveOnLinkEvent: Final destination for "
            + packet.getName() + " is " + packet.getSink().getName());
        packet.getSink().eventExecuted(this);

        //packet.getCurrentLink().eventComplete(this);
      } else {
        NetworkComponent nextStop = packet.getNextStop().route(packet.getSink());

        //nextStop.eventExecuted(this);
        if (packet.getNextStop() instanceof Hub) {
          Link currentLink = packet.getCurrentLink();
          ArrayList<?> links = (ArrayList<?>) ((Hub) packet.getNextStop()).getLinks();

          for (Iterator<?> i = links.iterator(); i.hasNext();) {
            Link link = (Link) i.next();

            if (!currentLink.equals(link)) {
              Packet copyPacket = (Packet) packet.clone();
              copyPacket.setTTL(copyPacket.getTTL()-1);
              copyPacket.setName(copyPacket.getName() + ":" + ++packetIds);

              // Assumes hubs go straight to sinks with no intermediate network components
              if (link.getRedComponent().equals(nextStop)) {
                //copyPacket.setSink((Sink) link.getRedComponent());
                copyPacket.setCurrentLink(copyPacket.getNextStop().findLinkTo(copyPacket.getCurrentLink(), link
                    .getRedComponent()));
                copyPacket.setNextStop(link.getRedComponent());
              } else {
                if (link.getBlueComponent() instanceof Sink) {
                  //copyPacket.setSink((Sink) link.getBlueComponent());
                  copyPacket.setCurrentLink(copyPacket.getNextStop().findLinkTo(copyPacket.getCurrentLink(), link
                      .getBlueComponent()));
                  copyPacket.setNextStop(link.getBlueComponent());
                } else {
                  copyPacket.setCurrentLink(link);
                  if (copyPacket.getNextStop().equals(link.getBlueComponent())) {
                    copyPacket.setNextStop(link.getRedComponent());
                  } else {
                    copyPacket.setNextStop(link.getBlueComponent());
                  }
                }
              }

              MoveOnLinkEvent event = new MoveOnLinkEvent(copyPacket,
                  STEPS_ON_LINK);
              EventManager eventManager = EventManager.getInstance();
              event.setEventTime(eventTime+EventManager.eventLength);
              eventManager.add(event);
              String linkName = "none";
              if (copyPacket.getCurrentLink() != null) {
                linkName = copyPacket.getCurrentLink().getName();
              }
              if (EventManager.isVerbose())
                System.out.println("MoveOnLinkEvent (hub): packet#"
                  + copyPacket.getName() + " moving on link#"
                  + linkName);
            }
          }

          return;
        }

        if (packet.getNextStop() instanceof Router) {
          packet.setTTL(packet.getTTL()-1);

          if (packet.getNextStop() instanceof RouterFirewall) {
             if (packet.getSource().getComponentID()==((RouterFirewall)packet.getNextStop()).getBlockedID()) {
               MessagePanel.appendLine("Firewall blocked packet from " + packet.getSource().getName());
               return;
             }
          }
//          packet.setCurrentLink(packet.getNextStop().findLinkTo(nextStop));
          Link next = packet.getNextStop().findLinkTo(packet.getCurrentLink(),packet.getSink());
          packet.setCurrentLink(next);
          if (next==null) {
          	MessagePanel.appendLine("Packet dropped by router--no path to destination, or would create a loop.");
          	return;
          }
          if (next.getRedComponent().equals(packet.getNextStop())) {
            nextStop = next.getBlueComponent();
          } else {
            nextStop = next.getRedComponent();
          }
          packet.setNextStop(nextStop);
          
          EventManager eventManager = EventManager.getInstance();
          numberOfTimesToExecuteAgain = STEPS_ON_LINK;
          eventTime += EventManager.eventLength;
          eventManager.add(this);
          if (EventManager.isVerbose())
            System.out.println("MoveOnLinkEvent (router): packet#"
              + packet.getName() + " moving on link#"
              + packet.getCurrentLink().getName());
        }
      }
    }
  }
}
