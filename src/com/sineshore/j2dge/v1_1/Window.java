package com.sineshore.j2dge.v1_1;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import javax.swing.JFrame;

public class Window extends JFrame {

	private static final long serialVersionUID = 8415476507899121988L;

	public Window(String title, int width, int height) {
		setTitle(title);
		Container contentPane = getContentPane();
		contentPane.setBackground(Color.BLACK);
		contentPane.setPreferredSize(new Dimension(width, height));
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
	}

	public void center() {
		Rectangle bounds = getBestGraphicDevice().getDefaultConfiguration().getBounds();
		setLocation(bounds.x + bounds.width / 2 - getBounds().width / 2,
				bounds.y + bounds.height / 2 - getBounds().height / 2);
	}

	public GraphicsDevice getBestGraphicDevice() {
		GraphicsDevice bestDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		GraphicsDevice[] devices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
		int largestArea = 0;
		for (GraphicsDevice device : devices) {
			Rectangle deviceBounds = device.getDefaultConfiguration().getBounds();
			if (deviceBounds.intersects(getBounds())) {
				Rectangle intersection = deviceBounds.intersection(getBounds());
				if (intersection.width * intersection.height > largestArea) {
					bestDevice = device;
					largestArea = intersection.width * intersection.height;
				}
			}
		}
		return bestDevice;
	}

}
