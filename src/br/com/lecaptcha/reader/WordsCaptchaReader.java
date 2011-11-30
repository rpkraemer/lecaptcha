package br.com.lecaptcha.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

/**
 * 
 * @author Robson Paulo Kraemer
 * @since 29/11/2011
 *
 */
public class WordsCaptchaReader {
	
	public final static String COMMA = ",";
	public final static String TABULATION = "\\t";
	public final static String LINE = "\\n";
	public final static String SEMICOLON = ";";
	
	private String path;
	private String file;
	private String fileFieldSeparator;
	private String randomWordsSeparator;
	private Random random;
	private String[] words;
	
	public WordsCaptchaReader(String path, String file) throws FileNotFoundException, IOException {
		this.path = path;
		this.file = file;
		this.random = new Random();
		readWordsFor(getFile());
	}
	
	public WordsCaptchaReader(String path, String file, String fileFieldSeparator) throws FileNotFoundException, IOException {
		this.path = path;
		this.file = file;
		this.fileFieldSeparator = fileFieldSeparator;
		this.random = new Random();
		readWordsFor(getFile());
	}
	
	public WordsCaptchaReader withRandomWordsSeparator(String randomWordsSeparator) {
		this.randomWordsSeparator = randomWordsSeparator;
		return this;
	}
	
	public String getRandomWordsSeparator() {
		return this.randomWordsSeparator;
	}
	
	public String getFileFieldSeparator() {
		return this.fileFieldSeparator;
	}

	public String getRandomWord() {
		if (words.length - 1 <= 0)
			throw new IllegalArgumentException("Por favor, confira se o caracter separador de campos no arquivo corresponde ao informado");
		return words[this.random.nextInt(words.length)];
	}
	
	public String getRandomWords(int numberOfWords) {
		if (numberOfWords <= 0) throw new IllegalArgumentException("O número de palavras deve ser maior que 0");
		if (this.randomWordsSeparator == null) this.randomWordsSeparator = " ";

		String words = "";
		int maximumWordsPermited = this.words.length;
		
		if (numberOfWords > maximumWordsPermited)
			throw new IllegalArgumentException("Não é possível definir " + numberOfWords + " palavra(s) aleatória(s). " +
					"Existe(m) somente " + maximumWordsPermited + " cadastrada(s) no arquivo " + this.file);
		else if (numberOfWords == maximumWordsPermited)
			for (String w : this.words)
				words += w + this.randomWordsSeparator;
		else {
			while (--numberOfWords > -1) {
				String word = getRandomWord();
				if (!words.contains(word))
					if (numberOfWords > 0)
						words += word + this.randomWordsSeparator;
					else
						words += word;
				else
					numberOfWords++;
			}
		}
		return words;
	}
	
	private void readWordsFor(File file) throws FileNotFoundException, IOException {
		try {
			String wordsWithComma = "", line = "";
			BufferedReader br = new BufferedReader(new FileReader(file));
			while ((line = br.readLine()) != null) {
				line = line.trim();
				if (this.fileFieldSeparator != null && this.fileFieldSeparator.equals(LINE))
					line += "\n";
				wordsWithComma += line;
			}
			String [] words = wordsWithComma.split((this.fileFieldSeparator != null) ? this.fileFieldSeparator : ",");
			br.close();
			this.words = words;
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("Não foi possível encontrar o arquivo de captchas: " + this.file);
		} catch (IOException e) {
			throw new IOException("Não foi possível recuperar palavras do arquivo de captchas: " + this.file);
		}
	}

	private File getFile() {
		return new File(this.path, this.file);
	}
}
