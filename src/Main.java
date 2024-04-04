import pessoa.Pessoa;
import pessoa.TipoCargo;
import projeto.Tarefa;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        // Pessoa
        Pessoa pessoa = new Pessoa.Builder().nome("Paulo").cargo(TipoCargo.JUNIOR).build();
        System.out.println(pessoa);

        String uuid = UUID.randomUUID().toString();
        System.out.println(uuid);
        Pessoa pessoa2 = new Pessoa.Builder().id(uuid).nome("Pedro").cargo(TipoCargo.JUNIOR).build();
        System.out.println(pessoa2);

        // Tarefa
        Tarefa tarefa = new Tarefa.Builder().titulo("Estruturar projeto").build();
        System.out.println(tarefa);

        String uuidTarefa = UUID.randomUUID().toString();
        System.out.println(uuidTarefa);
        Tarefa tarefa1 = new Tarefa.Builder().id(uuidTarefa).titulo("Testar classes").pessoa(pessoa).build();
        System.out.println(tarefa1);
    }
}