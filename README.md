
# Intuitive Care - Teste para Estágio

## Descrição

Este repositório contém a implementação do desafio proposto pela Intuitive Care para a vaga de estágio. O projeto foi desenvolvido em **Java** e tem como objetivo baixar, processar e analisar dados extraídos de um arquivo PDF, salvando os resultados em um arquivo CSV e compactando-o. Além da criação da estrutura relacional e import dos dados em csv em um banco para realização de querys personalizadas.

## Tecnologias Utilizadas

- Java 17

- JSoup (para manipulação de HTML)

- Apache PDFBox (para extração de dados de PDFs)

- Tabula (para extração de tabelas de PDFs)

- OpenCSV (para geração de arquivos CSV)

- Java Util Zip (para compactação do arquivo CSV)

- Maven (para gerenciamento de dependências)

- Docker Compose (para configuração e execução do banco de dados PostgreSQL)

- PostgreSQL (banco de dados utilizado no projeto)

- DBeaver (realizar a conexão com o PostgreSQL)

## Estrutura do Projeto

```
INTUITIVE-CARE-TEST
|── database/                       # Contém os arquivos .sql para criação das tabelas, imports dos dados e query com as respostas do desafio
│── src/
│   ├── main/
│       ├── java/com/georges/intuitive_care_test/
│           ├── DataTransform.java  # Responsável pela extração da tabela do PDF, gerar e formatar a tabela em csv
│           ├── WebScraper.java     # Responsável por fazer o download dos arquivos pdf e compactar os arquivos
│                                   
│                                   
│── docker-compose.yml              # Configuração do PostgreSQL com Docker Compose
│── README.md
│── pom.xml                         # Gerenciador de dependências do Maven
```

## Como Executar

### Pré-requisitos

- Ter o **Java 21** instalado
- Ter o **Maven** instalado
- Ter o **Docker** e **Docker Compose** instalados

### Passos

1. Clone o repositório:
   ```sh
   git clone https://github.com/GeorgesCarmo/intuitive-care-test.git
   ```
2. Acesse a pasta do projeto:
   ```sh
   cd intuitive-care-test
   ```
3. Inicie o banco de dados PostgreSQL com Docker Compose:
   ```sh
   docker-compose up -d
   ```
4. Execute o build do projeto:
   ```sh
   mvn dependency:build-classpath -Dmdep.outputFile=classpath.txt
   ```
5. Execute a aplicação WebScraper:
   ```sh
   java -cp target/classes:$(cat classpath.txt) com.georges.intuitive_care_test.WebScraper
   ```
   se estiver no Windows use:
   ```sh
   java -cp target/classes;$(type classpath.txt) com.georges.intuitive_care_test.WebScraper
   ```
6. Execute a aplicação DataTransform:
   ```sh
   java -cp target/classes:$(cat classpath.txt) com.georges.intuitive_care_test.DataTransform
   ```
   se estiver no Windows use:
   ```sh
   java -cp target/classes;$(type classpath.txt) com.georges.intuitive_care_test.DataTransform
   ```
## Banco de dados
As querys slq estão na pasta database na raiz do projeto. Observe que o docker-compose possui um volumes para que os arquivos csv previamente baixados sejam copiados para o container docker. No meu caso o diretório **/home/georges/Downloads** foi copiado para **/dados** no container.

- Para confirmar se os arquivos foram copiados, execute os comandos:
  ```sh
  docker exec -it intuitive-care-test-db-1 bash
  ```
  ```sh
  ls /dados
  ```
Em seguida:
- Conecte-se ao banco postgres: localhost, porta 5432, usuário e senha: postgres. 
- Execute as querys na sequência: create->import->query

## Funcionalidades

- Download de arquivos PDF
- Extração de tabelas do PDF
- Conversão para CSV
- Substituição de abreviações no CSV
- Compactação dos arquivos PDF e CSV

## Melhorias Futuras

- Implementação de uma interface gráfica para facilitar a execução
- Melhor tratamento de erros e validação de dados
- Testes unitários para garantir a estabilidade do sistema
- Adição de logging para facilitar depuração

## Contato

Desenvolvido por **Georges do Carmo Pereira**. Para dúvidas ou sugestões, entre em contato:

- **Email:** [georgesdc13@gmail.com](mailto:georgesdc13@gmail.com)
- **LinkedIn:** [Meu LinkedIn](https://www.linkedin.com/in/georges-do-carmo)
- **Telefone:** 61 99395-6720
---

*Projeto desenvolvido para a seleção da Intuitive Care.*


