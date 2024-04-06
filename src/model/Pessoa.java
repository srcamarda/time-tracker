package model;

import utility.TipoCargo;

import java.util.Objects;
import java.util.UUID;

public class Pessoa {
    private UUID id;
    private String nome;
    private TipoCargo cargo;

    private Pessoa(UUID id, String nome, TipoCargo cargo) {
        this.id = id;
        this.nome = nome;
        this.cargo = cargo;
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public TipoCargo getCargo() {
        return cargo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pessoa pessoa = (Pessoa) o;
        return Objects.equals(id, pessoa.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cargo=" + cargo +
                '}';
    }

    public static class Builder {
        private UUID id;
        private String nome;
        private TipoCargo cargo;

        public Builder id(String id) {
            this.id = UUID.fromString(id);
            return this;
        }

        public Builder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder cargo(TipoCargo cargo) {
            this.cargo = cargo;
            return this;
        }

        public Pessoa build() {
            if (Objects.isNull(id)) {
                id = UUID.randomUUID();
            }

            return new Pessoa(id, nome, cargo);
        }
    }

    public enum TipoCargo {
        JUNIOR,
        PLENO,
        SENIOR,
        SCRUM_MASTER,
        PRODUCT_OWNER
    }
}


