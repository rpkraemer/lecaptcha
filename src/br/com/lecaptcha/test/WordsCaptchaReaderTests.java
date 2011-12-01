package br.com.lecaptcha.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.lecaptcha.reader.WordsCaptchaReader;

public class WordsCaptchaReaderTests {
	
	private WordsCaptchaReader validWCR, invalidWCR;
	
	@Before
	public void setUp() throws FileNotFoundException, IOException {
		this.validWCR = new WordsCaptchaReader(givenAValidPath(), 
										  	   givenAValidCaptchaFile())
		.withRandomWordsSeparator("#"); 
		//IMPORTANTE: SETAR UM SEPARADOR DE PALAVRAS PARA TRATAR CORRETAMENTE REGISTROS COM MAIS DE UMA PALAVRA
	}
	
	private String givenAValidCaptchaFile() {
		//CONTEÚDO DO ARQUIVO captcha_pt_txt deve ser em 1 linha: teste,gol,bola,brasil
		return "captcha.txt";
	}

	private String givenAValidPath() {
		return "C:/Users/Robson/Desktop/captchas";
	}
	
	@Test
	public void testShouldReadOneWordFromTheFile() {
		String word = this.validWCR.getRandomWord();
		Assert.assertNotNull(word);
	}
	
	@Test
	public void testShouldReadTwoWordsFromTheFile() {
		String words = this.validWCR.getRandomWords(2);
		Assert.assertEquals(2, words.split("#").length);
	}
	
	@Test
	public void testShouldReadFourWordsFromTheFile() {
		String words = this.validWCR.getRandomWords(4);
		Assert.assertEquals(4, words.split("#").length);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testShouldFailWhenTryToReadFiveWordsFromFile() {
		String words = this.validWCR.getRandomWords(5);
	}
	
	@Test
	public void testShouldReturnThreeWordsSeparatedBySharp() {
		String words = this.validWCR.getRandomWords(3);
		Pattern p = Pattern.compile("^(.)+(#){1,}(.)+(#){1,}(.)+");
		Assert.assertEquals(true, p.matcher(words).matches());
	}
	
	@Test(expected = FileNotFoundException.class)
	public void testShouldFailWhenNotFoundCaptchasWordsFile() throws FileNotFoundException, IOException {
		this.invalidWCR = new WordsCaptchaReader("not_existing_path", "not_existing_file");
	}

}
