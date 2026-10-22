package br.com.laviky.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "solicitacoes_empresariais")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpresaContato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String empresa;

    @Column(nullable = false, length = 20)
    private String cnpj;

    @Column(nullable = false)
    private String responsavel;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, length = 20)
    private String telefone;

    private String quantidadeMedia;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensagem;

    private String arquivoNome;
    private String arquivoNomeOriginal;
    private String arquivoTipo;
    private Long arquivoTamanho;

    private String ipOrigem;
    private String userAgent;

    @Column(length = 50)
    private String status = "PENDENTE";

    @Column(length = 50)
    private String statusEmail = "NAO_ENVIADO";

    @Column(name = "data_criacao", updatable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao = LocalDateTime.now();

    @PreUpdate
    protected void onUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }
}
