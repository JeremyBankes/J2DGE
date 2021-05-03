package com.sineshore.j2dge.v1_1.state;

import static java.lang.Math.*;

import com.sineshore.j2dge.v1_1.Game;
import com.sineshore.j2dge.v1_1.state.component.Container;

public abstract class State extends Container {

    private State lastState;
    private StateManager manager;

    public State(Game game) {
	super(0.0f, 0.0f, 1.0f, 1.0f);
	this.manager = game.getStateManager();
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

    protected void exit() {}

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
	setX(round(getXPercent() * renderWidth));
	setY(round(getYPercent() * renderHeight));
	setWidth(round(getWidthPercent() * renderWidth));
	setHeight(round(getHeightPercent() * renderHeight));
	super.validate();
    }

}
