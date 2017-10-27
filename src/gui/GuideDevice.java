package gui;

public class GuideDevice extends BaseDevice {

	private static final long serialVersionUID = 1143605653613490193L;
	BaseConnector newConnector;
  Descriptor newConnectorDescriptor;

  public GuideDevice(Foundation theFoundation, Descriptor theDescriptor,
                     int newSizeX, int newSizeY   ) {
    super(theFoundation,theDescriptor);
    this.setSize(newSizeX,newSizeY);
    newConnectorDescriptor = new Descriptor(Descriptor.CONNECTOR_TYPE,
                                           Descriptor.BASE_CONNECTOR,
                                           "Base Connector",
                                           0.0);
    newConnector = new BaseConnector(this,newConnectorDescriptor);
    this.addConnector(newConnector);
  }

}