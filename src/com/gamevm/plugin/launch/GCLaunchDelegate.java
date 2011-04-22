package com.gamevm.plugin.launch;

import java.io.File;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.debug.core.model.IProcess;

public class GCLaunchDelegate implements ILaunchConfigurationDelegate {

	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
			throws CoreException {
		Process p = DebugPlugin.exec(new String[] { "java", "-cp", "lib/gccompiler.jar;lib/utils.jar", "com.gamevm.execution.VirtualMachine", configuration.getAttribute("mainClassName", "") }, new File("."));
		IProcess process = DebugPlugin.newProcess(launch, p, "gvm");
	}

}
