package gui;

import java.awt.*;
import javax.swing.*;

public class PickPanel extends JPanel {

	private static final long serialVersionUID = -5398206654284770503L;
	BorderLayout borderLayout1 = new BorderLayout();
  JScrollPane gridScrollPane = new JScrollPane();
  GridPanel gridPanel = new GridPanel();
  int visibleCols = 3;
  int visibleRows = 1;
  int cellWidth = 33;
  int cellHeight = 33;
  int numCells = 0;

  public PickPanel() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    this.add(gridScrollPane, BorderLayout.CENTER);
    gridScrollPane.setViewportView(gridPanel);
    gridPanel.setCellWidth(33);
    gridPanel.setCellHeight(33);
    gridScrollPane.getHorizontalScrollBar().setBlockIncrement(33);
    gridScrollPane.getHorizontalScrollBar().setUnitIncrement(33);
    gridScrollPane.getVerticalScrollBar().setBlockIncrement(33);
    gridScrollPane.getVerticalScrollBar().setUnitIncrement(33);
    int newWidth = visibleCols*cellWidth+4;
    int newHeight = visibleRows*cellHeight+24;
    this.setSize(newWidth,newHeight);
    this.setMaximumSize(new Dimension(newWidth,newHeight));
    this.setPreferredSize(new Dimension(newWidth,newHeight));
  }

  public void addPickCell(PickCell PC) {
    numCells++;
    if (numCells<=3) {
      visibleRows = 1;
      visibleCols = numCells;
    }
    else if (numCells<=6) {
      visibleRows = 2;
      visibleCols = numCells - 3;
    }
    else {
      visibleRows = 3;
      visibleCols = 3;
    }
    gridPanel.addPickCell(PC);
    gridScrollPane.setPreferredSize(gridPanel.getPreferredDimension());
  }

}

