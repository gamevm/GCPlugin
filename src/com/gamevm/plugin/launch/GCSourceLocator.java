package com.gamevm.plugin.launch;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.sourcelookup.AbstractSourceLookupDirector;
import org.eclipse.debug.core.sourcelookup.AbstractSourceLookupParticipant;
import org.eclipse.debug.core.sourcelookup.ISourceLookupParticipant;

import com.gamevm.compiler.assembly.ClassDeclaration;

public class GCSourceLocator extends AbstractSourceLookupDirector {
	
	private class LookupParticipant extends AbstractSourceLookupParticipant {

		@Override
		public String getSourceName(Object object) throws CoreException {
			ClassDeclaration classDeclaration = (ClassDeclaration)object;
			return classDeclaration.getSimpleName() + ".gc";
		}
		
	}

	@Override
	public void initializeParticipants() {
		addParticipants(new ISourceLookupParticipant[] { new LookupParticipant() });
	}

}
