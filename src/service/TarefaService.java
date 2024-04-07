package service;


import model.Tarefa;
import utility.singleton.TarefaSingleton;

public class TarefaService {
    public static Tarefa buscarProjeto(String titulo) {
        String tarefaSearch = titulo.toLowerCase();
        return TarefaSingleton.INSTANCE.getRepositoryTarefa().buscarTarefaTitulo(tarefaSearch);
    }
}
