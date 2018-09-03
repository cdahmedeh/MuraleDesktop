package net.cdahmedeh.muraledesktop.view.menu;

import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuItem;
import com.alee.managers.hotkey.Hotkey;

import net.cdahmedeh.muraledesktop.controller.AppController;
import net.cdahmedeh.muraledesktop.helper.IconLoader;

public class FileMenu extends WebMenu {
	private static final long serialVersionUID = -5397882800613761725L;
	
	// Dependencies
	private AppController appController;
	
	public FileMenu(AppController appController) {
		super("File", IconLoader.getIcon("file"));
		this.appController = appController;
		
		WebMenuItem exitItem = new WebMenuItem("Exit", IconLoader.getIcon("exit"));
		add(exitItem);
		exitItem.setAccelerator(Hotkey.CTRL_Q);
		exitItem.addActionListener(e -> appController.exit());
	}
}
