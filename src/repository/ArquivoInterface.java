package repository;

import java.util.List;

public interface ArquivoInterface {
    public List<String> lerArquivo();
    public boolean escreverArquivo(String linha);
}