package com.gamevm.plugin.utils;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.ISelectionStatusValidator;
import org.eclipse.ui.model.BaseWorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;

import com.gamevm.plugin.GCPlugin;

public class SourceFileSelectionDialog extends ElementTreeSelectionDialog {

	private String natureFilter;
	private String sourceFileExtension;

	public SourceFileSelectionDialog(Shell parent, String natureFilter, String sourceFileEnding) {
		super(parent, new WorkbenchLabelProvider(), new BaseWorkbenchContentProvider());
		this.natureFilter = natureFilter;
		this.sourceFileExtension = sourceFileEnding;
		setInput(ResourcesPlugin.getWorkspace().getRoot());
		setAllowMultiple(false);
		setValidator(new ISelectionStatusValidator() {
			@Override
			public IStatus validate(Object[] selection) {
				if (selection.length == 1 && selection[0] instanceof IFile) {
					return new Status(Status.OK, GCPlugin.PLUGIN_ID, "");
				} else {
					return new Status(Status.ERROR, GCPlugin.PLUGIN_ID, "Please select a source file");
				}
			}
		});
		addFilter(new ViewerFilter() {
			@Override
			public boolean select(Viewer viewer, Object parentElement, Object element) {
				try {
					if (element instanceof IProject) {
						IProject p = (IProject) element;
						if (p.hasNature(SourceFileSelectionDialog.this.natureFilter)) {
							return true;
						}
					} else if (element instanceof IFile) {
						IFile f = (IFile) element;
						if (f.getLocation().getFileExtension().equals(SourceFileSelectionDialog.this.sourceFileExtension)) {
							return true;
						}
					}

				} catch (CoreException e1) {
					e1.printStackTrace();
					return false;
				}
				return false;
			}
		});
	}

}
