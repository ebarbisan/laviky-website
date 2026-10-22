package br.com.laviky.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${laviky.mail.from}")
    private String fromEmail;

    public void enviarNotificacaoEmpresarial(String destinatario, String corpo) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(destinatario);
            message.setSubject("Nova Solicitação de Atendimento Empresarial - Lavanderia Laviky");
            message.setText(corpo);
            mailSender.send(message);
        } catch (Exception e) {
            log.error("Erro ao enviar e-mail de notificação: {}", e.getMessage());
            throw new RuntimeException("Falha no envio do e-mail de notificação.");
        }
    }
}
