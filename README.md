# Time Tracker :clock10:
Esse é um sistema de controle de tempo em Java, inspirado no Clockify, que permite aos usuários acompanhar e gerenciar suas atividades e controlar o uso de tempo de maneira rápida e eficiente. 
Este sistema oferece funcionalidades essenciais para monitorar o tempo gasto em diferentes tarefas, ajudando na organização e na análise do desempenho.

## Funcionalidades
- Cadastro de usuários, atividades e projetos
- Cadastro do tempo utilizado em atividades
- Visualização de tarefas e projetos por usuário
- Visualização de projetos por usuário
- Visualização de usuários do projeto
### Relatórios
- Tempo trabalhado por usuário
- Tempo trabalhado por projeto
- Tempo trabalhado em um período
- Média de tempo em tarefas

### Requisitos
- Java JDK 22
- PostgreSQL 16
- (Opcional) Git
- (Opcional) Insomia 

## Como Usar
- Clone ou faça o download do código-fonte do repositório e importe-o para a sua IDE de preferência.
- Execute o PostgresSQL e crie um banco de nome "time-tracker". Atualize o arquivo application.properties caso necessário.
- Execute a classe TimeTrackerApplication, que irá iniciar o programa e o servidor web TomCat integrado.
- Para utilizar de forma simples os endpoints, importe na aplicação Insomnia o arquivo JSON presente no diretório insomnia.
- Utilize os mapeamentos disponíveis de POST, GET, PUT e DELETE para interagir com a aplicação.
- Os endpoints /register e /login podem ser acessados sem autenticação. Para os demais, devem ser utilizados username e CPF (Basic Auth).
