import dto.PessoaDTO;
import mockup.Mockup;
import model.Pessoa;
import model.Tarefa;
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
import utility.Conversores;
import utility.TipoCargo;
import utility.singleton.TarefaSingleton;

import java.util.List;


public class Main {

    MenuProjeto menuProjeto;

    public static void main(String[] args) { 
  //      System.out.println(PessoaService.buscarTodasAsPessoas(TipoCargo.SENIOR));
 //       System.out.println(ProjetoService.buscarProjeto(""));
//        System.out.println(TarefaService.buscarTarefa("desen"));
//        Pessoa pessoa = PessoaService.buscarPessoa("  igor   ");
//        System.out.println(TarefaService.buscarTarefa(Conversores.converterParaDTO(pessoa)));
//
//        Mockup.init();
//        Tarefa tarefaTeste = Conversores.converterParaModel(Mockup.tarefaDTOList.get(0));
//        System.out.println(tarefaTeste);
//        TarefaSingleton.INSTANCE.getRepositoryTarefa().salvarTarefa(tarefaTeste);
//
//        System.out.println(TarefaService.criarTarefa(tarefaTeste));

          MenuPrincipal.mainMenu();
    }
}