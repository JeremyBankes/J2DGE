package com.sineshore.j2dge.v1_1;

import java.util.HashMap;

public class ControlsManager extends HashMap<String, Integer> {

	private static final long serialVersionUID = 1L;

	private KeyInput keyInput;

	public ControlsManager(KeyInput keyInput) {
		this.keyInput = keyInput;

		put("move-up", KeyInput.KEY_W);
		put("move-down", KeyInput.KEY_S);
		put("move-right", KeyInput.KEY_D);
		put("move-left", KeyInput.KEY_A);
		put("sprint", KeyInput.KEY_G);
	}

	public boolean isMoveUp() {
		return keyInput.isPressed(get("move-up"));
	}

	public boolean isMoveDown() {
		return keyInput.isPressed(get("move-down"));
	}

	public boolean isMoveRight() {
		return keyInput.isPressed(get("move-right"));
	}

	public boolean isMoveLeft() {
		return keyInput.isPressed(get("move-left"));
	}

	public boolean isMovementDown() {
		return isMoveUp() || isMoveRight() || isMoveDown() || isMoveLeft();
	}

	public boolean isMovementKey(int asciiCode) {
		return get("move-up") == asciiCode || get("move-right") == asciiCode || get("move-down") == asciiCode || get("move-left") == asciiCode;
	}

	public boolean isSprint() {
		return keyInput.isPressed(get("sprint"));
	}

}
