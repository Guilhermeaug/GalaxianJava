# GalaxianJava

## Integrantes
* Diogo Emanuel Antunes Santos (20213002091)
* Guilherme Augusto de Oliveira (20213006564)
* Luiz Eduardo Leroy Souza (20213002126)

## Instruções para compilação/execução
No projeto fornecido existe uma pasta "Executavel". Dentro da pasta há um 
artefato .jar que pode ser executado. Para executar o projeto, basta executar o 
comando
````java-jar JogoGalaxian.jar````  
É importante que o jogo seja executado no mesmo diretório da pasta src, para que a máquina virtual encontre os arquivos de imagem e sons.

O projeto foi compilado usando o compilador Java 8 Eclipse-Adoptium. Caso haja problemas com a execução do programa,
recomendamos que o mesmo seja compilado novamente usando a sua versão atual do java (>=8)

O projeto final foi construido usando a IDE **IntelliJ IDEA Ultimate**, mas pode ser
importado por qualquer IDE que possa ser usada para compilar e executar programas java,
como o Eclipse e Netbeans.

## Lista de itens adicionais implementados

Além do jogo base, o projeto conta com:
* **Texturas animadas** para o jogador e os inimigos;
* **Fundo com animação**;
* **Items**: teia de aranha, óculos;
* **Pontuação**;
* **Vidas**;
* **Sons**: música de fundo, disparo;
* **Telas**: tela de splash, menu, jogo e tela de game over;

## Desenvolvimento
O jogo foi inteiramente desenvolvido utilizando o paradigma de programação orientado a objetos
com Java. Nenhuma biblioteca externa foi utilizada para a realização do trabalho,
apenas os componentes Swing e AWT presentes no Java 8.

Em relação à separação do projeto por pacotes:
* **constants** - contém as constantes utilizadas no jogo;
* **enums** - contém as enumerações utilizadas no jogo;
* **models** - contém as classes que representam os objetos do jogo. Para a passagem de dados do jogo para estas classes foi utilizado o padrão de projeto Observer, munido de eventos.
* **items** - como se fosse um pacote de models, mas com classes que representam exclusivamente os dois items do jogo.
* **views** - contém o pacote **screns**, que representa as telas do jogo. Cada tela é um JFrame (janela do Java Swing). E também classes de utilidade para som e imagem.
* **resources** - contém todas as imagens e sons utilizados no jogo.

## Fontes

Todas as imagens e sons são públicos e foram retiradas de fontes distintas da internet.
Para entender mais sobre o padrão Observable e implementar os eventos utilizamos o site:
https://www.devmedia.com.br/o-padrao-observer-e-swing/5719