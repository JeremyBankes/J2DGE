package com.sineshore.j2dge.v1_0.state.component;

import com.sineshore.j2dge.v1_0.Renderer;

public class Label extends Component {

	protected Alignment textAlignmentHorizontal;
	protected Alignment textAlignmentVertical;

	protected String text;

	protected float fontSize = 0.5f;

	public Label(String text, float xPercent, float yPercent, float widthPercent, float heightPercent) {
		super(xPercent, yPercent, widthPercent, heightPercent);
		this.text = text;
		textAlignmentHorizontal = textAlignmentVertical = Alignment.CENTER;
	}

	@Override
	protected void renderContent(Renderer renderer) {
		super.renderContent(renderer);
		renderer.setColor(foregroundColor);
		renderer.setFont(renderer.getFittingFont(getFont(), (int) (fontSize * height)));
		int x = 0, y = 0;
		if (textAlignmentHorizontal == Alignment.CENTER) {
			x = this.x + this.width / 2 - renderer.getTextWidth(text) / 2;
		} else if (textAlignmentHorizontal == Alignment.LEFT) {
			x = this.x;
		} else if (textAlignmentHorizontal == Alignment.RIGHT) {
			x = this.x + this.width - renderer.getTextWidth(text);
		}

		if (textAlignmentVertical == Alignment.CENTER) {
			y = this.y + this.height / 2 + renderer.getTextAscent() / 2;
		} else if (textAlignmentVertical == Alignment.TOP) {
			y = this.y + renderer.getTextAscent();
		}
		renderer.drawText(text, x, y);
	}

	public float getFontSize() {
		return fontSize;
	}

	public void setFontSize(float fontSize) {
		this.fontSize = fontSize;
	}

	public Alignment getTextAlignmentHorizontal() {
		return textAlignmentHorizontal;
	}

	public void setTextAlignmentHorizontal(Alignment textAlignmentHorizontal) {
		this.textAlignmentHorizontal = textAlignmentHorizontal;
	}

	public Alignment getTextAlignmentVertical() {
		return textAlignmentVertical;
	}

	public void setTextAlignmentVertical(Alignment textAlignmentVertical) {
		this.textAlignmentVertical = textAlignmentVertical;
	}

}
