package com.sineshore.j2dge.v1_1.audio;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audio {

	public static Clip getClip(String soundPath) {
		try {
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(Audio.class.getResourceAsStream(soundPath));
			Clip clip = AudioSystem.getClip();
			clip.open(inputStream);
			return clip;
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void playClip(Clip clip, boolean wait) {
		try {
			clip.setFramePosition(0);
			clip.start();
			if (wait) {
				while (clip.getMicrosecondPosition() < clip.getMicrosecondLength()) {
					Thread.sleep(100);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
