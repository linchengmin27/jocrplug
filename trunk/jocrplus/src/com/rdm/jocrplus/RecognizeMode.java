package com.rdm.jocrplus;

public enum RecognizeMode {
	SINGLE_TEXT_LINE(7),
	SINGLE_WORD(8),
	SINGLE_WORD_IN_CIRCLE(9),
	SINGLE_CHARACTER(10);
	
	int mode = 10;
	
	RecognizeMode(int i) {
		mode = i;
	}
	
	public int getMode() {
		return mode;
	}
}
