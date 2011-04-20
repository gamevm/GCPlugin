package com.gamevm.plugin.launch.tabs;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.gamevm.plugin.builder.GCProjectNature;
import com.gamevm.plugin.utils.ProjectSelectionDialog;
import com.gamevm.plugin.utils.SourceFileSelectionDialog;

public class MainTab extends AbstractLaunchConfigurationTab {

	Text projectName;
	Text mainClassName;
	IProject project;
	IFile mainClass;
	
	private Text addBrowseGroup(Composite parent, String label, SelectionAdapter browseAction) {
		Group projectPanel = new Group(parent, SWT.SHADOW_NONE);
		projectPanel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		GridLayout glayout = new GridLayout(2, false);
		projectPanel.setLayout(glayout);
		projectPanel.setText(label);
		Text text = new Text(projectPanel, SWT.BORDER | SWT.SINGLE);
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		Button browseProject = new Button(projectPanel, SWT.PUSH);
		browseProject.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		browseProject.setText("Browse...");
		browseProject.addSelectionListener(browseAction);
		return text;
	}
	
	@Override
	public void createControl(Composite parent) {
		Composite c = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout(1, false);
		c.setLayout(layout);
		projectName = addBrowseGroup(c, "Project:", new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ProjectSelectionDialog dialog = new ProjectSelectionDialog(getShell(), GCProjectNature.NATURE_ID);		
				if (dialog.open() == ProjectSelectionDialog.OK) {
					IProject p = dialog.getSelection();
					projectName.setText(p.getName());
					project = p;
				}

			}
		});
		mainClassName = addBrowseGroup(c, "Main Class:", new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				SourceFileSelectionDialog dialog = new SourceFileSelectionDialog(getShell(), GCProjectNature.NATURE_ID, "gc");		
				if (dialog.open() == SourceFileSelectionDialog.OK) {
					IFile f = (IFile)dialog.getResult()[0];
					mainClassName.setText(f.getName());
					mainClass = f;
				}

			}
		});
		
		
		setControl(c);
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute("project", "");
		configuration.setAttribute("mainClass", "");
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			projectName.setText(configuration.getAttribute("project", ""));
			mainClassName.setText(configuration.getAttribute("mainClass", ""));
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute("project", projectName.getText());
		configuration.setAttribute("mainClass", mainClass.getLocation().toPortableString());
	}

	@Override
	public String getName() {
		return "Main";
	}

}
