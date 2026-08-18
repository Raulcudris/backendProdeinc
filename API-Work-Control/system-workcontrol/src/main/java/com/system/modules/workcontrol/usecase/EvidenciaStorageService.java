package com.system.modules.workcontrol.usecase;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.system.crosscutting.domain.model.EvidenciaUploadResponse;

@Service
public class EvidenciaStorageService {

    private static final String BASE_FOLDER = "uploads/evidencias";
    private static final long MAX_FILE_SIZE = 20L * 1024L * 1024L;

    public EvidenciaUploadResponse upload(
            final MultipartFile file
    ) throws IOException {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("El archivo es obligatorio.");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException(
                    "El archivo no puede superar 20 MB."
            );
        }

        String originalName = file.getOriginalFilename() != null
                ? file.getOriginalFilename()
                : "archivo";

        String extension = extractExtension(originalName);
        String safeName = buildSafeName(extension);

        Path folder = Paths.get(BASE_FOLDER).toAbsolutePath().normalize();

        Files.createDirectories(folder);

        Path destination = folder.resolve(safeName).normalize();

        file.transferTo(destination.toFile());

        return EvidenciaUploadResponse
                .builder()
                .rspValue("OK")
                .rspMessage("Archivo cargado correctamente.")
                .fileName(safeName)
                .originalFileName(originalName)
                .contentType(file.getContentType())
                .size(file.getSize())
                .url("/uploads/evidencias/" + safeName)
                .build();
    }

    private String buildSafeName(final String extension) {
        String timestamp = LocalDateTime
                .now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        String uuid = UUID.randomUUID().toString().replace("-", "");

        if (extension == null || extension.isEmpty()) {
            return timestamp + "-" + uuid;
        }

        return timestamp + "-" + uuid + "." + extension;
    }

    private String extractExtension(final String fileName) {
        int index = fileName.lastIndexOf(".");

        if (index < 0 || index == fileName.length() - 1) {
            return "";
        }

        return fileName.substring(index + 1).toLowerCase();
    }
}