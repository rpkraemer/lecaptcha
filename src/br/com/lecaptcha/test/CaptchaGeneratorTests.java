package br.com.lecaptcha.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import br.com.lecaptcha.captcha.Captcha;
import br.com.lecaptcha.generator.CaptchaGenerator;
import br.com.lecaptcha.reader.WordsCaptchaReader;

public class CaptchaGeneratorTests {

	private CaptchaGenerator cg;
	private WordsCaptchaReader wcr;

	@Before
	public void setUp() throws FileNotFoundException, IOException {
		this.wcr = new WordsCaptchaReader("C:/Users/Robson/Desktop/captchas", 
										  "captcha.txt").withRandomWordsSeparator("#");
		this.cg = new CaptchaGenerator();
	}

	private String givenAValidDestinationPathForCaptchas() {
		return "C:/Users/Robson/Desktop/captchas";
	}

	private String givenAInvalidDestinationPathForCaptchas() {
		return "C:/Documents and Settings/Robson Kraemer/captchas/123123123123";
	}

	@Test(expected = IllegalArgumentException.class)
	public void testShouldFailWithNoDimensionsConfiguration() {
		this.cg.render();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testShouldFailWithNoWordsConfiguration() {
		this.cg.withDimensions(50, 50).
				render();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testShouldFailWithNoDestinationPathConfiguration() {
		this.cg.withDimensions(50, 50).
				captchasWith(1).words().
				render();
	}

	@Test
	public void testShouldGenerateOneCaptchaWithOneRandomWord()
			throws IOException {
		this.cg.generateAt(givenAValidDestinationPathForCaptchas())
				.withDimensions(120, 50).
				captchasWith(1).words().
				render();
		List<Captcha> captchas = this.cg.getGeneratedCaptchas();
		String[] captcha = captchas.get(0).getAnswer().split(" ");

		Assert.assertEquals(1, captchas.size());
		Assert.assertEquals(1, captcha.length);

	}

	@Test
	public void testShouldGenerateOneCaptchaWithTwoRandomWords()
			throws IOException {
		this.cg.generateAt(givenAValidDestinationPathForCaptchas())
				.withDimensions(200, 50).
				captchasWith(2).words().
				render();
		List<Captcha> captchas = this.cg.getGeneratedCaptchas();
		String[] captcha = captchas.get(0).getAnswer().split(" ");

		Assert.assertEquals(1, captchas.size());
		Assert.assertEquals(2, captcha.length);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testShouldNotGenerateOneCaptchaWithTwoRandomWords()
			throws IOException {
		this.cg.generateAt(givenAInvalidDestinationPathForCaptchas())
				.withDimensions(200, 50).
				captchasWith(2).words().
				render();
	}

	@Test
	public void testShouldGenerateFiveCaptchasWithThreeRandomWordsEach() throws IOException {
		this.cg.generateAt(givenAValidDestinationPathForCaptchas())
				.withDimensions(200, 60).
				captchasWith(3).words().
				render(5);
		List<Captcha> captchas = this.cg.getGeneratedCaptchas();
		String[] captcha1 = captchas.get(0).getAnswer().split(" ");
		String[] captcha2 = captchas.get(1).getAnswer().split(" ");
		String[] captcha3 = captchas.get(2).getAnswer().split(" ");

		Assert.assertEquals(5, captchas.size());
		//First captcha
		Assert.assertEquals(3, captcha1.length);
		//Second captcha
		Assert.assertEquals(3, captcha2.length);
		//Third captcha
		Assert.assertEquals(3, captcha3.length);
	}
	
	@Test
	public void testShouldGenerateOneCaptchaWithOneWordInMemory() {
		this.cg.withDimensions(200, 30).
				captchasWith(1).words().
				renderInMemory();
		Captcha captcha = this.cg.getGeneratedCaptchas().get(0);
		
		Assert.assertNotNull(captcha.getImageInBytes());
	}
	
	@Test
	public void testShouldGenerateOneCaptchaWithFourWordsInMemory() {
		this.cg.withDimensions(200, 90).
				captchasWith(4).words().
				renderInMemory();
		Captcha captcha = this.cg.getGeneratedCaptchas().get(0);
		String[] captchaWords = captcha.getAnswer().split(" ");
		
		Assert.assertNotNull(captcha.getImageInBytes());
		Assert.assertEquals(1, this.cg.getGeneratedCaptchas().size());
		Assert.assertEquals(4, captchaWords.length);
	}
	
	/* ******************************** TESTES COM O READER ************************************* */
	
	@Test
	public void testShouldGenerateOneCaptchaWithOneWordFromFile()
			throws IOException {
		
		this.cg.generateAt(givenAValidDestinationPathForCaptchas())
				.withDimensions(120, 50).
				fromWordsCaptchaReader(this.wcr).
				captchasWith(1).words().
				render();
		List<Captcha> captchas = this.cg.getGeneratedCaptchas();
		String[] captcha = captchas.get(0).getAnswer().split(" ");

		Assert.assertEquals(1, captchas.size());
		Assert.assertEquals(1, captcha.length);
	}
}
