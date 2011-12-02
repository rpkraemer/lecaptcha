package br.com.lecaptcha.support.vraptor.controller;

import br.com.caelum.vraptor.interceptor.download.InputStreamDownload;

public interface CaptchaController {

	InputStreamDownload generateCaptcha();
	void setDimensions(int w, int h);
	void setNumberOfWords(int words);
	
}
