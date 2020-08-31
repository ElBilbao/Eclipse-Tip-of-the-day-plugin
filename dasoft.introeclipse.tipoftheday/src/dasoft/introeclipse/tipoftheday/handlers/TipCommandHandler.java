package dasoft.introeclipse.tipoftheday.handlers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.osgi.framework.Bundle;

import dasoft.introeclipse.tipoftheday.extensionsource.ITipSource;

import org.eclipse.jface.dialogs.MessageDialog;

public class TipCommandHandler extends AbstractHandler {
	
	private List<String> consejos = new ArrayList<String>();
	private int rand;
	
	public TipCommandHandler() {
		
		// Tips from data file
		try {
			Bundle bundle = Platform.getBundle("dasoft.introeclipse.tipoftheday");
			URL fileURL = bundle.getEntry("data/consejos.txt");
			InputStream is = fileURL.openStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(is));
			String consejo;
			
			while ((consejo = in.readLine()) != null) consejos.add(consejo);
			
			in.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Tips from extension points
		IExtensionRegistry registro = Platform.getExtensionRegistry();
		IConfigurationElement[] extensiones = registro.getConfigurationElementsFor("dasoft.introeclipse.tipoftheday.tipsource");
		
		for (IConfigurationElement e : extensiones) {
			if (e.getName().equals("TipSource")) {
				try {
					ITipSource fuenteConsejos = (ITipSource) e.createExecutableExtension("class");
					//String nombreFuente = (String) e.getAttribute("name");
					for (String c : fuenteConsejos.getTips()) consejos.add(c);
				}
				catch (CoreException exception) {
					exception.printStackTrace();
				}
			}
		}
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		try {
			IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
			
			// Avoid repeating the same tip twice
			int randNum;
			do {
				randNum = (int)(Math.random() * consejos.size());
			} while(randNum == rand);
			rand = randNum;
						
			String random = consejos.get(randNum);
			MessageDialog.openInformation(window.getShell(), "Tip of the Day", random);
		}
		catch (Exception e) { e.printStackTrace(); }
		
		return null;
	}
}
