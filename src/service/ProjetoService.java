package service;

import model.Pessoa;
import model.Projeto;
import repository.RepositoryProjeto;
import utility.Conversores;

import java.util.List;
import java.util.function.Predicate;

public class ProjetoService {
    private static final int MAX_PESSOAS_POR_PROJETO = 5;

    public static void criarProjeto(Projeto projeto){
        RepositoryProjeto.INSTANCE.salvarProjeto(projeto);
    }

    public static boolean adicionarPessoa(String titulo, String username){
        // Obter projeto e verificar se ele está cheio
        Predicate<Projeto> predicate = projeto -> projeto.getTitulo().equals(titulo);
        Projeto projeto = RepositoryProjeto.INSTANCE.buscarProjetos(predicate).orElseThrow().get(0);

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

    public static List<Projeto> buscarProjetos(String titulo) {
        Predicate<Projeto> predicate = projeto -> projeto.getTitulo().trim().toLowerCase().contains(titulo.toLowerCase());
        return RepositoryProjeto.INSTANCE.buscarProjetos(predicate).orElseThrow();
    }
}
