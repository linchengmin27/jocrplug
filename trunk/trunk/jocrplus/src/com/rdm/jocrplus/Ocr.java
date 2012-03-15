package com.rdm.jocrplus;

import java.awt.image.BufferedImage;
import java.io.File;

/**
 * It's not thread safe.
 * @author lokier
 *
 */
public class Ocr {
	
	int hw = 0;
	
	/*Package*/ Ocr(int hw) {
		this.hw = hw;
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		dispose();
	}
	
	public void dispose() {
		if (hw != 0) {
			Native.destroy(hw);
			hw = 0;
		}
	}
	
	public String recognizeFile(String fileName, RecognizeMode mode) {
		if (fileName == null) {
			throw new NullPointerException();
		}
		
		return recognizeFile(new File(fileName), mode);
	}
	
	public String recognizeFile(File file, RecognizeMode mode) {
		if (file == null) {
			throw new NullPointerException();
		}
		
		String fileName = file.getAbsolutePath();
		if (fileName.endsWith(".gif")) {
			throw new RuntimeException("unsupported *.gif file.");
		}
		
		if (Native.ocr(hw, file.getAbsolutePath(), mode.getMode())) {
			return Native.getUTF8Result(hw);
		}
		return null;
	}
	
	
	public String recognizeImage(BufferedImage image, RecognizeMode mode) {
		int w = image.getWidth();
		int h = image.getHeight();
		int[] argb = image.getRGB(0, 0, w, h, null, 0, w);
		return recognizePixes(argb, w, h, mode);
	}
	
	public String recognizePixes(int[] rgb, int w, int h, RecognizeMode mode) {
		if (Native.ocr(hw, w, h, rgb, mode.getMode())) {
			return Native.getUTF8Result(hw);
		}
		return null;
	}
	
}
