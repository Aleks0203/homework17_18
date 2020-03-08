package ru.mail.druk_aleksandr;

import ru.mail.druk_aleksandr.model.Document;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface DocumentRepository {
    void add(Connection connection, Document document) throws SQLException;

    List<Document> findAll(Connection connection) throws SQLException;

    List<Document> findById(Connection connection, int id) throws SQLException;

    void delete(Connection connection, int id) throws SQLException;
}
