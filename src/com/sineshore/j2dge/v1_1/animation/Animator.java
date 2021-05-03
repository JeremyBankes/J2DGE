package com.sineshore.j2dge.v1_1.animation;

import java.awt.image.BufferedImage;

import com.sineshore.j2dge.v1_1.SpriteSheet;

public class Animator {

	private SpriteSheet sheet;
	private int fps;

	private long lastFrame;
	private int frame;
	private final int frameTime;

	public Animator(SpriteSheet sheet, int fps) {
		this.sheet = sheet;
		this.fps = fps;

		frameTime = 1000 / fps;
	}

	public void tick() {
		long currentTime = System.currentTimeMillis();
		if (currentTime - lastFrame > frameTime) {
			frame = (frame + 1) % sheet.getCount();
			lastFrame = currentTime;
		}
	}

	public BufferedImage getFrame() {
		return sheet.getSprite(frame);
	}

	public int getFps() {
		return fps;
	}

}
