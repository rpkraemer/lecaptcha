package br.com.lecaptcha.support.vraptor.controller;

import java.io.ByteArrayInputStream;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.interceptor.download.InputStreamDownload;
import br.com.lecaptcha.captcha.Captcha;
import br.com.lecaptcha.generator.CaptchaGenerator;
import br.com.lecaptcha.support.vraptor.session.CaptchaSession;

@Resource
public class CaptchaControllerImpl implements CaptchaController {
	
	private CaptchaGenerator cg;
	private CaptchaSession session;
	private int[] dimensions;
	private int words;
	
	public CaptchaControllerImpl(CaptchaSession session) {
		this.session = session;
		this.cg = new CaptchaGenerator();
		this.dimensions = new int[] {140, 30};
		this.words = 1;
	}

	private void configGenerator() {
		int w = getDimensions()[0];
		int h = getDimensions()[1];
		
		this.cg.withDimensions(w, h).
				captchasWith(getNumberOfWords()).words();
	}

	protected int[] getDimensions() {
		return dimensions;
	}

	protected int getNumberOfWords() {
		return words;
	}
	
	@Get
	@Path("/captcha")
	@Override
	public InputStreamDownload generateCaptcha() {
		configGenerator();
		this.cg.renderInMemory();
		Captcha captcha = this.cg.getGeneratedCaptchas().get(0); //Pega o captcha gerado
		// seta as dimensões escolhidas
		captcha.setWidth(getDimensions()[0]);
		captcha.setHeight(getDimensions()[1]);
		// salva o captcha em sessão
		this.session.setCaptcha(captcha);
		// retorna a imagem do captcha para a taglib mostrar no JSP
		ByteArrayInputStream input = new ByteArrayInputStream(captcha.getImageInBytes());
		return new InputStreamDownload(input, "image/jpeg", captcha.getID().toString(), false, captcha.getImageInBytes().length);
	}

	@Override
	public void setDimensions(int w, int h) {
		this.dimensions = new int[] {w, h};
	}

	@Override
	public void setNumberOfWords(int words) {
		this.words = words;
	}

}
