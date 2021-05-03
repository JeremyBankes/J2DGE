package com.sineshore.j2dge.v1_0.state.component;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.sineshore.j2dge.v1_0.MouseInput.MouseAction;
import com.sineshore.j2dge.v1_0.MouseInput.MouseInputEvent;
import com.sineshore.j2dge.v1_0.Renderer;

public class Button extends Label {

	protected Color hoverColor;
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
		if (event.isConsumed() || !hover || !visible) {
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
		if (hover) {
			if (hoverImage != null) {
				renderer.drawImage(hoverImage, x, y, width, height);
				return;
			} else if (hoverColor != null) {
				renderer.setColor(hoverColor);
				renderer.fillRectangle(x, y, width, height);
				return;
			}
		}
		super.renderBackground(renderer);
	}

	@Override
	protected void renderBorder(Renderer renderer) {
		if (hover) {
			if (hoverBorder != null) {
				hoverBorder.render(this, renderer);
				return;
			}
		}
		super.renderBorder(renderer);
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

	@Override
	public float getFontSize() {
		return fontSize;
	}

	@Override
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
