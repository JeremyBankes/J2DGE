package com.sineshore.j2dge.v1_1.state.component;

import static java.lang.Math.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.sineshore.j2dge.v1_1.KeyInput.KeyInputEvent;
import com.sineshore.j2dge.v1_1.MouseInput.MouseAction;
import com.sineshore.j2dge.v1_1.MouseInput.MouseInputEvent;
import com.sineshore.j2dge.v1_1.Renderer;

public class Component {

	private static Component focus;
	private static int focusStealingEvent;

	public static enum Alignment {

		TOP, BOTTOM, CENTER, RIGHT, LEFT

	}

	private Alignment horizontalAlignment;
	private Alignment verticalAlignment;

	private Color backgroundColor;
	private Color foregroundColor;
	private BufferedImage image;
	private Border border;

	private Rectangle offsets;
	private int x, y, width, height;
	private float xPercent, yPercent, widthPercent, heightPercent;
	private boolean hover, press;
	private boolean focusable;
	private boolean visible = true;

	protected Container parent;

	private Font font;

	public Component(float xPercent, float yPercent, float widthPercent, float heightPercent) {
		this.xPercent = xPercent;
		this.yPercent = yPercent;
		this.widthPercent = widthPercent;
		this.heightPercent = heightPercent;
		horizontalAlignment = verticalAlignment = Alignment.CENTER;
		foregroundColor = Color.WHITE;
		offsets = new Rectangle();
	}

	public void validate() {
		if (!visible) {
			return;
		}
		Component parent = getParent();
		if (parent != null) {
			x = parent.x + round(getXPercent() * parent.getWidth() + offsets.x);
			y = parent.y + round(getYPercent() * parent.getHeight() + offsets.y);
			width = round(getWidthPercent() * parent.getWidth() + offsets.width);
			height = round(getHeightPercent() * parent.getHeight() + offsets.height);

			if (horizontalAlignment == Alignment.CENTER) {
				x -= width / 2;
			} else if (horizontalAlignment == Alignment.RIGHT) {
				x -= width;
			}

			if (verticalAlignment == Alignment.CENTER) {
				y -= height / 2;
			} else if (verticalAlignment == Alignment.BOTTOM) {
				y -= height;
			} else if (verticalAlignment != Alignment.TOP) {
				throw new IllegalStateException("Invalid vertical alignment: " + verticalAlignment);
			}
		}
	}

	public void mouseEvent(MouseInputEvent event) {
		if (!visible) {
			return;
		}
		if (event.action == MouseAction.MOVE) {
			hover = event.x >= x && event.x <= x + width && event.y >= y && event.y <= y + height;
		} else if (event.action == MouseAction.PRESS) {
			if (hover) {
				press = true;
				if (focusable) {
					if (event.hashCode() != focusStealingEvent) {
						focusStealingEvent = event.hashCode();
						focus = this;
					}
				}
			}
		} else if (event.action == MouseAction.RELEASE) {
			press = false;
		} else if (event.action == MouseAction.EXIT) {
			hover = false;
			press = false;
		}
	}

	public void keyEvent(KeyInputEvent event) {
	}

	public void resizeEvent(int x, int y) {
		validate();
	}

	protected void tick() {
	}

	protected void render(Renderer renderer) {
		if (!visible) {
			return;
		}
		renderBackground(renderer);
		renderContent(renderer);
		renderBorder(renderer);
	}

	protected void renderBackground(Renderer renderer) {
		if (image != null) {
			renderer.drawImage(getImage(), getX(), getY(), getWidth(), getHeight());
		} else if (getBackgroundColor() != null) {
			renderer.setColor(getBackgroundColor());
			renderer.drawRectangle(getX(), getY(), getWidth(), getHeight());
		}
	}

	protected void renderContent(Renderer renderer) {
	}

	protected void renderBorder(Renderer renderer) {
		if (border != null) {
			border.render(this, renderer);
		}
	}

	public boolean isFocused() {
		return this == focus;
	}

	public boolean isFocusable() {
		return focusable;
	}

	public void setFocusable(boolean focusable) {
		this.focusable = focusable;
	}

	public Font getFont() {
		if (font == null) {
			if (getParent() == null) {
				Font[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
				return font = fonts[(int) Math.round(Math.random() * (fonts.length - 1))].deriveFont(Font.PLAIN, 16);
			}
			return font = getParent().getFont();
		}
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	protected Component getRoot() {
		if (getParent() == null) {
			return this;
		}
		Component parent;
		do {
			parent = getParent();
		} while (parent.getParent() != null);
		return parent;
	}

	protected void setParent(Container parent) {
		this.parent = parent;
	}

	public Alignment getHorizontalAlignment() {
		return horizontalAlignment;
	}

	public void setHorizontalAlignment(Alignment horizontalAlignment) {
		this.horizontalAlignment = horizontalAlignment;
	}

	public Alignment getVerticalAlignment() {
		return verticalAlignment;
	}

	public void setVerticalAlignment(Alignment verticalAlignment) {
		this.verticalAlignment = verticalAlignment;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public Color getForegroundColor() {
		return foregroundColor;
	}

	public void setForegroundColor(Color foregroundColor) {
		this.foregroundColor = foregroundColor;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setBackgroundImage(BufferedImage image) {
		this.image = image;
	}

	public Border getBorder() {
		return border;
	}

	public void setBorder(Border border) {
		this.border = border;
	}

	public float getXPercent() {
		return xPercent;
	}

	public void setXPercent(float xPercent) {
		this.xPercent = xPercent;
	}

	protected void setX(int x) {
		this.x = x;
	}

	public float getYPercent() {
		return yPercent;
	}

	public void setYPercent(float yPercent) {
		this.yPercent = yPercent;
	}

	protected void setY(int y) {
		this.y = y;
	}

	public void setBounds(float xPercent, float yPercent, float widthPercent, float heightPercent) {
		setXPercent(xPercent);
		setYPercent(yPercent);
		setWidthPercent(widthPercent);
		setHeightPercent(heightPercent);
	}

	public float getWidthPercent() {
		return widthPercent;
	}

	public void setWidthPercent(float widthPercent) {
		this.widthPercent = widthPercent;
	}

	protected void setWidth(int width) {
		this.width = width;
	}

	public float getHeightPercent() {
		return heightPercent;
	}

	public void setHeightPercent(float heightPercent) {
		this.heightPercent = heightPercent;
	}

	protected void setHeight(int height) {
		this.height = height;
	}

	public boolean isHover() {
		return hover;
	}

	public void setHover(boolean hover) {
		this.hover = hover;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isPress() {
		return press;
	}

	public Container getParent() {
		return parent;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
		validate();
	}

	public void setOffsets(int x, int y, int width, int height) {
		this.offsets = new Rectangle(x, y, width, height);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

}
