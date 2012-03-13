package com.rdm.jocrplus;


public class OcrManager {
	
	private static OcrManager instance = new OcrManager();
	
	public static OcrManager getInstance() {
		return instance;
	}
	
	private OcrManager() {
	}

	public Ocr createOcr(String langFileName) {
		int hw = Native.create(langFileName);
		if (hw == 0) {
			throw new RuntimeException("createOcr() fail!");
		}
		return new Ocr(hw);
	}
	

}
