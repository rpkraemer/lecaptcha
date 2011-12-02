package br.com.lecaptcha.support.vraptor.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class CaptchaTagLib extends SimpleTagSupport {
	
	private PageContext context;
	private JspWriter writer;
	
	@Override
	public void doTag() throws JspException {
		this.context = (PageContext) getJspContext();
		this.writer = context.getOut();
		
		String contextPath = context.getServletContext().getContextPath();
		try {		
			String imgHtmlTag = "<img src=\"" + contextPath + "/captcha" + "\" alt=\"\"/>";
			String answerBox = "<input type=\"text\" name=\"captchaAnswer\" id=\"captchaAnswer\"/>";
			writer.write(imgHtmlTag + "<br/>" + answerBox);
		} catch (IOException e) { 
			
		}
	}

}
