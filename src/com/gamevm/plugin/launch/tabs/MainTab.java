package com.gamevm.plugin.launch.tabs;

import java.io.File;
import java.io.IOException;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.ui.AbstractLaunchConfigurationTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

import com.gamevm.compiler.assembly.ClassDefinition;
import com.gamevm.compiler.assembly.GClassLoader;
import com.gamevm.compiler.parser.ASTNode;
import com.gamevm.compiler.parser.GCASTLexer;
import com.gamevm.compiler.parser.GCASTParser;
import com.gamevm.plugin.builder.GCProjectNature;
import com.gamevm.plugin.utils.ProjectSelectionDialog;
import com.gamevm.plugin.utils.SourceFileSelectionDialog;

public class MainTab extends AbstractLaunchConfigurationTab {

	Text projectName;
	Text mainClassName;
	IProject project;
	IFile mainClass;
	
	private GClassLoader classLoader;
	
	public MainTab() {
	}
	
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
	
	private String getClassName(IFile file) {
		try {
			CharStream charStream = new ANTLRInputStream(file.getContents());
			GCASTLexer lexer = new GCASTLexer(charStream);
			GCASTParser parser = new GCASTParser(new CommonTokenStream(lexer));
			ClassDefinition<ASTNode> ast = parser.program();
			//configuration.setAttribute("mainClassLocation", mainClass.getLocation().toPortableString());
			return ast.getDeclaration().getName();
		} catch (IOException e) {
		} catch (CoreException e) {
		} catch (RecognitionException e) {
		}
		return "";
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
					mainClassName.setText(getClassName(f));
					mainClass = f;
				}

			}
		});
		
		
		setControl(c);
	}

	@Override
	public void setDefaults(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute("project", "");
		//configuration.setAttribute("mainClassLocation", "");
		configuration.setAttribute("mainClassName", "");
	}

	@Override
	public void initializeFrom(ILaunchConfiguration configuration) {
		try {
			projectName.setText(configuration.getAttribute("project", ""));
			mainClassName.setText(configuration.getAttribute("mainClassName", ""));
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void performApply(ILaunchConfigurationWorkingCopy configuration) {
		configuration.setAttribute("project", projectName.getText());
		if (mainClass != null) {
			configuration.setAttribute("mainClassName", mainClassName.getText());
		}
	}

	@Override
	public String getName() {
		return "Main";
	}

}
