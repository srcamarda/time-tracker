package com.dev.tptimetracker.repository.arquivo;

import java.nio.file.Path;
import java.nio.file.Paths;

//Retorna o caminho absoluto para um arquivo do banco de dados
public enum ArquivoPaths {
    PESSOAS("pessoas"),
    PESSOAS_PROJ("pessoasEmProjeto"),
    PROJETOS("projetos"),
    TAGS_PROJ("tagsEmProjeto"),
    TAREFAS("tarefas"),
    TAREFAS_PROJ("tarefasEmProjeto");

    private final String path;

    ArquivoPaths(String pathArquivo) {
        this.path = pathArquivo;
    }

    public String getPath() {
        Path arquivo = Paths.get("src","database", path + ".csv");
        return arquivo.toFile().getAbsolutePath();
    }
}