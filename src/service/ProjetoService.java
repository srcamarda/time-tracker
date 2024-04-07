package service;

import model.Projeto;
import utility.singleton.ProjetoSingleton;

public class ProjetoService {

    public static Projeto buscarProjeto(String titulo) {
        String tituloSearch = titulo.toLowerCase();
        return ProjetoSingleton.INSTANCE.getRepositoryProjeto().buscarProjetoTitulo(tituloSearch);
    }
}
