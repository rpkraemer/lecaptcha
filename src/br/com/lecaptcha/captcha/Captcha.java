package br.com.lecaptcha.captcha;


/**
 * 
 * @author Robson Paulo Kraemer
 * @since 29/11/2011
 *
 */
public class Captcha {

	private String captchaLocation;
	private String answer;
	private Long ID;
	private byte[] imageInBytes;
	
	public Captcha(String answer, Long ID) {
		this.answer = answer;
		this.ID = ID;
	}
	
	public Captcha(String captchaLocation, String answer, Long ID) {
		this.captchaLocation = captchaLocation;
		this.answer = answer;
		this.ID = ID;
	}

	public String getCaptchaLocation() {
		return captchaLocation;
	}
	public void setCaptchaLocation(String captchaLocation) {
		this.captchaLocation = captchaLocation;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public Long getID() {
		return ID;
	}
	public byte[] getImageInBytes() {
		return imageInBytes;
	}
	public void setImageInBytes(byte[] imageInBytes) {
		this.imageInBytes = imageInBytes;
	}
}
