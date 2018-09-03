package net.cdahmedeh.muraledesktop.controller;

import com.alee.laf.optionpane.WebOptionPane;

public class AppController {
	public void exit() {
		int confirm = WebOptionPane.showConfirmDialog(
				null, 
				"Are you sure you would like to exit Murale? Your wallpaper won't be updated periodically anymore if you leave Murale...", 
				"Exit Confirmation", 
				WebOptionPane.YES_NO_OPTION);
		
		if (confirm == WebOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}
}
