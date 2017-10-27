/*
 * Created on Sep 14, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package network;

/**
 * @author Karl
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class RouterFirewall extends Router {
   private int blockedID = -1;
   
   public int getBlockedID() {
     return blockedID;
   }
   
   public void setBlockedID(int blockedID) {
     this.blockedID = blockedID;
   }
}
