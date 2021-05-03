package com.sineshore.j2dge.v1_1;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.HashSet;

public class MouseInput {

	private HashSet<MouseEventCallback> mouseCallbacks;
	private HashSet<Integer> pressed;

	private int x, y;
	int xOffset, yOffset;

	public MouseInput(Component component) {
		mouseCallbacks = new HashSet<>();
		pressed = new HashSet<>();

		component.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent event) {
				x = event.getX() - xOffset;
				y = event.getY() - yOffset;
				mouseCallbacks.forEach(callback -> callback.invoke(new MouseInputEvent(MouseAction.MOVE, x, y)));
			}

			@Override
			public void mouseDragged(MouseEvent event) {
				mouseMoved(event);
			}
		});

		component.addMouseListener(new MouseListener() {

			@Override
			public void mousePressed(MouseEvent event) {
				MouseInputEvent newEvent = new MouseInputEvent(MouseAction.PRESS, event.getX() - xOffset, event.getY() - yOffset);
				mouseCallbacks.forEach(callback -> callback.invoke(newEvent));
				pressed.add(event.getButton());
			}

			@Override
			public void mouseReleased(MouseEvent event) {
				MouseInputEvent newEvent = new MouseInputEvent(MouseAction.RELEASE, event.getX() - xOffset, event.getY() - yOffset);
				mouseCallbacks.forEach(callback -> callback.invoke(newEvent));
				pressed.remove(event.getButton());
			}

			@Override
			public void mouseExited(MouseEvent event) {
				MouseInputEvent newEvent = new MouseInputEvent(MouseAction.EXIT, event.getX() - xOffset, event.getY() - yOffset);
				mouseCallbacks.forEach(callback -> callback.invoke(newEvent));
			}

			@Override
			public void mouseEntered(MouseEvent event) {
				MouseInputEvent newEvent = new MouseInputEvent(MouseAction.ENTER, event.getX() - xOffset, event.getY() - yOffset);
				mouseCallbacks.forEach(callback -> callback.invoke(newEvent));
			}

			@Override
			public void mouseClicked(MouseEvent event) {
				MouseInputEvent newEvent = new MouseInputEvent(MouseAction.CLICK, event.getX() - xOffset, event.getY() - yOffset);
				mouseCallbacks.forEach(callback -> callback.invoke(newEvent));
			}
		});

		component.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent event) {
				mouseCallbacks.forEach(callback -> callback.invoke(new MouseInputEvent(MouseAction.SCROLL, 0, event.getWheelRotation())));
			}
		});
	}

	public void addMouseCallback(MouseEventCallback callback) {
		mouseCallbacks.add(callback);
	}

	public void removeMouseCallback(MouseEventCallback callback) {
		mouseCallbacks.remove(callback);
	}

	public boolean isPressed(int button) {
		return pressed.contains(button);
	}

	public boolean isAnyPressed(int... button) {
		for (int asciiCode : button) {
			if (pressed.contains(asciiCode)) {
				return true;
			}
		}
		return false;
	}

	public boolean isAllPressed(int... button) {
		for (int asciiCode : button) {
			if (!pressed.contains(asciiCode)) {
				return false;
			}
		}
		return true;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public static enum MouseAction {
		MOVE, PRESS, CLICK, RELEASE, SCROLL, ENTER, EXIT
	}

	public static interface MouseEventCallback {

		public abstract void invoke(MouseInputEvent event);

	}

	public static class MouseInputEvent {

		public final MouseAction action;
		public final int x, y;

		private boolean consumed;

		public MouseInputEvent(MouseAction action, int x, int y) {
			this.action = action;
			this.x = x;
			this.y = y;
		}

		public boolean isConsumed() {
			return consumed;
		}

		public void consume() {
			consumed = true;
		}

		@Override
		public String toString() {
			return action + ", " + x + ", " + y + (consumed ? " consumed" : "");
		}

	}

	public static final int MOUSE_LEFT = 1, MOUSE_MIDDLE = 2, MOUSE_RIGHT = 3;

}
