package com.sineshore.j2dge.v1_1.animation;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class SpriteSheet {

	private int spriteWidth, spriteHeight;
	private ArrayList<BufferedImage> sprites;
	private int count;

	public SpriteSheet(BufferedImage source, int spriteWidth, int spriteHeight, int emptySlots) {
		this.sprites = new ArrayList<>();
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
		int spritesAcross = source.getWidth() / spriteWidth;
		int spritesDown = source.getHeight() / spriteHeight;
		count = spritesAcross * spritesDown - emptySlots;
		for (int y = 0; y < spritesDown; y++) {
			for (int x = 0; x < (y == spritesDown - 1 ? spritesAcross - emptySlots : spritesAcross); x++) {
				BufferedImage newSprite = source.getSubimage(x * spriteWidth, y * spriteHeight, spriteWidth,
						spriteHeight);
				sprites.add(newSprite);
			}
		}
	}

	public SpriteSheet(BufferedImage[] images) {
		count = images.length;
		spriteWidth = images[0].getWidth();
		spriteHeight = images[0].getHeight();
		for (BufferedImage image : images) {
			if (image.getWidth() != spriteWidth || image.getHeight() != spriteHeight) {
				throw new IllegalStateException(
						"created sprite sheet out of an Image array with images of different sizes");
			}
			sprites.add(image);
		}
	}

	public BufferedImage getSprite(int index) {
		return sprites.get(index);
	}

	public int getSpriteWidth() {
		return spriteWidth;
	}

	public int getSpriteHeight() {
		return spriteHeight;
	}

	public ArrayList<BufferedImage> getSprites() {
		return sprites;
	}

	public int getCount() {
		return count;
	}

}
