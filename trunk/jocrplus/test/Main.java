import java.io.IOException;

import com.rdm.jocrplus.Ocr;
import com.rdm.jocrplus.OcrManager;
import com.rdm.jocrplus.RecognizeMode;


public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		String data = "eng.traineddata";
		String file = "sample2.png";
		
		Ocr ocr = OcrManager.getInstance().createOcr(data);
		String result =  null;
		
		result = ocr.recognizeFile(file, RecognizeMode.SINGLE_TEXT_LINE);
		System.out.println("recognizeFile : " + result);
		
		java.awt.image.BufferedImage bi = javax.imageio.ImageIO.read(new java.io.File(file));

		result = ocr.ocrImage(bi, RecognizeMode.SINGLE_TEXT_LINE);
		System.out.println("ocrImage : " + result);

		ocr.dispose();
	}

}
