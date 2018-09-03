package net.cdahmedeh.muraledesktop.controller;

import static com.google.common.collect.Lists.newArrayList;
import static java.nio.charset.Charset.defaultCharset;
import static org.apache.commons.io.FileUtils.readFileToString;

import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.google.common.eventbus.EventBus;

import lombok.Getter;
import lombok.val;
import net.cdahmedeh.murale.provider.Provider;
import net.cdahmedeh.murale.provider.impl.wallhaven.WallhavenProvider;
import net.cdahmedeh.murale.provider.parser.ProviderParser;
import net.cdahmedeh.muraledesktop.event.ProvidersUpdatedEvent;
import net.cdahmedeh.muraledesktop.view.WallhavenProviderEditorView;

public class ProviderController {
	// Dependencies
	private final EventBus eventBus;
	private final ConfigurationController configurationController;
	
	@Getter
	private List<Provider> providers = newArrayList();
	
	public ProviderController(EventBus eventBus, ConfigurationController configurationController) {
		this.eventBus = eventBus;
		this.configurationController = configurationController;
	}
	
	public void loadProviders() {		
		try {
			String configText = readFileToString(configurationController.getProvidersConfigFile(), defaultCharset());
			providers = ProviderParser.fromConfigurationText(configText);
		} catch (IOException e) {
		}
		
		eventBus.post(new ProvidersUpdatedEvent());
	}

	public void saveProviders() {		
		try {
			String configText = ProviderParser.toConfigurationText(providers);
			FileUtils.write(configurationController.getProvidersConfigFile(), configText, defaultCharset());;
		} catch (IOException e) {
		}
		
		eventBus.post(new ProvidersUpdatedEvent());
	}
	
	public void saveProvider(Provider provider) {
		if (!providers.contains(provider)) {
			providers.add(provider);
		}
		
		saveProviders();
	}
	
	public void deleteProvider(Provider provider) {
		providers.remove(provider);
		saveProviders();
	}
	
	public void createProvider(Class<? extends Provider> type) {
		try {
			Provider provider = type.newInstance();
			launchEditProvider(provider);
		} catch (InstantiationException | IllegalAccessException e) {
		}		
	}
	
	public void launchEditProvider(Provider provider) {
		if (provider instanceof WallhavenProvider) {
			val providerEditorView = new WallhavenProviderEditorView(this, (WallhavenProvider) provider);
			providerEditorView.display();
		}
	}
}
