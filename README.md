# Corrida dos Sapos com Threads

Programa feito em Java para a disciplina de Sistemas Paralelos e Distribuídos. A ideia é simular uma corrida entre sapos onde cada sapo roda em uma thread separada, mostrando na prática como funciona concorrência e sincronização de threads.

## Como funciona

Cada sapo é uma instância da classe Sapo que herda de Thread. Quando a corrida começa, todas as threads são disparadas ao mesmo tempo. A cada iteração, o sapo pula entre 1 e 5 metros (valor aleatório) e depois descansa por um tempo entre 100 e 500 milissegundos, também aleatório. Isso faz com que cada corrida tenha um resultado diferente.

A pista tem 50 metros. O programa vai desenhando o progresso de cada sapo no terminal em tempo real, com barras coloridas que avançam conforme o sapo pula. Quando um sapo chega ao final, sua colocação é registrada de forma sincronizada para evitar condição de corrida entre as threads.

## O que foi usado

A classe Sapo estende Thread e contém toda a lógica do pulo e descanso. A classe CorridaSapos cuida da parte visual, do menu interativo e da sincronização. O método registrarChegada é synchronized para garantir que dois sapos não registrem a mesma colocação. A atualização da tela também é sincronizada para evitar que as threads escrevam no terminal ao mesmo tempo e baguncem a saída.

## Como rodar

Você precisa ter o JDK instalado na máquina. Depois é só compilar e executar:

```
javac Sapo.java CorridaSapos.java
java CorridaSapos
```

O programa vai perguntar quantos sapos você quer (entre 2 e 6) e se você quer dar nomes pra eles. Depois faz uma contagem regressiva e a corrida começa. No final mostra o placar e pergunta se você quer jogar de novo.

## Estrutura

O projeto tem dois arquivos:

Sapo.java contém a classe Sapo, que é a thread de cada competidor. Ela recebe o nome, a cor, a distância da corrida e uma referência para a corrida principal.

CorridaSapos.java contém a classe principal com o main, o menu interativo, a renderização da pista e o controle de sincronização.

## Conceitos aplicados

O programa usa criação e execução de threads com herança de Thread, sincronização com synchronized para proteger o registro de chegada e a escrita no terminal, sleep para simular tempos de pulo diferentes, e join para esperar todas as threads terminarem antes de mostrar o resultado final.
