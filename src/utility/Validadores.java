package utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Validadores {
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public static boolean validaCPF(String cpf) {
        cpf = cpf.replaceAll("[^\\d]", "");
        return cpf.length() == 11 && !cpf.matches("^(\\d)\\1{10}$");
    }

    public static boolean validaNome(String nome) {
        return nome != null && !nome.isEmpty();
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

    public static boolean validaDataFinal(Date dataInicio, Date dataFinal) {
        return dataFinal.after(dataInicio);
    }
}