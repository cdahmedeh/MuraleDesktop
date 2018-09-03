package net.cdahmedeh.muraledesktop.view;

import static javax.swing.BorderFactory.createLineBorder;
import static net.cdahmedeh.muraledesktop.app.AppConstants.PROVIDER_LIST_BACKGROUND;
import static net.cdahmedeh.muraledesktop.app.AppConstants.PROVIDER_LIST_BORDER;

import java.util.List;

import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import net.cdahmedeh.murale.provider.Provider;
import net.cdahmedeh.muraledesktop.controller.ProviderController;
import net.cdahmedeh.muraledesktop.custom.ColouredComponentPanel;
import net.cdahmedeh.muraledesktop.event.ProvidersUpdatedEvent;
import net.cdahmedeh.muraledesktop.view.fragment.ProviderFragment;

public class ProviderListView extends WebPanel {
	private static final long serialVersionUID = -3594568789889060115L;
	
	// Dependencies
	private final ProviderController providerController;

	// Components
	private ColouredComponentPanel providerListPane;

	public ProviderListView(EventBus eventBus, ProviderController providerController) {
		this.providerController = providerController;
		eventBus.register(this);
		
		constructLayout();
	}

	private void constructLayout() {
		providerListPane = new ColouredComponentPanel();
		WebScrollPane scrollPane = new WebScrollPane(providerListPane);
		add(scrollPane);
		
		providerListPane.setReorderingAllowed(true);
		
		setOpaque(true);
		scrollPane.setOpaque(true);
		providerListPane.setBackground(PROVIDER_LIST_BACKGROUND);
		scrollPane.setBackground(PROVIDER_LIST_BACKGROUND);
		scrollPane.setRound(0);
		scrollPane.setBorder(createLineBorder(PROVIDER_LIST_BORDER, 1));
	}
	
	@Subscribe
	private void fillProviderList(ProvidersUpdatedEvent event) {
		clearProviderList();
		
		List<Provider> providers = providerController.getProviders();
		for (Provider provider : providers) {
			ProviderFragment providerFragment = new ProviderFragment(providerController, provider);
			providerListPane.addElement(providerFragment);
		}
		
		revalidate();
	}

	private void clearProviderList() {
		while (providerListPane.getElementCount() != 0) {
			providerListPane.removeElement(0);
		}
		
		providerListPane.invalidate();
		providerListPane.repaint();
	}
}
