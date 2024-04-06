package model;

import dto.PessoaDTO;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public class Tarefa {
    private UUID id;
    private String titulo;
    private String descricao;
    private PessoaDTO pessoa;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private Tag tag;

    private Tarefa(UUID id, String titulo, String descricao, PessoaDTO pessoa, LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, Tag tag) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.pessoa = pessoa;
        this.dataHoraInicio = dataHoraInicio;
        this.dataHoraFim = dataHoraFim;
        this.tag = tag;
    }

    public UUID getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public PessoaDTO getPessoaDTO() {
        return pessoa;
    }

    public LocalDateTime getDataHoraInicio() {
        return dataHoraInicio;
    }

    public LocalDateTime getDataHoraFim() {
        return dataHoraFim;
    }

    public Tag getTag() {
        return tag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tarefa tarefa = (Tarefa) o;
        return Objects.equals(id, tarefa.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Tarefa{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", pessoa=" + pessoa +
                ", dataHoraInicio=" + dataHoraInicio +
                ", dataHoraFim=" + dataHoraFim +
                ", tag=" + tag +
                '}';
    }

    public static class Builder {
        private UUID id;
        private String titulo;
        private String descricao;
        private PessoaDTO pessoa;
        private LocalDateTime dataHoraInicio;
        private LocalDateTime dataHoraFim;
        private Tag tag;

        public Builder id(String id) {
            this.id = UUID.fromString(id);
            return this;
        }

        public Builder titulo(String titulo) {
            this.titulo = titulo;
            return this;
        }

        public Builder descricao(String descricao) {
            this.descricao = descricao;
            return this;
        }

        public Builder pessoaDTO(PessoaDTO pessoa) {
            this.pessoa = pessoa;
            return this;
        }

        public Builder dataHoraInicio(LocalDateTime dataHoraInicio) {
            this.dataHoraInicio = dataHoraInicio;
            return this;
        }

        public Builder dataHoraFim(LocalDateTime dataHoraFim) {
            this.dataHoraFim = dataHoraFim;
            return this;
        }

        public Builder tag(Tag tag) {
            this.tag = tag;
            return this;
        }

        public Tarefa build() {
            if(Objects.isNull(id)) {
                id = UUID.randomUUID();
            }

            if (Objects.isNull(dataHoraInicio)) {
                dataHoraInicio = LocalDateTime.now();
            }

            return new Tarefa(id, titulo, descricao, pessoa, dataHoraInicio, dataHoraFim, tag);
        }
    }
}
