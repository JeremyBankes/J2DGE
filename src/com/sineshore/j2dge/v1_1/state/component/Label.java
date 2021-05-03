package com.sineshore.j2dge.v1_1.state.component;

import com.sineshore.j2dge.v1_1.graphics.Renderer;

public class Label extends Component {

	protected Alignment textAlignmentHorizontal;
	protected Alignment textAlignmentVertical;

	protected String text;

	public Label(String text, float xPercent, float yPercent, float widthPercent, float heightPercent) {
		super(xPercent, yPercent, widthPercent, heightPercent);
		this.text = text;
		textAlignmentHorizontal = textAlignmentVertical = Alignment.CENTER;
	}

	@Override
	protected void renderContent(Renderer renderer) {
		super.renderContent(renderer);
		renderer.setColor(getForegroundColor());
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
			yOffset += renderer.getTextHeight();
		}
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Alignment getTextAlignmentHorizontal() {
		return textAlignmentHorizontal;
	}

	public void setHorizontalAlignmentText(Alignment textAlignmentHorizontal) {
		this.textAlignmentHorizontal = textAlignmentHorizontal;
	}

	public Alignment getTextAlignmentVertical() {
		return textAlignmentVertical;
	}

	public void setVerticalAlignmentText(Alignment textAlignmentVertical) {
		this.textAlignmentVertical = textAlignmentVertical;
	}

}
