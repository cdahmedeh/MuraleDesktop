package net.cdahmedeh.muraledesktop.view.fragment;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.EAST;
import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.SOUTH;
import static net.cdahmedeh.muraledesktop.app.AppConstants.PROVIDER_LIST_BACKGROUND;
import static net.cdahmedeh.muraledesktop.helper.IconLoader.getIcon;

import java.awt.BorderLayout;

import com.alee.extended.panel.GroupPanel;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;

import net.cdahmedeh.murale.provider.Provider;
import net.cdahmedeh.muraledesktop.controller.ProviderController;

public class ProviderFragment extends WebPanel {
	private static final long serialVersionUID = -1298640734569315134L;
	
	// Dependencies
	private final ProviderController providerController;
	private final Provider provider;

	public ProviderFragment(ProviderController providerController, Provider provider) {
		this.providerController = providerController;
		this.provider = provider;
		
		setOpaque(false);
		setBackground(PROVIDER_LIST_BACKGROUND);
		
		constructLayout();
	}

	private void constructLayout() {
		add(new TitleFragment(), CENTER);
		add(new ButtonFragment(), EAST);
	}
	
	public class TitleFragment extends WebPanel {
		private static final long serialVersionUID = -7679870336479345002L;
		
		public TitleFragment() {			
			setOpaque(false);
			
			setLayout(new BorderLayout());
			
			WebLabel title = new WebLabel();
			add(title, NORTH);
			title.setText(provider.getName());
			title.setIcon(getIcon(provider.getName()));
			title.setBoldFont(true);
			title.setMargin(5);
			
			WebLabel search = new WebLabel();
			add(search, SOUTH);
			search.setText(provider.getSearch());
			search.setMargin(0, 5, 5, 5);
		}
	}
	
	public class ButtonFragment extends GroupPanel {
		private static final long serialVersionUID = 4615524935123759696L;

		public ButtonFragment() {
			setOpaque(false);
			
			WebButton settingsButton = createButton();
			add(settingsButton);
			settingsButton.setIcon(getIcon("provider-settings"));
			settingsButton.addActionListener(e -> providerController.launchEditProvider(provider));
			
			WebButton deleteButton = createButton();
			add(deleteButton);
			deleteButton.setIcon(getIcon("delete-provider"));
			deleteButton.addActionListener(e -> providerController.deleteProvider(provider));
		}

		private WebButton createButton() {
			WebButton button = new WebButton();
            button.setRolloverDecoratedOnly(true);
            button.setDrawFocus(true);
            button.setRound(2);
            return button;
		}
	}
}
