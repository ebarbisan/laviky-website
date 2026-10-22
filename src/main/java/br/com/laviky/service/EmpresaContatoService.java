package br.com.laviky.service;

import br.com.laviky.dto.EmpresaContatoRequest;
import br.com.laviky.model.EmpresaContato;
import br.com.laviky.repository.EmpresaContatoRepository;
import br.com.laviky.validation.CnpjValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmpresaContatoService {

    private final EmpresaContatoRepository repository;
    private final CnpjValidator cnpjValidator;
    private final ArquivoService arquivoService;
    private final EmailService emailService;

    @Value("${laviky.contact-email}")
    private String contactEmail;

    @Transactional
    public void processarSolicitacao(EmpresaContatoRequest request, String ip, String userAgent) {
        // 1. Validate CNPJ
        if (!cnpjValidator.isValid(request.getCnpj())) {
            throw new IllegalArgumentException("CNPJ inválido.");
        }

        // 2. Handle File Upload
        String secureFileName = null;
        String originalFileName = null;
        String contentType = null;
        Long fileSize = null;

        if (request.getAnexo() != null && !request.getAnexo().isEmpty()) {
            try {
                originalFileName = request.getAnexo().getOriginalFilename();
                contentType = request.getAnexo().getContentType();
                fileSize = request.getAnexo().getSize();
                secureFileName = arquivoService.salvarArquivo(request.getAnexo());
            } catch (IOException e) {
                log.error("Erro ao salvar anexo: {}", e.getMessage());
                throw new RuntimeException("Erro ao processar arquivo anexo: " + e.getMessage());
            }
        }

        // 3. Save to Database
        EmpresaContato contato = EmpresaContato.builder()
                .empresa(request.getEmpresa())
                .cnpj(request.getCnpj().replaceAll("\\D", ""))
                .responsavel(request.getResponsavel())
                .email(request.getEmail())
                .telefone(request.getTelefone())
                .quantidadeMedia(request.getQuantidadeMedia())
                .mensagem(request.getMensagem())
                .arquivoNome(secureFileName)
                .arquivoNomeOriginal(originalFileName)
                .arquivoTipo(contentType)
                .arquivoTamanho(fileSize)
                .ipOrigem(ip)
                .userAgent(userAgent)
                .status("PENDENTE")
                .statusEmail("NAO_ENVIADO")
                .build();

        EmpresaContato saved = repository.save(contato);

        // 4. Send Notification Email
        try {
            String emailBody = String.format(
                "Nova solicitação recebida:\n\nEmpresa: %s\nCNPJ: %s\nResponsável: %s\nE-mail: %s\nTelefone: %s\nVolume: %s\n\nMensagem:\n%s",
                saved.getEmpresa(), saved.getCnpj(), saved.getResponsavel(), saved.getEmail(), saved.getTelefone(), saved.getQuantidadeMedia(), saved.getMensagem()
            );
            emailService.enviarNotificacaoEmpresarial(contactEmail, emailBody);

            saved.setStatusEmail("ENVIADO");
            repository.save(saved);
        } catch (Exception e) {
            log.error("Falha ao enviar e-mail de notificação para ID {}: {}", saved.getId(), e.getMessage());
            // We don't throw exception here so the lead isn't lost in DB
        }
    }
}
