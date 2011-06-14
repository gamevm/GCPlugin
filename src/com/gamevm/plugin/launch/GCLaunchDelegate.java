package com.gamevm.plugin.launch;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunch;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.model.ILaunchConfigurationDelegate;
import org.eclipse.debug.core.model.IProcess;

import com.gamevm.plugin.GCPlugin;

public class GCLaunchDelegate implements ILaunchConfigurationDelegate {

	@Override
	public void launch(ILaunchConfiguration configuration, String mode, ILaunch launch, IProgressMonitor monitor)
			throws CoreException {
		try {
			
			String libFolder = new File(GCPlugin.getPluginFolder(), "lib").getCanonicalPath();
			String projectName = configuration.getAttribute(GCLaunchAttributes.ATTRIBUTE_PROJECT_NAME, "");
			String mainClassName = configuration.getAttribute(GCLaunchAttributes.ATTRIBUTE_MAINCLASS_NAME, "");
			
			System.out.format("LibFolder: %s\nProjectName: %s\n", libFolder, projectName);
			
			File projectFolder = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName).getLocation().toFile();
			
			Process p = DebugPlugin.exec(new String[] { 
					"java", 
					"-cp", libFolder + "/gccompiler.jar;" + libFolder + "/utils.jar", 
					"com.gamevm.execution.VirtualMachine", 
					"-cp", "bin", 
					mainClassName }, 
				projectFolder);
			IProcess process = DebugPlugin.newProcess(launch, p, "gvm");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
