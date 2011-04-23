package com.gamevm.plugin.editors;

import org.eclipse.ui.texteditor.AbstractTextEditor;

public class BinaryEditor extends AbstractTextEditor {
	
	public BinaryEditor() {
		setDocumentProvider(new BinaryDocumentProvider());
	}
	
}
