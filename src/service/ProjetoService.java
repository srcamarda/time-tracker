package service;

import model.Pessoa;
import model.Projeto;
import model.Tag;
import repository.RepositoryProjeto;
import utility.TipoPlano;

import java.util.List;
import java.util.function.Predicate;

public class ProjetoService {
    private static final int MAX_PESSOAS_POR_PROJETO = 5;

    public static void criarProjeto(Projeto projeto){
        RepositoryProjeto.INSTANCE.salvarProjeto(projeto);
    }

    public static boolean adicionarPessoa(String titulo, String username){
        // Obter projeto e verificar se ele está cheio
        Projeto projeto = buscarProjeto(titulo);

        if (projeto.getPessoasDTO().size() >= MAX_PESSOAS_POR_PROJETO) return false;

        // Verificar se ela já está no projeto, e obter pessoa caso contrário
        int count = (int) projeto.getPessoasDTO().stream()
                .filter(pessoaDTO -> pessoaDTO.username().equalsIgnoreCase(username)).count();

        if (count > 0) return false;
        Pessoa pessoa = PessoaService.buscarPessoa(username);

        // Salvar pessoa no projeto
        RepositoryProjeto.INSTANCE.salvarPessoasProjeto(projeto.getId(), List.of(pessoa));
        return true;
    }

    public static boolean adicionarTag(String titulo, Tag tag) {
        // Obter projeto
        Projeto projeto = buscarProjeto(titulo);

        // Verificar se tag já existe
        if (projeto.getTags().contains(tag)) return false;

        RepositoryProjeto.INSTANCE.salvarTagProjeto(projeto.getId(), List.of(tag));
        return true;
    }

    public static Projeto buscarProjeto(String titulo) {
        Predicate<Projeto> predicate = projeto -> projeto.getTitulo().equals(titulo);
        return RepositoryProjeto.INSTANCE.buscarProjetos(predicate).orElseThrow().get(0);
    }

    public static List<Projeto> buscarProjetos(String titulo) {
        Predicate<Projeto> predicate = projeto -> projeto.getTitulo().trim().toLowerCase().contains(titulo.toLowerCase());
        return RepositoryProjeto.INSTANCE.buscarProjetos(predicate).orElseThrow();
    }
}
