package net.cdahmedeh.muraledesktop.view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.event.AncestorEvent;

import com.alee.laf.menu.WebCheckBoxMenuItem;
import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuItem;
import com.alee.laf.menu.WebRadioButtonMenuItem;
import com.alee.utils.swing.AncestorAdapter;

import net.cdahmedeh.muraledesktop.controller.ConfigurationController;
import net.cdahmedeh.muraledesktop.controller.TimerController;
import net.cdahmedeh.muraledesktop.helper.IconLoader;

public class WallpaperMenu extends WebMenu {
	private static final long serialVersionUID = -5397882800613761725L;
	
	// Dependencies
	private final ConfigurationController configurationController;
	private final TimerController timerController;
	
	public WallpaperMenu(ConfigurationController configurationController, TimerController timerController) {
		super("Wallpaper", IconLoader.getIcon("wallpaper"));
		this.configurationController = configurationController;
		this.timerController = timerController;
		
		WebMenuItem nextWallpaperItem = new WebMenuItem("Next Wallpaper", IconLoader.getIcon("next-wallpaper"));
		add(nextWallpaperItem);
		nextWallpaperItem.addActionListener(e -> timerController.forceNext());
		
		addSeparator();
		
		WebMenu intervalMenu = new WebMenu("Change Interval", IconLoader.getIcon("change-interval"));
		add(intervalMenu);
		
		ButtonGroup intervalGroup = new ButtonGroup();
		createInterval(intervalMenu, intervalGroup, 1, "1 minute");
		createInterval(intervalMenu, intervalGroup, 2, "2 minutes");
		createInterval(intervalMenu, intervalGroup, 3, "3 minutes");
		createInterval(intervalMenu, intervalGroup, 5, "5 minutes");
		createInterval(intervalMenu, intervalGroup, 10, "10 minutes");
		createInterval(intervalMenu, intervalGroup, 15, "15 minutes");
		createInterval(intervalMenu, intervalGroup, 20, "20 minutes");
		createInterval(intervalMenu, intervalGroup, 30, "30 minutes");
		createInterval(intervalMenu, intervalGroup, 60, "1 hour");
		createInterval(intervalMenu, intervalGroup, 120, "2 hours");
		createInterval(intervalMenu, intervalGroup, 180, "3 hours");
		
		WebCheckBoxMenuItem matchDisplayItem = new WebCheckBoxMenuItem("Match Display Resolution");
		add(matchDisplayItem);
		matchDisplayItem.setSelected(configurationController.getConfiguration().isMatchNativeResolution());
		matchDisplayItem.addActionListener(e -> configurationController.setMatchDisplayResolution(matchDisplayItem.isSelected()));
	}

	private void createInterval(WebMenu menu, ButtonGroup group, int minutes, String label) {
		WebRadioButtonMenuItem item = new WebRadioButtonMenuItem(label);
		menu.add(item);
		group.add(item);
		
		item.addAncestorListener(new AncestorAdapter() {
			@Override
			public void ancestorAdded(AncestorEvent event) {
				if (minutes == configurationController.getConfiguration().getChangeInterval()) {
					item.setSelected(true);
				}
			}
		});
		
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				configurationController.setChangeInterval(minutes);
				timerController.resetTimer();
				
				item.setSelected(true);
			}
		});
	}
}
