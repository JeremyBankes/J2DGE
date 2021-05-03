package com.sineshore.j2dge.v1_1.assets;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Assets {

	private static final HashMap<String, BufferedImage> IMAGES = new HashMap<>();
	private static final HashMap<String, Font> FONTS = new HashMap<>();

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

	public static Font getFont(String path) {
		String name = resolveName(path);
		if (FONTS.containsKey(name)) {
			return FONTS.get(name);
		}
		try {
			FONTS.put(name, Font.createFont(Font.PLAIN, Assets.class.getResourceAsStream(path)));
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}
		return getFont(path);
	}

	private static String resolveName(String name) {
		return name.replace("^\\/", "").replaceAll("\\/|\\\\\\\\", ".").toLowerCase();
	}

}
