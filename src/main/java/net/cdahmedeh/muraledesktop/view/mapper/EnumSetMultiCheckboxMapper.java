package net.cdahmedeh.muraledesktop.view.mapper;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class EnumSetMultiCheckboxMapper {
	public static <T extends Enum<T>> void map(Map<String, T> values, Set<T> set, JPanel checkBoxContainer) {
		checkBoxContainer.setOpaque(false);
		
		// fill panel with possible values as checkboxes
		for (Entry<String, T> value : values.entrySet()) {
			JCheckBox checkBox = new JCheckBox(value.getKey());
			checkBoxContainer.add(checkBox);
			
			// pre-select values
			if (set.contains(value.getValue())) {
				checkBox.setSelected(true);
			}
			
			// listen for changes and propagate to set
			checkBox.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					if (checkBox.isSelected()) {
						set.add(value.getValue());
					} else {
						set.remove(value.getValue());
					}
				}
			});
		}
	}
}
