package com.gamevm.plugin.editors;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenSource;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.ITokenScanner;

import com.gamevm.compiler.parser.GCASTLexer;

public abstract class AbstractTokenScanner implements ITokenScanner {

	private TokenSource tokenSource;
	private Token lastToken;
	
	protected abstract IToken convert(Token token);
	
	@Override
	public void setRange(IDocument document, int offset, int length) {
		try {
			CharStream input = new ANTLRStringStream(document.get(offset, length));
			tokenSource = new GCASTLexer(input);
		} catch (BadLocationException e) {
		}
	}

	@Override
	public IToken nextToken() {
		lastToken = tokenSource.nextToken();
		return convert(lastToken);
	}

	@Override
	public int getTokenOffset() {
		return lastToken.getTokenIndex();
	}

	@Override
	public int getTokenLength() {
		return lastToken.getText().length();
	}

}
