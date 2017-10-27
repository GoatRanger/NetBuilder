package gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class MessagePanel extends JPanel {
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private Border border = BorderFactory.createEmptyBorder(5,5,5,5);
  private BorderLayout borderLayout1 = new BorderLayout();
  private JScrollPane messagePaneScroller = new JScrollPane();
  //private JEditorPane messageEditorPane = new JEditorPane();
  static JTextArea messageEditorPane = new JTextArea(200,40);
  JLabel messagePanelHeader = new JLabel();

  public MessagePanel() {
    try {
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
    this.setVisible(true);
  }

  void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    this.setBorder(BorderFactory.createLineBorder(Color.black));
    messagePanelHeader.setText("Information");
    messagePanelHeader.setOpaque(true);
    messagePanelHeader.setHorizontalAlignment(SwingConstants.CENTER);
    messagePanelHeader.setBackground(SystemColor.info);
    messagePanelHeader.setForeground(SystemColor.infoText);
    this.add(messagePanelHeader, BorderLayout.NORTH);
    this.add(messagePaneScroller, BorderLayout.CENTER);
    messagePaneScroller.getViewport().add(messageEditorPane, null);
    messagePaneScroller.setVerticalScrollBarPolicy(
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    messagePaneScroller.setPreferredSize(new Dimension(800, 110));
    messageEditorPane.setEditable(false);
    messagePaneScroller.setVisible(true);
    messageEditorPane.setBorder(border);
    messageEditorPane.setVisible(true);
    messageEditorPane.setText("");
  }

  public static void appendLine(String newLine) {
    messageEditorPane.append(newLine);
    messageEditorPane.append("\n");
  }

  public static void clear() {
  	messageEditorPane.setText("");
  }
  
  public void showPage(String page) {
//    try {
//      messageEditorPane.setPage(page);
//    }
//    catch (IOException ioe) {
//      ioe.printStackTrace();
//    }
  }

}