package net.cdahmedeh.muraledesktop.view;

import static com.google.common.collect.Maps.newLinkedHashMap;

import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.alee.laf.text.WebTextField;
import com.ivkos.wallhaven4j.models.misc.enums.Category;
import com.ivkos.wallhaven4j.models.misc.enums.Order;
import com.ivkos.wallhaven4j.models.misc.enums.Purity;
import com.ivkos.wallhaven4j.models.misc.enums.Sorting;

import net.cdahmedeh.murale.provider.impl.wallhaven.WallhavenProvider;
import net.cdahmedeh.muraledesktop.controller.ProviderController;
import net.cdahmedeh.muraledesktop.view.mapper.EnumDropdownMapper;
import net.cdahmedeh.muraledesktop.view.mapper.EnumSetMultiCheckboxMapper;
import net.cdahmedeh.muraledesktop.view.mapper.ListTextFieldMapper;

public class WallhavenProviderEditorView extends ProviderEditorView<WallhavenProvider> {
	private static final long serialVersionUID = -5169316638391671738L;

	public WallhavenProviderEditorView(ProviderController providerController, WallhavenProvider provider) {
		super(providerController, provider);
	}
	
	@Override
	protected void fillForm(JPanel form) {
		form.add(new JLabel("Search Keywords"));
		WebTextField keywordsField = new WebTextField();
		form.add(keywordsField, "wrap");
		keywordsField.setPreferredWidth(200);
		ListTextFieldMapper.map(provider.getKeywords(), keywordsField);
		
		form.add(new JLabel("Categories"));
		JPanel categoriesCheckBoxContainer = new JPanel();
		form.add(categoriesCheckBoxContainer, "wrap");
		Map<String, Category> categories = newLinkedHashMap();
		categories.put("General", Category.GENERAL);
		categories.put("People", Category.PEOPLE);
		categories.put("Anime", Category.ANIME);
		EnumSetMultiCheckboxMapper.map(categories, provider.getCategories(), categoriesCheckBoxContainer);
		
		form.add(new JLabel("Purity"));
		JPanel puritiesCheckBoxContainer = new JPanel();
		form.add(puritiesCheckBoxContainer, "wrap");
		Map<String, Purity> purities = newLinkedHashMap();
		purities.put("SFW", Purity.SFW);
		purities.put("Sketchy", Purity.SKETCHY);
		EnumSetMultiCheckboxMapper.map(purities, provider.getPurities(), puritiesCheckBoxContainer);
		
		form.add(new JLabel("Sorting"));
		JComboBox<String> sortingComboBox = new JComboBox<>();
		form.add(sortingComboBox, "wrap");
		Map<String, Sorting> sortings = newLinkedHashMap();
		sortings.put("Random", Sorting.RANDOM);
		sortings.put("Date Added", Sorting.DATE_ADDED);
		sortings.put("Relevance", Sorting.RELEVANCE);
		sortings.put("Favourited", Sorting.FAVORITES);
		sortings.put("Views", Sorting.VIEWS);
		EnumDropdownMapper.map(sortings, provider.getSorting(), provider::setSorting, sortingComboBox);
		
		form.add(new JLabel("Sorting order"));
		JComboBox<String> orderComboBox = new JComboBox<>();
		form.add(orderComboBox, "wrap");
		Map<String, Order> orders = newLinkedHashMap();
		orders.put("Descending", Order.DESC);
		orders.put("Ascending", Order.ASC);
		EnumDropdownMapper.map(orders, provider.getOrder(), provider::setOrder, orderComboBox);
	}

}
