package com.gamevm.plugin.wizards.project;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class NewProjectMainPage extends WizardPage {

	private Text projectName;

	protected NewProjectMainPage() {
		super("New Project");
	}

	@Override
	public void createControl(Composite parent) {
		Composite c = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout(2, false);
		c.setLayout(layout);
		layout.verticalSpacing = 9;

		Label projectNameLabel = new Label(c, SWT.NULL);
		projectNameLabel.setText("Project Name: ");
		projectName = new Text(c, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		projectName.setLayoutData(gd);
		projectName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		setControl(c);
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	private void dialogChanged() {
		if (getProjectName().length() > 0 && ResourcesPlugin.getWorkspace().getRoot().getProject(getProjectName()).exists()) {
			updateStatus("A project with that name already exists in the workspace.");
		} else {
			updateStatus(null);
		}
	}

	public String getProjectName() {
		return projectName.getText();
	}

}
