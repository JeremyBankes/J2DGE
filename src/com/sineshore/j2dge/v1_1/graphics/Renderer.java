package com.sineshore.j2dge.v1_1.graphics;

import static java.awt.RenderingHints.*;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints.Key;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Renderer {

	private Canvas canvas;
	private BufferStrategy strategy;
	private Graphics2D g;
	private Camera camera;

	protected float aspectRatio;

	private Map<Object, Object> rendereringHints;

	private HashMap<FontKey, Font> derivedFonts;

	private HashMap<Class<? extends Camera>, Camera> cameras;

	private HashSet<RendererResizeCallback> resizeCallbacks;

	private Dimension priorToFullscreen;

	private AffineTransform savedTransform;

	@SuppressWarnings("unchecked")
	public Renderer(Canvas canvas) {
		this.canvas = canvas;
		rendereringHints = (Map<Object, Object>) Toolkit.getDefaultToolkit()
				.getDesktopProperty("awt.font.desktophints");
		rendereringHints.put(KEY_INTERPOLATION, VALUE_INTERPOLATION_BILINEAR);
		rendereringHints.put(KEY_TEXT_ANTIALIASING, VALUE_TEXT_ANTIALIAS_ON);
		rendereringHints.put(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
		rendereringHints.put(KEY_FRACTIONALMETRICS, VALUE_FRACTIONALMETRICS_ON);
		cameras = new HashMap<>();
		derivedFonts = new HashMap<>();
		resizeCallbacks = new HashSet<>();

		canvas.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent event) {
				super.componentResized(event);
				resizeCallbacks.forEach(callback -> callback.invoke(getWidth(), getHeight()));
			}

		});

		registerCamera(new DefaultCamera(this));
		useCamera(DefaultCamera.class);
	}

	public void registerCamera(Camera camera) {
		cameras.put(camera.getClass(), camera);
	}

	public void useCamera(Class<? extends Camera> cameraClass) {
		this.camera = cameras.get(cameraClass);
	}

	public <T extends Camera> T getCamera(Class<T> cameraClass) {
		return cameraClass.cast(cameras.get(cameraClass));
	}

	public void addResizeCallback(RendererResizeCallback callback) {
		resizeCallbacks.add(callback);
	}

	public void removeResizeCallback(RendererResizeCallback callback) {
		resizeCallbacks.remove(callback);
	}

	public void startRender() {
		while (strategy == null && canvas.isValid()) {
			canvas.createBufferStrategy(2);
			strategy = canvas.getBufferStrategy();
		}
		g = (Graphics2D) strategy.getDrawGraphics();
		g.setRenderingHints(rendereringHints);
		g.clearRect(0, 0, getWidth(), getHeight());
	}

	public void endRender() {
		g.dispose();
		strategy.show();
	}

	public boolean isReady() {
		return g != null;
	}

	public void setColor(Color color) {
		g.setColor(color);
	}

	public void saveTransform() {
		savedTransform = new AffineTransform(g.getTransform());
	}

	public void setTransform(AffineTransform transform) {
		g.setTransform(transform);
	}

	public void loadTransform() {
		g.setTransform(savedTransform);
	}

	public int getWidth() {
		return canvas.getWidth();
	}

	public int getHeight() {
		return canvas.getHeight();
	}

	public Font getFont() {
		return g.getFont();
	}

	public void setFont(Font font) {
		g.setFont(font);
	}

	public Camera getCamera() {
		return camera;
	}

	public void setRenderingHint(Key key, Object value) {
		g.setRenderingHint(key, value);
	}

	public void drawLine(int x0, int y0, int x1, int y1) {
		g.drawLine(x0, y0, x1, y1);
	}

	public void drawLine(float x0, float y0, float x1, float y1) {
		if (camera == null) {
			throw new IllegalStateException("No camera is defined.");
		}
		drawLine( //
				camera.transformX(x0), //
				camera.transformY(y0), //
				camera.transformX(x1), //
				camera.transformY(y1) //
		);
	}

	public void traceRectangle(int x, int y, int width, int height) {
		g.drawRect(x, y, width, height);
	}

	public void traceRectangle(float x, float y, float width, float height) {
		if (camera == null) {
			throw new IllegalStateException("No camera is defined.");
		}
		traceRectangle( //
				camera.transformX(x), //
				camera.transformY(y), //
				camera.transformWidth(width), //
				camera.transformHeight(height) //
		);
	}

	public void drawRectangle(int x, int y, int width, int height) {
		g.fillRect(x, y, width, height);
	}

	public void drawRectangle(float x, float y, float width, float height) {
		if (camera == null) {
			throw new IllegalStateException("No camera is defined.");
		}
		drawRectangle( //
				camera.transformX(x), //
				camera.transformY(y), //
				camera.transformWidth(width), //
				camera.transformHeight(height) //
		);
	}

	public void traceOval(int x, int y, int width, int height) {
		g.drawOval(x, y, width, height);
	}

	public void traceOval(float x, float y, float width, float height) {
		if (camera == null) {
			throw new IllegalStateException("No camera is defined.");
		}
		traceOval( //
				camera.transformX(x), //
				camera.transformY(y), //
				camera.transformWidth(width), //
				camera.transformHeight(height) //
		);
	}

	public void drawOval(int x, int y, int width, int height) {
		g.fillOval(x, y, width, height);
	}

	public void drawOval(float x, float y, float width, float height) {
		if (camera == null) {
			throw new IllegalStateException("No camera is defined.");
		}
		drawOval( //
				camera.transformX(x), //
				camera.transformY(y), //
				camera.transformWidth(width), //
				camera.transformHeight(height) //
		);
	}

	public void drawImage(BufferedImage image, int x, int y, int width, int height) {
		g.drawImage(image, x, y, width, height, null);
	}

	public void drawImage(BufferedImage image, float x, float y, float width, float height) {
		if (camera == null) {
			throw new IllegalStateException("No camera is defined.");
		}
		drawImage(image, //
				camera.transformX(x), //
				camera.transformY(y), //
				camera.transformWidth(width), //
				camera.transformHeight(height) //
		);
	}

	public void drawText(String text, int x, int y, boolean centerHorizontally, boolean centerVertically) {
		if (centerHorizontally) {
			x -= getTextWidth(text) / 2;
		}
		if (centerVertically) {
			y += getTextAscent() / 2;
		}
		g.drawString(text, x, y);
	}

	public void drawText(String text, float x, float y, boolean centerHorizontally, boolean centerVertically) {
		drawText(text, camera.transformX(x), camera.transformY(y), centerHorizontally, centerVertically);
	}

	public void drawText(String text, int x, int y) {
		drawText(text, x, y, false, false);
	}

	public void drawText(String text, float x, float y) {
		drawText(text, x, y, false, false);
	}

	public Graphics2D acquireGraphics() {
		return g;
	}

	public Font getFittingFont(float height) {
		return getFittingFont(g.getFont(), camera.transformHeight(height));
	}

	public Font getFittingFont(int height) {
		return getFittingFont(g.getFont(), height);
	}

	public Font getFittingFont(Font font, float height) {
		return getFittingFont(font, camera.transformHeight(height));
	}

	public Font getFittingFont(Font font, int height) {
		FontKey key = new FontKey(font.getName(), height);
		if (derivedFonts.containsKey(key)) {
			return derivedFonts.get(key);
		}
		float size = 0;
		do {
			font = font.deriveFont(size++);
		} while (getTextAscent(font) < height);
		derivedFonts.put(key, font);
		return font;
	}

	public int getTextHeight(Font font) {
		return g.getFontMetrics(font).getHeight();
	}

	public float getTextHeightPercent(Font font) {
		return (float) getTextHeight(font) / getHeight();
	}

	public int getTextHeight() {
		return getTextHeight(g.getFont());
	}

	public float getTextHeightPercent() {
		return (float) getTextHeight() / getHeight();
	}

	public int getTextAscent(Font font) {
		return g.getFontMetrics(font).getAscent();
	}

	public float getTextAscentPercent(Font font) {
		return (float) getTextAscent(font) / getHeight();
	}

	public int getTextAscent() {
		return getTextAscent(g.getFont());
	}

	public float getTextAscentPercent() {
		return (float) getTextAscent() / getHeight();
	}

	public int getTextWidth(String text, Font font) {
		return g.getFontMetrics(font).stringWidth(text);
	}

	public float getTextWidthPercent(String text, Font font) {
		return (float) getTextWidth(text, font) / getWidth();
	}

	public int getTextWidth(String text) {
		return getTextWidth(text, g.getFont());
	}

	public float getTextWidthPercent(String text) {
		return (float) getTextWidth(text) / getWidth();
	}

	public boolean isFullScreen() {
		JFrame window = (JFrame) SwingUtilities.getWindowAncestor(canvas);
		return window.isUndecorated();
	}

	public void setFullScreen(boolean fullscreen) {
		Window window = (Window) SwingUtilities.getWindowAncestor(canvas);
		window.dispose();
		if (fullscreen) {
			priorToFullscreen = window.getSize();
			window.setUndecorated(true);
			window.setBounds(window.getBestGraphicDevice().getDefaultConfiguration().getBounds());
		} else {
			window.setUndecorated(false);
			window.setSize(priorToFullscreen);
			window.center();
		}
		window.setVisible(true);
	}

	public void setAspectRatio(float aspectRatio) {
		this.aspectRatio = aspectRatio;
	}

	public float getAspectRatio() {
		return aspectRatio;
	}

	public static interface RendererResizeCallback {

		public abstract void invoke(int width, int height);

	}

	private static class FontKey {

		private String name;
		private int height;

		public FontKey(String name, int height) {
			this.name = name;
			this.height = height;
		}

		@Override
		public boolean equals(Object object) {
			if (!(object instanceof FontKey)) {
				return false;
			}
			FontKey key = (FontKey) object;
			return key.name.equals(name) && key.height == height;
		}

		@Override
		public int hashCode() {
			return 0;
		}

	}

}
