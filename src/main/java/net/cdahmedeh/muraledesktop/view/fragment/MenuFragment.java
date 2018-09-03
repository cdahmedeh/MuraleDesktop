package net.cdahmedeh.muraledesktop.view.fragment;

import static com.alee.laf.menu.MenuBarStyle.standalone;
import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.EAST;
import static net.cdahmedeh.muraledesktop.helper.IconLoader.getIcon;

import com.alee.extended.button.WebSplitButton;
import com.alee.extended.panel.GroupPanel;
import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuBar;
import com.alee.laf.panel.WebPanel;

import net.cdahmedeh.muraledesktop.controller.AppController;
import net.cdahmedeh.muraledesktop.controller.ConfigurationController;
import net.cdahmedeh.muraledesktop.controller.ProviderController;
import net.cdahmedeh.muraledesktop.controller.TimerController;
import net.cdahmedeh.muraledesktop.view.menu.FileMenu;
import net.cdahmedeh.muraledesktop.view.menu.ProviderPopupMenu;
import net.cdahmedeh.muraledesktop.view.menu.WallpaperMenu;

public class MenuFragment extends WebPanel {
	private static final long serialVersionUID = 1101407476997688096L;

	// Dependencies
	private final AppController appController;
	private final ConfigurationController configurationController;
	private final ProviderController providerController;
	private final TimerController timerController;

	public MenuFragment(AppController appController, ConfigurationController configurationController, ProviderController providerController, TimerController timerController) {
		this.appController = appController;
		this.configurationController = configurationController;
		this.providerController = providerController;
		this.timerController = timerController;
		
		constructLayout();
	}

	private void constructLayout() {
		add(new MenuBarFragment(), CENTER);
		add(new ButtonFragment(), EAST);
	}
	
	public class MenuBarFragment extends WebMenuBar {
		private static final long serialVersionUID = -6260587341676127608L;
		
		public MenuBarFragment() {
			setMenuBarStyle(standalone);
			setUndecorated(true);
			
			WebMenu fileMenu = new FileMenu(appController);
			add(fileMenu);
			
			WebMenu wallpaperMenu = new WallpaperMenu(configurationController, timerController);
			add(wallpaperMenu);
		}
	}
	
	public class ButtonFragment extends GroupPanel {
		private static final long serialVersionUID = -9021709731174522528L;
		
		public ButtonFragment() {
			WebSplitButton addProviderButton = new WebSplitButton("Add Provider", getIcon("add-provider"));
			add(addProviderButton);
			addProviderButton.setAlwaysShowMenu(true);
			addProviderButton.setPopupMenu(new ProviderPopupMenu(providerController));
		}
	}
}
