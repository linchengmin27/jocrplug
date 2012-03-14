package com.rdm.jocrplus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Library {

	static final String OS_NAME;
	
	static final String SEPARATOR;
	
	static final boolean isWin32OS;
	
	static final String USER_DIR;
	
	static {
		SEPARATOR = System.getProperty("file.separator");
		OS_NAME = System.getProperty("os.name");
		USER_DIR = System.getProperty("user.dir");

		if (OS_NAME != null) {
			isWin32OS = OS_NAME.startsWith("win") || OS_NAME.startsWith("Win") ;
		} else {
			isWin32OS = false;
		}
	}
	
	public static void loadLibraryFromJar(String name) {
		String libName = System.mapLibraryName(name);
		if (extract(USER_DIR + SEPARATOR + libName, libName)) {
			return;
		}
		throw new UnsatisfiedLinkError("no " + libName +" in the jar file"); 
	}
	
	public static boolean load(String libName) {
		try {
			if (libName.indexOf(SEPARATOR) != -1) {
				System.load(libName);
			} else {
				System.loadLibrary(libName);
			}
			return true;
		} catch (UnsatisfiedLinkError e) {
		}
		return false;
	}
	
	
	static boolean extract(String fileName, String mappedName) {
		FileOutputStream os = null;
		InputStream is = null;
		File file = new File(fileName);
		try {
			if (file.exists())
				file.delete();			
			is = Library.class.getResourceAsStream("/" + mappedName); //$NON-NLS-1$
			if (is != null) {
				int read;
				byte[] buffer = new byte[4096];
				os = new FileOutputStream(fileName);
				while ((read = is.read(buffer)) != -1) {
					os.write(buffer, 0, read);
				}
				os.close();
				is.close();
				if (!isWin32OS) { //$NON-NLS-1$
					try {
						Runtime.getRuntime()
								.exec(new String[] { "chmod", "755", fileName }).waitFor(); //$NON-NLS-1$ //$NON-NLS-2$
					} catch (Throwable e) {
					}
				}
				if (load(fileName))
					return true;
			}			
		} catch (Throwable e) {
			try {
				if (os != null)
					os.close();
			} catch (IOException e1) {
			}
			try {
				if (is != null)
					is.close();
			} catch (IOException e1) {
			}
		}
		if (file.exists())
			file.delete();
		return false;
	}
	
	

}
