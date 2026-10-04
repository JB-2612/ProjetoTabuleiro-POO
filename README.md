# Jogo de Tabuleiro Pokémon

Projeto desenvolvido para a disciplina de **Programação Orientada a Objetos (POO)**, período **2026.2**.

O projeto simula um jogo de tabuleiro multiplayer (até 6 participantes), com casas especiais, tipos de jogador com comportamentos distintos e uma interface gráfica construída em JavaFX. O jogo foi todo modelado em torno de **herança e polimorfismo**: tanto os jogadores quanto as casas do tabuleiro são hierarquias de classes abstratas, permitindo adicionar novos tipos de jogador ou novas casas especiais sem alterar a lógica principal do jogo.

## Autores

- **João Batista Alves de Sousa Júnior** — [github.com/JB-2612](https://github.com/JB-2612)
- **Geraldo Duarte de Medeiros Neto** — [github.com/ogeraldinh](https://github.com/ogeraldinh)

## Sobre o jogo

O tabuleiro tem 41 casas (0 a 40), dispostas visualmente em formato de **espiral**, com a casa final no centro. Cada jogador escolhe uma cor e um tipo:

- **Normal**: a soma dos dados pode ser qualquer valor.
- **Sortudo**: a soma dos dados é sempre 7 ou mais.
- **Azarado**: a soma dos dados é sempre 6 ou menos, e não avança em casas da sorte.

O tabuleiro conta com casas especiais (perde a vez, surpresa, sorte, volta ao início, mágica), e o jogo oferece um **modo Debug**, que permite escolher manualmente a casa de destino em vez de jogar os dados — útil para testar o efeito de cada casa especial.

## Tecnologias utilizadas

| Tecnologia | Versão |
|---|---|
| Java (JDK) | 21 |
| JavaFX | 21.0.6 |
| Maven | Maven Wrapper incluso (`mvnw` / `mvnw.cmd`) |
| javafx-maven-plugin | 0.0.8 |
| maven-compiler-plugin | 3.13.0 |
| JUnit (Jupiter) | 5.12.1 |
| FXML + Scene Builder | para a construção das telas |

O projeto é **modular** (utiliza `module-info.java`), o que permite gerar um executável nativo via `jlink`, sem exigir que o Java esteja instalado na máquina de destino.

## Estrutura do projeto

```
jogo/
├── pom.xml
└── src/
    ├── main/
    │   ├── java/com/example/jogo/
    │   │   ├── Main.java              → ponto de entrada da aplicação
    │   │   ├── Launcher.java          → launcher auxiliar (para jar executável)
    │   │   ├── Jogo.java              → orquestra as regras e os turnos
    │   │   ├── Tabuleiro.java         → monta as 41 casas do tabuleiro
    │   │   ├── Dado.java
    │   │   ├── jogador/
    │   │   │   ├── Jogador.java       → classe abstrata
    │   │   │   ├── JogadorNormal.java
    │   │   │   ├── JogadorSortudo.java
    │   │   │   └── JogadorAzarado.java
    │   │   ├── casa/
    │   │   │   ├── Casa.java          → classe abstrata
    │   │   │   ├── CasaNormal.java
    │   │   │   ├── CasaPerdeVez.java
    │   │   │   ├── CasaSurpresa.java
    │   │   │   ├── CasaSorte.java
    │   │   │   ├── CasaVoltaInicio.java
    │   │   │   └── CasaMagica.java
    │   │   └── ui/
    │   │       ├── TelaInicialController.java
    │   │       ├── TelaJogoController.java
    │   │       └── TelaResultadoController.java
    │   └── resources/com/example/jogo/
    │       ├── TelaInicial.fxml
    │       ├── TelaJogo.fxml
    │       ├── TelaResultado.fxml
    │       └── image/                 → ícones das casas especiais e pinos dos jogadores
    └── test/java/com/example/jogo/    → testes unitários (JUnit)
```

## Regras das casas especiais

| Casas | Efeito |
|---|---|
| 10, 25, 38 | Perde a vez: o jogador não joga na próxima rodada. |
| 13 | Surpresa: o jogador tira uma carta aleatória que muda seu tipo. |
| 5, 15, 30 | Sorte: avança 3 casas extras (exceto jogador Azarado). |
| 17, 27 | Volta ao início: o jogador escolhe um adversário para voltar à casa 0. |
| 20, 35 | Mágica: troca de posição com quem estiver mais atrás no jogo. |

Se os dois dados caírem com valores iguais, o jogador anda normalmente e **joga novamente**, respeitando todas as regras acima.

## Como executar

### Pré-requisitos

- JDK 21 ou superior instalado
- Conexão à internet na primeira execução (para o Maven baixar as dependências)

### Rodando com o Maven Wrapper

No diretório raiz do projeto (onde está o `pom.xml`):

```bash
# Linux/macOS
./mvnw clean javafx:run

# Windows
mvnw.cmd clean javafx:run
```

### Gerando um executável standalone (jlink)

```bash
./mvnw clean javafx:jlink
```

Isso gera a pasta `target/app/` e o arquivo `target/app.zip`, contendo um executável nativo que **não exige Java instalado** na máquina de destino. O pacote gerado é específico do sistema operacional em que foi criado.

Para rodar o executável gerado:

```bash
# Linux/macOS
./target/app/bin/app

# Windows
target\app\bin\app.bat
```

## Modo Debug

Ao marcar a opção **Modo Debug** na tela inicial, em vez de jogar os dados, o usuário informa manualmente o número da casa para onde o jogador da vez deve ir — facilitando testar o comportamento de cada casa especial sem depender do resultado aleatório dos dados.
