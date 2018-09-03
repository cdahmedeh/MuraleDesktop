package net.cdahmedeh.muraledesktop.view.mapper;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Map;
import java.util.function.Consumer;

import javax.swing.JComboBox;

public class EnumDropdownMapper {
	public static <T extends Enum<T>> void map(Map<String, T> values, T initial, Consumer<T> setter, JComboBox<String> comboBox) {
		// fill drop-down with possible values
		values.keySet().forEach(s -> comboBox.addItem(s));
		
		// listen for changes and propagate to setter
		comboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				Object item = e.getItem();
				setter.accept(values.get(item));
			}
		});
		
		// pre-select value
		comboBox.setSelectedItem(initial);
	}
}
