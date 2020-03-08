package ru.mail.druk_aleksandr.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.mail.druk_aleksandr.ConnectionRepository;
import ru.mail.druk_aleksandr.DocumentRepository;
import ru.mail.druk_aleksandr.DocumentService;
import ru.mail.druk_aleksandr.model.Document;
import ru.mail.druk_aleksandr.model.DocumentDTO;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DocumentServiceImpl implements DocumentService {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private ConnectionRepository connectionRepository;
    private DocumentRepository documentRepository;

    public DocumentServiceImpl(ConnectionRepository connectionRepository,
                               DocumentRepository documentRepository) {
        this.connectionRepository = connectionRepository;
        this.documentRepository = documentRepository;
    }

    @Override
    public void addDocument(DocumentDTO documentDTO) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Document document = convertDTOToDocument(documentDTO);
                documentRepository.add(connection, document);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public List<DocumentDTO> findAll() {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<Document> documents = documentRepository.findAll(connection);
                List<DocumentDTO> documentDTOS = documents.stream()
                        .map(this::convertObjectToDTO)
                        .collect(Collectors.toList());
                connection.commit();
                return documentDTOS;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public List<DocumentDTO> findDocumentById(int id) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                List<Document> documents = documentRepository.findById(connection, id);
                List<DocumentDTO> documentDTOS = documents.stream()
                        .map(this::convertObjectToDTO)
                        .collect(Collectors.toList());
                connection.commit();
                return documentDTOS;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return Collections.emptyList();
    }

    @Override
    public void deleteDocumentById(int id) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                documentRepository.delete(connection, id);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private DocumentDTO convertObjectToDTO(Document document) {
        DocumentDTO documentDTO = new DocumentDTO();
        documentDTO.setId(document.getId());
        documentDTO.setUnique_number(document.getUnique_number());
        documentDTO.setDescription(document.getDescription());
        return documentDTO;
    }

    private Document convertDTOToDocument(DocumentDTO documentDTO) {
        Document document = new Document();
        document.setUnique_number(UUID.randomUUID().toString());
        document.setDescription(documentDTO.getDescription());
        return document;
    }
}
