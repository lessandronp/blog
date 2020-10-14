# Preparação do ambiente:

## Banco de dados 

1. Após a instalação do banco de dados Postgres ter sido realizada, é necessário executar o script chamado data.sql, presente
	na pasta src/main/resources na estrutura do projeto.
2. O script criará o database blog e realizará os inserts iniciais de ROLE e usuários do sistema.

## Importação do projeto

1. O projeto foi criado no eclipse, como "Mavem Project". Realize a importação como projeto eclipse, pelo import "Existing project into Workspace".
2. Adicione o tomcat 8.5 como servidor de aplicação.
3. Para realização do deploy coloque o projeto dentro do servidor de aplicação. Se não for possível por algum problema de configuração do projeto,
	altere o arquivo "org.eclipse.wst.common.project.facet.core.xml", na pasta .settings, trocando a tag da versão 4.0 pra 3.1.
	
	<installed facet="jst.web" version="4.0"/> por
	<installed facet="jst.web" version="3.1"/>

4. O projeto está utilizando o Lombok. A versão que consegui compatibilidade com o eclipse mais novo (2020-09) foi a lombok-1.16.18.jar.

## Classes de teste

1. Foram adicionadas classes de teste unitário utilizando o jupiter. Na classe de testes de Post, é possível adicionar um Post com comentário, link ou imagem.
	Também temos a classe de testes de comentário, para possíveis comentários relacionados à um determinado Post, assim a classe de testes de álbum, onde
	é possível realizar a criação de um álbum com imagem, e a classe de testes de usuário. 
2. Os testes são executados com a versão mais nova, Junit 5. 
3. A versão que apresentou melhor compatibilidade com o Junit 5 foi a 2020-09.

## EndPoints

### Segurança

1. Para execução da maioria dos endpoints, é obrigatório que o token seja informado no cabeçalho das requisições. As requisições de listagem de
	álbuns, posts e comentários não precisam dessa informação.

### Usuários

1. Endpoint de geração do token JWT <br/>
	Requisição post realizada através do endpoint: <span style="font-family: 'Lucida Console'; font-size: 13px">localhost:8080/Blog/rest/users/authenticate</span> 

Exemplo de json de envio: POST

```json
{
    "username" : "user.teste",
    "password" : "123"
}
```

2. Endpoint para adição de usuários: <span style="font-family: 'Lucida Console'; font-size: 13px">localhost:8080/Blog/rest/users/addUser</span> <br/>
	Exemplo de json de envio: POST

```json
Bearer Token: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxNTEiLCJpYXQiOjE2MDI1NDYwMzksImV4cCI6MTYwMjU0OTYzOX0.3MHGdn6itg60kpfe4vhxVH-354wvFtftB-7fV4xFfEBTaYqQ9i8Z9sHAZufmL2IkcAmwOCg_U24WewqFlep1ZQ

{
    "name" : "Joaquim Barbosa",
    "email" : "joaquim.barbosa@teste.com.br",
    "username" : "joaquim.barbosa",
    "password" : "123",
    "images": [{
		"name": "Dog",
		"url": "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/dog-puppy-on-garden-royalty-free-image-1586966191.jpg"
	}]
}
```

### Post

1. Endpoint de Post simples <br/>
	Requisição através do endpoint: <span style="font-family: 'Lucida Console'; font-size: 13px">localhost:8080/Blog/rest/posts/addPost</span> 

Exemplo de json de envio: POST

```json
Bearer Token: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxNTEiLCJpYXQiOjE2MDI1NDYwMzksImV4cCI6MTYwMjU0OTYzOX0.3MHGdn6itg60kpfe4vhxVH-354wvFtftB-7fV4xFfEBTaYqQ9i8Z9sHAZufmL2IkcAmwOCg_U24WewqFlep1ZQ

{
    "title" : "Novo Post",
    "content"  : "Criando post do tipo texto"
}
```
2. Endpoint de Post com imagem <br/>
	Requisição através do endpoint: <span style="font-family: 'Lucida Console'; font-size: 13px">localhost:8080/Blog/rest/posts/addPost</span> 

Exemplo de json de envio: POST

```json
Bearer Token: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxNTEiLCJpYXQiOjE2MDI1NDYwMzksImV4cCI6MTYwMjU0OTYzOX0.3MHGdn6itg60kpfe4vhxVH-354wvFtftB-7fV4xFfEBTaYqQ9i8Z9sHAZufmL2IkcAmwOCg_U24WewqFlep1ZQ

{
    "title" : "Novo Post",
    "content"  : "Criando post do tipo texto",
    "images": [{
		"name": "Dog",
		"url": "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/dog-puppy-on-garden-royalty-free-image-1586966191.jpg"
	}]
}

```

3. Endpoint de Post com link <br/>
	Requisição através do endpoint: <span style="font-family: 'Lucida Console'; font-size: 13px">localhost:8080/Blog/rest/posts/addPost</span> 

Exemplo de json de envio: POST

```json
Bearer Token: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxNTEiLCJpYXQiOjE2MDI1NDYwMzksImV4cCI6MTYwMjU0OTYzOX0.3MHGdn6itg60kpfe4vhxVH-354wvFtftB-7fV4xFfEBTaYqQ9i8Z9sHAZufmL2IkcAmwOCg_U24WewqFlep1ZQ

{
    "title" : "Post Link",
    "content"  : "Post Link Blog Sempre Java",
    "links": [{
		"url": "http://https://semprejava.wordpress.com"
	}]
}

```

4. Endpoint de listagem de Posts <br/>
	Requisição através do endpoint: <span style="font-family: 'Lucida Console'; font-size: 13px">localhost:8080/Blog/rest/posts</span> <br />
	Também é possível passar a página e número de registros <span style="font-family: 'Lucida Console'; font-size: 13px">localhost:8080/Blog/rest/posts?page=0&size=5</span> 

Exemplo de retorno da chamada: GET

```json

{
    "content": [
        {
            "id": 302,
            "title": "Post Comment",
            "content": "Post Comment",
            "user": {
                "id": 151,
                "name": "Usuário teste"
            },
            "comments": [
                {
                    "id": 1,
                    "text": "Comentário post",
                    "user": {
                        "id": 151,
                        "name": "Usuário teste"
                    },
                    "idPost": 302
                },
                {
                    "id": 252,
                    "text": "Post está de acordo",
                    "user": {
                        "id": 151,
                        "name": "Usuário teste"
                    },
                    "idPost": 302
                }
            ],
            "images": [],
            "links": []
        }
        ],
    "page": 0,
    "size": 5,
    "totalElements": 2,
    "totalPages": 1,
    "last": true
}
        
```
 
### Álbum

1. Endpoint que adiciona álbum com imagem <br/>
	Requisição através do endpoint: <span style="font-family: 'Lucida Console'; font-size: 13px">localhost:8080/Blog/rest/albums/addAlbum</span> 

Exemplo de json de envio: POST

```json
Bearer Token: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxNTEiLCJpYXQiOjE2MDI1NDYwMzksImV4cCI6MTYwMjU0OTYzOX0.3MHGdn6itg60kpfe4vhxVH-354wvFtftB-7fV4xFfEBTaYqQ9i8Z9sHAZufmL2IkcAmwOCg_U24WewqFlep1ZQ

{
    "name" : "Novo Álbum",
    "images": [{
		"name": "Dog",
		"url": "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/dog-puppy-on-garden-royalty-free-image-1586966191.jpg"
	}]
}

```

2. Endpoint que remove um álbum <br/>
	Requisição através do endpoint: <span style="font-family: 'Lucida Console'; font-size: 13px">localhost:8080/Blog/rest/albums/deleteAlbum/53</span> 

Exemplo de json de envio: DELETE

```json
Bearer Token: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxNTEiLCJpYXQiOjE2MDI1NDYwMzksImV4cCI6MTYwMjU0OTYzOX0.3MHGdn6itg60kpfe4vhxVH-354wvFtftB-7fV4xFfEBTaYqQ9i8Z9sHAZufmL2IkcAmwOCg_U24WewqFlep1ZQ

O Álbum foi removido com sucesso.

```

3. Endpoint de listagem de Álbuns <br/>
	Requisição através do endpoint: <span style="font-family: 'Lucida Console'; font-size: 13px">localhost:8080/Blog/rest/albums</span> <br />
	Também é possível passar a página e número de registros <span style="font-family: 'Lucida Console'; font-size: 13px">localhost:8080/Blog/rest/albums?page=0&size=5</span> 

Exemplo de retorno da chamada: GET

```json

{
    "content": [
        {
            "id": 53,
            "name": "Novo Álbum",
            "user": {
                "id": 201,
                "name": "Usuário teste 2"
            },
            "images": [
                {
                    "id": 303,
                    "name": "Dog",
                    "url": "https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/dog-puppy-on-garden-royalty-free-image-1586966191.jpg"
                }
            ]
        }
    ],
    "page": 0,
    "size": 10,
    "totalElements": 1,
    "totalPages": 1,
    "last": true
}
        
```

### Comentários

1. Endpoint que adiciona um comentário à um Post <br/>
	Requisição através do endpoint: <span style="font-family: 'Lucida Console'; font-size: 13px">localhost:8080/Blog/rest/comments/addComment</span> 

Exemplo de json de envio: POST

```json
Bearer Token: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxNTEiLCJpYXQiOjE2MDI1NDYwMzksImV4cCI6MTYwMjU0OTYzOX0.3MHGdn6itg60kpfe4vhxVH-354wvFtftB-7fV4xFfEBTaYqQ9i8Z9sHAZufmL2IkcAmwOCg_U24WewqFlep1ZQ

{
    "text" : "Post está de acordo",
    "idPost": "302"
}

```

2. Endpoint que remove um comentário <br/>
	Requisição através do endpoint: <span style="font-family: 'Lucida Console'; font-size: 13px">localhost:8080/Blog/rest/comments/deleteComment/252</span> 

Exemplo de json de envio: DELETE

```json
Bearer Token: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxNTEiLCJpYXQiOjE2MDI1NDYwMzksImV4cCI6MTYwMjU0OTYzOX0.3MHGdn6itg60kpfe4vhxVH-354wvFtftB-7fV4xFfEBTaYqQ9i8Z9sHAZufmL2IkcAmwOCg_U24WewqFlep1ZQ

O comentário foi removido com sucesso.

```

3. Endpoint de listagem de comentários de um Post <br/>
	Requisição através do endpoint: <span style="font-family: 'Lucida Console'; font-size: 13px">localhost:8080/Blog/rest/comments/302</span> <br />
	Também é possível passar a página e número de registros <span style="font-family: 'Lucida Console'; font-size: 13px">localhost:8080/Blog/rest/comments/302?page=0&size=5</span> 

Exemplo de retorno da chamada: GET

```json

{
    "content": [
        {
            "id": 252,
            "text": "Post está de acordo",
            "user": {
                "id": 151,
                "name": "Usuário teste"
            },
            "idPost": 302
        },
        {
            "id": 1,
            "text": "Comentário post",
            "user": {
                "id": 151,
                "name": "Usuário teste"
            },
            "idPost": 302
        }
    ],
    "page": 0,
    "size": 10,
    "totalElements": 2,
    "totalPages": 1,
    "last": true
}
        
```

