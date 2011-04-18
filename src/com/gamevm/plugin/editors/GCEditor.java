package com.gamevm.plugin.editors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.editors.text.TextEditor;

public class GCEditor extends TextEditor {

	private TextAttributeManager textAttributeManager;

	public GCEditor() {
		super();
		textAttributeManager = new TextAttributeManager(new ColorManager(), new Font(Display.getCurrent(), "Courier New", 12, SWT.NORMAL));
		setSourceViewerConfiguration(new GCConfiguration(textAttributeManager));
		setDocumentProvider(new GCDocumentProvider());
	}
	
	public void dispose() {
		textAttributeManager.dispose();
		super.dispose();
	}

}
