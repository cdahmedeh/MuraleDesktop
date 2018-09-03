package net.cdahmedeh.muraledesktop.view;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.SOUTH;
import static javax.swing.BoxLayout.X_AXIS;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;

import com.alee.laf.button.WebButton;
import com.alee.laf.panel.WebPanel;

import net.cdahmedeh.murale.provider.Provider;
import net.cdahmedeh.muraledesktop.controller.ProviderController;
import net.cdahmedeh.muraledesktop.helper.IconLoader;
import net.miginfocom.swing.MigLayout;

public abstract class ProviderEditorView<P extends Provider> extends JDialog {
	private static final long serialVersionUID = -75649338032177982L;
	
	// Dependencies
	private final ProviderController providerController;
	protected final P provider;

	public ProviderEditorView(ProviderController providerController, P provider) {
		this.providerController = providerController;
		this.provider = provider;

		setModal(true);
		setTitle("Editing Provider - " + provider.getName());
		setSize(400, 300);
		setLocationRelativeTo(null);
		
		setLayout(new BorderLayout());
		
		WebPanel formPanel = new WebPanel();
		add(formPanel, CENTER);
		formPanel.setMargin(2);
		
		WebPanel form = new WebPanel(new MigLayout());
		formPanel.add(form, CENTER);
		fillForm(form);
		form.setUndecorated(false);
		form.setRound(2);
		form.setMargin(2);
		
		ButtonFragment buttonFragment = new ButtonFragment();
		add(buttonFragment, SOUTH);
		buttonFragment.setMargin(0, 2, 2, 2);
	}
	
	public void display() {
		setVisible(true);
	}
	
	protected abstract void fillForm(JPanel form);
	
	public class ButtonFragment extends WebPanel {
		private static final long serialVersionUID = 1234773111774073641L;
		
		public ButtonFragment() {
			setLayout(new BoxLayout(ButtonFragment.this, X_AXIS));
			
			add(Box.createHorizontalGlue());
			
			WebButton cancelButton = new WebButton("Cancel", IconLoader.getIcon("cancel"));
			add(cancelButton);
			cancelButton.addActionListener(e -> cancel());
			cancelButton.setMargin(3);
			
			WebButton saveButton = new WebButton("OK", IconLoader.getIcon("save-provider"));
			add(saveButton);
			saveButton.addActionListener(e -> save());
			saveButton.setMargin(3);
		}
	}
	
	private void cancel() {
		setVisible(false);
		dispose();
	}
	
	private void save() {
		providerController.saveProvider(provider);
		
		setVisible(false);
		dispose();
	}
}
