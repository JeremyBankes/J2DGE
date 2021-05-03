package com.sineshore.j2dge.v1_1;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

import com.sineshore.j2dge.v1_1.state.StateManager;

public abstract class Game {

	protected String name;
	protected String version;

	protected int tps = 60;
	protected int maxFps = 0;

	protected int currentFps;
	protected int currentTps;

	protected Thread thread;
	protected boolean running;
	protected int age;

	protected final Window window;
	protected final Canvas canvas;
	protected final Renderer renderer;
	protected final MouseInput mouseInput;
	protected final KeyInput keyInput;
	protected final ControlsManager controlsManager;
	protected final StateManager stateManager;

	protected final Random random = new Random();

	public Game(String name, String version, int width, int height) {
		this.name = name;
		this.version = version;
		canvas = new Canvas();
		canvas.setSize(new Dimension(width, height));
		renderer = new Renderer(canvas);
		window = new Window(name, width, height);
		window.getContentPane().add(canvas);
		mouseInput = new MouseInput(canvas);
		keyInput = new KeyInput(canvas);
		controlsManager = new ControlsManager(keyInput);
		stateManager = new StateManager(this);
		renderer.setAspectRatio((float) width / height);
		window.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		window.getContentPane().addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent event) {
				Dimension size = window.getContentPane().getSize();

				if (size.height * renderer.getAspectRatio() > size.width) {
					size.height = Math.round(size.width / renderer.getAspectRatio());
				} else if (size.width / renderer.getAspectRatio() > size.height) {
					size.width = Math.round(size.height * renderer.getAspectRatio());
				}

				canvas.setPreferredSize(size);
				canvas.setSize(size);
				event.getComponent().validate();
			}
		});

		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				super.windowClosing(event);
				stop();
			}
		});

		Toolkit.getDefaultToolkit().setDynamicLayout(false);
	}

	public final void start() {
		if (!running) {
			running = true;
			thread = new Thread(this::run, name);
			thread.start();
		}
	}

	public final void stop() {
		if (running) {
			running = false;
		}
	}

	protected void init() {
		window.pack();
		Insets insets = window.getInsets();
		int minWidth = 100 + insets.left + insets.right;
		int minHeight = (int) (100 / renderer.getAspectRatio() + insets.top + insets.bottom);
		window.setMinimumSize(new Dimension(minWidth, minHeight));
		window.center();
		window.setVisible(true);
	}

	protected void term() {
		window.dispose();
	}

	private void run() {
		try {
			init();
			long currentTime;
			final long second = 1000000000;
			int ticks = 0;
			int frames = 0;
			long tickTime = second / getTps();
			long tickTimer = System.nanoTime();
			long secondTimer = System.nanoTime();
			long frameTime = getMaxFps() == 0 ? 0 : second / getMaxFps();
			long frameTimer = System.nanoTime();
			while (running) {
				currentTime = System.nanoTime();
				if (currentTime - tickTimer > tickTime) {
					if (currentTime - tickTimer > tickTime * 2) {
						tickTimer = currentTime;
					}
					tickTimer += tickTime;
					ticks++;
					age++;
					tick();
				}
				if (currentTime - frameTimer > frameTime) {
					frameTimer += frameTime;
					frames++;
					renderer.startRender();
					render(renderer);
					renderer.endRender();
				}
				if (currentTime - secondTimer > second) {
					secondTimer += second;
					currentFps = frames;
					currentTps = ticks;
					frames = ticks = 0;
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		term();
	}

	public void tick() {
		stateManager.getCurrentState().tick();
	}

	public void render(Renderer renderer) {
		stateManager.getCurrentState().render(renderer);
	}

	public Random getRandom() {
		return random;
	}

	public int getTps() {
		return tps;
	}

	public void setTps(int tps) {
		this.tps = tps;
	}

	public int getMaxFps() {
		return maxFps;
	}

	public void setMaxFps(int maxFps) {
		this.maxFps = maxFps;
	}

	public int getCurrentFps() {
		return currentFps;
	}

	public int getCurrentTps() {
		return currentTps;
	}

	public String getName() {
		return name;
	}

	public String getVersion() {
		return version;
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public Renderer getRenderer() {
		return renderer;
	}

	public MouseInput getMouseInput() {
		return mouseInput;
	}

	public KeyInput getKeyInput() {
		return keyInput;
	}

	public ControlsManager getControls() {
		return controlsManager;
	}

	public StateManager getStateManager() {
		return stateManager;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void setCurrentFps(int currentFps) {
		this.currentFps = currentFps;
	}

	public void setCurrentTps(int currentTps) {
		this.currentTps = currentTps;
	}

	public int getAge() {
		return age;
	}

}
