package br.com.lecaptcha.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({CaptchaGeneratorTests.class, WordsCaptchaReaderTests.class})
public class LeCaptchaSuitTest {

}
