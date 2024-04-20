package com.dev.timetracker.repository;

import com.dev.timetracker.model.Pessoa;
import com.dev.timetracker.utility.TipoCargo;
import com.dev.timetracker.utility.TipoPlano;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
@EnableAutoConfiguration
public enum RepositoryPessoa {
    INSTANCE(new JdbcTemplate());

    private static final String SELECT_ALL = "SELECT * FROM users order by 1";
    private static final String SELECT = "SELECT * FROM users WHERE username = ?";
    private static final String INSERT = "INSERT INTO users (username, full_name, cpf, role, plan) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE users SET full_name = ?, cpf = ?, role = ?, plan = ? WHERE username = ?";
    private static final String DELETE = "DELETE FROM users WHERE username = ?";

    private final JdbcTemplate jdbcTemplate;

    @Getter
    private List<Pessoa> pessoas;

    RepositoryPessoa(JdbcTemplate jdbcTemplate) {;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void getUsers() {
        pessoas = jdbcTemplate.query(SELECT_ALL, (rs, _) -> new Pessoa.Builder()
                .username(rs.getString("username"))
                .nome(rs.getString("full_name"))
                .cpf(rs.getString("cpf"))
                .cargo(TipoCargo.valueOf(rs.getString("role")))
                .plano(TipoPlano.valueOf(rs.getString("plan")))
                .build());
    }

    public Pessoa searchUser(String username) {
        return jdbcTemplate.query(SELECT, (rs, _) -> new Pessoa.Builder()
                .username(rs.getString("username"))
                .nome(rs.getString("full_name"))
                .cpf(rs.getString("cpf"))
                .cargo(TipoCargo.valueOf(rs.getString("role")))
                .plano(TipoPlano.valueOf(rs.getString("plan")))
                .build())
                .stream()
                .findFirst()
                .orElse(null);
    }

    public boolean deleteUser(String username) {
        return jdbcTemplate.update(DELETE, username) > 0;
    }

    public boolean updateUser(Pessoa user) {
        Object[] attr = new Object[]{
                user.getNome(),
                user.getCpf(),
                user.getCargo(),
                user.getPlano(),
                user.getUsername()
        };
        return jdbcTemplate.update(UPDATE, attr) > 0;
    }

    public void salvarPessoa(Pessoa user) {
        Object[] attr = new Object[]{
                user.getUsername(),
                user.getNome(),
                user.getCpf(),
                user.getCargo(),
                user.getPlano()
        };
        jdbcTemplate.update(INSERT, attr);
        pessoas.add(user);
    }

    public Pessoa buscarPessoa(String id) {
        return pessoas.stream()
                .filter(pessoa -> pessoa.getId().toString().equals(id))
                .findFirst().orElse(null);
    }

    public Optional<List<Pessoa>> buscarPessoas(Predicate<Pessoa> predicate) {
        return Optional.of(pessoas.stream()
                .filter(predicate)
                .collect(Collectors.toList()));
    }
}