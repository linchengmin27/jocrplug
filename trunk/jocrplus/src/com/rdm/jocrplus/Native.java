package com.rdm.jocrplus;

public class Native {

	static {
		if ( !Library.load("jocrplus")) {
			Library.loadLibraryFromJar("jocrplus");
		}
	}
	
	static native int create(String data);
	static native void destroy(int handle);
	static native boolean ocr(int handle,int w, int h, int[] rgb,int mode);
	static native boolean ocr(int handle,String fileName,int mode);
	static native String getUTF8Result(int handle);
}
