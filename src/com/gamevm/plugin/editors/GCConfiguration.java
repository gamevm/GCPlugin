package com.gamevm.plugin.editors;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.ITokenScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

public class GCConfiguration extends SourceViewerConfiguration {
	private XMLDoubleClickStrategy doubleClickStrategy;
	private SyntaxHighlightingTokenScanner syntaxScanner;
	private TextAttributeManager attributeManager;

	public GCConfiguration(TextAttributeManager attributeManager) {
		this.attributeManager = attributeManager;
	}

	@Override
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return new String[] { IDocument.DEFAULT_CONTENT_TYPE, GCPartitionScanner.GC_SINGLELINE_COMMENT,
				GCPartitionScanner.GC_MULTILINE_COMMENT, GCPartitionScanner.GC_SOURCE };
	}

	@Override
	public ITextDoubleClickStrategy getDoubleClickStrategy(ISourceViewer sourceViewer, String contentType) {
		if (doubleClickStrategy == null)
			doubleClickStrategy = new XMLDoubleClickStrategy();
		return doubleClickStrategy;
	}

	protected ITokenScanner getSyntaxScanner() {
		if (syntaxScanner == null) {
			syntaxScanner = new SyntaxHighlightingTokenScanner(attributeManager);
		}
		return syntaxScanner;
	}

	@Override
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();

		DefaultDamagerRepairer dr = new DefaultDamagerRepairer(getSyntaxScanner());
		reconciler.setDamager(dr, GCPartitionScanner.GC_SOURCE);
		reconciler.setRepairer(dr, GCPartitionScanner.GC_SOURCE);

		NonRuleBasedDamagerRepairer ndr = new NonRuleBasedDamagerRepairer(
				attributeManager.getAttribute(TextAttributeManager.ATTRIBUTE_SINGLELINE_COMMENT));
		reconciler.setDamager(ndr, GCPartitionScanner.GC_SINGLELINE_COMMENT);
		reconciler.setRepairer(ndr, GCPartitionScanner.GC_SINGLELINE_COMMENT);

		ndr = new NonRuleBasedDamagerRepairer(
				attributeManager.getAttribute(TextAttributeManager.ATTRIBUTE_MULTILINE_COMMENT));
		reconciler.setDamager(ndr, GCPartitionScanner.GC_MULTILINE_COMMENT);
		reconciler.setRepairer(ndr, GCPartitionScanner.GC_MULTILINE_COMMENT);

		return reconciler;
	}

}