package service;


import model.Tarefa;
import utility.singleton.TarefaSingleton;

import java.util.List;

public class TarefaService {
    public static List<Tarefa> buscarTarefa(String titulo) {
        return TarefaSingleton.INSTANCE.getRepositoryTarefa().buscarTarefasComTitulo(titulo.toLowerCase());
    }
}
