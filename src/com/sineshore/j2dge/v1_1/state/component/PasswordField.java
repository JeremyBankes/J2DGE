package com.sineshore.j2dge.v1_1.state.component;

public class PasswordField extends TextField {

	public PasswordField(float xPercent, float yPercent, float widthPercent, float heightPercent) {
		super("", xPercent, yPercent, widthPercent, heightPercent);
	}

	@Override
	protected String getDisplayText() {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0, len = super.getDisplayText().length(); i < len; i++) {
			buffer.append('•');
		}
		return buffer.toString();
	}

}
