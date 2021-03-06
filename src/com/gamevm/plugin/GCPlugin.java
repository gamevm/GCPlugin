package com.gamevm.plugin;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class GCPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "GCPlugin"; //$NON-NLS-1$

	// The shared instance
	private static GCPlugin plugin;
	
	private static File pluginFolder;
	
	/**
	 * The constructor
	 */
	public GCPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static GCPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
	public static File getPluginFolder() {
        if(pluginFolder == null) {
            URL url = Platform.getBundle(PLUGIN_ID).getEntry("/");
            try {
                url = Platform.resolve(url);
            }
            catch(IOException ex) {
                ex.printStackTrace();
            }
            pluginFolder = new File(url.getPath());
        }

        return pluginFolder;
 }
}
