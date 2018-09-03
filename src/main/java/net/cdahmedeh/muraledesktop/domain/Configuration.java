package net.cdahmedeh.muraledesktop.domain;

import lombok.Getter;
import lombok.Setter;

public class Configuration {
	@Getter @Setter
	private int changeInterval = 30;
	
	@Getter @Setter
	private boolean matchNativeResolution = true;
}
