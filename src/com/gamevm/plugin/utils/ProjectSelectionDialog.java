package com.gamevm.plugin.utils;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

public class ProjectSelectionDialog extends ElementTreeSelectionDialog {

	private String natureFilter;
	
	public ProjectSelectionDialog(Shell parent, String natureFilter) {
		super(parent, new WorkbenchLabelProvider(), new BaseWorkbenchContentProvider());
		this.natureFilter = natureFilter;
		setTitle("Select Project");
		setMessage("Select the project:");
		setInput(ResourcesPlugin.getWorkspace().getRoot());
		setAllowMultiple(false);
		addFilter(new ViewerFilter() {				
			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				if(element instanceof IProject){
					IProject p = (IProject) element;
					try{
						if(p.hasNature(ProjectSelectionDialog.this.natureFilter)){
							return true;
						}
					}
					catch(CoreException e1){
						return false;
					}		
				}
				return false;
			}
		});	
	}
	
	public IProject getSelection() {
		Object[] result = getResult();
		IProject p = (IProject) result[0];
		return p;
	}

}
