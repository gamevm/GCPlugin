package com.gamevm.plugin.wizards.project;

import java.io.IOException;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import com.gamevm.compiler.assembly.runtime.RuntimeClasses;
import com.gamevm.plugin.builder.GCProjectNature;

public class NewGCProjectWizard extends Wizard implements INewWizard {

	private NewProjectMainPage mainPage;
	
	private IWorkbench workbench;
	private IStructuredSelection selection;
	
	@Override
	public void addPages() {
		super.addPages();
		
		mainPage = new NewProjectMainPage();
		mainPage.setTitle("New Project");
		mainPage.setDescription("Create a GC project");
		
		addPage(mainPage);
	}



	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;
		this.selection = selection;
		
	}



	@Override
	public boolean performFinish() {
		try {
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IProject newProject = root.getProject(mainPage.getProjectName());
			newProject.create(null);
			newProject.open(null);
			IProjectDescription description = newProject.getDescription();
			description.setNatureIds(new String[] { GCProjectNature.NATURE_ID });
			newProject.setDescription(description, null);
			
			IFolder folder = newProject.getFolder("bin");
			folder.create(true, true, null);
			try {
				RuntimeClasses.generateRuntimeLibrary(folder.getLocation().toFile());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}

		
		return true;
	}


}
