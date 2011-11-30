package br.com.lecaptcha.generator;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import br.com.lecaptcha.captcha.Captcha;
import br.com.lecaptcha.reader.WordsCaptchaReader;


/**
 * 
 * @author Robson Paulo Kraemer
 * @since 29/11/2011
 *
 */
public class CaptchaGenerator {
	
	private List<Captcha> captchas = new ArrayList<Captcha>();
	
	private BufferedImage captcha;
	private WordsCaptchaReader wcr;
	private String pathname;
	private String textToCaptcha = "";
	private int h, w, nrWords, fontSizeLimit = 24;
	private Random random = new Random();
	
	public List<Captcha> getGeneratedCaptchas() {
		return this.captchas;
	}
	
	public CaptchaGenerator generateAt(String pathname) throws IOException {
		this.pathname = pathname;
		return this;
	}
	
	public CaptchaGenerator withDimensions(int w, int h) {
		this.w = w;
		this.h = h;
		return this;
	}
	
	public CaptchaGenerator fromWordsCaptchaReader(WordsCaptchaReader wcr) {
		this.wcr = wcr;
		return this;
	}
	
	public CaptchaGenerator withFontSizeLimit(int fontSizeLimit) {
		this.fontSizeLimit = fontSizeLimit;
		return this;
	}
	
	public CaptchaGenerator captchasWith(int nrWords) {
		this.nrWords = nrWords;
		return this;
	}
	
	public CaptchaGenerator words() {
		if (this.nrWords > 0) {
			if (this.nrWords == 1) 
				if (this.wcr != null) //Gerar palavras a partir de READER
					this.textToCaptcha = this.wcr.getRandomWord();
				else
					this.textToCaptcha = generateRandomWords(1); //Gerar palavras randomicamente em memória
			else
				if (this.wcr != null) //Gerar palavras a partir de READER
					this.textToCaptcha = this.wcr.getRandomWords(nrWords);
				else
					this.textToCaptcha = generateRandomWords(this.nrWords);
		} else 
			throw new IllegalArgumentException("A quantidade de palavras para o captcha deve ser informada");
		return this;
	}
	
	private String generateRandomWords(int nrWords) {
		
		if (nrWords == 0) throw new IllegalArgumentException("A quantidade de palavras a ser gerada deve ser maior que 0");
		
		int qtyChars = this.random.nextInt(10);
		if (qtyChars <= 5) qtyChars = 5;
		
		String words = "";
		if (nrWords == 1) {
			while (qtyChars-- > 0)
				words = words + getRandomChar();
		} else
			while (nrWords-- > 0) { 
				while (qtyChars-- > 1) { 
					words = words + getRandomChar();
					if (qtyChars == 1)
						words = words + " ";
				}
				qtyChars = 4;
			}
		return words;
	}
	
	private char getRandomChar() {
		int ascii;
		do {
			ascii = this.random.nextInt(90);
			if ((ascii >= 48 && ascii <= 58) || (ascii >= 64 && ascii <= 90))
				break;
		} while (true);
		return (char) ascii;
	}

	public boolean render() {
		if (this.w <= 0 || this.h <= 0)
			throw new IllegalArgumentException("As dimensões do captcha devem ser informadas");
		if (this.textToCaptcha.length() == 0 || this.nrWords <= 0)
			throw new IllegalArgumentException("A quantidade de palavras para o captcha deve ser informada");
		if (this.pathname == null)
			throw new IllegalArgumentException("O diretório de destino dos captchas deve ser informado");
		
		this.captcha = new BufferedImage(this.w, this.h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = this.captcha.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		
		String [] words = this.textToCaptcha.split(
				this.wcr != null && this.wcr.getRandomWordsSeparator() != null ? this.wcr.getRandomWordsSeparator() : " ");
		int h = this.fontSizeLimit, w = this.fontSizeLimit;
		
		//Set background color
		g2d.setColor(Color.GRAY);
		g2d.fillRect(0, 0, this.w, this.h);
		
		generateRandomLines();
		
		String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		String font = "Arial";
		
		for (String word : words) {
			g2d.setBackground(getRandomColor());
			if (this.wcr != null)
				font = fonts[this.random.nextInt( (int) (fonts.length - 1) / 2)];
			g2d.setFont(new Font(font, Font.BOLD, getRandomFontSize()));
			g2d.setColor(getRandomColor());
			g2d.drawString(word, h, w);
			w += this.wcr != null ? 12 : 15;
		}
		g2d.dispose();
		
		return saveCaptchaAtDisk(words);
	}
	
	private boolean saveCaptchaAtDisk(String [] words) {
		try {
			int targetFileName = this.words().hashCode() + new Date().hashCode() + this.random.nextInt();
			if (targetFileName < 0) targetFileName *= -1;
			File targetFile = new File(this.pathname, targetFileName + ".JPG");

			if (ImageIO.write(this.captcha, "JPG", targetFile)) {
				String answer = "";
				for (String word : words) 
					answer += word + " ";
				this.captchas.add(new Captcha(targetFile.getAbsolutePath(), answer.trim(), new Long(targetFileName)));
				return true;
			}
			return false;
		} catch (Exception e) {
			throw new IllegalArgumentException("Não foi possível gravar os captchas no diretório especificado");
		}
	}

	private void generateRandomLines() {
		Graphics2D g2d = (Graphics2D) this.captcha.getGraphics();
		
		for (int i = 0; i < 600; i++) {
			g2d.setColor(getRandomColor());
			final int x1 = this.random.nextInt(500);  
	        final int y1 = this.random.nextInt(500);  
	        final int x2 = this.random.nextInt(500); 
	        final int y2 = this.random.nextInt(500);		
	        g2d.drawLine(x1, y1, x2, y2);
		}
	}

	private int getRandomFontSize() {
		int fontSize = 0;
		if (this.fontSizeLimit == 24) return this.fontSizeLimit;
		do 
			fontSize = this.random.nextInt(this.fontSizeLimit);
		while (fontSize < 24);
		return fontSize;
	}
	
	private Color getRandomColor() {
		int R, G, B;
		R = this.random.nextInt(200);
		G = this.random.nextInt(100);
		B = this.random.nextInt(5);
		return new Color(R, G, B);
	}

	public boolean render(int captchas) {
		while (captchas-- > 0)
			if (!this.render())
				return false;
		return true;
	}
}
