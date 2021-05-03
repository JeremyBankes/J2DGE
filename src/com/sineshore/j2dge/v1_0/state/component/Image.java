package com.sineshore.j2dge.v1_0.state.component;

import java.awt.image.BufferedImage;

public class Image extends Component {

	public Image(BufferedImage image, float xPercent, float yPercent, float widthPercent, float heightPercent) {
		super(xPercent, yPercent, widthPercent, heightPercent);
		setBackgroundImage(image);
	}

}
