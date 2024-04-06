import pessoa.Pessoa;
import pessoa.TipoCargo;
import projeto.Projeto;
import projeto.Tag;
import projeto.Tarefa;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        // Pessoa
        Pessoa pessoa = new Pessoa.Builder().nome("Paulo").cargo(TipoCargo.JUNIOR).build();
        System.out.println(pessoa);

        String uuid = UUID.randomUUID().toString();
        System.out.println(uuid);
        Pessoa pessoa2 = new Pessoa.Builder().id(uuid).nome("Pedro").cargo(TipoCargo.JUNIOR).build();
        System.out.println(pessoa2);
        System.out.println();

        // Tarefa
        Tarefa tarefa = new Tarefa.Builder().titulo("Estruturar projeto").build();
        System.out.println(tarefa);

        String uuidTarefa = UUID.randomUUID().toString();
        System.out.println(uuidTarefa);
        Tarefa tarefa1 = new Tarefa.Builder().id(uuidTarefa).titulo("Testar classes").pessoa(pessoa).build();
        System.out.println(tarefa1);
        System.out.println();

        // Projeto
        Projeto projeto = new Projeto.Builder()
                .titulo("TP Time Tracker")
                .descricao("Projeto Ada")
                .tags(List.of(Tag.IMPORTANTE))
                .build();
        System.out.println(projeto);

        String uuidProjeto = UUID.randomUUID().toString();
        System.out.println(uuidProjeto);
        Projeto projeto1 = new Projeto.Builder()
                .id(uuidProjeto)
                .titulo("Projeto final")
                .descricao("Projeto Final Ada")
                .tags(List.of(Tag.URGENTE))
                .tarefas(List.of(tarefa, tarefa1))
                .dataHoraInicio(LocalDateTime.of(2024, 4, 1, 12, 30))
                .pessoas(List.of(pessoa, pessoa2))
                .build();
        System.out.println(projeto1);
        System.out.println();
    }
}