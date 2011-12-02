package br.com.lecaptcha.support.vraptor.session;

import java.io.Serializable;

import br.com.lecaptcha.captcha.Captcha;

public interface CaptchaSession extends Serializable {

	public Captcha getCaptcha();
	public void setCaptcha(Captcha captcha);
}
