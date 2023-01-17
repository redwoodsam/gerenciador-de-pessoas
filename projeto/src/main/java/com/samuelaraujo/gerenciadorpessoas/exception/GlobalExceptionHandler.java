package com.samuelaraujo.gerenciadorpessoas.exception;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Configura as mensagens de erro retornadas ao usuário
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {
            InformacaoInvalidaException.class,
            NaoEncontradoException.class,
            JsonParseException.class,
            JsonMappingException.class,
            RuntimeException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleGeral(RuntimeException ex, WebRequest request) {
        RespostaErro respostaErro = criarMensagemErro(request, ex);
        return ResponseEntity.badRequest().body(respostaErro);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<RespostaErro> erros = criarListaDeErros(ex.getBindingResult(), request);
        return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
    }

    private List<RespostaErro> criarListaDeErros(BindingResult bindingResult, WebRequest request) {
        List<RespostaErro> erros = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            erros.add(criarMensagemErro(request, new InformacaoInvalidaException(fieldError.getDefaultMessage())));
        }

        return erros;
    }

    private RespostaErro criarMensagemErro(WebRequest request, RuntimeException ex) {
        String caminho = getPath(request);
        String metodoHttp = getHttpMethod(request);
        String mensagem = ex.getMessage();

        return new RespostaErro(caminho, metodoHttp, mensagem);
    }

    class RespostaErro {
        private String caminho;
        private String metodo;
        private String mensagem;

        private RespostaErro(String caminho, String metodo, String mensagem) {
            this.caminho = caminho;
            this.metodo = metodo;
            this.mensagem = mensagem;
        }

        public String getCaminho() {
            return caminho;
        }

        public String getMetodo() {
            return metodo;
        }

        public String getMensagem() {
            return mensagem;
        }

    }

    private String getPath(WebRequest request) {
        return ((ServletWebRequest) request).getRequest().getRequestURI();
    }

    private String getHttpMethod(WebRequest request) {
        return ((ServletWebRequest) request).getRequest().getMethod();
    }
}
