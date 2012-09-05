package com.gamevm.plugin.editors;


import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

import com.gamevm.compiler.parser.TokenClassifier;

public class SyntaxHighlightingTokenScanner extends AbstractTokenScanner {

	private TokenClassifier tokenClassifier;
	private TextAttributeManager attributeManager;
	
	private IToken keyword;
	private IToken defaultText;
	
	public SyntaxHighlightingTokenScanner(TextAttributeManager attributeManager) {
		tokenClassifier = new TokenClassifier();
		this.attributeManager = attributeManager;
		
		defaultText = new Token(attributeManager.getAttribute(TextAttributeManager.ATTRIBUTE_DEFAULT));
		keyword = new Token(attributeManager.getAttribute(TextAttributeManager.ATTRIBUTE_KEYWORD));
	}
	
	@Override
	protected IToken convert(org.antlr.runtime.Token token) {
		if (tokenClassifier.isKeyword(token))
			return keyword;
		else
			return defaultText;
	}

}
