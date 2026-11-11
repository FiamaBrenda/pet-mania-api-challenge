## Ajuste 01: Configuração de Dependências (`pom.xml`)

### O que foi identificado
O projeto inicial estava configurado apenas com o `spring-boot-starter` padrão e o driver do MySQL, não possuindo suporte para exposição de rotas HTTP (API REST) nem mapeamento objeto-relacional (ORM) para persistência de dados.

### O que foi alterado
- **Camada Web (`spring-boot-starter-webmvc`):** Adicionado para habilitar o servidor embutido e permitir a criação de controllers e endpoints REST.
- **Persistência de Dados (`spring-boot-starter-data-jpa`):** Adicionado para integrar o Spring Data JPA e o Hibernate, facilitando a comunicação e o mapeamento das entidades com o banco de dados MySQL.
- **Testes (`webmvc-test` e `data-jpa-test`):** Substituído o starter de teste genérico por módulos específicos para testar requisições web e repositórios JPA.

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-webmvc</artifactId>
    </dependency>
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa-test</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-webmvc-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

## Ajuste 02: Mapeamento e Correções na Entidade (`Pet` e `Enums`)

### O que foi identificado
A classe de entidade `Pet` apresentava diversos erros de compilação e de mapeamento JPA, impedindo a criação da tabela no banco de dados e a leitura correta dos dados enviados via JSON:
- Ausência da anotação `@Entity` e falta das importações do pacote `jakarta.persistence` e dos enums `Sexo` e `Porte`.
- Sintaxe incorreta na anotação `@Table("tb_pet")`, faltando o parâmetro `name`.
- As anotações `@Id` e `@GeneratedValue` estavam posicionadas acima do atributo `nome`, pois o atributo `id` não havia sido declarado.
- Erros de tipagem: uso de `ballena` no lugar de `boolean`/`Boolean`, `getIdade()` retornando `String` em vez de número, e `setNome()` recebendo `int` em vez de `String`.
- Ausência do construtor padrão vazio (exigido pelo JPA/Hibernate) e falta da chave `}` de fechamento da classe.
- Inconsistência de capitalização no enum `Sexo` (`fêmea` em minúsculo).

### O que foi alterado
- **Mapeamento JPA:** Adicionada a anotação `@Entity`, corrigida a sintaxe para `@Table(name = "tb_pet")` e incluídas todas as importações necessárias (`jakarta.persistence.*` e os enums).
- **Chave Primária:** Criado o atributo `private Integer id` com `@Id` e `@GeneratedValue(strategy = GenerationType.IDENTITY)`, além de seus respectivos métodos `getId()` e `setId()`.
- **Tipos Wrapper:** Alterados os tipos primitivos `int` e `boolean` para `Integer` e `Boolean` (`id`, `idade` e `disponivel`). Isso permite que campos vazios enviados como `null` no JSON sejam aceitos sem quebrar a desserialização antes de chegar ao controller.
- **Construtores e Métodos:** Adicionado o construtor vazio `public Pet() {}`, corrigida a assinatura do construtor completo, ajustado o retorno de `getIdade()` para `Integer`, corrigido o parâmetro de `setNome(String nome)` e fechada a estrutura da classe.
- **Padronização de Enum:** Ajustado o valor `fêmea` para `Fêmea` no enum `Sexo` para manter o padrão PascalCase e garantir a conversão correta do JSON.

## Ajuste 03: Criação do Banco de Dados e Configuração (`application.properties`)

### O que foi identificado
O arquivo `application.properties` continha apenas o nome da aplicação (`spring.application.name=petMania`), sem as credenciais de acesso ao MySQL, a URL do banco de dados ou as diretivas do Hibernate para criação automática das tabelas.

### O que foi alterado
- **Banco de Dados (MySQL):** Criado o schema `petmania` com suporte completo a caracteres especiais e acentuação (`utf8mb4`).
- **Conexão Datasource:** Configuradas a URL JDBC apontando para o servidor local (`localhost:3306/petmania`) e as credenciais de autenticação (`username` e `password`).
- **Automação e Debug do JPA/Hibernate:** Habilitada a atualização automática do esquema de tabelas (`ddl-auto=update`) a partir das entidades mapeadas e ativada a exibição formatada das queries SQL no console (`show-sql` e `format_sql`) para facilitar o acompanhamento das operações.

#### Script executado no MySQL
```sql
CREATE DATABASE IF NOT EXISTS petmania
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE petmania;
```

#### Como estava (`application.properties`)
```properties
spring.application.name=petMania
```

#### Como ficou (`application.properties`)
```properties
spring.application.name=petMania
spring.datasource.url=jdbc:mysql://localhost:3306/petmania?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```
## Ajuste 04: Camada de Persistência (`PetRepository`)

### O que foi identificado
A camada de repositório apresentava erros estruturais e de tipagem que impediam o Spring Data JPA de gerar a implementação de acesso ao banco de dados:
- O repositório foi declarado como `class` em vez de `interface`.
- Faltava a importação da interface de repositório do Spring Data (reflexo da ausência inicial do `spring-boot-starter-data-jpa` no `pom.xml`).
- Uso do tipo primitivo `int` como segundo parâmetro genérico (`CrudRepository<Pet, int>`), o que não é permitido pelos *Generics* do Java.

### O que foi alterado
- **Definição de Interface:** Alterada a declaração de `public class` para `public interface`, permitindo que o Spring crie o proxy de implementação em tempo de execução.
- **Herança com `JpaRepository`:** Substituído `CrudRepository` por `JpaRepository` (com sua devida importação), garantindo suporte nativo a operações CRUD completas, paginação e retorno direto de listas (`List`).
- **Tipagem Genérica:** Alterado o tipo da chave primária de `int` (primitivo) para a classe wrapper `Integer` (`JpaRepository<Pet, Integer>`), alinhando com o tipo do `id` na entidade `Pet`.


## Ajuste 05: Camada de Controle e Endpoints REST (`PetController`)

### O que foi identificado
O controller responsável pelas rotas da API apresentava erros de sintaxe nas anotações, inversão de verbos HTTP, ausência de importações e implementação incompleta:
- Erro de digitação em `@RequestMaping` (faltando um "p") e passagem da rota `"api/pets"` na anotação `@RestController` em vez de `@RequestMapping`.
- Ausência das importações do pacote `org.springframework.web.bind.annotation.*`.
- Inversão dos verbos HTTP: o método de listagem estava mapeado com `@PostMapping` e o método de criação com `@GetMapping`.
- O método `mostrar()` declarava retorno `List<Pet>`, mas chamava `repository.findById()` sem argumentos.
- O método `criar()` possuía retorno `void` sem feedback visual da operação, e os endpoints de busca individual (`GET /{id}`) e exclusão (`DELETE /{id}`) ainda não haviam sido implementados.
- Ausência da chave de fechamento `}` no final da classe.

### O que foi alterado
- **Configuração da Rota Base:** Corrigida a grafia para `@RequestMapping("/api/pets")` e ajustada a anotação `@RestController` na classe, adicionando todas as importações necessárias do Spring Web.
- **Endpoint de Listagem (`GET`):** Alterada a anotação do método `mostrar()` para `@GetMapping` e substituída a chamada para `repository.findAll()`, retornando todos os pets cadastrados em formato de lista.
- **Endpoint de Busca por ID (`GET /{id}`):** Implementado o método `buscarPorId` utilizando `@PathVariable` e `repository.findById(id).orElse(null)` com tratamento em `try-catch`, retornando o objeto `Pet` encontrado ou uma mensagem amigável caso o registro não exista ou ocorra falha na consulta.
- **Endpoint de Criação (`POST`):** Alterada a anotação do método `criar(@RequestBody Pet p)` para `@PostMapping`, adicionado tratamento com bloco `try-catch` e retorno em `String` (com `p.getNome()`) para exibir feedback claro de sucesso ou falha nas requisições via Postman.
- **Endpoint de Exclusão (`DELETE /{id}`):** Implementado o método `deletar` mapeado com `@DeleteMapping("/{id}")`, utilizando `@PathVariable` para capturar o ID pela URL e executar `repository.deleteById(id)`.
- **Estrutura da Classe:** Adicionada a chave de fechamento `}` ao final do arquivo e limpos os comentários de rascunho.
