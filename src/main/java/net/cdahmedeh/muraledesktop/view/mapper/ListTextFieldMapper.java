package net.cdahmedeh.muraledesktop.view.mapper;

import static java.util.Arrays.asList;
import static net.cdahmedeh.murale.util.CollectionUtil.join;

import java.beans.PropertyChangeEvent;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

public class ListTextFieldMapper {
	public static void map(List<String> list, JTextField textField) {
		// display value initially
		textField.setText(join(list, " "));
		
		// listen for changes and propagate to list
		addChangeListener(textField, new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				String text = textField.getText();
				
				list.clear();
				list.addAll(asList(text.split(" ")));
			}
		});
	}
	
	public static void addChangeListener(JTextComponent text, ChangeListener changeListener) {
		DocumentListener dl = new DocumentListener() {
			private int lastChange = 0;
			private int lastNotifiedChange = 0;

			@Override
			public void insertUpdate(DocumentEvent e) {
				changedUpdate(e);
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				changedUpdate(e);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				lastChange++;
				SwingUtilities.invokeLater(() -> {
					if (lastNotifiedChange != lastChange) {
						lastNotifiedChange = lastChange;
						changeListener.stateChanged(new ChangeEvent(text));
					}
				});
			}
		};
		
		text.addPropertyChangeListener("document", (PropertyChangeEvent e) -> {
			Document d1 = (Document) e.getOldValue();
			Document d2 = (Document) e.getNewValue();
			if (d1 != null) {
				d1.removeDocumentListener(dl);
			}
			if (d2 != null) {
				d2.addDocumentListener(dl);
			}
			dl.changedUpdate(null);
		});
		
		Document d = text.getDocument();
		if (d != null) {
			d.addDocumentListener(dl);
		}
	}
}
