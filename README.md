![Badge Em Construção](http://img.shields.io/static/v1?label=STATUS&message=EMCONSTRUÇÃO&color=yellow&style=for-the-badge)
![Badge Java](http://img.shields.io/static/v1?label=JAVA&message=11.0.13&color=yellow&style=for-the-badge)
![Badge Spring](http://img.shields.io/static/v1?label=SPRING&message=2.7.1&color=GREEN&style=for-the-badge)
![Badge PostgreSQL](http://img.shields.io/static/v1?label=POSTGRESQL&message=12.11-1&color=blue&style=for-the-badge)

# Wallet Control
Repositório voltado ao desenvolvimento do <a href="https://github.com/tracefinance/backend-challenge"> Challenge Back-end para a empresa Trace Finance </a>

# Configurações para subir o projeto
Para conseguir subir o projeto, irei utilizar Java versão 11 ou superior e PostgreSQL versão 12.11-1 ou superior.
Para o banco de dados, precisa ter instalado o PostgreSQL e precisa executar o arquivo script inicial.
Para conectar ao banco, precisamos também alterar as propriedades spring.datasource.username e spring.datasource.password.
Para testar os endpoints eu estarei utilizando o Insomnia versão v2022.4.2.

# Relatos
## 28/06/2022 - Dia 01
Hoje foi o primeiro dia em que foquei no projeto, eu estou muito animado em poder aplicar os meus conhecimentos que aprendi ao longo de semanas. Comecei fazendo um Diagrama Entidade Relacionamento.
<p align="center">
  <img src="https://user-images.githubusercontent.com/48693812/176249895-72b74f10-3c40-45b0-8e49-ae3b71d79ed3.png" /> 
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
  <img src="https://user-images.githubusercontent.com/48693812/176567551-294e5d8a-509c-431f-9951-7b338321cccb.png"/>
  Clique <a href="https://user-images.githubusercontent.com/48693812/176567551-294e5d8a-509c-431f-9951-7b338321cccb.png">aqui</a> para ver a imagem expandida.
</p>
E com isto finalizo os trabalhos do dia 2... Acho q tô conseguindo me sair bem... Tem umas repetições mas primeiro quero fazer funcionar para depois refatorar!

### 30/06/2022 - Dia 03
Hoje foi o terceiro dia de projeto, botei em prática aquele fluxograma que elaborei ontem. E surpreendentemente deu certo! 😄 senti tanta alegria na hora (foi inesquecível!). Porém... tava errado! 😩 E o que tinha de errado? 😕 Simples, eu tinha esquecido de verificar se os limites tanto diurno quanto noturno estavam de acordo com a quantidade informada no pagamento! Então alterei primeiro no código e depois no fluxograma! E aumentou bastante o fluxograma com essa simples mudança. Mas acredito que está "blindado" meu código 😎. Agora só basta eu refatorar, talvez isolar essa lógica em outra classe... Vou pensar nos próximos passos. Seria uma boa eu ver algum padrão de projeto e aplicar,mas também tenho que alterar o retorno da função de cadastrar e limpar os println's. Abaixo está o novo fluxograma e também a nova tabela de Wallet.

<p align="center">
  <img src="https://user-images.githubusercontent.com/48693812/176717810-0b1535d0-1b22-41fd-b846-eeb3b8a40c54.png"/>
  Clique <a href="https://user-images.githubusercontent.com/48693812/176717810-0b1535d0-1b22-41fd-b846-eeb3b8a40c54.png">aqui</a> para ver a imagem expandida.
</p>

<p align="center">
  <img src="https://user-images.githubusercontent.com/48693812/176720108-949caa00-f896-489d-8f69-4758c1db3de6.png"/>
  Clique <a href="https://user-images.githubusercontent.com/48693812/176720108-949caa00-f896-489d-8f69-4758c1db3de6.png">aqui</a> para ver a imagem expandida.
</p>