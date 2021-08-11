## Bootcamp Santander Mobile Developer
Atividade Final da Trilha de Kotlin

O objetivo do projeto era fazer uma aplicativo de lista de tarefas utilizando as melhores práticas no desenvolvimento de App para android utilizando kotlin.
O App original continha uma lista de tarefas, uma tela para editar as tarefas. As tarefas eram guardadas na memória. Foram utilizados recursos como viewBinding para facilitar a manipulação das informações nas Views, DatePicker e TimePicker para alterar os valores dos campos de data e hora da tarefa.

Como melhoria ao app desenvolvido durante as aulas, tentei deixar o mais parecido com o design original proposto, adicionando recursos que foram deixados de fora, como a lista de datas para mostrar as tarefas por dia ao invés de todas as tarefas cadastradas. Também adicionei o banco de dados RoomDatabase para persistir as tarefas e lifecycle para ajudar na atualização da listas usadas, e adicionei também a biblioteca Koin para gerenciar as injeções de dependência.
Para a lista do calendário foi usada uma recicleview e uma bliblioteca para paginação (androidx.paging) para que a recicleview mostrasse datas, sem precisar definir o tamanho máximo da lista, assim pode se dizer que a lista de datas é "infinita".