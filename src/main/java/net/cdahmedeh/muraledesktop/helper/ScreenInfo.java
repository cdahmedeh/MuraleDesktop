package net.cdahmedeh.muraledesktop.helper;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import net.cdahmedeh.murale.domain.Resolution;

public class ScreenInfo {
	public static Resolution getMaxResolution() {
		int maxWidth = 0, maxHeight = 0;
		
		GraphicsDevice[] screenDevices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
		
		for (GraphicsDevice graphicsDevice : screenDevices) {
			maxWidth = Math.max(maxWidth, graphicsDevice.getDisplayMode().getWidth());
			maxHeight = Math.max(maxHeight, graphicsDevice.getDisplayMode().getHeight());
		}
		
		return Resolution.of(maxWidth, maxHeight);
	}
}
