import mockup.Mockup;
import model.Pessoa;
import model.Tarefa;
import repository.*;
import utility.TipoCargo;
import utility.TipoPlano;
import utility.singleton.PessoaSingleton;
import utility.singleton.ProjetoSingleton;
import utility.singleton.TarefaSingleton;

public class Main {
    public static void main(String[] args) {
        //Mockup.init();
//        RepositoryPessoa repositoryPessoa = new RepositoryPessoa(ArquivoPaths.PESSOAS);
//        RepositoryTarefa repositoryTarefa = new RepositoryTarefa(ArquivoPaths.TAREFAS, repositoryPessoa);
//        RepositoryProjeto repositoryProjeto = new RepositoryProjeto(ArquivoPaths.PROJETOS, repositoryPessoa, repositoryTarefa);

//        SistemaRepository sistema = SistemaRepository.INSTANCE;

        PessoaSingleton pessoaSingleton = PessoaSingleton.INSTANCE;
        TarefaSingleton tarefaSingleton = TarefaSingleton.INSTANCE;
        ProjetoSingleton projetoSingleton = ProjetoSingleton.INSTANCE;
        System.out.println("Hello");
    }
}