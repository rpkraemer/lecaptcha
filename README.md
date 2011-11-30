LeCaptcha

## Introducao

Este projeto é uma pequena biblioteca para facilitar a criação de captchas (apenas imagem). 
Com ele é possível gerar captchas a partir de palavras gravadas em um arquivo ou gerar com caracteres aleatórios,
é possível escolher o número de palavras em cada captcha, quantos captchas deverão ser gerados e o diretório onde serão
colocados.

## Onde Usar

Você pode usar este projeto em qualquer aplicação que necessitar de uma solução simples e rápida para captchas visuais.

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
		e finalmente, mandamos renderizar os captchas passando por parâmetro a QUANTIDADE desejada (render(5))
		o método render possui uma sobrecarga sem parâmetros (render()) nesse caso, gera-se apenas 1 captcha