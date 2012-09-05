package com.gamevm.plugin.editors;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;

public class TextAttributeManager {
	
	public static final int ATTRIBUTE_DEFAULT = 0;
	public static final int ATTRIBUTE_KEYWORD = 1;
	public static final int ATTRIBUTE_SINGLELINE_COMMENT = 2;
	public static final int ATTRIBUTE_MULTILINE_COMMENT = 3;
	
	private ColorManager colorManager;
	private TextAttribute[] attributes;
	
	private Font defaultFont;
	private Font boldFont;
	
	public TextAttributeManager(ColorManager colorManager, Font defaultFont) {
		this.colorManager = colorManager;
		this.defaultFont = defaultFont;
		
		FontData[] boldFontData = defaultFont.getFontData();
		for (FontData d : boldFontData) {
			d.setStyle(SWT.BOLD);
		}
		boldFont = new Font(defaultFont.getDevice(), boldFontData);
		
		
		Color textColor = colorManager.getColor(GCColorConstants.TEXT);
		Color keywordColor = colorManager.getColor(GCColorConstants.KEYWORD);
		Color defaultBackground = colorManager.getColor(GCColorConstants.DEFAULT_BACKGROUND);
		
		attributes = new TextAttribute[] {
				new TextAttribute(textColor),
				new TextAttribute(keywordColor, null, 0, boldFont),
				new TextAttribute(textColor),
				new TextAttribute(textColor)
		};
	}
	
	
	public TextAttribute getAttribute(int type) {
		return attributes[type];
	}
	
	public void dispose() {
		colorManager.dispose();
		defaultFont.dispose();
		boldFont.dispose();
	}
}
