import mockup.Mockup;
import service.PessoaService;
import service.ProjetoService;
import service.TarefaService;
import view.MenuPrincipal;

public class Main {
    public static void main(String[] args) {
        //System.out.println(PessoaService.buscarTodasAsPessoas());
        //System.out.println(ProjetoService.buscarProjeto(""));
        //System.out.println(TarefaService.buscarTarefa(""));

        //Mockup.init();

//        MenuPrincipal.mainMenu();

        System.out.println(ProjetoService.adicionarPessoa("App de Monitoramento Fitness", "julia"));
    }
}