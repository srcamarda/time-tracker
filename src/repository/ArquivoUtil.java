package repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//Funções base de leitura e escrita para arquivos
public class ArquivoUtil {
    File arquivo;

    public ArquivoUtil(ArquivoPaths path) {
        this.arquivo = new File(path.getPath());
    }

    public List<String> lerArquivo() {
        List<String> linhas = new ArrayList<>();

        try (Scanner scanner = new Scanner(this.arquivo)) {
            while (scanner.hasNextLine()) {
                linhas.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return linhas;
    }

    public void escreverArquivo(String linha) {
        try (FileWriter writer = new FileWriter(this.arquivo, true)) {
            writer.write("\n" + linha);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}