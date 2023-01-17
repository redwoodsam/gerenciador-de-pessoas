package com.samuelaraujo.gerenciadorpessoas.service;

import com.samuelaraujo.gerenciadorpessoas.exception.InformacaoInvalidaException;
import com.samuelaraujo.gerenciadorpessoas.exception.NaoEncontradoException;
import com.samuelaraujo.gerenciadorpessoas.dto.request.CadastroEnderecoDTO;
import com.samuelaraujo.gerenciadorpessoas.dto.request.CadastroEnderecoPrincipalDTO;
import com.samuelaraujo.gerenciadorpessoas.dto.request.CadastroPessoaDTO;
import com.samuelaraujo.gerenciadorpessoas.dto.request.ConsultaDTO;
import com.samuelaraujo.gerenciadorpessoas.entity.Endereco;
import com.samuelaraujo.gerenciadorpessoas.entity.Pessoa;
import com.samuelaraujo.gerenciadorpessoas.repository.EnderecoRepository;
import com.samuelaraujo.gerenciadorpessoas.repository.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    private DateTimeFormatter formatadorDeDatas = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public List<Pessoa> listar() {
        return pessoaRepository.findAll();
    }

    public Pessoa buscarPorUuid(String uuid) {
        return pessoaRepository.findByUuid(uuid).orElseThrow(() -> new NaoEncontradoException("Pessoa não encontrada"));
    }

    public List<Pessoa> consultar(ConsultaDTO consultaDTO) {
        return pessoaRepository.findByNomeContainingIgnoreCase(consultaDTO.getConsulta());
    }

    public Pessoa salvar(CadastroPessoaDTO pessoaDTO) {
        LocalDate dataNascimento = validaData(pessoaDTO.getDataNascimento());
        Pessoa pessoa = new Pessoa(UUID.randomUUID().toString(), pessoaDTO.getNome(), dataNascimento);
        Pessoa pessoaPersistida = pessoaRepository.save(pessoa);

        try {
            if (pessoaDTO.getEndereco() == null) {
                throw new InformacaoInvalidaException("Um endereço precisa ser informado");
            }

            CadastroEnderecoDTO enderecoDTO = pessoaDTO.getEndereco();
            Endereco entidadeEndereco = new Endereco(UUID.randomUUID().toString(),
                    enderecoDTO.getLogradouro(),
                    enderecoDTO.getNumero(),
                    enderecoDTO.getCep(),
                    enderecoDTO.getCidade(),
                    pessoaPersistida
            );
            pessoaPersistida.adicionarEndereco(entidadeEndereco);
            pessoaPersistida.setEnderecoPrincipal(entidadeEndereco);

            return pessoaRepository.save(pessoaPersistida);

        } catch (RuntimeException ex) {
            pessoaRepository.delete(pessoaPersistida);
            throw new InformacaoInvalidaException(ex.getMessage());
        }

    }

    public Pessoa atualizar(String uuidPessoa, CadastroPessoaDTO pessoaDTO) {
        LocalDate dataNascimento = validaData(pessoaDTO.getDataNascimento());
        Pessoa pessoaSalva = buscarPorUuid(uuidPessoa);
        Pessoa pessoa = new Pessoa(UUID.randomUUID().toString(), pessoaDTO.getNome(), dataNascimento);

        BeanUtils.copyProperties(pessoa, pessoaSalva, "id", "uuid", "enderecos", "enderecoPrincipal");

        return pessoaRepository.save(pessoaSalva);
    }

    public List<Endereco> listarEnderecosPorUsuarioUuid(String uuid) {
        buscarPorUuid(uuid);
        return enderecoRepository.listaPorUuidPessoa(uuid);
    }

    public Endereco adicionarEndereco(String uuidPessoa, CadastroEnderecoDTO enderecoDTO) {
        Pessoa pessoaSalva = buscarPorUuid(uuidPessoa);

        Endereco entidadeEndereco = new Endereco(UUID.randomUUID().toString(),
                enderecoDTO.getLogradouro(),
                enderecoDTO.getNumero(),
                enderecoDTO.getCep(),
                enderecoDTO.getCidade(),
                pessoaSalva
                );

        if(pessoaSalva.getEnderecos().contains(entidadeEndereco)) {
            throw new InformacaoInvalidaException("O endereço informado já existe. Insira outro diferente.");
        }



        return enderecoRepository.save(entidadeEndereco);
    }

    public Pessoa setarEnderecoPrincipal(String uuidPessoa, CadastroEnderecoPrincipalDTO enderecoDTO) {
        Pessoa pessoaSalva = buscarPorUuid(uuidPessoa);
        Endereco enderecoSalvo = buscarEnderecoPorUuid(enderecoDTO.getUuid());
        pessoaSalva.setEnderecoPrincipal(enderecoSalvo);
        return pessoaRepository.save(pessoaSalva);
    }

    private Endereco buscarEnderecoPorUuid(String uuid) {
        return enderecoRepository
                .findByUuid(uuid)
                .orElseThrow(() -> new NaoEncontradoException("Endereco não encontrado"));
    }

    private LocalDate validaData(String data) {
        try {
            LocalDate dataConvertida = LocalDate.parse(data, formatadorDeDatas);
            return dataConvertida;
        } catch (DateTimeParseException ex) {
            throw new InformacaoInvalidaException("Data inválida.");
        }
    }
}
