package com.samuelaraujo.gerenciadorpessoas.repository;

import com.samuelaraujo.gerenciadorpessoas.entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    Optional<Endereco> findByUuid(String uuid);
    @Query("select e from Endereco e where e.pessoa.uuid = :uuid")
    List<Endereco> listaPorUuidPessoa(String uuid);
}
