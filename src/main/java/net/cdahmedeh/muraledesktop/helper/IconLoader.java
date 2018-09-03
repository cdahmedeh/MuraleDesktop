package net.cdahmedeh.muraledesktop.helper;

import static com.google.common.io.Resources.getResource;

import java.net.URL;

import javax.swing.ImageIcon;

public class IconLoader {
	public static ImageIcon getIcon(String iconName) {
		URL url = getResource("icons/" + iconName + ".png");
		return new ImageIcon(url);
	}
}
