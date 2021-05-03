package com.sineshore.j2dge.v1_1.state.component;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import com.sineshore.j2dge.v1_1.KeyInput;
import com.sineshore.j2dge.v1_1.KeyInput.KeyAction;
import com.sineshore.j2dge.v1_1.KeyInput.KeyInputEvent;
import com.sineshore.j2dge.v1_1.Renderer;

public class TextField extends Label {

	private int cursor;
	private boolean blink;
	private int blinkTimer;
	private int blinkInterval;
	private StringBuilder builder;
	private Runnable execution;

	public TextField(String text, float xPercent, float yPercent, float widthPercent, float heightPercent) {
		super(null, xPercent, yPercent, widthPercent, heightPercent);
		builder = new StringBuilder(text);
		cursor = builder.length();
		blinkInterval = 30;
		setFocusable(true);
	}

	@Override
	public synchronized void keyEvent(KeyInputEvent event) {
		super.keyEvent(event);
		if ((isFocusable() && !isFocused()) || !isVisible() || event.isConsumed()) {
			return;
		}
		if (event.action == KeyAction.PRESS) {
			if (event.asciiCode == KeyInput.KEY_RIGHT) {
				if (event.modifiers.contains("ctrl")) {
					cursor = builder.indexOf(" ", cursor + 1) + 1;
					cursor = cursor == 0 ? builder.length() : cursor;
				} else {
					cursor = cursor + 1 > builder.length() ? builder.length() : cursor + 1;
				}
			} else if (event.asciiCode == KeyInput.KEY_LEFT) {
				if (event.modifiers.contains("ctrl")) {
					cursor = builder.lastIndexOf(" ", cursor - 2) + 1;
					cursor = cursor == -1 ? 0 : cursor;
				} else {
					cursor = cursor - 1 < 0 ? 0 : cursor - 1;
				}
			} else if (event.asciiCode == KeyInput.KEY_BACK_SPACE) {
				if (event.modifiers.contains("ctrl")) {
					int end = cursor;
					cursor = builder.lastIndexOf(" ", cursor - 2) + 1;
					cursor = cursor == -1 ? 0 : cursor;
					builder.delete(cursor, end);
				} else {
					if (cursor > 0) {
						builder.deleteCharAt(--cursor);
					}
				}
			} else if (event.asciiCode == KeyInput.KEY_DELETE) {
				if (event.modifiers.contains("ctrl")) {
					int end = builder.indexOf(" ", cursor + 1) + 1;
					end = end == 0 ? builder.length() : end;
					builder.delete(cursor, end);
				} else {
					if (cursor < builder.length()) {
						builder.deleteCharAt(cursor);
					}
				}
			} else if (event.asciiCode == KeyInput.KEY_V) {
				if (event.modifiers.contains("ctrl")) {
					try {
						String clipboard = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
						builder.insert(cursor, clipboard);
						cursor += clipboard.length();
					} catch (HeadlessException | UnsupportedFlavorException | IOException exception) {
						exception.printStackTrace();
					}
				}
			} else if (event.asciiCode == KeyInput.KEY_ENTER) {
				if (execution != null) {
					execution.run();
				}
			}
		} else if (event.action == KeyAction.TYPE) {
			if (event.asciiCode == KeyInput.KEY_DELETE || event.asciiCode == KeyInput.KEY_ENTER) {
				return;
			}
			if (getFont().canDisplay(event.asciiCode)) {
				builder.insert(cursor++, (char) event.asciiCode);
			}
		}
		skipBlink();
	}

	public void skipBlink() {
		blink = false;
		blinkTimer = 0;
	}

	public Runnable getExecution() {
		return execution;
	}

	public void setExecution(Runnable execution) {
		this.execution = execution;
	}

	@Override
	public String getText() {
		return builder.toString();
	}

	@Override
	public synchronized void setText(String text) {
		builder = new StringBuilder(text);
		cursor = text.length();
	}

	@Override
	public void setVisible(boolean visible) {
		skipBlink();
		super.setVisible(visible);
	}

	public synchronized void clear() {
		builder.setLength(0);
		cursor = 0;
	}

	@Override
	public void validate() {
		super.validate();
	}

	@Override
	protected void tick() {
		super.tick();
		if (blinkTimer < blinkInterval) {
			blinkTimer++;
		} else {
			blink = !blink;
			blinkTimer = 0;
		}
	}

	protected String getDisplayText() {
		return getText();
	}

	@Override
	protected synchronized void renderContent(Renderer renderer) {
		int x = 0, y = 0;
		renderer.setColor(getForegroundColor());
		renderer.setFont(getFont());

		String display = getDisplayText();
		if (textAlignmentHorizontal == Alignment.CENTER) {
			x = this.getX() + this.getWidth() / 2 - renderer.getTextWidth(display) / 2;
		} else if (textAlignmentHorizontal == Alignment.LEFT) {
			x = this.getX();
		} else if (textAlignmentHorizontal == Alignment.RIGHT) {
			x = this.getX() + this.getWidth() - renderer.getTextWidth(display);
		} else {
			throw new IllegalStateException("Invalid horizontal text alignment: " + textAlignmentHorizontal);
		}

		if (textAlignmentVertical == Alignment.CENTER) {
			y = this.getY() + this.getHeight() / 2 + renderer.getTextAscent() / 2;
		} else if (textAlignmentVertical == Alignment.TOP) {
			y = this.getY() + renderer.getTextAscent();
		} else if (textAlignmentVertical == Alignment.BOTTOM) {
			y = this.getY() + this.getHeight();
		} else {
			throw new IllegalStateException("Invalid vertical text alignment: " + textAlignmentVertical);
		}

		if (!blink) {
			int xOffset = renderer.getTextWidth(display.substring(0, Math.min(display.length(), cursor)));
			renderer.drawRectangle(x + xOffset, y - renderer.getTextAscent(), 2, renderer.getTextAscent());
		}
		renderer.drawText(display, x, y);
	}

}
