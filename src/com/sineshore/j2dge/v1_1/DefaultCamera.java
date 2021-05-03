package com.sineshore.j2dge.v1_1;

public class DefaultCamera implements Camera {

	private Renderer renderer;

	public DefaultCamera(Renderer renderer) {
		this.renderer = renderer;
	}

	@Override
	public int transformX(float x) {
		return Math.round(renderer.getWidth() * x);
	}

	@Override
	public int transformY(float y) {
		return Math.round(renderer.getHeight() * y);
	}

	@Override
	public int transformWidth(float width) {
		return Math.round(renderer.getWidth() * width);
	}

	@Override
	public int transformHeight(float height) {
		return Math.round(renderer.getHeight() * height);
	}

}
