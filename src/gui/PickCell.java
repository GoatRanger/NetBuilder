package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.*;
import java.awt.datatransfer.*;

public class PickCell extends JLabel
                      implements DragSourceListener,
                                 DragGestureListener {

	private static final long serialVersionUID = 1L;
	DragSource dragSource = null;
  String infoString = "Null";
  Descriptor myDescriptor = null;

  public PickCell() {
    try  {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    dragSource = new DragSource();
    dragSource.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_MOVE, this);
    this.setBorder(BorderFactory.createLoweredBevelBorder());
    this.setOpaque(true);
    this.setSize(32,32);
    this.setPreferredSize(new Dimension(32,32));
    this.setMinimumSize(new Dimension(32,32));
//    this.setHorizontalAlignment(SwingConstants.CENTER);
//    this.setHorizontalTextPosition(SwingConstants.CENTER);
  }

  public void dragDropEnd(DragSourceDropEvent event) {
    if ( event.getDropSuccess()){
      // Do nothing, for now.
    }
  }

  public void dragGestureRecognized( DragGestureEvent event) {
    // Get the name the component wants passed to the DropTarget.
    StringSelection selectedString = new StringSelection(myDescriptor.toString());
    // Start the dragging.
    dragSource.startDrag (event, DragSource.DefaultMoveDrop,
                          selectedString, this);
  }

  public void dragEnter(DragSourceDragEvent event) {
  }

  public void dragExit(DragSourceEvent event) {
  }

  public void dragOver(DragSourceDragEvent event) {
  }

  public void dropActionChanged ( DragSourceDragEvent event) {
  }

  protected String handleSelection(Component Comp) {
    String ReturnString = null;
    if (Comp != null)
      ReturnString = Comp.toString();
     return ReturnString;
  }

  public void setInfoString(String newString) {
    infoString = newString;
  }

  protected void paintComponent(Graphics g) {
  	super.paintComponent(g);
    if (myDescriptor != null) {
      myDescriptor.getMyRenderer().render(g,0,0,this.getWidth(),this.getHeight());
    }
    else {
      super.paintComponent(g);
    }
  }

  public void setDescriptor(Descriptor newDescriptor) {
    myDescriptor = newDescriptor;
    this.setToolTipText(myDescriptor.getShortDescription());
  }


}


