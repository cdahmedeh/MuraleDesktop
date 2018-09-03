package net.cdahmedeh.muraledesktop.controller;

import static net.cdahmedeh.muraledesktop.app.AppConstants.PROJECT_NAME;
import static net.cdahmedeh.muraledesktop.app.AppConstants.RELEASE_VERSION;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.common.eventbus.EventBus;

import lombok.Getter;
import lombok.val;
import net.cdahmedeh.murale.domain.Resolution;
import net.cdahmedeh.murale.domain.Wallpaper;
import net.cdahmedeh.murale.provider.service.ProviderHandler;
import net.cdahmedeh.muraledesktop.event.NewWallpaperRequestedEvent;
import net.cdahmedeh.muraledesktop.event.WallpaperSetEvent;
import net.cdahmedeh.muraledesktop.helper.ScreenInfo;

public class WallpaperController {
	private static final String USER_AGENT = PROJECT_NAME + "/" + RELEASE_VERSION;
	
	// Dependencies
	private final EventBus eventBus;
	private final ConfigurationController configurationController;
	private final ScriptController scriptController;
	private final ProviderController providerController;

	@Getter
	private Wallpaper current;
	
	public WallpaperController(EventBus eventBus, ConfigurationController configurationController, ScriptController scriptController, ProviderController providerController) {
		this.eventBus = eventBus;
		this.configurationController = configurationController;
		this.scriptController = scriptController;
		this.providerController = providerController;
	}
	
	public void nextWallpaper() {
		eventBus.post(new NewWallpaperRequestedEvent());
		
		Resolution resolution = null;
		if (configurationController.getConfiguration().isMatchNativeResolution()) {
			resolution = ScreenInfo.getMaxResolution();
		}
		
		List<Wallpaper> wallpapers = ProviderHandler.getWallpapers(providerController.getProviders(), 1, resolution);
		
		if (wallpapers.size() >= 1) {
			Wallpaper wallpaper = wallpapers.get(0);
			setDesktopWallpaper(wallpaper);
			current = wallpaper;
			eventBus.post(new WallpaperSetEvent());
		}
	}
	
	public void setDesktopWallpaper(Wallpaper wallpaper) {		
		File file = downloadWallpaper(wallpaper);
		scriptController.setWindowsDesktopWallpaper(file.getAbsolutePath());
	}
	
	private File downloadWallpaper(Wallpaper wallpaper) {
		String filename = wallpaper.getId() + ".img";
		File file = new File(configurationController.getWallpapersFolder(), filename);
		
		try {
			val client = HttpClientBuilder.create().setUserAgent(USER_AGENT).build();
			
			HttpGet request = new HttpGet(wallpaper.getUrl());
			request.setHeader("Referer", wallpaper.getOrigin());
			val response = client.execute(request);
			
			FileUtils.copyInputStreamToFile(response.getEntity().getContent(), file);
		} catch (IOException e) {
		}
		
		return file;
	}
}
