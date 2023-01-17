package com.samuelaraujo.gerenciadorpessoas.controller;

import com.samuelaraujo.gerenciadorpessoas.dto.request.CadastroEnderecoDTO;
import com.samuelaraujo.gerenciadorpessoas.dto.request.CadastroEnderecoPrincipalDTO;
import com.samuelaraujo.gerenciadorpessoas.dto.request.CadastroPessoaDTO;
import com.samuelaraujo.gerenciadorpessoas.dto.request.ConsultaDTO;
import com.samuelaraujo.gerenciadorpessoas.dto.response.RespostaEnderecoDTO;
import com.samuelaraujo.gerenciadorpessoas.dto.response.RespostaPessoaDTO;
import com.samuelaraujo.gerenciadorpessoas.entity.Endereco;
import com.samuelaraujo.gerenciadorpessoas.entity.Pessoa;
import com.samuelaraujo.gerenciadorpessoas.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping
    public List<RespostaPessoaDTO> listar() {
        return pessoaService.listar()
                .stream()
                .map(pessoa -> RespostaPessoaDTO.from(pessoa))
                .collect(Collectors.toList());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<RespostaPessoaDTO> buscarPorUuid(@PathVariable String uuid) {
        Pessoa pessoa = pessoaService.buscarPorUuid(uuid);
        return ResponseEntity.ok(RespostaPessoaDTO.from(pessoa));
    }

    @PostMapping("/consulta")
    public List<RespostaPessoaDTO> consultaPessoas(@RequestBody @Valid ConsultaDTO consultaDTO) {
        return pessoaService.consultar(consultaDTO)
                .stream()
                .map(pessoa -> RespostaPessoaDTO.from(pessoa))
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<RespostaPessoaDTO> cadastrar(@RequestBody @Valid CadastroPessoaDTO cadastroPessoaDTO) {
        Pessoa pessoaCadastrada = pessoaService.salvar(cadastroPessoaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(RespostaPessoaDTO.from(pessoaCadastrada));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<RespostaPessoaDTO> atualizar(@PathVariable String uuid,
                                                       @RequestBody @Valid CadastroPessoaDTO cadastroPessoaDTO) {
        Pessoa pessoaCadastrada = pessoaService.atualizar(uuid, cadastroPessoaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(RespostaPessoaDTO.from(pessoaCadastrada));
    }

    @GetMapping("/{uuid}/enderecos")
    public List<RespostaEnderecoDTO> listarEnderecos(@PathVariable String uuid) {
        return pessoaService.listarEnderecosPorUsuarioUuid(uuid)
                .stream()
                .map(endereco -> RespostaEnderecoDTO.from(endereco))
                .collect(Collectors.toList());
    }
    
    @PostMapping("/{uuid}/enderecos")
    public ResponseEntity<RespostaEnderecoDTO> cadastrarEndereco(@PathVariable String uuid, 
                                                       @RequestBody @Valid CadastroEnderecoDTO enderecoDTO) {
        Endereco enderecoSalvo = pessoaService.adicionarEndereco(uuid, enderecoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(RespostaEnderecoDTO.from(enderecoSalvo));
    }

    @PostMapping("/{uuid}/enderecos/principal")
    public ResponseEntity<RespostaPessoaDTO> setarEnderecoPrincipal(@PathVariable String uuid,
                                                       @RequestBody @Valid CadastroEnderecoPrincipalDTO enderecoDTO) {
        Pessoa pessoa = pessoaService.setarEnderecoPrincipal(uuid, enderecoDTO);
        return ResponseEntity.ok(RespostaPessoaDTO.from(pessoa));
    }

}
