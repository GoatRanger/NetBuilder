package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import event.EventManager;

import network.Network;

public class SimControlPanel extends JPanel implements event.SimControlListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Network network;
	JButton rewindButton = new JButton();
	JButton runPauseButton;
	JButton stopButton = new JButton();
	JSlider timeScaleSlider = new JSlider();
	JSlider ttlSlider = new JSlider();
	JSlider scaleSlider = new JSlider();
	TitledBorder titledBorder1;
	TitledBorder titledBorder2;
	JLabel simTimeLabel = new JLabel();
	NetBuilderFrame myFrame;
	BoxLayout verticalFlowLayout1 = new BoxLayout(this, BoxLayout.Y_AXIS);

	final ImageIcon runIcon = new ImageIcon(SimControlPanel.class.getResource("j-play.gif"));
	final ImageIcon pauseIcon = new ImageIcon(SimControlPanel.class.getResource("j-pause.gif"));

	public SimControlPanel(NetBuilderFrame frame) {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		myFrame = frame;
	}

	void jbInit() throws Exception {
		titledBorder1 = new TitledBorder(BorderFactory.createEtchedBorder(), "Event Delay (100ms)");
		titledBorder2 = new TitledBorder(BorderFactory.createEtchedBorder(), "Sim Time");
		this.setLayout(verticalFlowLayout1);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		// this.setBackground(new Color(255, 180, 160));
		timeScaleSlider.setMajorTickSpacing(1);
		timeScaleSlider.setMaximum(10);
		timeScaleSlider.setValue(EventManager.getDelay() / 100);
		timeScaleSlider.setPaintTicks(true);
		timeScaleSlider.setPaintLabels(true);
		timeScaleSlider.setBorder(titledBorder1);
		timeScaleSlider.setMaximumSize(new Dimension(240, 70));
		timeScaleSlider.setMinimumSize(new Dimension(64, 70));
		timeScaleSlider.setPreferredSize(new Dimension(240, 70));
		timeScaleSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				if (!source.getValueIsAdjusting()) {
					EventManager.setDelay(source.getValue() * 100);
				}
			}
		});
		// simTimeLabel.setBackground(new Color(184, 190, 210));
		simTimeLabel.setBorder(titledBorder2);
		simTimeLabel.setOpaque(true);
		this.add(timeScaleSlider, null);
		Border ttlBorder = new TitledBorder(BorderFactory.createEtchedBorder(), "Packet Time to Live");
		ttlSlider.setMajorTickSpacing(15);
		ttlSlider.setMaximum(124);
		ttlSlider.setMinimum(4);
		ttlSlider.setValue(Network.MAX_TTL);
		ttlSlider.setPaintTicks(true);
		ttlSlider.setPaintLabels(true);
		ttlSlider.setBorder(ttlBorder);
		ttlSlider.setMaximumSize(new Dimension(240, 70));
		ttlSlider.setMinimumSize(new Dimension(64, 70));
		ttlSlider.setPreferredSize(new Dimension(240, 70));
		ttlSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				if (!source.getValueIsAdjusting()) {
					Network.setMaxTTL(source.getValue());
				}
			}
		});
		this.add(ttlSlider, null);

		Border scaleBorder = new TitledBorder(BorderFactory.createEtchedBorder(), "Object Scale");

		scaleSlider.setMajorTickSpacing(1);
		scaleSlider.setMaximum(6);
		scaleSlider.setMinimum(1);
		scaleSlider.setValue(3);
		scaleSlider.setPaintTicks(true);
		scaleSlider.setPaintLabels(true);
		scaleSlider.setBorder(scaleBorder);
		scaleSlider.setSnapToTicks(true);
		scaleSlider.setMaximumSize(new Dimension(240, 70));
		scaleSlider.setMinimumSize(new Dimension(64, 70));
		scaleSlider.setPreferredSize(new Dimension(240, 70));
		scaleSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				BaseDevice.setScale(source.getValue());
				BaseConnector.setScale(source.getValue());
				myFrame.repaint();
			}
		});
		this.add(scaleSlider, null);
		JPanel simControlPanel = new JPanel();
		// simControlPanel.setLayout(new GridLayout(1,2));
		this.add(simControlPanel, null);
		JButton newButton = new JButton();
		newButton.setSize(new Dimension(180, 30));
		newButton.setPreferredSize(new Dimension(180, 30));
		newButton.setText("Clear Network");
		newButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				myFrame.masterMenu.menuFileNew.doClick();
				myFrame.repaint();
			}
		});
		simControlPanel.add(newButton);
		// this.add(rewindButton, null);
		// rewindButton.setBackground(new Color(184, 190, 210));
		rewindButton.setEnabled(false);
		rewindButton.setDoubleBuffered(true);
		rewindButton.setIcon(new ImageIcon(SimControlPanel.class.getResource("j-rewind.gif")));
		rewindButton.setText("Rewind");
		// runStopButton.setBackground(new Color(184, 190, 216));

		runPauseButton = new JButton();

		runPauseButton.setIcon(runIcon);
		// runStopButton.setText("Run");
		runPauseButton.setSize(new Dimension(80, 30));
		runPauseButton.setPreferredSize(new Dimension(80, 30));
		runPauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (network == null)
					return;
				EventManager eventManager = EventManager.getInstance();
				if (eventManager.isRunning()) {
					eventManager.pause();
				} else if (eventManager.isPaused()) {
					eventManager.run();
				} else {
					MessagePanel.clear();
					network.resetAll();
					if (eventManager.populateEventsWith(network) == 0) {
					}
					eventManager.run();
				}
			}
		});
		simControlPanel.add(runPauseButton, null);
		final ImageIcon stopIcon = new ImageIcon(SimControlPanel.class.getResource("j-stop.gif"));
		stopButton.setIcon(stopIcon);
		stopButton.setSize(new Dimension(80, 30));
		stopButton.setPreferredSize(new Dimension(80, 30));
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (network == null)
					return;
				EventManager eventManager = EventManager.getInstance();
				if (eventManager.isRunning() || eventManager.isPaused()) {
					eventManager.stop();
				}
//				runPauseButton.setIcon(runIcon);
			}
		});
		stopButton.setEnabled(false);
		simControlPanel.add(stopButton, null);
		JPanel buttons = new JPanel();
		this.add(buttons, null);
		Border buttonBorder = new TitledBorder(BorderFactory.createEtchedBorder(), "Quick Setups");
		buttons.setLayout(new GridLayout(5, 1));
		buttons.setBorder(buttonBorder);

		JButton robot1Button = new JButton();
		robot1Button.setSize(90, 30);
		buttons.add(robot1Button, null);
		robot1Button.setText("Robot--Create Host-Host");
		robot1Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				new RobotHostToHost();
			}
		});
		JButton robot2Button = new JButton();
		robot2Button.setSize(90, 30);
		buttons.add(robot2Button, null);
		robot2Button.setText("Robot-Hub Demo");
		robot2Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				new RobotHubDemo();
			}
		});
		JButton robot3Button = new JButton();
		robot3Button.setSize(90, 30);
		buttons.add(robot3Button, null);
		robot3Button.setText("Robot-Router Demo");
		robot3Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				new RobotRouterDemo();
			}
		});

		JButton ice1Button = new JButton();
		ice1Button.setSize(90, 30);
		buttons.add(ice1Button, null);
		ice1Button.setText("Setup ICE 1");
		ice1Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				new Ice1();
			}
		});
		JButton moronButton = new JButton();
		moronButton.setSize(200, 30);
		moronButton.setPreferredSize(new Dimension(200, 30));
		buttons.add(moronButton, null);
		moronButton.setText("Don't Press Me For Any Reason");
		moronButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				new Moron();
			}
		});
		// this.add(simTimeLabel, null);
		simTimeLabel.setText("Elapsed:  0:00:00");
	}

	public void setNetwork(Network net) {
		network = net;
	}

	class RobotHostToHost implements Runnable {

		Thread thread;

		// Create some temporary adapters to intercept mouse/keyboard while we automate
		// the build (Prevent user from accidentally disturbing the automation)
		MouseAdapter newMouse = new MouseAdapter() {
		};
		MouseMotionAdapter newMouseMotion = new MouseMotionAdapter() {
		};
		KeyAdapter newKeyAdapter = new KeyAdapter() {
		};

		public RobotHostToHost() {
			JOptionPane.showMessageDialog(null,
					"Click OK to begin--I'm about to take control of your computer.\n  After clicking, DO NOT TOUCH YOUR MOUSE OR KEYBOARD until directed to do so. \n If you move your mouse during this process, you will lose control of your system, which could result in lost files, death, or worse.");

			myFrame.addMouseListener(newMouse);
			myFrame.addMouseMotionListener(newMouseMotion);
			myFrame.addKeyListener(newKeyAdapter);
			new Thread(this).start();
		}

		public void run() {

			// create a robot to feed in GUI events
			Robot rob = null;
			try {
				rob = new Robot();
			} catch (AWTException e) {
				myFrame.removeMouseListener(newMouse);
				myFrame.removeMouseMotionListener(newMouseMotion);
				myFrame.removeKeyListener(newKeyAdapter);
				return;
			}
			rob.setAutoDelay(500);

			maximizeWindow(rob);

			addSource(rob, 50, 200, null);
			addSink(rob, 250, 200, null);

			connectDevices(rob, 50, 200, 250, 200);
			connectSourceToSink(rob, 50, 200, 1);

			gotoRunButton(rob);
			myFrame.removeMouseListener(newMouse);
			myFrame.removeMouseMotionListener(newMouseMotion);
			myFrame.removeKeyListener(newKeyAdapter);
		}
	}

	class RobotHubDemo implements Runnable {

		Thread thread;

		// Create some temporary adapters to intercept mouse/keyboard while we automate
		// the build (Prevent user from accidentally disturbing the automation)
		MouseAdapter newMouse = new MouseAdapter() {
		};
		MouseMotionAdapter newMouseMotion = new MouseMotionAdapter() {
		};
		KeyAdapter newKeyAdapter = new KeyAdapter() {
		};

		public RobotHubDemo() {
			JOptionPane.showMessageDialog(null,
					"Click OK to begin--I'm about to take control of your computer.\n  After clicking, DO NOT TOUCH YOUR MOUSE OR KEYBOARD until directed to do so. \n If you move your mouse during this process, you will lose control of your system, which could result in lost files, death, or worse.");

			myFrame.addMouseListener(newMouse);
			myFrame.addMouseMotionListener(newMouseMotion);
			myFrame.addKeyListener(newKeyAdapter);
			new Thread(this).start();
		}

		public void run() {

			// create a robot to feed in GUI events
			Robot rob = null;
			try {
				rob = new Robot();
			} catch (AWTException e) {
				return;
			}
			rob.setAutoDelay(500);
			maximizeWindow(rob);

			addSource(rob, 50, 100, null);
			addHub(rob, 180, 100, null);
			addSink(rob, 360, 100, null);
			addSink(rob, 360, 300, null);

			connectDevices(rob, 50, 100, 180, 100);
			connectDevices(rob, 180, 100, 360, 100);
			connectDevices(rob, 180, 100, 360, 300);

			connectSourceToSink(rob, 50, 100, 1);

			gotoRunButton(rob);
			myFrame.removeMouseListener(newMouse);
			myFrame.removeMouseMotionListener(newMouseMotion);
			myFrame.removeKeyListener(newKeyAdapter);
		}
	}

	class RobotRouterDemo implements Runnable {

		Thread thread;

		// Create some temporary adapters to intercept mouse/keyboard while we automate
		// the build (Prevent user from accidentally disturbing the automation)
		MouseAdapter newMouse = new MouseAdapter() {
		};
		MouseMotionAdapter newMouseMotion = new MouseMotionAdapter() {
		};
		KeyAdapter newKeyAdapter = new KeyAdapter() {
		};

		public RobotRouterDemo() {
			JOptionPane.showMessageDialog(null,
					"Click OK to begin--I'm about to take control of your computer.\n  After clicking, DO NOT TOUCH YOUR MOUSE OR KEYBOARD until directed to do so. \n If you move your mouse during this process, you will lose control of your system, which could result in lost files, death, or worse.");

			myFrame.addMouseListener(newMouse);
			myFrame.addMouseMotionListener(newMouseMotion);
			myFrame.addKeyListener(newKeyAdapter);
			new Thread(this).start();
		}

		public void run() {

			// create a robot to feed in GUI events
			Robot rob = null;
			try {
				rob = new Robot();
			} catch (AWTException e) {
				myFrame.removeMouseListener(newMouse);
				myFrame.removeMouseMotionListener(newMouseMotion);
				myFrame.removeKeyListener(newKeyAdapter);
				return;
			}
			rob.setAutoDelay(300);
			maximizeWindow(rob);
			addSource(rob, 50, 100, "TH452A");
			addRouter(rob, 180, 100, null);
			addSink(rob, 360, 100, "ZTROOPER");
			addSink(rob, 360, 300, "VICTORY");

			connectDevices(rob, 50, 100, 180, 100);
			connectDevices(rob, 180, 100, 360, 100);
			connectDevices(rob, 180, 100, 360, 300);

			connectSourceToSink(rob, 50, 100, 2);
			
			gotoRunButton(rob);

			myFrame.removeMouseListener(newMouse);
			myFrame.removeMouseMotionListener(newMouseMotion);
			myFrame.removeKeyListener(newKeyAdapter);
		}
	}

	class Ice1 implements Runnable {

		Thread thread;

		// Create some temporary adapters to intercept mouse/keyboard while we automate
		// the build (Prevent user from accidentally disturbing the automation)
		MouseAdapter newMouse = new MouseAdapter() {
		};
		MouseMotionAdapter newMouseMotion = new MouseMotionAdapter() {
		};
		KeyAdapter newKeyAdapter = new KeyAdapter() {
		};

		public Ice1() {
			JOptionPane.showMessageDialog(null,
					"Click OK to begin--I'm about to take control of your computer.\n  After clicking, DO NOT TOUCH YOUR MOUSE OR KEYBOARD until directed to do so. \n If you move your mouse during this process, you will lose control of your system, which could result in lost files, death, or worse.");

			myFrame.addMouseListener(newMouse);
			myFrame.addMouseMotionListener(newMouseMotion);
			myFrame.addKeyListener(newKeyAdapter);
			new Thread(this).start();
		}

		public void run() {

			// create a robot to feed in GUI events
			Robot rob = null;
			try {
				rob = new Robot();
			} catch (AWTException e) {
				myFrame.removeMouseListener(newMouse);
				myFrame.removeMouseMotionListener(newMouseMotion);
				myFrame.removeKeyListener(newKeyAdapter);
				return;
			}
			rob.setAutoDelay(300);
			maximizeWindow(rob);
			Point p = runPauseButton.getLocationOnScreen();
			Point p2 = myFrame.builderPanel.foundation.getLocationOnScreen();
			Point p3 = myFrame.messagePanel.getLocationOnScreen();

			if (p.getX() - p2.getX() < 800 || p3.getY() - p2.getY() < 460) {
				JOptionPane.showMessageDialog(null, "Your screen resolution is too low to run this autocreator.\n"
						+ "If you cannot increase your screen resolution you will not be able to automatically generate this network setup.");
				return;
			}

			addSource(rob, 100, 50, "TH452A");
			addSource(rob, 50, 100, "TH452B");
			addSource(rob, 100, 200, "TH452C");
			addHub(rob, 180, 100, null);
			connectDevices(rob, 50, 100, 180, 100);
			connectDevices(rob, 100, 50, 180, 100);
			connectDevices(rob, 100, 200, 180, 100);
			rob.delay(400);
			addSource(rob, 650, 50, "TH455A");
			addSource(rob, 700, 100, "TH455B");
			addSource(rob, 650, 200, "TH455C");
			addHub(rob, 600, 100, null);
			connectDevices(rob, 650, 50, 600, 100);
			connectDevices(rob, 650, 200, 600, 100);
			connectDevices(rob, 700, 100, 600, 100);
			rob.delay(400);
			addSink(rob, 325, 50, "ZTROOPER");
			addSink(rob, 425, 50, "VICTORY");
			addSource(rob, 375, 25, "129.29.74.45");
			rob.setAutoDelay(400);
			addSource(rob, 325, 380, "64.123.24.5");
			addSink(rob, 425, 380, "BADGUYSERVER");

			addFirewall(rob, 375, 325, null);
			rob.setAutoDelay(300);
			connectDevices(rob, 325, 380, 375, 325);
			connectDevices(rob, 425, 380, 375, 325);

			gotoRunButton(rob);

			myFrame.removeMouseListener(newMouse);
			myFrame.removeMouseMotionListener(newMouseMotion);
			myFrame.removeKeyListener(newKeyAdapter);
		}
	}

	class Moron implements Runnable {

		Thread thread;

		// Create some temporary adapters to intercept mouse/keyboard while we automate
		// the build (Prevent user from accidentally disturbing the automation)
		MouseAdapter newMouse = new MouseAdapter() {
		};
		MouseMotionAdapter newMouseMotion = new MouseMotionAdapter() {
		};
		KeyAdapter newKeyAdapter = new KeyAdapter() {
		};

		public Moron() {
			myFrame.addMouseListener(newMouse);
			myFrame.addMouseMotionListener(newMouseMotion);
			myFrame.addKeyListener(newKeyAdapter);
			new Thread(this).start();
		}

		public void run() {

			// create a robot to feed in GUI events
			Robot rob = null;
			try {
				rob = new Robot();
			} catch (AWTException e) {
				myFrame.removeMouseListener(newMouse);
				myFrame.removeMouseMotionListener(newMouseMotion);
				myFrame.removeKeyListener(newKeyAdapter);
				return;
			}
			rob.setAutoDelay(300);
			minimizeWindow(rob);
			try {
				Runtime.getRuntime().exec("notepad");
			} catch (IOException e) {
	  	  myFrame.removeMouseListener(newMouse);
	  	  myFrame.removeMouseMotionListener(newMouseMotion);
	  	  myFrame.removeKeyListener(newKeyAdapter);
	  	  return;
			}
			rob.delay(1200);
			robotTypeString(rob, "DEAR MOM, I'M A MORON.  THE BUTTON SAID NOT TO PRESS IT, BUT I DID IT ANYWAY.");
			rob.keyPress(KeyEvent.VK_ENTER);
			rob.keyRelease(KeyEvent.VK_ENTER);
			robotTypeString(rob, "PLEASE RETURN MY EMAIL.");
			rob.keyPress(KeyEvent.VK_ENTER);
			rob.keyRelease(KeyEvent.VK_ENTER);
			String id = System.getProperty("user.name");
			robotTypeString(rob, id+"@usma.edu");

			myFrame.removeMouseListener(newMouse);
			myFrame.removeMouseMotionListener(newMouseMotion);
			myFrame.removeKeyListener(newKeyAdapter);
		}
	}

	void maximizeWindow(Robot rob) {
		int delay = rob.getAutoDelay();
		rob.delay(1000);
		rob.setAutoDelay(200);
		rob.keyPress(KeyEvent.VK_ALT);
		rob.keyPress(KeyEvent.VK_SPACE);
		rob.keyRelease(KeyEvent.VK_SPACE);
		rob.keyRelease(KeyEvent.VK_ALT);
		rob.keyPress(KeyEvent.VK_DOWN);

		rob.keyPress(KeyEvent.VK_DOWN);
		rob.keyPress(KeyEvent.VK_DOWN);
		rob.keyPress(KeyEvent.VK_DOWN);
		rob.keyPress(KeyEvent.VK_ENTER);
		rob.delay(2000);
		rob.setAutoDelay(delay);
	}

	void minimizeWindow(Robot rob) {
		int delay = rob.getAutoDelay();
		rob.delay(1000);
		rob.setAutoDelay(50);
		rob.keyPress(KeyEvent.VK_ALT);
		rob.keyPress(KeyEvent.VK_SPACE);
		rob.keyRelease(KeyEvent.VK_SPACE);
		rob.keyRelease(KeyEvent.VK_ALT);
		rob.keyPress(KeyEvent.VK_DOWN);

		rob.keyPress(KeyEvent.VK_DOWN);
		rob.keyPress(KeyEvent.VK_DOWN);
		rob.keyPress(KeyEvent.VK_ENTER);
		rob.delay(2000);
		rob.setAutoDelay(delay);
	}

	void gotoRunButton(Robot rob) {
		
		JOptionPane.showMessageDialog(null, "I'm done. You can now use your computer again.");
		
		// Could move the cursor to the run button, but might be better to just leave it where it was last seen
		//Point stopBtn = runStopButton.getLocationOnScreen();
		//rob.mouseMove(stopBtn.x + 40, stopBtn.y + 10);
	}

	void connectSourceToSink(Robot rob, int x, int y, int index) {
		
		// get font metrics for the current frame
		FontMetrics metrics = myFrame.getFontMetrics(myFrame.getFont());
		// get the height of a line of text in this context
		// Since this is used for menu robot navigation, add 6 (spacing between menu lines)
		int fontHeight = metrics.getHeight() + 6;
		
		Point p2 = myFrame.builderPanel.foundation.getLocationOnScreen();
		rob.mouseMove(p2.x + x + 25, p2.y + y + 34);
		rob.mousePress(InputEvent.BUTTON3_MASK);
		rob.mouseRelease(InputEvent.BUTTON3_MASK);

		rob.mouseMove(p2.x + x + 45, p2.y + y + 34 + (fontHeight * (index + 4)));
		rob.mousePress(InputEvent.BUTTON3_MASK);
		rob.mouseRelease(InputEvent.BUTTON3_MASK);
	}

	void connectDevices(Robot rob, int x1, int y1, int x2, int y2) {
		Point p2 = myFrame.builderPanel.foundation.getLocationOnScreen();
		rob.mouseMove(p2.x + x1 + 10, p2.y + y1 + 10);
		rob.mousePress(InputEvent.BUTTON1_MASK);
		rob.mouseMove(p2.x + x2 + 10, p2.y + y2 + 10);
		rob.mouseRelease(InputEvent.BUTTON1_MASK);
	}

	void addSink(Robot rob, int x, int y, String name) {
		Point p = myFrame.builderPanel.getLocationOnScreen();
		Point p2 = myFrame.builderPanel.foundation.getLocationOnScreen();

		rob.mouseMove(p.x + 50, p.y + 55);
		rob.mousePress(InputEvent.BUTTON1_MASK);
		rob.mouseMove(p2.x + x, p2.y + y);
		rob.mouseRelease(InputEvent.BUTTON1_MASK);
		changeComponentNameAt(rob, x, y, name);
	}

	void addSource(Robot rob, int x, int y, String name) {
		Point p = myFrame.builderPanel.getLocationOnScreen();
		Point p2 = myFrame.builderPanel.foundation.getLocationOnScreen();

		rob.mouseMove(p.x + 30, p.y + 55);
		rob.mousePress(InputEvent.BUTTON1_MASK);
		rob.mouseMove(p2.x + x, p2.y + y);
		rob.mouseRelease(InputEvent.BUTTON1_MASK);
		changeComponentNameAt(rob, x, y, name);
	}

	void addRouter(Robot rob, int x, int y, String name) {
		Point p = myFrame.builderPanel.getLocationOnScreen();
		Point p2 = myFrame.builderPanel.foundation.getLocationOnScreen();

		rob.mouseMove(p.x + 100, p.y + 55);
		rob.mousePress(InputEvent.BUTTON1_MASK);
		rob.mouseMove(p2.x + x, p2.y + y);
		rob.mouseRelease(InputEvent.BUTTON1_MASK);
		changeComponentNameAt(rob, x, y, name);
	}

	void addFirewall(Robot rob, int x, int y, String name) {
		Point p = myFrame.builderPanel.getLocationOnScreen();
		Point p2 = myFrame.builderPanel.foundation.getLocationOnScreen();

		rob.mouseMove(p.x + 130, p.y + 55);
		rob.mousePress(InputEvent.BUTTON1_MASK);
		rob.mouseMove(p2.x + x, p2.y + y);
		rob.mouseRelease(InputEvent.BUTTON1_MASK);
		changeComponentNameAt(rob, x, y, name);
	}

	void addHub(Robot rob, int x, int y, String name) {
		Point p = myFrame.builderPanel.getLocationOnScreen();
		Point p2 = myFrame.builderPanel.foundation.getLocationOnScreen();

		rob.mouseMove(p.x + 160, p.y + 55);
		rob.mousePress(InputEvent.BUTTON1_MASK);
		rob.mouseMove(p2.x + x, p2.y + y);
		rob.mouseRelease(InputEvent.BUTTON1_MASK);
		changeComponentNameAt(rob, x, y, name);
	}

	private void changeComponentNameAt(Robot rob, int x, int y, String name) {
		
		Point p2 = myFrame.builderPanel.foundation.getLocationOnScreen();
		
		// get font metrics for the current frame
		FontMetrics metrics = myFrame.getFontMetrics(myFrame.getFont());
		// get the height of a line of text in this context
		// Since this is used for menu robot navigation, add 6 (spacing between menu lines)
		int fontHeight = metrics.getHeight() + 6;
		
		if (name != null) {
			rob.mouseMove(p2.x + x + 15, p2.y + y + 34);

			rob.mousePress(InputEvent.BUTTON3_MASK);
			rob.mouseRelease(InputEvent.BUTTON3_MASK);
			// Assumes menu line height where 80 px will move down to first line (Change Name)
			rob.mouseMove(p2.x + x + 50, p2.y + y + 34 + (fontHeight * 2));
			rob.delay(500);
			rob.mousePress(InputEvent.BUTTON3_MASK);
			rob.mouseRelease(InputEvent.BUTTON3_MASK);
			robotTypeString(rob, name);
			rob.keyPress(KeyEvent.VK_ENTER);
			rob.keyRelease(KeyEvent.VK_ENTER);
		}
	}

	private void robotTypeString(Robot rob, String string) {
		int delay = rob.getAutoDelay();

		rob.setAutoDelay(50);
		System.err.println(string);
		for (int i = 0; i < string.length(); i++) {
			rob.keyPress(string.charAt(i));
			rob.keyRelease(string.charAt(i));
		}
		rob.setAutoDelay(delay);
	}

	@Override
	public void started() {
    stopButton.setEnabled(true);
		runPauseButton.setIcon(pauseIcon);
	}

	@Override
	public void paused() {
    stopButton.setEnabled(true);
		runPauseButton.setIcon(runIcon);
	}

	@Override
	public void stopped() {
    stopButton.setEnabled(false);
		runPauseButton.setIcon(runIcon);
		MessagePanel.appendLine("Simulation stopped.");
	}

}
