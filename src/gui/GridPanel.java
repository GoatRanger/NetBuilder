package gui;

import java.awt.*;
import javax.swing.JPanel;

public class GridPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	int numCols = 0;
  int numRows = 0;
  int maxRows = 3;
  int numPickCells = 0;
  JPanel internalPanel = new JPanel();
  GridLayout gridLayout1 = new GridLayout();
  int cellHeight = 33;
  int cellWidth = 33;
  Dimension actualDimension = new Dimension();
  Dimension preferredDimension = new Dimension(3*33,3*33);

  public GridPanel() {
    try  {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setLayout(null);
    internalPanel.setLayout(gridLayout1);
    this.add(internalPanel);
    internalPanel.setSize(10,10);
    preferredDimension.setSize(3*cellWidth,numRows*cellHeight);
  }

  public void addPickCell(PickCell PC) {
    internalPanel.add(PC);
    numPickCells = numPickCells + 1;
    if (numPickCells<=3) {
      numCols = numPickCells;
      numRows = 1;
    }
    else if (numPickCells <= 6) {
      numCols = 3;
      numRows = 2;
    }
    else if (numPickCells <= 9) {
      numCols = 3;
      numRows = 3;
    }
    else {
      numCols = 1 + (numPickCells-1)/3;
      numRows = 3;
    }
    actualDimension.setSize(numCols*cellWidth,numRows*cellHeight);
    internalPanel.setSize(actualDimension);
    gridLayout1.setColumns(numCols);
    gridLayout1.setRows(numRows);
    preferredDimension.setSize(3*cellWidth,numRows*cellHeight);
    this.setPreferredSize(preferredDimension);
  }

  public Dimension getPreferredDimension() {
    return preferredDimension;
  }

  public int getCellHeight() {
    return cellHeight;
  }

  public void setCellHeight(int newHeight) {
    cellHeight = newHeight;
  }

  public int getCellWidth() {
    return cellWidth;
  }

  public void setCellWidth(int newWidth) {
    cellWidth = newWidth;
  }

  public int getNumCols() {
    return numCols;
  }

  public int getNumRows() {
    return numRows;
  }

}