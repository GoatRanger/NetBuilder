package simulator;

import javax.swing.UIManager;

import gui.NetBuilder;
import network.Hub;
import network.Link;
import network.Network;
import network.Router;
import network.Sink;
import network.Source;

//import javax.swing.UIManager;
//import gui.NetBuilder;

public class Simulator {
  public static void main(String[] args) {
    Network network = new Network();
    //constructAHardCodedHostToHostWithHubNetworkModel(network);
    
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    NetBuilder netBuilder = new NetBuilder();
    netBuilder.construct();
    netBuilder.setNetworkModel(network);
    
//	EventManager eventManager = EventManager.getInstance();
//	eventManager.populateEventsWith(network);
//	eventManager.run();

  }

  private static void constructAHardCodedHostToHostNetworkModel(Network network) {
    Source source = new Source();
    source.setName("Source", true);
    source.setNumberOfPacketsToGenerate(1);    
    network.addSource(source);

    Sink sink = new Sink();
    sink.setName("Sink", true);
    network.addSink(sink);    
    source.setNextStop(sink);
    source.setSink(sink);
    
    Link link = new Link();
    link.setName("Link", true);
    link.connect(source, sink);
    source.setLink(link);
    sink.setLink(link);
    network.addLink(link);
  }

  private static void constructAHardCodedHostToHostWithRouterNetworkModel(Network network) {
    Source source = new Source();
    source.setName("Src", true);
    source.setNumberOfPacketsToGenerate(1);    
    network.addSource(source);

    Sink sink = new Sink();
    sink.setName("Sink", true);
    network.addSink(sink);
    
    Router router = new Router();
    router.setName("Router", true);
    router.addNextStopForSink(sink, sink);
    network.addRouter(router);
        
    source.setNextStop(router);
    source.setSink(sink);
    
    Link link = new Link();
    link.setName("Link", true);
    link.connect(source, router);
    source.setLink(link);
    router.addLink(link);

    Link link2 = new Link();
    link2.setName("Link", true);
    link2.connect(router, sink);
    sink.setLink(link2);
    router.addLink(link2);

    network.addLink(link);
    network.addLink(link2);
  }
  
  private static void constructAHardCodedHostToHostWithHubNetworkModel(Network network) {
    Source source = new Source();
    source.setName("Source", true);
    source.setNumberOfPacketsToGenerate(2);    
    network.addSource(source);

    Sink sink = new Sink();
    sink.setName("Sink", true);
    network.addSink(sink);

    Sink sink2 = new Sink();
    sink2.setName("Sink", true);
    network.addSink(sink2);
    
    Hub hub = new Hub();
    hub.setName("Hub", true);
    hub.addNextStopForSink(sink, sink);
    hub.addNextStopForSink(sink2, sink2);
    network.addRouter(hub);
        
    source.setNextStop(hub);
    source.setSink(sink);
    
    Link link = new Link();
    link.setName("Link", true);
    link.connect(source, hub);
    source.setLink(link);
    hub.addLink(link);

    Link link2 = new Link();
    link2.setName("Link", true);
    link2.connect(hub, sink);
    sink.setLink(link2);
    hub.addLink(link2);

    Link link3 = new Link();
    link3.setName("Link", true);
    link3.connect(hub, sink2);
    sink2.setLink(link3);
    hub.addLink(link3);

    network.addLink(link);
    network.addLink(link2);
    network.addLink(link3);
  }
  
}