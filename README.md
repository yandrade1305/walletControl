![Badge Concluído](http://img.shields.io/static/v1?label=STATUS&message=CONCLUÍDO&color=GREEN&style=for-the-badge)
![Badge Java](http://img.shields.io/static/v1?label=JAVA&message=11.0.13&color=yellow&style=for-the-badge)
![Badge Spring](http://img.shields.io/static/v1?label=SPRING&message=2.7.1&color=GREEN&style=for-the-badge)
![Badge PostgreSQL](http://img.shields.io/static/v1?label=POSTGRESQL&message=12.11-1&color=blue&style=for-the-badge)

# Wallet Control

Repositório voltado ao desenvolvimento do <a href="https://github.com/tracefinance/backend-challenge"> Challenge Back-end para a empresa Trace Finance </a>

## Índice 

* [Índice](#índice)
* [Configurações para subir o projeto](#configurações-para-subir-o-projeto)
* [Testes](#testes)
* [Relatos](#relatos)

## Configurações para subir o projeto

Para conseguir subir o projeto, irei utilizar Java versão 11 ou superior e PostgreSQL versão 12.11-1 ou superior.
Para o banco de dados, precisa ter instalado o PostgreSQL e precisa executar o arquivo ``script-inicial.sql``.
Para conectar ao banco, precisamos também alterar as propriedades spring.datasource.username e spring.datasource.password.
Para testar os endpoints eu estarei utilizando o Insomnia versão v2022.4.2.

## Testes

Os testes serão desenvolvidos para testar as Controller's: ``PaymentController.java`` e ``WalletController.java``. Testarei as controllers por elas dependerem do Service e Repository, uma vez que ambos estiverem corretos todos os testes passarão. Fiz uma breve análise em cima do <a href="https://user-images.githubusercontent.com/48693812/176567551-294e5d8a-509c-431f-9951-7b338321cccb.png">fluxograma</a> e levantei os seguintes testes a serem desenvolvidos:
* Não ser possível cadastrar carteira com nome já existente, nulo ou vazio;
* Ser possível cadastrar uma carteira utilizando um nome;
* Não ser possível consultar o limite de uma carteira que não exista;
* Ser possível consultar o limite de uma carteira existente;
* Não ser possível realizar um pagamento se caso uma carteira não exista;
* Não ser possível realizar um pagamento com valor acima de 1000 reais;
* Ser possível realizar um pagamento com valor abaixo de 1000 reais;
* Ser possível realizar um pagamento no período diurno quando não estiver excedido limite de 4000 reais;
* Não ser possível realizar um pagamento no período diurno quando estiver excedido limite de 4000 reais;
* Ser possível realizar um pagamento no período noturno quando não estiver excedido limite de 1000 reais;
* Não ser possível realizar um pagamento no período noturno quando estiver excedido limite de 1000 reais;
* Ser possível transferir 5000 reais por dia. Sendo 4000 pelo turno diurno e 1000 noturno;
* Ser possível realizar um pagamento no final de semana quando não estiver excedido limite de 1000 reais;
* Não ser possível realizar um pagamento no final de semana quando estiver excedido limite de 1000 reais;
* Limite deverá ser diário;
* Limite diurno não poderá interferir no limite noturno;
* Deverá ser possível classificar um período diurno ou noturno.

Antes e de executar as classes de teste é necessário limpar o banco de dados, para isso basta executar o script do arquivo ``script-final.sql``


## Relatos
## 28/06/2022 - Dia 01
Hoje foi o primeiro dia em que foquei no projeto, eu estou muito animado em poder aplicar os meus conhecimentos que aprendi ao longo de semanas. Comecei fazendo um Diagrama Entidade Relacionamento.
<p align="center">
  <img width="372px" height="372px" src="https://user-images.githubusercontent.com/48693812/176249895-72b74f10-3c40-45b0-8e49-ae3b71d79ed3.png" />
   <p align="center">
    <i>
       Clique <a width="372px" height="372px" href="https://user-images.githubusercontent.com/48693812/176249895-72b74f10-3c40-45b0-8e49-ae3b71d79ed3.png">aqui</a> para ver a imagem expandida.
    </i>   
</p>

Como podemos ver escolhi dividir em duas tabelas(porém mais na frente percebi que não precisava disso), depois desse esboço eu parti pro SQL, escrevi o script, em seguida rodei e tudo deu certo!
Em seguida passei para o Java e comecei a modelar as entidades, fiz o repository, fiz services e por fim controllers.
Depois de ter feito as funcionalidades de cadastrar uma carteira, exibir o limite de uma carteira parti para a funcionalidade que considerei mais difícil, que era a funcionalidade de realizar pagamento.
Inicialmente eu acreditava que seria necessário uma entidade chamada Payment, pois achava que deveria armazenar as informações, porém depois de ter finalizado uma parte da funcionalidade de realizar pagamento eu percebi que Payment não estava sendo utilizado em canto nenhum! Logo eu exclui e atualizei em outros lugares onde citava Payment. Ainda tenho bastante coisas para desenvolver mas estou bem animado 🤩.

Depois de ter passado um tempo longe do PC fiquei pensando mais sobre o problema em si e acabei conseguindo encontrar uma solução, resta implementar somente a parte de calcular os valores, e substituir os limites (Mas oq importa é que deu tudo certo). A minha classe service ficou enorme, tive que mexer na entidade de novo também mas acredito que cheguei em uma solução! 😁 Mas ainda tem muito o que refatorar, tem muitos testes unitários para desenvolver e quem sabe ir atrás de conhecer e implemntar alguns pontos da aba "Seria Legal"

Ah! E já ia me esquecendo... Eu implementei umas pequenas regras de negócio. 
- Não é possível cadastrar uma carteira com mesmo nome
- Não foi encontrada uma carteira com o id informado

Ainda falta algumas regras de negócio a serem implementadas... Mas fico feliz com o andamento do projeto!

### 29/06/2022 - Dia 02
Hoje foi o segundo dia desenvolvendo o projeto eu fiz alguns avanços e refatorações na funcionalidade de realizar o pagamento, a maioria delas foi realizar as operações em si. Optei em utilizar o BigDecimal para armazenar os valores, uma vez que como se trata de dinheiro o tipo BigDecimal é o mais recomendado. Depois de implementar os cálculos vi que ficou muito grande a classe de Service então refatorei e isolei todas essas operações em uma classe abstrata utilitária. Ainda tenho muito o que desenvolver, incluindo rever a questão de receber uma data e hora no padrão "dd/MM/yyyy HH:mm:ss" e converter para LocalDateTime (Achei que tinha resolvido mas estava errado 😭). Fora esses pontos, eu espero conseguir resolver o quanto antes e colocar as validações de:
- Limite do pagamento

Está difícil, mas não impossível! Quero aprender como resolvo esses problemas pois sei que vai aparecer algo parecido no meu dia-a-dia! 💪📚
Antes de finalizar o dia de hoje, eu retornei no meu código e vi diversos problemas... alguns eram simples (não estava atualizando a data) e outros mais difíceis (os limites não estavam relacionando-se bem)
Então decidi jogar todo aquele meu código fora e refazer do 0. Voltei pro drawn.io e comecei a desenhar o fluxograma, consegui terminar o fluxograma e acredito que esteja tudo certo!
Abaixo vou deixar o fluxograma que montei.
<p align="center">
  <img width="372px" height="372px" src="https://user-images.githubusercontent.com/48693812/176567551-294e5d8a-509c-431f-9951-7b338321cccb.png"/>
  <p align="center">
    <i>
      Clique <a width="372px" height="372px" href="https://user-images.githubusercontent.com/48693812/176567551-294e5d8a-509c-431f-9951-7b338321cccb.png">aqui</a> para ver a imagem expandida.
    </i>
</p>

E com isto finalizo os trabalhos do dia 2... Acho q tô conseguindo me sair bem... Tem umas repetições mas primeiro quero fazer funcionar para depois refatorar!

### 30/06/2022 - Dia 03
Hoje foi o terceiro dia de projeto, botei em prática aquele fluxograma que elaborei ontem. E surpreendentemente deu certo! 😄 senti tanta alegria na hora (foi inesquecível!). Porém... tava errado! 😩 E o que tinha de errado? 😕 Simples, eu tinha esquecido de verificar se os limites tanto diurno quanto noturno estavam de acordo com a quantidade informada no pagamento! Então alterei primeiro no código e depois no fluxograma! E aumentou bastante o fluxograma com essa simples mudança. Mas acredito que está "blindado" meu código 😎. Agora só basta eu refatorar, talvez isolar essa lógica em outra classe... Vou pensar nos próximos passos. Seria uma boa eu ver algum padrão de projeto e aplicar,mas também tenho que alterar o retorno da função de cadastrar e limpar os println's. Abaixo está o novo fluxograma e também a nova tabela de Wallet.

<p align="center">
  <img id="fluxograma" width="372px" height="372px" src="https://user-images.githubusercontent.com/48693812/176717810-0b1535d0-1b22-41fd-b846-eeb3b8a40c54.png"/>
  <p align="center">
    <i>
      Clique <a href="https://user-images.githubusercontent.com/48693812/176717810-0b1535d0-1b22-41fd-b846-eeb3b8a40c54.png">aqui</a> para ver a imagem expandida.
    </i>     
</p>


<p align="center">
  <img src="https://user-images.githubusercontent.com/48693812/176885546-41108235-3ec6-48b5-a97e-a6984956c5a2.png"/>
  <p align="center">
    <i>
    Clique <a href="https://user-images.githubusercontent.com/48693812/176885546-41108235-3ec6-48b5-a97e-a6984956c5a2.png">aqui</a> para ver a imagem expandida.
    </i>  
</p>


### 01/07/2022 - Dia 04

Hoje foi o quarto e último dia de desenvolvimento do projeto! Hoje elaborei testes unitários para as Controller's. Pois queria ter certeza de que meu código estava cobrindo vários casos de uso e satisfatoriamente ele cobriu todos! Isso me deixou muito feliz! 😆 Em seguida eu fui refatorar aquele código gigantesco que estava na service, o Intellij me ajudou nessa hora. E enquanto tava refatorando e rodando os testes para ver se nada quebrava eu percebi que podia evitar de comparar os turnos diurno e noturnos quando se trata de ser final de semana (Tava suspeitando um pouco quando tava revendo o fluxograma) vou deixar aqui embaixo a versão final. 
<p align="center">
  <img width="372px" height="372px" src="https://user-images.githubusercontent.com/48693812/176973957-80bb4f43-d2ce-46cd-8f5b-c79d0628fede.png"/>
  <p align="center">
    <i>
    Clique <a href="https://user-images.githubusercontent.com/48693812/176973957-80bb4f43-d2ce-46cd-8f5b-c79d0628fede.png">aqui</a> para ver a imagem expandida.
    </i>  
</p>
Também organizei o README, deixei ele bem bonito! E obrigado pelo challenge, foi bem desafiador no início mas consegui chegar do outro lado! Consegui aprender bastante e teve vários momentos de alegria, mais uma vez obrigado! ❤️

