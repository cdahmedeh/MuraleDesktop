package net.cdahmedeh.muraledesktop.helper;

import static java.awt.Color.BLACK;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import static org.imgscalr.Scalr.resize;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr.Mode;

import lombok.val;

public class ImageLoader {
	public static final Image getBlankImage(int width, int height) {
		BufferedImage image = new BufferedImage(width, height, TYPE_INT_RGB);
		
		Graphics g = image.getGraphics();
		g.setColor(BLACK);
		g.fillRect(0, 0, width, height);
		
		return image;
	}
	
	public static final Image loadImage(File file, int width, int height) {
		try {
			val image = ImageIO.read(file);
			return resize(image, Mode.FIT_TO_HEIGHT, width, height);
		} catch (IOException e) {
		}
		return getBlankImage(width, height);
	}
}
