package net.cdahmedeh.muraledesktop.controller;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.yaml.snakeyaml.Yaml;

import lombok.Getter;
import net.cdahmedeh.muraledesktop.domain.Configuration;

public class ConfigurationController {
	private static final String MURALE_CONFIG_LOCATION = System.getProperty("user.home") + "/.murale";
	
	private static final String CONFIGURATION_FILE = "config.yaml";
	private static final String PROVIDERS_CONFIG_FILE = "providers.yaml";
	private static final String SCRIPTS_FOLDER = "scripts";
	private static final String WALLPAPERS_FOLDER = "wallpapers";

	@Getter
	private Configuration configuration;
	
	public ConfigurationController() {
		loadConfiguration();
	}
	
	public File getConfigurationFolder() {
		File configFolder = new File(MURALE_CONFIG_LOCATION);
		configFolder.mkdirs();
		return configFolder;
	}
	
	public File getConfigurationFile() {
		return new File(getConfigurationFolder(), CONFIGURATION_FILE);
	}
	
	public File getProvidersConfigFile() {
		File folder = new File(getConfigurationFolder(), PROVIDERS_CONFIG_FILE);
		folder.mkdirs();
		return folder;
	}
	
	public File getScriptsFolder() {
		File folder = new File(getConfigurationFolder(), SCRIPTS_FOLDER);
		folder.mkdirs();
		return folder;
	}
	
	public File getWallpapersFolder() {
		File folder = new File(getConfigurationFolder(), WALLPAPERS_FOLDER);
		folder.mkdirs();
		return folder;
	}
	
	public void loadConfiguration() {
		try {
			Yaml yaml = new Yaml();
			String text = FileUtils.readFileToString(getConfigurationFile(), Charset.defaultCharset());
			configuration = yaml.loadAs(text, Configuration.class);
		} catch (IOException e) {
			configuration = new Configuration();
		}
	}
	
	public void saveConfiguration() {
		try {
			Yaml yaml = new Yaml();
			String text = yaml.dump(configuration);
			FileUtils.writeStringToFile(getConfigurationFile(), text, Charset.defaultCharset());
		} catch (IOException e) {
		}
	}

	public void setChangeInterval(int minutes) {
		configuration.setChangeInterval(minutes);
		saveConfiguration();
	}

	public void setMatchDisplayResolution(boolean match) {
		configuration.setMatchNativeResolution(match);
		saveConfiguration();
	}
}
