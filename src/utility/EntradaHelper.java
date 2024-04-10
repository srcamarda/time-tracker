package utility;

import java.util.Scanner;

public class EntradaHelper {
    public static String obterDado(String mensagem, Scanner scanner) {
        String dado;
        System.out.print("\n" + mensagem);
        dado = scanner.nextLine();
        return dado;
    }
}