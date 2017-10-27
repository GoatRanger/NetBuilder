/*
 * Created on Sep 13, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package network;

import event.Event;

/**
 * @author Karl
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface NetworkEventListener {
    public void networkEvent(Event e);
    public void eventComplete(Event e);
}
