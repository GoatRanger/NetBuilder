/*
 * Created on Dec 1, 2003
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package utils;

import gui.NetBuilderFrame;

import java.beans.ExceptionListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import network.Network;

/**
 * @author DK8685
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class FileManager {

	public FileManager() {
		
	}
	
	public static void saveNetwork(Network net) {
		try {
			XMLEncoder e = new XMLEncoder(
					new BufferedOutputStream(new FileOutputStream("Test.xml")));
			e.writeObject(net);
			e.close();
		} catch (FileNotFoundException fnfe) {
			
		}

	}
	
	public static void saveNetworkGUI(NetBuilderFrame frame) {
		try {
			XMLEncoder e = new XMLEncoder(
					new BufferedOutputStream(new FileOutputStream("TestFrame.xml")));
			e.setExceptionListener(el);
			e.writeObject(frame);
			e.close();
		} catch (FileNotFoundException fnfe) {
			
		}

	}
	
	public static NetBuilderFrame openNetworkGUI() {
		try {
		XMLDecoder d = new XMLDecoder(
						   new BufferedInputStream(
							   new FileInputStream("TestFrame.xml")));
		Object result = d.readObject();
		d.close();
		if (result instanceof NetBuilderFrame) return (NetBuilderFrame)result;
		} catch (FileNotFoundException fnfe) {
			return null;
		}
		return null;


	}
	
	
	static ExceptionListener el = new ExceptionListener() {

    public void exceptionThrown(Exception arg0) {
      arg0.printStackTrace();
      
    }
	};
}
