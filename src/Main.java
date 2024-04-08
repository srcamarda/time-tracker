import service.PessoaService;
import service.ProjetoService;
import service.TarefaService;
import model.Pessoa;
import utility.TipoCargo;
import model.Projeto;
import model.Tag;
import model.Tarefa;
import view.MenuPrincipal;
import view.MenuProjeto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Main {

    MenuProjeto menuProjeto;

    public static void main(String[] args) {
        System.out.println(PessoaService.buscarPessoa("  JuliA   "));
        System.out.println(ProjetoService.buscarProjeto("Ap"));
        System.out.println(TarefaService.buscarTarefa("desen"));

        MenuPrincipal.mainMenu();

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
                .dataHoraInicio(LocalDateTime.of(2024, 4, 1, 12, 30))
                .build();
        System.out.println(projeto1);
        System.out.println();
    }
}