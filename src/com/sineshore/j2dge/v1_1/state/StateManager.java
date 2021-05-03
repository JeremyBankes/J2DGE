package com.sineshore.j2dge.v1_1.state;

import java.util.HashMap;

import com.sineshore.j2dge.v1_1.Game;
import com.sineshore.j2dge.v1_1.KeyInput;
import com.sineshore.j2dge.v1_1.MouseInput;
import com.sineshore.j2dge.v1_1.Renderer;

public class StateManager {

    private HashMap<Class<? extends State>, State> states;

    private State currentState;
    private Game game;

    public StateManager(Game game) {
	states = new HashMap<>();
	this.game = game;
	
	getKeyInput().addKeyEventCallback(event -> {
	    if (currentState != null) {
		currentState.keyEvent(event);
	    }
	});
	getMouseInput().addMouseCallback(event -> {
	    if (currentState != null) {
		currentState.mouseEvent(event);
	    }
	});
	getRenderer().addResizeCallback((x, y) -> {
	    if (currentState != null) {
		currentState.resizeEvent(x, y);
	    }
	});
    }

    public void tick() {
	currentState.tick();
    }

    public void render(Renderer renderer) {
	currentState.render(renderer);
    }

    public void enterState(Class<? extends State> stateClass) {
	getState(stateClass).enter();
    }

    public void registerState(State state) {
	states.put(state.getClass(), state);
    }

    public <T extends State> T getState(Class<T> stateClass) {
	return stateClass.cast(states.get(stateClass));
    }

    public boolean isCurrentState(Class<? extends State> stateClass) {
	return getCurrentState().getClass() == stateClass;
    }

    public Game getGame() {
	return game;
    }

    public KeyInput getKeyInput() {
	return game.getKeyInput();
    }

    public MouseInput getMouseInput() {
	return game.getMouseInput();
    }

    public Renderer getRenderer() {
	return game.getRenderer();
    }

    public State getCurrentState() {
	return currentState;
    }

    void setCurrentState(State state) {
	currentState = state;
    }

}
