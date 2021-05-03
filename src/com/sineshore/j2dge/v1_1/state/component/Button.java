package com.sineshore.j2dge.v1_1.state.component;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.sineshore.j2dge.v1_1.graphics.Renderer;
import com.sineshore.j2dge.v1_1.input.MouseInput.MouseAction;
import com.sineshore.j2dge.v1_1.input.MouseInput.MouseInputEvent;

public class Button extends Label {

	protected Color hoverColor;
	protected Color hoverForegroundColor;
	protected BufferedImage hoverImage;
	protected Border hoverBorder;
	protected Runnable execution;
	protected float fontSize = 0.25f;

	public Button(String text, float xPercent, float yPercent, float widthPercent, float heightPercent) {
		super(text, xPercent, yPercent, widthPercent, heightPercent);
	}

	@Override
	public void mouseEvent(MouseInputEvent event) {
		super.mouseEvent(event);
		if (event.isConsumed() || !isHover() || !isVisible()) {
			return;
		}
		if (event.action == MouseAction.PRESS) {
			if (execution != null) {
				execution.run();
			}
			event.consume();
		}
	}

	@Override
	protected void renderBackground(Renderer renderer) {
		if (isHover()) {
			if (hoverImage != null) {
				renderer.drawImage(hoverImage, getX(), getY(), getWidth(), getHeight());
				return;
			} else if (hoverColor != null) {
				renderer.setColor(hoverColor);
				renderer.drawRectangle(getX(), getY(), getWidth(), getHeight());
				return;
			}
		}
		super.renderBackground(renderer);
	}

	@Override
	protected void renderBorder(Renderer renderer) {
		if (isHover()) {
			if (hoverBorder != null) {
				hoverBorder.render(this, renderer);
				return;
			}
		}
		super.renderBorder(renderer);
	}

	@Override
	protected void renderContent(Renderer renderer) {
		super.renderContent(renderer);
		renderer.setColor(getForegroundColor());
		if (isHover()) {
			if (hoverForegroundColor != null) {
				renderer.setColor(getHoverForegroundColor());
			}
		}
		renderer.setFont(getFont());
		int yOffset = 0;
		for (String line : getText().split("\n")) {
			int x = 0, y = 0;
			if (textAlignmentHorizontal == Alignment.CENTER) {
				x += this.getX() + this.getWidth() / 2 - renderer.getTextWidth(line) / 2;
			} else if (textAlignmentHorizontal == Alignment.LEFT) {
				x += this.getX();
			} else if (textAlignmentHorizontal == Alignment.RIGHT) {
				x += this.getX() + this.getWidth() - renderer.getTextWidth(line);
			}

			if (textAlignmentVertical == Alignment.CENTER) {
				y += this.getY() + this.getHeight() / 2 + renderer.getTextAscent() / 2;
			} else if (textAlignmentVertical == Alignment.TOP) {
				y += this.getY() + renderer.getTextAscent();
			}
			renderer.drawText(line, x, y + yOffset);
			yOffset += renderer.getTextAscent();
		}
	}

	public Color getHoverColor() {
		return hoverColor;
	}

	public void setHoverColor(Color hoverColor) {
		this.hoverColor = hoverColor;
	}

	public BufferedImage getHoverImage() {
		return hoverImage;
	}

	public void setHoverImage(BufferedImage hoverImage) {
		this.hoverImage = hoverImage;
	}

	public Runnable getExecution() {
		return execution;
	}

	public void setExecution(Runnable execution) {
		this.execution = execution;
	}

	public Border getHoverBorder() {
		return hoverBorder;
	}

	public void setHoverBorder(Border hoverBorder) {
		this.hoverBorder = hoverBorder;
	}

	public Color getHoverForegroundColor() {
		return hoverForegroundColor;
	}

	public void setHoverForegroundColor(Color hoverForegroundColor) {
		this.hoverForegroundColor = hoverForegroundColor;
	}

	public float getFontSize() {
		return fontSize;
	}

	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
