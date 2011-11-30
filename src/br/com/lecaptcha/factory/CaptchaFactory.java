package br.com.lecaptcha.factory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import br.com.lecaptcha.captcha.Captcha;
import br.com.lecaptcha.generator.CaptchaGenerator;
import br.com.lecaptcha.reader.WordsCaptchaReader;


/**
 * 
 * @author Robson Paulo Kraemer
 * @since 29/11/2011
 *
 */
public class CaptchaFactory {
	
	private static CaptchaFactory cf;
	private CaptchaGenerator cg;
	private WordsCaptchaReader wcr;
	private String wordsFileLocation, wordsFilename;
	
	public static CaptchaFactory getInstance() {
		if (cf == null)
			cf = new CaptchaFactory();
		return cf;
	}
	
	public void setWordsFileLocation(String wordsFileLocation) {
		this.wordsFileLocation = wordsFileLocation;
	}
	
	public void setWordsFilename(String wordsFilename) {
		this.wordsFilename = wordsFilename;
	}
	
	public void generateCaptchas(int numberOfCaptchas, String captchasLocation) {
		if (this.wordsFileLocation == null || this.wordsFilename == null)
			createCaptchasFromRandomChars(numberOfCaptchas, captchasLocation);
		else {
			createCaptchasFromFile(numberOfCaptchas, captchasLocation);
		}
	}
	
	private void createCaptchasFromRandomChars(int numberOfCaptchas, String captchasLocation) {
		try {
			this.cg = new CaptchaGenerator();
			this.cg.generateAt(captchasLocation).
					 withDimensions(200, 50).
					 captchasWith(2).words().
					 render(numberOfCaptchas);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) { 
			e.printStackTrace();
		}
	}

	private void createCaptchasFromFile(int numberOfCaptchas, String captchasLocation) {
		try {
			this.wcr = new WordsCaptchaReader(this.wordsFileLocation, this.wordsFilename, WordsCaptchaReader.COMMA).
				withRandomWordsSeparator("-");
			this.cg = new CaptchaGenerator();
			this.cg.generateAt(captchasLocation).
					 fromWordsCaptchaReader(this.wcr).
					 withDimensions(200, 50).
					 captchasWith(2).words().
					 render(numberOfCaptchas);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) { 
			e.printStackTrace();
		}
	}
	
	public List<Captcha> getGeneratedCaptchas() { 
		return this.cg.getGeneratedCaptchas(); 
	}
}
