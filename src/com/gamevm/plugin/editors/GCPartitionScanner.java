package com.gamevm.plugin.editors;

import org.eclipse.jface.text.rules.*;

public class GCPartitionScanner extends RuleBasedPartitionScanner {
	
	public final static String GC_SINGLELINE_COMMENT = "__gc_sl_comment";
	public final static String GC_MULTILINE_COMMENT = "__gc_ml_comment";
	public final static String GC_SOURCE = "__gc_source";

	public GCPartitionScanner() {

		IToken singleLineComment = new Token(GC_SINGLELINE_COMMENT);
		IToken multiLineComment = new Token(GC_MULTILINE_COMMENT);
		IToken source = new Token(GC_SOURCE);

		IPredicateRule[] rules = new IPredicateRule[3];

		rules[0] = new SingleLineRule("//", "*", singleLineComment);
		rules[1] = new MultiLineRule("/*", "*/", multiLineComment);
		rules[2] = new SourceRule(source);

		setPredicateRules(rules);
	}
}
