package server.dataTransferObject;


import lombok.Builder;

@Builder
public record CreatePDIDTO(String nome, Posicao posicao, String aviso, Boolean acessivel) {
}
