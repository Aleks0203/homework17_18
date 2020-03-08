package ru.mail.druk_aleksandr.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class DocumentDTO {
    private Integer id;
    private String unique_number;
    @NotNull(message = "is required")
    @Size(min = 5, max = 100, message = "is required")
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
