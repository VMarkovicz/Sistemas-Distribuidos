package server.dataTransferObject;


import lombok.Builder;
import server.dataTransferObject.Utils.Posicao;

@Builder
public record CreatePDIDTO(String nome, Posicao posicao, String aviso, Boolean acessivel) {
}
