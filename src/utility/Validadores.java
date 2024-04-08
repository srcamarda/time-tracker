package utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class Validadores {
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
    public static boolean validaCPF(String cpf) {
        cpf = cpf.replaceAll("[^\\d]", "");
        return cpf.length() == 11 && !cpf.matches("^(\\d)\\1{10}$");
    }

    public static boolean validaNome(String nome) {
        return nome != null && !nome.isEmpty() && !nome.equals("null") && nome.matches("^[a-zA-Z ]+$");
    }

    public static boolean validaUsername(String username) {
        return username != null && !username.isEmpty() && !username.equals("null") && username.matches("^[a-z0-9_]+$");
    }

    public static boolean validaTexto(String texto) {
        return texto != null && !texto.isEmpty() && !texto.equals("null");
    }

    public static boolean validaData(String data) {
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(data.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    public static boolean validaDataFinal(LocalDateTime dataInicio, LocalDateTime dataFinal) {
        return dataFinal.isAfter(dataInicio);
    }

    public static boolean validaEnum(String valor){
        return valor.matches("^[A-Z0-9_]*$");
    }

    public static boolean validaUUID(String uuid) {
        return uuid.matches("^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$");
    }
}