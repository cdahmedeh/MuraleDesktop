package net.cdahmedeh.muraledesktop.view.fragment;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.WEST;
import static java.awt.Color.WHITE;
import static net.cdahmedeh.muraledesktop.helper.ImageLoader.loadImage;

import java.io.File;

import com.alee.extended.image.WebDecoratedImage;
import com.alee.extended.label.WebLinkLabel;
import com.alee.extended.progress.WebProgressOverlay;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import net.cdahmedeh.murale.domain.Wallpaper;
import net.cdahmedeh.muraledesktop.controller.ConfigurationController;
import net.cdahmedeh.muraledesktop.controller.WallpaperController;
import net.cdahmedeh.muraledesktop.event.NewWallpaperRequestedEvent;
import net.cdahmedeh.muraledesktop.event.WallpaperSetEvent;
import net.cdahmedeh.muraledesktop.helper.ImageLoader;
import net.miginfocom.swing.MigLayout;

public class CurrentWallpaperFragment extends WebPanel {
	private static final long serialVersionUID = -17178719464686913L;
	
	// Dependencies
	private final ConfigurationController configurationController;
	private final WallpaperController wallpaperController;

	// Components
	private WebPanel panel;
	private WebDecoratedImage image;
	private WebProgressOverlay overlay;
	private WebLabel providerLabel;
	private WebLabel titleLabel;
	private WebLabel authorLabel;
	private WebLinkLabel originLabel;

	public CurrentWallpaperFragment(EventBus eventBus, ConfigurationController configurationController, WallpaperController wallpaperController) {
		this.configurationController = configurationController;
		this.wallpaperController = wallpaperController;
		eventBus.register(this);
		
		panel = new WebPanel();
		add(panel, CENTER);
		panel.setUndecorated(false);
		panel.setMargin(5);
		
		constructLayout(panel);
	}

	private void constructLayout(WebPanel panel) {
		image = new WebDecoratedImage();
		image.setRound(0);
		image.setImage(ImageLoader.getBlankImage(142, 90));

		overlay = new WebProgressOverlay(image);
		panel.add(overlay, WEST);
		overlay.setConsumeEvents(false);
		overlay.setProgressColor(WHITE);

		InfoFragment infoFragment = new InfoFragment();
		panel.add(infoFragment, CENTER);
	}
	
	@Subscribe
	private void startLoadingThrobber(NewWallpaperRequestedEvent event) {
		overlay.setShowLoad(true);
	}
	
	@Subscribe
	private void showCurrentWallpaper(WallpaperSetEvent event) {
		overlay.setShowLoad(false);
		
		Wallpaper wallpaper = wallpaperController.getCurrent();
		
		File file = new File(configurationController.getWallpapersFolder(), wallpaper.getId() + ".img");
		image.setImage(loadImage(file, 142, 90));
		
		providerLabel.setText(wallpaper.getProvider().getName());
		titleLabel.setText(wallpaper.getTitle());
		authorLabel.setText(wallpaper.getAuthor());
		originLabel.setText(wallpaper.getOrigin());
		
		refreshLayout();
	}

	private void refreshLayout() {
		image.revalidate();
		panel.revalidate();
		revalidate();
	}
	
	public class InfoFragment extends WebPanel {
		private static final long serialVersionUID = -3230934311582922296L;

		public InfoFragment() {
			setOpaque(false);
			setLayout(new MigLayout());

			WebLabel providerHeader = new WebLabel("Provider:");
			add(providerHeader);
			providerHeader.setBoldFont(true);

			providerLabel = new WebLabel("None");
			add(providerLabel, "wrap");

			WebLabel titleHeader = new WebLabel("Title:");
			add(titleHeader);
			titleHeader.setBoldFont(true);

			titleLabel = new WebLabel("None");
			add(titleLabel, "wrap");

			WebLabel authorHeader = new WebLabel("Author:");
			add(authorHeader);
			authorHeader.setBoldFont(true);

			authorLabel = new WebLabel("None");
			add(authorLabel, "wrap");

			WebLabel originHeader = new WebLabel("Origin:");
			add(originHeader);
			originHeader.setBoldFont(true);

			originLabel = new WebLinkLabel("None");
			add(originLabel);
		}
	}
}
