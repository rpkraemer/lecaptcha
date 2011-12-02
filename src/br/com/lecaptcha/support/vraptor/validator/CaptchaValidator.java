package br.com.lecaptcha.support.vraptor.validator;

import br.com.caelum.vraptor.ioc.Component;
import br.com.lecaptcha.support.vraptor.session.CaptchaSession;

@Component
public class CaptchaValidator {

	private CaptchaSession cs;
	
	public CaptchaValidator(CaptchaSession cs) {
		this.cs = cs;
	}
	
	public boolean isValidAnswer(String captchaAnswer) {
		return cs.getCaptcha().getAnswer().equals(captchaAnswer);
	}
}
