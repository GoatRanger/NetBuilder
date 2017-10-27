package event;

import network.NetworkComponent;

public abstract class Event {
  NetworkComponent networkComponent = null;
  long eventTime = 0;

  public NetworkComponent getNetworkComponent() {
    return networkComponent;
  }

  public void setNetworkComponent(NetworkComponent component) {
    networkComponent = component;
  }
  
  public void setEventTime(long time) {
    eventTime = time;
  }
  
  public long getEventTime() {
    return eventTime;
  }
  
  public abstract void execute();
}