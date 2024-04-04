import pessoa.Pessoa;
import pessoa.TipoCargo;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        Pessoa pessoa = new Pessoa.Builder().nome("Paulo").cargo(TipoCargo.JUNIOR).build();
        System.out.println(pessoa);

        String uuid = UUID.randomUUID().toString();
        System.out.println(uuid);
        Pessoa pessoa2 = new Pessoa.Builder().id(uuid).nome("Pedro").cargo(TipoCargo.JUNIOR).build();
        System.out.println(pessoa2);
    }
}