package com.sineshore.j2dge.v1_0.state;

import static java.lang.Math.round;

import com.sineshore.j2dge.v1_0.Game;
import com.sineshore.j2dge.v1_0.state.component.Container;

public abstract class State extends Container {

	private State lastState;
	private StateManager manager;

	public State(StateManager manager) {
		super(0.0f, 0.0f, 1.0f, 1.0f);
		this.manager = manager;
	}

	public void enter() {
		if (manager.getCurrentState() != this) {
			lastState = manager.getCurrentState();
		}
		if (lastState != null) {
			lastState.getChildren().forEach(child -> child.setHover(false));
			lastState.exit();
		}
		manager.setCurrentState(this);
		validate();
	}

	protected void exit() {
	}

	public boolean isCurrentState() {
		return manager.getCurrentState() == this;
	}

	public State getCurrentState() {
		return manager.getCurrentState();
	}

	public State getLastState() {
		return lastState;
	}

	public StateManager getManager() {
		return manager;
	}

	public Game getGame() {
		return manager.getGame();
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

	@Override
	public void validate() {
		int renderWidth = manager.getRenderer().getWidth();
		int renderHeight = manager.getRenderer().getHeight();
		x = round(xPercent * renderWidth);
		y = round(yPercent * renderHeight);
		width = round(widthPercent * renderWidth);
		height = round(heightPercent * renderHeight);
		super.validate();
	}

}
