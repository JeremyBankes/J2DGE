package com.sineshore.j2dge.v1_0.state.component;

import java.util.LinkedList;

import com.sineshore.j2dge.v1_0.KeyInput.KeyInputEvent;
import com.sineshore.j2dge.v1_0.MouseInput.MouseInputEvent;
import com.sineshore.j2dge.v1_0.Renderer;

public class Container extends Component {

	private LinkedList<Component> children;

	public Container(float xPercent, float yPercent, float widthPercent, float heightPercent) {
		super(xPercent, yPercent, widthPercent, heightPercent);
		children = new LinkedList<>();
	}

	@Override
	public void mouseEvent(MouseInputEvent event) {
		children.descendingIterator().forEachRemaining(child -> {
			child.mouseEvent(event);
		});
		super.mouseEvent(event);
	}

	@Override
	public void keyEvent(KeyInputEvent event) {
		super.keyEvent(event);
		for (Component child : children) {
			if (!event.isConsumed()) {
				child.keyEvent(event);
			}
		}
	}

	@Override
	public void tick() {
		super.tick();
		children.forEach(child -> child.tick());
	}

	@Override
	public void render(Renderer renderer) {
		super.render(renderer);
		children.forEach(child -> child.render(renderer));
	}

	@Override
	public void validate() {
		super.validate();
		children.forEach(child -> child.validate());
	}

	public void add(Component component) {
		component.setParent(this);
		children.add(component);
		component.validate();
	}

	public LinkedList<Component> getChildren() {
		return children;
	}

}
