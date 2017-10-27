package gui;

import java.awt.*;

public class SteamConnector extends BaseConnector {

  /**
	 * 
	 */
	private static final long serialVersionUID = 146262441795537944L;

	public SteamConnector() {
  }

  public void drawMe(Graphics g) {
    g.setColor(Color.white);
    g.fillOval(0,0,this.getWidth()-1,this.getHeight()-1);
  }

}