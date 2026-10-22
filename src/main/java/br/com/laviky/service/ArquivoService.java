package br.com.laviky.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArquivoService {

    @Value("${laviky.upload.dir}")
    private String uploadDir;

    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("pdf", "jpg", "jpeg", "png");
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB

    public String salvarArquivo(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) return null;

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) throw new IOException("Nome do arquivo inválido");

        String extension = getFileExtension(originalFilename);
        if (!ALLOWED_EXTENSIONS.contains(extension.toLowerCase())) {
            throw new IOException("Extensão de arquivo não permitida. Use PDF, JPG, JPEG ou PNG.");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IOException("O arquivo excede o tamanho máximo permitido de 5MB.");
        }

        // Validate MIME type
        String contentType = file.getContentType();
        if (!isValidContentType(contentType, extension)) {
            throw new IOException("O conteúdo do arquivo não corresponde à extensão fornecida.");
        }

        // Secure filename
        String secureName = UUID.randomUUID().toString() + "." + extension;
        Path root = Paths.get(uploadDir).toAbsolutePath().normalize();

        if (!Files.exists(root)) {
            Files.createDirectories(root);
        }

        Files.copy(file.getInputStream(), root.resolve(secureName), StandardCopyOption.REPLACE_EXISTING);

        return secureName;
    }

    private String getFileExtension(String filename) {
        int lastIndexOf = filename.lastIndexOf(".");
        if (lastIndexOf == -1) return "";
        return filename.substring(lastIndexOf + 1);
    }

    private boolean isValidContentType(String contentType, String extension) {
        if (contentType == null) return false;
        return switch (extension.toLowerCase()) {
            case "pdf" -> contentType.equals("application/pdf");
            case "jpg", "jpeg" -> contentType.equals("image/jpeg");
            case "png" -> contentType.equals("image/png");
            default -> false;
        };
    }
}
