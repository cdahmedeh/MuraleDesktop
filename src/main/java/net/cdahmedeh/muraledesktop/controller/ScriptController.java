package net.cdahmedeh.muraledesktop.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;

import com.google.common.io.Resources;

public class ScriptController {
	private static final String WINDOWS_SCRIPT_ORIGIN = "scripts/muralewincommand/";

	private static final String WINDOWS_SCRIPT_EXE = "MuraleWinCommand.exe";
	private static final String WINDOWS_SCRIPT_FILENAMES[] = new String[] 
			{WINDOWS_SCRIPT_EXE, WINDOWS_SCRIPT_EXE + ".config", "CommandLine.dll", "CommandLine.xml"};

	// Dependencies
	private final ConfigurationController configurationController;
	
	public ScriptController(ConfigurationController configurationController) {
		this.configurationController = configurationController;
		
		copyWindowsScript();
	}
	
	public void setWindowsDesktopWallpaper(String path) {
		try {
			String exec = configurationController.getScriptsFolder().getAbsolutePath() + "/" + WINDOWS_SCRIPT_EXE;
			ProcessBuilder builder = new ProcessBuilder(exec, "-f", path);
			builder.start();
		} catch (IOException e) {
		}
	}
	
	private void copyWindowsScript() {
		if (verifyWindowsScript()) {
			return;
		}
		
		try {
			for (String filename : WINDOWS_SCRIPT_FILENAMES) {
				URL localUrl = Resources.getResource(WINDOWS_SCRIPT_ORIGIN + filename);
				File destination = new File(configurationController.getScriptsFolder(), filename);
				
				FileUtils.copyURLToFile(localUrl, destination);
			}
		} catch (IOException e) {
		}
	}
	
	private boolean verifyWindowsScript() {
		for (String filename : WINDOWS_SCRIPT_FILENAMES) {
			File location = new File(configurationController.getScriptsFolder(), filename);
			if (!location.exists()) {
				return false;
			}
		}
		return true;
	}
}