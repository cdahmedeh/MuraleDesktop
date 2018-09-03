package net.cdahmedeh.muraledesktop.view.menu;

import static net.cdahmedeh.muraledesktop.helper.IconLoader.getIcon;

import com.alee.laf.menu.WebMenuItem;
import com.alee.laf.menu.WebPopupMenu;

import net.cdahmedeh.murale.provider.impl.wallhaven.WallhavenProvider;
import net.cdahmedeh.muraledesktop.controller.ProviderController;

public class ProviderPopupMenu extends WebPopupMenu {
	private static final long serialVersionUID = 3130336441506272917L;

	// Dependencies
	private final ProviderController providerController;
	
	public ProviderPopupMenu(ProviderController providerController) {
		this.providerController = providerController;
		
		WebMenuItem wallhavenItem = new WebMenuItem("Wallhaven", getIcon("wallhaven.cc"));
		add(wallhavenItem);
		wallhavenItem.addActionListener(e -> providerController.createProvider(WallhavenProvider.class));
	}
}