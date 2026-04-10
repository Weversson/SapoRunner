# Corrida dos Sapos com Threads

Programa feito em Java para a disciplina de Sistemas Paralelos e Distribuidos. A ideia e simular uma corrida entre sapos onde cada sapo roda em uma thread separada, mostrando na pratica como funciona concorrencia e sincronizacao de threads.

## Como funciona

Cada sapo e uma instancia da classe Sapo que herda de Thread. Quando a corrida comeca, todas as threads sao disparadas ao mesmo tempo. A cada iteracao, o sapo pula entre 1 e 5 metros (valor aleatorio) e depois descansa por um tempo entre 100 e 500 milissegundos, tambem aleatorio. Isso faz com que cada corrida tenha um resultado diferente.

A pista tem 50 metros. O programa vai desenhando o progresso de cada sapo no terminal em tempo real, com barras coloridas que avancam conforme o sapo pula. Quando um sapo chega ao final, sua colocacao e registrada de forma sincronizada para evitar condicao de corrida entre as threads.

## O que foi usado

A classe Sapo estende Thread e contem toda a logica do pulo e descanso. A classe CorridaSapos cuida da parte visual, do menu interativo e da sincronizacao. O metodo registrarChegada e synchronized para garantir que dois sapos nao registrem a mesma colocacao. A atualizacao da tela tambem e sincronizada para evitar que as threads escrevam no terminal ao mesmo tempo e baguncem a saida.

## Como rodar

Voce precisa ter o JDK instalado na maquina. Depois e so compilar e executar:

```
javac Sapo.java CorridaSapos.java
java CorridaSapos
```

O programa vai perguntar quantos sapos voce quer (entre 2 e 6) e se voce quer dar nomes pra eles. Depois faz uma contagem regressiva e a corrida comeca. No final mostra o placar e pergunta se voce quer jogar de novo.

## Estrutura

O projeto tem dois arquivos:

Sapo.java contem a classe Sapo, que e a thread de cada competidor. Ela recebe o nome, a cor, a distancia da corrida e uma referencia para a corrida principal.

CorridaSapos.java contem a classe principal com o main, o menu interativo, a renderizacao da pista e o controle de sincronizacao.

## Conceitos aplicados

O programa usa criacao e execucao de threads com heranca de Thread, sincronizacao com synchronized para proteger o registro de chegada e a escrita no terminal, sleep para simular tempos de pulo diferentes, e join para esperar todas as threads terminarem antes de mostrar o resultado final.
