package ru.mail.druk_aleksandr.webmodule.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.mail.druk_aleksandr.DocumentService;
import ru.mail.druk_aleksandr.model.DocumentDTO;

import javax.validation.Valid;
import java.util.List;

@Controller
public class DocumentController {
    private DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/documents")
    public String getDocuments(Model model) {
        List<DocumentDTO> documentDTOList = documentService.findAll();
        model.addAttribute("documents", documentDTOList);
        return "documents";
    }

    @GetMapping("/document/add")
    public String addDocumentPage(Model model) {
        model.addAttribute("document", new DocumentDTO());
        return "document_add";
    }

    @PostMapping("/document/add")
    public String addDocument(Model model, @Valid @ModelAttribute(name = "document") DocumentDTO document,
                              BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("document", document);
            return "document_add";
        }
        documentService.addDocument(document);
        return "redirect:/documents";
    }

    @GetMapping("/document/find")
    public String findDocumentPage(Model model) {
        model.addAttribute("document", new DocumentDTO());
        return "document_display";
    }

    @PostMapping("/document/find")
    public String findDocument(Model model, @ModelAttribute(name = "document") DocumentDTO document) {
        List<DocumentDTO> documentDTOList = documentService.findAll();
        for (DocumentDTO docummentDTO : documentDTOList) {
            if (docummentDTO.getId() == document.getId()) {
                List<DocumentDTO> documentDTOS = documentService.findDocumentById(document.getId());
                model.addAttribute("documents", documentDTOS);
                return "document_display";
            }
        }
        model.addAttribute("error", "is required");
        return "document_display";
    }

    @GetMapping("/document/delete")
    public String deleteDocumentPage(Model model) {
        model.addAttribute("document", new DocumentDTO());
        return "document_delete";
    }

    @PostMapping("/document/delete")
    public String deleteDocument(Model model, @ModelAttribute(name = "document") DocumentDTO document) {
        List<DocumentDTO> documentDTOList = documentService.findAll();
        for (DocumentDTO docummentDTO : documentDTOList) {
            if (docummentDTO.getId() == document.getId()) {
                documentService.deleteDocumentById(document.getId());
                return "redirect:/documents";
            }
        }
        model.addAttribute("error", "is required");
        return "document_delete";
    }
}
