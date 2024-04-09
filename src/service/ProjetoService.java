package service;

import dto.PessoaDTO;
import model.Projeto;
import repository.RepositoryProjeto;
import utility.Conversores;
import utility.TipoCargo;
import utility.singleton.PessoaSingleton;
import utility.singleton.ProjetoSingleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProjetoService {

//    public void criarProjetoMenu(){
//        List<PessoaDTO> listaPessoas = new ArrayList<>();
//        PessoaSingleton.INSTANCE.getRepositoryPessoa().listarPessoasAptas(TipoCargo.SENIOR);
//        System.out.println("Escolha uma pessoa do sistema:");
//        String pessoa = new Scanner(System.in).nextLine();
//
//        listaPessoas.add(Conversores.converterParaDTO(PessoaService.buscarPessoas(pessoa).get(0)));
//
//        System.out.println(PessoaSingleton.INSTANCE.getRepositoryPessoa().listarPessoasAptas(TipoCargo.JUNIOR));
//        System.out.println(PessoaSingleton.INSTANCE.getRepositoryPessoa().listarPessoasAptas(TipoCargo.PLENO));
//
//        System.out.println("Escolha as pessoas do time:");
//        String pessoas = new Scanner(System.in).nextLine();
//
//        System.out.println("Digite o Titulo do projeto:");
//        String titulo = new Scanner(System.in).nextLine();
//
//        System.out.println(criarProjeto(new Projeto.Builder().pessoasDTO(listaPessoas).titulo(titulo).build()));
//    }

    public static boolean criarProjeto(Projeto projeto){

        return true;
    }

    public static List<Projeto> buscarProjeto(String titulo) {
        return RepositoryProjeto.INSTANCE.buscarProjetos(titulo.toLowerCase());
    }
}
