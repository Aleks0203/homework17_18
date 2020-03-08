package ru.mail.druk_aleksandr;

import ru.mail.druk_aleksandr.model.DocumentDTO;

import java.util.List;

public interface DocumentService {
    void addDocument(DocumentDTO documentDTO);

    List<DocumentDTO> findAll();

    List<DocumentDTO> findDocumentById(int id);

    void deleteDocumentById(int id);
}
