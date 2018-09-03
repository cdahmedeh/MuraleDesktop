package net.cdahmedeh.muraledesktop.view;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;

import java.awt.BorderLayout;

import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebFrame;
import com.google.common.eventbus.EventBus;

import net.cdahmedeh.muraledesktop.controller.AppController;
import net.cdahmedeh.muraledesktop.controller.ConfigurationController;
import net.cdahmedeh.muraledesktop.controller.ProviderController;
import net.cdahmedeh.muraledesktop.controller.TimerController;
import net.cdahmedeh.muraledesktop.controller.WallpaperController;
import net.cdahmedeh.muraledesktop.view.fragment.CurrentWallpaperFragment;
import net.cdahmedeh.muraledesktop.view.fragment.MenuFragment;
import net.cdahmedeh.muraledesktop.view.fragment.StatusBarFragment;

public class MainWindowView extends WebFrame {
	private static final long serialVersionUID = 6537746723286364916L;
	
	// Dependencies
	private final EventBus eventBus;
	private final AppController appController;
	private final ConfigurationController configurationController;
	private final ProviderController providerController;
	private final WallpaperController wallpaperController;
	private final TimerController timerController;

	public MainWindowView(EventBus eventBus, AppController appController, ConfigurationController configurationController, ProviderController providerController, WallpaperController wallpaperController, TimerController timerController) {
		this.eventBus = eventBus;
		this.appController = appController;
		this.configurationController = configurationController;
		this.providerController = providerController;
		this.wallpaperController = wallpaperController;
		this.timerController = timerController;
	
		setTitle("Murale");

		setSize(640, 540);
		setLocationRelativeTo(null);

		setLayout(new BorderLayout());
		
		constructLayout();
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	
	public void display() {
		setVisible(true);
	}

	private void constructLayout() {
		MenuFragment menuFragment = new MenuFragment(appController, configurationController, providerController, timerController);
		add(menuFragment, NORTH);
		menuFragment.setMargin(5);
		
		WebPanel mainPanel = new WebPanel();
		add(mainPanel, CENTER);
		
		ProviderListView providerListView = new ProviderListView(eventBus, providerController);
		mainPanel.add(providerListView, CENTER);
		providerListView.setMargin(0, 5, 4, 5);
		
		CurrentWallpaperFragment currentWallpaperFragment = new CurrentWallpaperFragment(eventBus, configurationController, wallpaperController);
		mainPanel.add(currentWallpaperFragment, BorderLayout.SOUTH);
		currentWallpaperFragment.setMargin(0, 3, 4, 3);
		
		StatusBarFragment statusBarFragment = new StatusBarFragment(eventBus, configurationController, timerController);
		add(statusBarFragment, BorderLayout.SOUTH);
	}
}
