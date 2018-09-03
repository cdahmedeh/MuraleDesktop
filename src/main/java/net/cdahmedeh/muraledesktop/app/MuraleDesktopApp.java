package net.cdahmedeh.muraledesktop.app;

import com.alee.laf.WebLookAndFeel;
import com.google.common.eventbus.EventBus;

import net.cdahmedeh.muraledesktop.controller.AppController;
import net.cdahmedeh.muraledesktop.controller.ConfigurationController;
import net.cdahmedeh.muraledesktop.controller.ProviderController;
import net.cdahmedeh.muraledesktop.controller.ScriptController;
import net.cdahmedeh.muraledesktop.controller.TimerController;
import net.cdahmedeh.muraledesktop.controller.WallpaperController;
import net.cdahmedeh.muraledesktop.view.MainWindowView;

public class MuraleDesktopApp {
	public static void main(String[] args) {
		MuraleDesktopApp app = new MuraleDesktopApp();
		app.start();
	}

	private void start() {
		// Event Handling
		EventBus eventBus = new EventBus();
		
		// Controllers
		AppController appController = new AppController();
		ConfigurationController configurationController = new ConfigurationController();
		ScriptController scriptController = new ScriptController(configurationController);
		ProviderController providerController = new ProviderController(eventBus, configurationController);
		WallpaperController wallpaperController = new WallpaperController(eventBus, configurationController, scriptController, providerController);
		TimerController timerController = new TimerController(eventBus, configurationController, wallpaperController);

		// Setup Look and Feel
		WebLookAndFeel.install();
		
		// Start Main Window
		MainWindowView mainWindowView = new MainWindowView(eventBus, appController, configurationController, providerController, wallpaperController, timerController);
		mainWindowView.display();
		
		// Fill Providers List
		providerController.loadProviders();
	}
}