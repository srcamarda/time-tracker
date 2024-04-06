package repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Arquivo implements ArquivoInterface {
    File arquivo;

    public Arquivo(ArquivoPaths path) {
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

    public boolean escreverArquivo(String linha) {
        try (FileWriter writer = new FileWriter(this.arquivo, true)) {
            writer.write(linha + "\n");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return true;
    }
}