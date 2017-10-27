package event;

import network.Network;
import network.Source;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

//import java.util.Timer;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
 */
public class EventManager {

	private List<SimControlListener> listeners = new ArrayList<SimControlListener>();

	public static int eventLength = 100;
	/** DOCUMENT ME! */
	private static boolean verbose = false;

	/** DOCUMENT ME! */
	private static boolean isRunning = false;

	/** DOCUMENT ME! */
	private static Timer timer;

	/** DOCUMENT ME! */
	private static EventTask eventTask;

	/** DOCUMENT ME! */
	private static int delay = 100;

	/** DOCUMENT ME! */
	private static EventManager instance = new EventManager();

	/** DOCUMENT ME! */
	private Collection<Event> queue = new ArrayList<Event>();

	/** DOCUMENT ME! */
	private Network network = null;

	/** DOCUMENT ME! */
	private boolean causeDelayAfterEachEvent = true;

	/** DOCUMENT ME! */
	private boolean returnAfterEachEvent = false;

	private static boolean isPaused = false;

	private long simulationTime = 0;

	{
		eventTask = new EventTask();
		timer = new Timer(0, null);
		timer.addActionListener(eventTask);
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param d
	 *          DOCUMENT ME!
	 */
	public static void setDelay(int d) {
		delay = d;

		if (timer.isRunning()) {
			timer.setDelay(delay);
		}
	}

	public static int getDelay() {
		return delay;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public static EventManager getInstance() {
		return instance;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param b
	 *          DOCUMENT ME!
	 */
	public void setCauseDelayAfterEachEvent(boolean b) {
		causeDelayAfterEachEvent = b;
	}

	/**
	 * Add listeners that can respond to changes in events
	 * 
	 * @param toAdd
	 */
	public void addListener(SimControlListener toAdd) {
		listeners.add(toAdd);
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean isCauseDelayAfterEachEvent() {
		return causeDelayAfterEachEvent;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param b
	 *          DOCUMENT ME!
	 */
	public void setReturnAfterEachEvent(boolean b) {
		returnAfterEachEvent = b;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean isReturnAfterEachEvent() {
		return returnAfterEachEvent;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean isRunning() {
		return isRunning;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param v
	 *          DOCUMENT ME!
	 */
	public static void setVerbose(boolean v) {
		verbose = v;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public static boolean isVerbose() {
		return verbose;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param event
	 *          DOCUMENT ME!
	 */
	public void add(Event event) {
		queue.add(event);
	}

	public void pause() {
		if (isRunning) {
			timer.stop();
		}
		isRunning = false;
		isPaused = true;
		for (SimControlListener sl : listeners)
			sl.paused();
	}

	public boolean isPaused() {
		return isPaused;
	}

	public void stop() {
		timer.stop();
		Iterator<Event> i = queue.iterator();
		while (i.hasNext()) {
			Event evt = (Event) i.next();
			if (evt instanceof MoveOnLinkEvent) {
				((MoveOnLinkEvent) evt).getPacket().getCurrentLink().eventComplete(evt);
			}
		}
		queue.clear();

		isRunning = false;
		isPaused = false;

		// Notify everybody that may be interested.
		for (SimControlListener sl : listeners)
			sl.stopped();
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param network
	 *          DOCUMENT ME!
	 */
	public int populateEventsWith(Network network) {
		int result = 0;
		queue.clear();
		this.network = network;

		ArrayList<?> sources = (ArrayList<?>) network.getSources();
		if (sources.size() == 0) {
			JOptionPane.showMessageDialog(null, "You must have at least one source (desktop machine).");
			result = 1;
		}
		if (network.getSinks().isEmpty()) {
			JOptionPane.showMessageDialog(null, "You must have at least one sink (destination machine).");
			result = 1;
		}
		for (Iterator<?> i = sources.iterator(); i.hasNext();) {
			Source source = (Source) i.next();
			if (source.getSink() == null)
				continue;
			StartAtSourceEvent event = new StartAtSourceEvent();
			event.setSource(source);
			event.setEventTime(0);
			add(event);
		}
		if (queue.isEmpty()) {
			JOptionPane.showMessageDialog(null, "You need to connect at least one source to a destination!");
			result = 1;
		}
		return result;
	}

	/**
	 * DOCUMENT ME!
	 */
	public int run() {
		if (timer.isRunning()) {
			return 1;
		}

		if (EventManager.isVerbose()) {
			System.out.println(network + "\n");
			System.out.println("Running Sim with " + queue.size() + " event(s).");
		}
		timer.setDelay(delay);
		if (!isRunning && !isPaused) {
			timer.restart();
			// Notify everybody that may be interested.
			for (SimControlListener sl : listeners)
				sl.started();
		}
		timer.start();
		isRunning = true;
		// Notify everybody that may be interested.
		for (SimControlListener sl : listeners)
			sl.started();
		return 0;
	}

	class EventTask implements ActionListener {
		public void actionPerformed(ActionEvent e) {
      if (queue.isEmpty()) {
        timer.stop();
        isRunning = false;
        isPaused = false;
    		// Notify everybody that may be interested.
    		for (SimControlListener sl : listeners)
    			sl.stopped();
        return;
      }
      simulationTime += delay;
      
      Object[] list = queue.toArray();
      Event event;
      for (int evt=0; evt < list.length && ((Event)list[evt]).getEventTime() <= simulationTime; evt++) {
      	event = (Event)list[evt];
      	if (event.getEventTime() <= simulationTime) {
      		event.execute();
      		queue.remove(event);
      	} else {
      		// Notify everybody that may be interested.
      		for (SimControlListener sl : listeners)
      			sl.stopped();
      	}
      }
    }
	}
}
