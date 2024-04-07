package service;

import model.Projeto;
import utility.singleton.ProjetoSingleton;

import java.util.List;

public class ProjetoService {

    public static List<Projeto> buscarProjeto(String titulo) {
        return ProjetoSingleton.INSTANCE.getRepositoryProjeto().buscarProjetosComTitulo(titulo.toLowerCase());
    }
}
