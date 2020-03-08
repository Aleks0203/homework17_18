package ru.mail.druk_aleksandr.impl;

import org.springframework.stereotype.Repository;
import ru.mail.druk_aleksandr.DocumentRepository;
import ru.mail.druk_aleksandr.model.Document;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DocumentRepositoryImpl implements DocumentRepository {
    @Override
    public void add(Connection connection, Document document) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO document(unique_number,description) VALUES (?,?)",
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, document.getUnique_number());
            statement.setString(2, document.getDescription());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating document failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    document.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating document failed, no ID obtained.");
                }
            }
        }
    }

    @Override
    public List<Document> findAll(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT id, unique_number, description FROM document")) {
            List<Document> documents = new ArrayList<>();
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Document document = getDocument(rs);
                    documents.add(document);
                }
                return documents;
            }
        }
    }

    @Override
    public List<Document> findById(Connection connection, int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT id, unique_number, description FROM document WHERE id =?")) {
            statement.setInt(1, id);
            List<Document> documents = new ArrayList<>();
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Document document = getDocument(rs);
                    documents.add(document);
                }
                return documents;
            }
        }
    }

    @Override
    public void delete(Connection connection, int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM document WHERE id=?")) {
            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting document failed, no rows affected.");
            }
        }
    }

    private Document getDocument(ResultSet rs) throws SQLException {
        Document document = new Document();
        int id = rs.getInt("id");
        document.setId(id);
        String unique_number = rs.getString("unique_number");
        document.setUnique_number(unique_number);
        String description = rs.getString("description");
        document.setDescription(description);
        return document;
    }
}
