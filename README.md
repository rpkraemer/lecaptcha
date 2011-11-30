LeCaptcha

## Introducao

Este projeto � uma pequena biblioteca para facilitar a cria��o de captchas (apenas imagem). 
Com ele � poss�vel gerar captchas a partir de palavras gravadas em um arquivo ou gerar com caracteres aleat�rios,
� poss�vel escolher o n�mero de palavras em cada captcha, quantos captchas dever�o ser gerados e o diret�rio onde ser�o
colocados.

## Onde Usar

Voc� pode usar este projeto em qualquer aplica��o que necessitar de uma solu��o simples e r�pida para captchas visuais.

#Exemplos

	### Exemplo 1 - Criando 5 captchas com 2 palavras (caracteres randomicos) em cada
		
		import java.io.IOException;
		import br.com.lecaptcha.generator.CaptchaGenerator;
		
		public class Main {		
			public static void main(String[] args) throws IOException {
				CaptchaGenerator cg = new CaptchaGenerator();
				cg.generateAt("C:/Users/Robson/Desktop/captchas").
				   withDimensions(200, 60).
				   captchasWith(2).words().
				   render(5);
			}
		}

	### Sobre o Exemplo 1
	
	No exemplo acima, utilizando a classe CaptchaGenerator definimos: 
		ONDE os captchas devem ser gerados (generateAt())
		DIMENSOES de cada captcha (withDimensions(200, 60) - 200 width e 60 height)
		cada um dos captchas com 2 palavras (captchasWith(2).words())
		e finalmente, mandamos renderizar os captchas passando por par�metro a QUANTIDADE desejada (render(5))
		o m�todo render possui uma sobrecarga sem par�metros (render()) nesse caso, gera-se apenas 1 captcha