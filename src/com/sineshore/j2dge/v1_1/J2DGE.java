package com.sineshore.j2dge.v1_1;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

public class J2DGE {

	public static Color mix(Color color1, Color color2, float ratio) {
		return new Color( //
				(int) (color1.getRed() * (1f - ratio) + color2.getRed() * ratio), //
				(int) (color1.getGreen() * (1f - ratio) + color2.getGreen() * ratio), //
				(int) (color1.getBlue() * (1f - ratio) + color2.getBlue() * ratio), //
				(int) (color1.getAlpha() * (1f - ratio) + color2.getAlpha() * ratio)//
		);
	}

	public static void runOnThread(Runnable execution) {
		Thread thread = new Thread(execution, "on-thread");
		thread.setDaemon(true);
		thread.start();
	}

	public static BufferedImage getImage(String path) {
		try {
			InputStream input = J2DGE.class.getResourceAsStream(path);
			if (input != null) {
				return ImageIO.read(input);
			} else if (new File(path).exists()) {
				return ImageIO.read(new File(path));
			} else {
				return ImageIO.read(new URL(path));
			}
		} catch (IOException exception) {
			throw new IllegalStateException("Failed to load image '" + path + "'.");
		}
	}

}
