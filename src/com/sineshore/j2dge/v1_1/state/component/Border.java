package com.sineshore.j2dge.v1_1.state.component;

import java.awt.Color;

import com.sineshore.j2dge.v1_1.Renderer;

public class Border {

    private Color color;
    private int thickness;

    public Border() {
	color = Color.WHITE;
	thickness = 1;
    }

    public Border(Color color, int thickness) {
	this.color = color;
	this.thickness = thickness;
    }

    public void render(Component component, Renderer renderer) {
	int x = component.getX();
	int y = component.getY();
	int width = component.getWidth();
	int height = component.getHeight();
	renderer.setColor(color);
	/* TOP */ renderer.drawRectangle(x, y, width, thickness);
	/* BOT */ renderer.drawRectangle(x, y + height - thickness, width, thickness);
	/* RGT */ renderer.drawRectangle(x, y + thickness, thickness, height - thickness - thickness);
	/* LFT */ renderer.drawRectangle(x + width - thickness, y + thickness, thickness, height - thickness - thickness);
    }

}
