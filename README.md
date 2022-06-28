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

# Relatos
## 28/06/2022 - Dia 01
Hoje foi o primeiro dia em que foquei no projeto, eu estou muito animado em poder aplicar os meus conhecimentos que aprendi ao longo de semanas. Comecei fazendo um Diagrama Entidade Relacionamento.
<p align="center">
  <img src="https://user-images.githubusercontent.com/48693812/176249895-72b74f10-3c40-45b0-8e49-ae3b71d79ed3.png" /> 
</p>

Como podemos ver escolhi dividir em duas tabelas(porém mais na frente percebi que não precisava disso), depois desse esboço eu parti pro SQL, escrevi o script, em seguida rodei e tudo deu certo!
Em seguida passei para o Java e comecei a modelar as entidades, fiz o repository, fiz services e por fim controllers.
Depois de ter feito as funcionalidades de cadastrar uma carteira, exibir o limite de uma carteira parti para a funcionalidade que considerei mais difícil, que era a funcionalidade de realizar pagamento.
Inicialmente eu acreditava que seria necessário uma entidade chamada Payment, pois achava que deveria armazenar as informações, porém depois de ter finalizado uma parte da funcionalidade de realizar pagamento eu percebi que Payment não estava sendo utilizado em canto nenhum! Logo eu exclui e atualizei em outros lugares onde citava Payment. Ainda tenho bastante coisas para desenvolver mas estou bem animado 🤩
