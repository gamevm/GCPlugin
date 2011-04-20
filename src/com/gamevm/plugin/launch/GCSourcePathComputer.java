package com.gamevm.plugin.launch;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.sourcelookup.ISourceContainer;
import org.eclipse.debug.core.sourcelookup.ISourcePathComputer;
import org.eclipse.debug.core.sourcelookup.containers.DirectorySourceContainer;

public class GCSourcePathComputer implements ISourcePathComputer {

	public static final String ID = "com.gamevm.plugin.launch.sourcePathComputer";
	
	@Override
	public ISourceContainer[] computeSourceContainers(ILaunchConfiguration configuration, IProgressMonitor monitor)
			throws CoreException {
		return new ISourceContainer[] {
				new DirectorySourceContainer(new Path("."), true)
		};
	}

	@Override
	public String getId() {
		return ID;
	}

}
