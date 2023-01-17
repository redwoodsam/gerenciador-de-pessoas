package com.samuelaraujo.gerenciadorpessoas.repository;

import com.samuelaraujo.gerenciadorpessoas.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    Optional<Pessoa> findByUuid(String uuid);
    List<Pessoa> findByNomeContainingIgnoreCase(String consulta);
}
