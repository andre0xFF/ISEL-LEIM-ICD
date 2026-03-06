# Tutorial 1 — Agenda: XML → HTML com Java, DOM e XSLT

Transformação de um ficheiro XML (`agenda.xml`) numa página HTML estilizada, usando Java/DOM para aplicar lógica de negócio e XSLT para a apresentação.

## Estrutura do Projeto

```
tutorial-1/
├── README.md
├── .gitignore
├── data/                         # Ficheiros XML (dados fonte)
│   ├── agenda.xml
│   ├── calendario_academico.xml
│   └── calendario_avaliacoes.xml
├── xsl/                          # Folhas de estilos XSLT (regras de transformação)
│   └── agenda.xsl
├── src/                          # Código fonte Java
│   └── TransformAgenda.java
├── build/                        # Ficheiros .class compilados (gitignored)
└── output/                       # Ficheiros HTML gerados (gitignored)
```

## Pré-requisitos

- **Java JDK 8+** (utiliza a API `javax.xml.transform`, incluída no JDK)

Não são necessárias dependências externas.

## Como Executar

Todos os comandos devem ser executados a partir da raiz de `tutorial-1/`:

```sh
# 1. Compilar o programa Java (output para build/)
javac -d build src/TransformAgenda.java

# 2. Executar a transformação (usa valores por omissão)
java -cp build TransformAgenda

# 3. Abrir o ficheiro gerado no browser
# Linux
xdg-open output/agenda.html
# macOS
open output/agenda.html
```

### Argumentos opcionais

```sh
java -cp build TransformAgenda [xml-file] [xsl-file] [output-html]
```

| Argumento     | Valor por omissão    | Descrição                        |
| ------------- | -------------------- | -------------------------------- |
| `xml-file`    | `data/agenda.xml`    | Caminho para o ficheiro XML      |
| `xsl-file`    | `xsl/agenda.xsl`     | Caminho para a folha de estilos  |
| `output-html` | `output/agenda.html` | Caminho para o ficheiro de saída |

Exemplo com argumentos personalizados:

```sh
java -cp build TransformAgenda data/calendario_academico.xml xsl/calendario.xsl output/calendario.html
```

## Descrição dos Ficheiros

### `data/agenda.xml` — Dados

Ficheiro XML com três secções principais:

- **`<eventos>`** — Lista de eventos com título, datas de início/fim, local e categoria (atributo opcional)
- **`<tarefas>`** — Lista de tarefas com descrição, prazo, prioridade e estado de conclusão (atributo `concluida`)
- **`<inscricoes>`** — Inscrições em turmas com as respetivas unidades curriculares

### `xsl/agenda.xsl` — Regras de Transformação

Folha de estilos XSLT 1.0 que define a transformação XML → HTML. Principais características:

- **Template matching** — cada secção (`eventos`, `tarefas`, `inscricoes`) tem o seu próprio template
- **Formatação de datas** — templates utilitários convertem `YYYY-MM-DDThh:mm:ss` → `DD/MM/YYYY hh:mm` (formato português)
- **Estilos condicionais** — badges com cores diferentes por categoria/prioridade; tarefas mostram ✓ Concluída / ◯ Pendente
- **CSS embutido** — o HTML gerado é autónomo, sem dependências externas
- **Tratamento de dados em falta** — campos opcionais (como `local` ou `@categoria`) apresentam placeholders em itálico

### `src/TransformAgenda.java` — Lógica de Negócio + Transformação

Programa Java que combina processamento DOM com transformação XSLT em três fases:

1. **Parse** — carrega o XML num `Document` DOM (`DocumentBuilder.parse()`)
2. **Enrich** — aplica lógica de negócio manipulando a árvore DOM
3. **Transform** — passa o DOM enriquecido (`DOMSource`) ao XSLT para gerar HTML

```java
// 1. Parse XML into DOM
Document doc = builder.parse(xml);

// 2. Apply business logic (enrich the DOM)
enrichEventos(doc);
enrichTarefas(doc);
enrichInscricoes(doc);

// 3. Transform enriched DOM with XSLT (DOMSource instead of StreamSource)
transformer.transform(new DOMSource(doc), new StreamResult(new File(outFile)));
```

#### Lógica de negócio aplicada

| Secção         | Campo adicionado  | Tipo     | Descrição                                                    |
| -------------- | ----------------- | -------- | ------------------------------------------------------------ |
| **Eventos**    | `<duracao>`       | Elemento | Duração calculada entre início e fim (e.g. `9h00m`, `1h30m`) |
| **Tarefas**    | `<diasRestantes>` | Elemento | Dias até ao prazo (negativo = atrasada)                      |
| **Tarefas**    | `@atrasada`       | Atributo | `"true"` se prazo ultrapassado e tarefa não concluída        |
| **Inscrições** | `@totalUCs`       | Atributo | Contagem de unidades curriculares na inscrição               |

> **Nota:** os campos computados seguem a mesma convenção atributo/elemento descrita na secção [Atributos vs. Elementos](#atributos-vs-elementos-em-xml) — flags e classificadores como atributos, dados de conteúdo como elementos.

## Arquitetura

```
┌───────────┐      ┌────────────────┐      ┌───────────┐      ┌────────────┐
│ data/*.xml│─────▶│  Java (DOM)    │─────▶│ xsl/*.xsl │─────▶│output/*.html│
│ (dados)   │      │ lógica negócio │      │ (apres.)  │      │ (resultado)│
└───────────┘      └────────────────┘      └───────────┘      └────────────┘
                   enrich/filter/compute
```

| Componente | Pasta     | Papel                                                     |
| ---------- | --------- | --------------------------------------------------------- |
| **XML**    | `data/`   | Fonte de dados estruturados (dados brutos)                |
| **Java**   | `src/`    | Lógica de negócio — enriquece o DOM com campos calculados |
| **XSLT**   | `xsl/`    | Lógica de apresentação — define _como_ gerar o HTML       |
| **HTML**   | `output/` | Resultado final, visualizável em qualquer browser         |

### Separação de responsabilidades

- **Java** trata do _quê_ — decisões de negócio, cálculos, validações (e.g. "esta tarefa está atrasada?")
- **XSLT** trata do _como_ — formatação, layout, estilos (e.g. "tarefas atrasadas aparecem com badge vermelho")
- **XML** é o contrato entre ambos — o Java enriquece-o, o XSLT consome-o

## Atributos vs. Elementos em XML

Uma decisão frequente ao desenhar um documento XML é: quando usar **atributos** e quando usar **elementos**?

### Critérios Gerais

| Critério              | Atributo                               | Elemento                                              |
| --------------------- | -------------------------------------- | ----------------------------------------------------- |
| **Natureza do valor** | Metadado, classificador, flag          | Conteúdo principal, dados do domínio                  |
| **Complexidade**      | Valor simples e atómico                | Valor que pode ser estruturado ou longo               |
| **Repetição**         | Nunca se repete (um atributo por nome) | Pode repetir-se (múltiplos filhos)                    |
| **Extensibilidade**   | Difícil de estender no futuro          | Fácil de acrescentar sub-elementos                    |
| **Ordem**             | Sem ordem garantida                    | Ordem preservada no documento                         |
| **Valores possíveis** | Texto simples (sem markup)             | Pode conter texto, outros elementos ou conteúdo misto |

### Regra prática

> **Se descreve _o que a coisa é_ → atributo. Se descreve _o que a coisa contém_ → elemento.**

### Exemplos em `data/agenda.xml`

**Atributos** — classificadores e flags:

```xml
<evento categoria="Conferência">     <!-- tipo/classificação do evento -->
<tarefa concluida="true">            <!-- flag booleana de estado -->
<inscricao turma="LEIM51D">          <!-- identificador da turma -->
```

Estes valores são:

- Simples (uma palavra ou booleano)
- Metadados que qualificam o elemento pai
- Não precisam de sub-estrutura

**Elementos** — conteúdo e dados do domínio:

```xml
<evento categoria="Conferência">
  <titulo>Conferência de Tecnologia 2026</titulo>   <!-- conteúdo textual longo -->
  <inicio>2026-04-10T09:00:00</inicio>              <!-- dado estruturado (data) -->
  <fim>2026-04-10T18:00:00</fim>
  <local>Centro de Congressos de Lisboa</local>      <!-- pode crescer em complexidade -->
</evento>
```

Estes valores são:

- O conteúdo principal da entidade
- Potencialmente longos ou estruturados
- Candidatos a futura extensão (e.g., `<local>` poderia vir a ter `<morada>`, `<sala>`, `<coordenadas>`)

**Elementos repetidos** — impossível com atributos:

```xml
<inscricao turma="LEIM51D">
  <abrUC>IECD</abrUC>    <!-- múltiplas UCs na mesma inscrição -->
  <abrUC>SMI</abrUC>     <!-- atributos não podem repetir-se -->
  <abrUC>DAM</abrUC>
</inscricao>
```

### Anti-padrões a evitar

❌ **Tudo em atributos** — perde legibilidade e extensibilidade:

```xml
<evento categoria="Conferência" titulo="Conferência de Tecnologia 2026"
        inicio="2026-04-10T09:00:00" fim="2026-04-10T18:00:00"
        local="Centro de Congressos de Lisboa"/>
```

❌ **Tudo em elementos** — metadados misturados com conteúdo:

```xml
<evento>
  <categoria>Conferência</categoria>   <!-- isto é um classificador, não conteúdo -->
  <titulo>Conferência de Tecnologia 2026</titulo>
</evento>
```

Ambos funcionam tecnicamente, mas o equilíbrio usado em `agenda.xml` é o mais idiomático.

## Secções do HTML Gerado

| Secção        | Conteúdo                                                                          |
| ------------- | --------------------------------------------------------------------------------- |
| 📌 Eventos    | Tabela com título, categoria (badge), datas, **duração** e local                  |
| ✅ Tarefas    | Tabela com descrição, prazo, **dias restantes**, prioridade e estado (⚠ Atrasada) |
| 📚 Inscrições | Tabela com turma, unidades curriculares (tags) e **total de UCs**                 |
