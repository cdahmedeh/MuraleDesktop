package net.cdahmedeh.muraledesktop.view.fragment;

import static java.awt.BorderLayout.CENTER;
import static java.lang.String.format;

import java.awt.BorderLayout;

import com.alee.extended.statusbar.WebStatusBar;
import com.alee.laf.progressbar.WebProgressBar;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import net.cdahmedeh.muraledesktop.controller.ConfigurationController;
import net.cdahmedeh.muraledesktop.controller.TimerController;
import net.cdahmedeh.muraledesktop.event.IntervalTickEvent;
import net.cdahmedeh.muraledesktop.event.NewWallpaperRequestedEvent;

public class StatusBarFragment extends WebStatusBar {
	private static final long serialVersionUID = 5729409653672154177L;
	
	// Dependencies
	private final ConfigurationController configurationController;
	private final TimerController timerController;

	// Components
	private WebProgressBar timerBar;

	public StatusBarFragment(EventBus eventBus, ConfigurationController configurationController, TimerController timerController) {
		eventBus.register(this);
		this.configurationController = configurationController;
		this.timerController = timerController;
		
		setLayout(new BorderLayout());
		constructLayout();
	}

	private void constructLayout() {
		timerBar = new WebProgressBar();
		add(timerBar, CENTER);
		timerBar.setStringPainted(true);
		timerBar.setString("Welcome to Murale!");
		timerBar.setIndeterminate(true);
	}
	
	@Subscribe
	public void tick(IntervalTickEvent event) {
		timerBar.setIndeterminate(false);
		timerBar.setMaximum(60 * configurationController.getConfiguration().getChangeInterval());
		
		int secondsLeft = timerController.getSecondsLeft();
		timerBar.setValue(Math.max(0, secondsLeft));
		
		int minutes = secondsLeft/60;
		int seconds = secondsLeft%60;
		
		timerBar.setString(format("%d:%02d left until next wallpaper change", minutes, seconds));
	}
	
	@Subscribe
	public void wait(NewWallpaperRequestedEvent event) {
		timerBar.setIndeterminate(true);
		timerBar.setString("Retrieving new wallpaper...");
	}
}
