package br.com.lecaptcha.support.vraptor.session;

import javax.servlet.http.HttpSession;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;
import br.com.lecaptcha.captcha.Captcha;

@SessionScoped
@Component
public class CaptchaSessionImpl implements CaptchaSession {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpSession session;
	
	public CaptchaSessionImpl(HttpSession session) {
		this.session = session;
	}

	@Override
	public Captcha getCaptcha() {
		return (Captcha) this.session.getAttribute("captcha");
	}

	@Override
	public void setCaptcha(Captcha captcha) {
		this.session.setAttribute("captcha", 
				new Captcha(captcha.getAnswer(), captcha.getID(), captcha.getWidth(), captcha.getHeight()));
	}

}
