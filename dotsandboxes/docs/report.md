# Dots and Boxes. Relatório do Projeto

**Instituto Superior de Engenharia de Lisboa**  
**Licenciatura em Engenharia Informática e Multimédia**  
**Unidade Curricular:** Infraestruturas Computacionais Distribuídas  
**Ano Letivo:** 2025/2026

**Grupo:** <!-- TODO: Preencher número do grupo -->

**Autores:**

<!-- TODO: Preencher o número de aluno do Daniel Santos. -->

| Nome          | Número de Aluno |
| ------------- | --------------- |
| Andre Fonseca | 39758           |
| Daniel Santos |                 |

---

## Índice

1. [Introdução](#1-introdução)
2. [Descrição do Jogo](#2-descrição-do-jogo)
3. [Arquitetura do Sistema](#3-arquitetura-do-sistema)
   - 3.1 [Visão Geral](#31-visão-geral)
   - 3.2 [Servidor](#32-servidor)
   - 3.3 [Cliente](#33-cliente)
   - 3.4 [Comunicação](#34-comunicação)
4. [Modelo de Domínio](#4-modelo-de-domínio)
   - 4.1 [Lógica de Jogo](#41-lógica-de-jogo)
   - 4.2 [Gestão de Utilizadores](#42-gestão-de-utilizadores)
5. [Protocolo de Comunicação](#5-protocolo-de-comunicação)
   - 5.1 [Formato das Mensagens (XML)](#51-formato-das-mensagens-xml)
   - 5.2 [Validação com XSD](#52-validação-com-xsd)
   - 5.3 [Catálogo de Comandos](#53-catálogo-de-comandos)
6. [Padrões de Desenho](#6-padrões-de-desenho)
7. [Persistência de Dados](#7-persistência-de-dados)
8. [Interface Gráfica (JavaFX)](#8-interface-gráfica-javafx)
   - 8.1 [Navegação entre Vistas](#81-navegação-entre-vistas)
   - 8.2 [Descrição dos Ecrãs](#82-descrição-dos-ecrãs)
9. [Instruções de Execução](#9-instruções-de-execução)
10. [Conclusão](#10-conclusão)

---

## 1. Introdução

O Dots and Boxes é um jogo clássico de lápis e papel para dois jogadores, onde o objetivo é fechar mais caixas do que o adversário. Este projeto tem como objetivo desenvolver uma aplicação cliente-servidor que permita a dois jogadores disputarem partidas em tempo real, utilizando conceitos aprendidos no âmbito da unidade curricular Infraestruturas Computacionais Distribuídas do curso de Licenciatura em Engenharia Informática e Multimédia do Instituto Superior de Engenharia de Lisboa.

O presente relatório descreve a arquitetura do sistema, o modelo de domínio, o protocolo de comunicação, os padrões de desenho utilizados, a persistência de dados e a interface gráfica implementada. Além disso, são fornecidas instruções para compilar e executar a aplicação, bem como uma conclusão que reflete sobre o desenvolvimento do projeto e as aprendizagens retiradas.

---

## 2. Descrição do Jogo

Dots and Boxes é um jogo de lápis e papel para dois jogadores. O tabuleiro consiste numa grelha de pontos (por omissão, 4×4 pontos, formando 3×3 caixas). Em cada turno, um jogador coloca uma linha horizontal ou vertical entre dois pontos adjacentes. Quando um jogador completa o quarto lado de uma caixa, essa caixa fica na sua posse e o jogador ganha um turno extra. O jogo termina quando todas as linhas estão colocadas; ganha o jogador com mais caixas.

**Regras implementadas:**

- Dois jogadores (marcadores `A` e `B`).
- Grelha configurável (padrão: 4×4 pontos → 3×3 caixas).
- Turno alternado; turno extra ao fechar uma caixa.
- O jogo termina quando o tabuleiro está cheio; empate se os pontos forem iguais.

---

## 3. Arquitetura do Sistema

### 3.1 Visão Geral

O sistema segue uma arquitetura **cliente-servidor** sobre TCP. O servidor gere a lógica de jogo, a autenticação e a persistência de dados. Cada cliente liga-se ao servidor, envia comandos (pedidos) e recebe comandos (respostas). A comunicação é feita através de mensagens XML, uma por linha, validadas contra um esquema XSD.

```
┌──────────────┐         TCP / XML           ┌──────────────────┐
│              │  ◄──────────────────────►   │                  │
│   Cliente    │       Comandos (XML)        │    Servidor      │
│  (JavaFX)    │                             │  (ServerSocket)  │
│              │  ◄──────────────────────►   │                  │
└──────────────┘                             └──────────────────┘
 UI / FXML                                    Lógica de Jogo
 ViewManager                                  Persistência de dados XML
 ClientController                             ServerController
```

### 3.2 Servidor

O servidor (`ServerApplication`) é o ponto de entrada do lado do servidor. Trata-se de um **servidor concorrente**: para cada cliente que se liga, é criada uma thread dedicada (`ClientHandler`) que processa os seus pedidos de forma independente, permitindo que múltiplos clientes estejam ligados em simultâneo. Cria e liga as seguintes dependências:

| Componente            | Responsabilidade                                                                                                                                                |
| --------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `Server`              | Escuta no porto 8000 e aceita ligações TCP. Para cada ligação cria um `SimpleSocket` e lança um `ClientHandler` numa thread dedicada.                           |
| `ServerController`    | Hub de lógica do jogo. Processa todos os comandos recebidos (autenticação, criação de utilizadores, gestão de jogo). Implementa `Controller` e `Authenticator`. |
| `SimpleSocketManager` | Coordenador central. Regista sockets ligados, delega o encaminhamento de comandos ao `SimpleSocketRouter` e expõe métodos de escrita.                           |
| `SimpleSocketRouter`  | Despacha comandos. Mapeia a classe de cada comando ao controller correspondente, verifica autenticação e invoca `command.execute()`.                            |
| `ClientHandler`       | Thread de leitura por ligação. Lê linhas XML do socket, deserializa para comandos e encaminha-os pelo `SimpleSocketManager`.                                    |
| `SchemaValidator`     | Valida as mensagens XML de entrada contra o `Commands.xsd`.                                                                                                     |

### 3.3 Cliente

O cliente (`ClientApplication`) é um **fat client** — uma aplicação JavaFX autónoma que contém lógica de apresentação, validação local de dados (e.g., campos de registo) e mantém uma cópia local do estado do tabuleiro para efeitos de renderização. O servidor permanece a fonte de verdade para toda a lógica de jogo.

| Componente          | Responsabilidade                                                                                                                                                                      |
| ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `ClientApplication` | Ponto de entrada da interface gráfica JavaFX. monta o pipeline de serialização, cria o `Client`, o `ClientController` e o `ViewManager`.                                              |
| `Client`            | Cria a ligação TCP ao servidor (localhost:8000), lança um `ClientHandler` para leitura e expõe `sendCommand()`.                                                                       |
| `ClientController`  | Mediador central. Mantém o estado de sessão (utilizador autenticado, jogo corrente) e traduz acções do UI em comandos de rede, bem como respostas do servidor em callbacks para o UI. |
| `ViewManager`       | Gestor de navegação. Carrega FXML, injecta dependências nos controllers e troca a `Scene` no `Stage`.                                                                                 |
| `GameEventListener` | Interface _observer_. define callbacks (`onAuthenticated`, `onGameJoined`, `onLinePlaced`, etc.) que os controllers de UI implementam.                                                |

### 3.4 Comunicação

A comunicação baseia-se num protocolo de **mensagens XML sobre TCP (porto 8000)**, uma mensagem por linha. O fluxo é:

1. O cliente serializa um comando para XML (`CommandSerializer.serialize()`).
2. A string XML é enviada como uma linha através da socket.
3. O servidor lê a linha, valida-a contra o XSD (`SchemaValidator`) e deserializa-a (`CommandSerializer.deserialize()`).
4. O `SimpleSocketRouter` encontra o controller associado ao tipo de comando, verifica autenticação se necessário, e executa o comando.
5. O servidor responde com um comando de resposta, seguindo o mesmo processo em sentido inverso.

De notar que o `SimpleSocket` abstrai toda a lógica de leitura/escrita de linhas, e o `CommandSerializer` é responsável por toda a serialização/deserialização, garantindo que os comandos são convertidos para XML e vice-versa de forma consistente, em ambos os lados (cliente e servidor).

## 4. Modelo de Domínio

### 4.1 Lógica de Jogo

| Classe         | Descrição                                                                                                                                                 |
| -------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `Game`         | Orquestra uma sessão de jogo. Mantém dois `Player`, um `Board`, o jogador corrente, o estado do ciclo de vida (`GameState`) e determina o vencedor.       |
| `Board`        | Representa a grelha. Regista as linhas colocadas (`Set<Line>`) e a posse das caixas (`PlayerMarker[][]`). Detecta o fecho de caixas ao colocar uma linha. |
| `Line`         | _Value object_ (record). Par imutável de dois `Dot` adjacentes (horizontal ou vertical). Normaliza a ordem para garantir igualdade consistente.           |
| `Dot`          | _Value object_ (record). Coordenada `(row, col)` no tabuleiro.                                                                                            |
| `Player`       | Contém um `PlayerMarker` e uma pontuação mutável (caixas conquistadas).                                                                                   |
| `PlayerMarker` | Enum com dois valores: `A` e `B`.                                                                                                                         |
| `GameState`    | Enum do ciclo de vida: `CLOSED` → `OPEN` → `STARTED` → `FINISHED`.                                                                                        |

<!-- TODO: Incluir diagrama de classes UML -->

### 4.2 Gestão de Utilizadores

| Classe                 | Descrição                                                                                                                   |
| ---------------------- | --------------------------------------------------------------------------------------------------------------------------- |
| `User`                 | Record com `username` (3–20 caracteres) e `password` (8–20 caracteres).                                                     |
| `Profile`              | Record com `username`, `nationality`, `age`, `photo` (Base64), `wins` e `losses`.                                           |
| `UserServerRepository` | Repositório em memória backed por `XmlFileStore`. CRUD de utilizadores e perfis, com persistência imediata em cada mutação. |
| `Authenticator`        | Interface que expõe `isAuthenticated(UUID socketId)`.                                                                       |

---

## 5. Protocolo de Comunicação

### 5.1 Formato das Mensagens (XML)

Todas as mensagens seguem a estrutura:

```
<Command>
  <NomeDoComando>
    <!-- campos do comando -->
  </NomeDoComando>
</Command>
```

O elemento raiz é sempre `<Command>` e contém exatamente um elemento filho que identifica o tipo de comando. A serialização e deserialização são feitas com a DOM API (`javax.xml.parsers`), sem recurso a frameworks como JAXB.

### 5.2 Validação com XSD

As mensagens são validadas contra os seguintes esquemas:

| Esquema        | Função                                                                                              |
| -------------- | --------------------------------------------------------------------------------------------------- |
| `Commands.xsd` | Define a estrutura de todos os 16 tipos de comando trocados entre cliente e servidor.               |
| `Users.xsd`    | Valida o ficheiro `Users.xml` de persistência (username: 3–20 chars, password: 8–20 chars).         |
| `Profiles.xsd` | Valida o ficheiro `Profiles.xml` de persistência (username, nationality, age, photo, wins, losses). |

### 5.3 Catálogo de Comandos

#### Gestão de Utilizadores

| Comando                    | Direção            | Campos                                                                    | Descrição                                   |
| -------------------------- | ------------------ | ------------------------------------------------------------------------- | ------------------------------------------- |
| `AuthenticateUser`         | Cliente → Servidor | `username`, `password`                                                    | Autenticação de um utilizador existente.    |
| `AuthenticateUserResponse` | Servidor → Cliente | `username`, `authenticated`                                               | Resultado da autenticação.                  |
| `CreateUser`               | Cliente → Servidor | `username`, `password`                                                    | Registo de um novo utilizador.              |
| `CreateUserResponse`       | Servidor → Cliente | `username`, `created`                                                     | Resultado do registo.                       |
| `ReadUserProfile`          | Cliente → Servidor | _(nenhum)_                                                                | Pedido do perfil do utilizador autenticado. |
| `ReadUserProfileResponse`  | Servidor → Cliente | `hasProfile`, `username`, `nationality`, `age`, `photo`, `wins`, `losses` | Dados do perfil.                            |
| `UpdateUser`               | Cliente → Servidor | `nationality`, `age`, `photo`                                             | Atualização do perfil.                      |

#### Gestão de Jogo

| Comando             | Direção            | Campos                                                      | Descrição                                                         |
| ------------------- | ------------------ | ----------------------------------------------------------- | ----------------------------------------------------------------- |
| `JoinGame`          | Cliente → Servidor | _(nenhum)_                                                  | Pedido para entrar/criar um jogo.                                 |
| `JoinGameResponse`  | Servidor → Cliente | `joined`, `marker`, `boardRows`, `boardCols`                | Resultado. Inclui o marcador atribuído e dimensões do tabuleiro.  |
| `PlaceLine`         | Cliente → Servidor | `dot1Row`, `dot1Col`, `dot2Row`, `dot2Col`                  | Colocação de uma linha entre dois pontos.                         |
| `PlaceLineResponse` | Servidor → Cliente | `placed`, coordenadas, `boxesClosed`, `marker`, `extraTurn` | Resultado da jogada. indica se fechou caixas e se há turno extra. |
| `LeaveGame`         | Cliente → Servidor | _(nenhum)_                                                  | Pedido para abandonar o jogo.                                     |
| `LeaveGameResponse` | Servidor → Cliente | `left`                                                      | Confirmação de saída.                                             |
| `GameOver`          | Servidor → Cliente | `hasWinner`, `winnerMarker`, `scoreA`, `scoreB`             | Notificação de fim de jogo com resultados.                        |

#### Eventos de Ligação

| Comando               | Descrição                                              |
| --------------------- | ------------------------------------------------------ |
| `ConnectedCommand`    | Evento interno disparado quando um cliente se liga.    |
| `DisconnectedCommand` | Evento interno disparado quando um cliente se desliga. |

> **Nota sobre autenticação:** Os comandos `AuthenticateUser` e `CreateUser` não requerem autenticação prévia. Todos os restantes comandos (jogo, perfil) são rejeitados pelo `SimpleSocketRouter` se o cliente ainda não estiver autenticado.

---

## 6. Padrões de Desenho

O projeto faz uso dos seguintes padrões de desenho:

| Padrão                 | Onde é Aplicado                             | Descrição                                                                                                                                                          |
| ---------------------- | ------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| **Command**            | `SimpleSocketCommand` e todas as subclasses | Cada comando encapsula um pedido/resposta como objeto. O comando sabe serializar-se para XML (`toXml`/`fromXml`) e executar-se contra um _receiver_ (`execute()`). |
| **Observer**           | `GameEventListener`                         | Interface que define callbacks para eventos do servidor. Os controllers de UI registam-se como _listeners_ no `ClientController` para receber notificações.        |
| **MVC**                | Camada de UI (JavaFX)                       | Separação entre Model (domínio), View (FXML) e Controller (classes `*Controller`).                                                                                 |
| **Factory / Registry** | `CommandRegistry` + `CommandSerializer`     | Registo de fábricas (_lambdas_) que instanciam comandos a partir de elementos XML, permitindo deserialização polimórfica.                                          |
| **Mediator**           | `ClientController` / `ServerController`     | Centralizam a comunicação entre a camada de rede e a camada de UI/lógica, evitando acoplamento direto.                                                             |
| **Repository**         | `UserServerRepository`                      | Abstrai o acesso a dados de utilizadores e perfis, com lógica de cache em memória e persistência em XML.                                                           |

---

## 7. Persistência de Dados

A persistência é feita em ficheiros XML no servidor, geridos pela classe `XmlFileStore`:

| Ficheiro       | Conteúdo                                                                                   |
| -------------- | ------------------------------------------------------------------------------------------ |
| `Users.xml`    | Lista de utilizadores com `username` e `password`.                                         |
| `Profiles.xml` | Lista de perfis com `username`, `nationality`, `age`, `photo` (Base64), `wins` e `losses`. |

A classe `UserServerRepository` carrega os dados para memória no arranque e persiste (reescreve o ficheiro completo) em cada operação de escrita. A validação dos ficheiros é assegurada pelos esquemas `Users.xsd` e `Profiles.xsd`.

<!-- TODO: Ponderar mencionar limitações desta abordagem (e.g., concorrência, escalabilidade). e possiveis alternativas -->

---

## 8. Interface Gráfica (JavaFX)

A interface gráfica do cliente foi desenvolvida em JavaFX com layouts definidos em FXML e controladores Java.

### 8.1 Navegação entre Vistas

O `ViewManager` gere a navegação entre ecrãs num único `Stage`, carregando o FXML correspondente e injetando o `ClientController` e a própria referência ao `ViewManager` em cada controller de vista.

```
LoginView ──[Login sucesso]──► MainMenuView ──[Play]──► GameView
    │                              │                        │
    │ [Register]                   │ [Profile]              │ [Leave]
    ▼                              ▼                        │
RegisterView ──[sucesso]──► LoginView      ProfileView      │
                                              │ [Back]      │
                                              ▼             ▼
                                         MainMenuView ◄─────┘
```

Cada controller de vista implementa `ViewController` (contrato de injeção de dependências e caminho FXML) e `GameEventListener` (callbacks de eventos do servidor). Apenas um _listener_ está ativo de cada vez. o ecrã visível.

### 8.2 Descrição dos Ecrãs

#### Login (`LoginView`)

- Campos: nome de utilizador e palavra-passe.
- Ações: Iniciar sessão, Registar, Sair.
- Em caso de sucesso, navega para o menu principal.

#### Registo (`RegisterView`)

- Campos: nome de utilizador, palavra-passe e confirmação.
- Validação local: comprimento do username (3–20), password (8–20) e correspondência.
- Em caso de sucesso, regressa ao ecrã de login.

#### Menu Principal (`MainMenuView`)

- Mostra uma mensagem de boas-vindas com o nome do utilizador.
- Ações: Jogar, Perfil, Sair.

#### Jogo (`GameView`)

- Quadro de pontuação no topo (Jogador A, turno corrente, Jogador B).
- Tabuleiro dinâmico (`GridPane`) com pontos, botões de linha (horizontal/vertical) e painéis de caixa.
- O tabuleiro é construído programaticamente com base nas dimensões recebidas do servidor.
- Mantém uma cópia local do `Board` para efeitos visuais; o servidor é a fonte de verdade.
- As caixas fechadas são coloridas de acordo com o marcador do jogador.
- Ao terminar, mostra o resultado (vitória, derrota ou empate) e desativa o input.

#### Perfil (`ProfileView`)

- Grelha com campos: username, nacionalidade, idade, foto (ImageView), vitórias e derrotas.
- Modo de visualização e modo de edição (com botões Editar, Guardar, Escolher Foto).
- A foto é codificada/descodificada em Base64.

---

## 9. Instruções de Execução

### Pré-requisitos

- Java 17+
- Maven 3.8+

### Compilar

```sh
mvn clean compile
```

### Executar o Servidor

```sh
mvn javafx:run -pl dotsandboxes -Djavafx.mainClass=pt.isel.icd.ServerApplication
```

<!-- TODO: Confirmar/ajustar o comando exato de arranque do servidor. -->

### Executar o Cliente

```sh
mvn javafx:run -pl dotsandboxes
```

> **Nota:** O cliente liga-se a `localhost:8000` por omissão. O servidor deve estar a correr antes de iniciar o cliente.

<!-- TODO: Confirmar porto e parâmetros de configuração. -->

---

## 10. Conclusão

<!-- TODO: Escrever a conclusão. O enunciado EXIGE que esta secção aborde explicitamente: -->
<!--       vantagens e desvantagens da arquitetura/solução proposta, destacando: -->

<!-- 1. EXPANSIBILIDADE — Facilidade de adicionar novos comandos (basta criar classe + registar no CommandRegistry), -->
<!--    possibilidade de suportar múltiplos jogos simultâneos, novos tipos de jogo, etc. -->
<!--    Limitação: protocolo proprietário dificulta interoperabilidade com outros clientes. -->

<!-- 2. TOLERÂNCIA ÀS FALHAS — O que acontece se o servidor cair? E se um cliente desligar a meio do jogo? -->
<!--    O DisconnectedCommand deteta desconexões. Limitação: não há reconexão automática nem persistência -->
<!--    do estado de jogo (se o servidor reiniciar, os jogos em curso perdem-se). -->

<!-- 3. SEGURANÇA — Passwords guardadas em texto claro no Users.xml (sem hashing). -->
<!--    Comunicação em texto claro (sem TLS/SSL). Validação XSD protege contra mensagens malformadas. -->
<!--    Melhoria futura: hashing de passwords (bcrypt), TLS para a ligação TCP. -->

<!-- 4. TRANSPARÊNCIA — O cliente não precisa de saber detalhes da implementação do servidor (mediado pelo -->
<!--    ClientController). A serialização XML e o padrão Command abstraem a complexidade da comunicação. -->

<!-- 5. CONCORRÊNCIA — Servidor concorrente (uma thread por cliente). Limitação: acesso ao estado partilhado -->
<!--    (e.g., lista de jogos, repositório de utilizadores) pode necessitar de sincronização adicional. -->
<!--    No cliente, callbacks do servidor são marshalled para a JavaFX Application Thread via Platform.runLater(). -->

<!-- Outros tópicos a incluir: -->
<!-- - Resumo do trabalho desenvolvido. -->
<!-- - Principais desafios encontrados (e.g., concorrência de threads com JavaFX, serialização XML manual). -->
<!-- - Limitações conhecidas: -->
<!--     * Tempo gasto em cada jogo NÃO é registado (requisito do enunciado não implementado). -->
<!--     * Fotografia não é pedida no registo (apenas editável no perfil, após registo). -->
<!--     * Persistência naïve (reescrita total do XML em cada mutação). -->
<!--     * Apenas um jogo ativo de cada vez no servidor. -->
<!-- - Possíveis melhorias futuras. -->
<!-- - Aprendizagens retiradas do projeto. -->

---

## Anexos

<!-- TODO (OBRIGATÓRIO pelo enunciado): -->
<!-- - Capturas de ecrã com exemplos relevantes que comprovem o bom funcionamento da solução. -->
<!-- - Diagrama de classes UML completo (opcional mas valorizado). -->
