import mockup.Mockup;
import model.Tag;
import service.PessoaService;
import service.ProjetoService;
import service.TarefaService;
import view.MenuPrincipal;

public class Main {
    public static void main(String[] args) {
        //System.out.println(PessoaService.buscarTodasAsPessoas());
        //System.out.println(ProjetoService.buscarProjeto(""));
        //System.out.println(TarefaService.buscarTarefa(""));
        // System.out.println(ProjetoService.adicionarTag("App de Monitoramento Fitness", Tag.EM_ANDAMENTO));

        //Mockup.init();

        MenuPrincipal.mainMenu();
    }
}