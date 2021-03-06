package com.gamevm.plugin.launch;

import org.eclipse.core.resources.IFile;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.internal.core.LaunchConfiguration;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorPart;

public class GCLaunchShortcut implements ILaunchShortcut {

	@Override
	public void launch(ISelection selection, String mode) {
		if (selection instanceof StructuredSelection) {
			StructuredSelection s = (StructuredSelection)selection;
			IFile f = (IFile)s.getFirstElement();
			
		}
		
		DebugPlugin.getDefault().getLaunchManager();
	}

	@Override
	public void launch(IEditorPart editor, String mode) {
		
	}

}
