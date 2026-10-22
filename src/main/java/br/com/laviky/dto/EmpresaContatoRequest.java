package br.com.laviky.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaContatoRequest {

    @NotBlank(message = "O nome da empresa é obrigatório")
    private String empresa;

    @NotBlank(message = "O CNPJ é obrigatório")
    @Pattern(regexp = "\\d{14}", message = "O CNPJ deve conter 14 dígitos numéricos")
    private String cnpj;

    @NotBlank(message = "O nome do responsável é obrigatório")
    private String responsavel;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "Informe um e-mail corporativo válido")
    private String email;

    @NotBlank(message = "O telefone é obrigatório")
    private String telefone;

    private String quantidadeMedia;

    @NotBlank(message = "A mensagem é obrigatória")
    @Size(max = 5000, message = "A mensagem deve ter no máximo 5000 caracteres")
    private String mensagem;

    private MultipartFile anexo;
}
