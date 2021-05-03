package com.sineshore.j2dge.v1_1;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Assets {

	private static final HashMap<String, BufferedImage> IMAGES = new HashMap<>();

	public static BufferedImage getImage(String path) {
		String name = resolveName(path);
		if (IMAGES.containsKey(name)) {
			return IMAGES.get(name);
		}
		try {
			IMAGES.put(name, ImageIO.read(Assets.class.getResourceAsStream(path)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return getImage(path);
	}

	private static String resolveName(String name) {
		return name.replace("^\\/", "").replaceAll("\\/|\\\\\\\\", ".").toLowerCase();
	}

}
