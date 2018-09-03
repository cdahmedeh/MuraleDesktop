package net.cdahmedeh.muraledesktop.controller;

import com.google.common.eventbus.EventBus;

import lombok.Getter;
import net.cdahmedeh.muraledesktop.event.IntervalTickEvent;

public class TimerController {
	// Dependencies
	private final EventBus eventBus;
	private final ConfigurationController configurationController;
	private final WallpaperController wallpaperController;

	@Getter
	private int secondsLeft = -1;
	
	public TimerController(EventBus eventBus, ConfigurationController configurationController, WallpaperController wallpaperController) {
		this.eventBus = eventBus;
		this.configurationController = configurationController;
		this.wallpaperController = wallpaperController;
		
		resetTimer();
		startTimer();
	}
	
	public void resetTimer() {
		secondsLeft = 60 * configurationController.getConfiguration().getChangeInterval();
	}
	
	public void forceNext() {
		Thread thread = new Thread(() -> {
			secondsLeft = -1;
			wallpaperController.nextWallpaper();
			resetTimer();
		});
		
		thread.start();
	}
	
	private void startTimer() {
		Thread thread = new Thread(() -> {
			while(true) {
				sleep(1000);
				
				if (secondsLeft >= 0) {
					secondsLeft--;
					eventBus.post(new IntervalTickEvent());
				}
				
				if (secondsLeft == 0) {
					wallpaperController.nextWallpaper();
					resetTimer();
				}
			}
		});
		
		thread.start();
	}
	
	private void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}
}
