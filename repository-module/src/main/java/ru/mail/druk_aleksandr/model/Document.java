package ru.mail.druk_aleksandr.model;

public class Document {
    private Integer id;
    private String unique_number;
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUnique_number() {
        return unique_number;
    }

    public void setUnique_number(String unique_number) {
        this.unique_number = unique_number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
