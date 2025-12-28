package com.infy.recruitment.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.recruitment.dto.request.CompanyCreateRequestDTO;
import com.infy.recruitment.dto.request.CompanyUpdateRequestDTO;
import com.infy.recruitment.dto.response.CompanyResponseDTO;
import com.infy.recruitment.service.CompanyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    /* ================= ADD ================= */

    @PostMapping
    public ResponseEntity<Long> addCompany(
            @Valid @RequestBody CompanyCreateRequestDTO dto) {

        return ResponseEntity.ok(
                companyService.addNewCompany(dto)
        );
    }

    /* ================= UPDATE ================= */

    @PutMapping("/{companyId}")
    public ResponseEntity<Void> updateCompany(
            @PathVariable Long companyId,
            @RequestBody CompanyUpdateRequestDTO dto) {

        companyService.updateCompany(companyId, dto);
        return ResponseEntity.noContent().build();
    }

    /* ================= DELETE ================= */

    @DeleteMapping("/{companyId}")
    public ResponseEntity<Void> deleteCompany(
            @PathVariable Long companyId) {

        companyService.removeCompany(companyId);
        return ResponseEntity.noContent().build();
    }

    /* ================= GET BY ID ================= */

    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyResponseDTO> getById(
            @PathVariable Long companyId) {

        return ResponseEntity.ok(
                companyService.getCompanyByCompanyId(companyId)
        );
    }

    /* ================= GET ALL ================= */

    @GetMapping
    public ResponseEntity<List<CompanyResponseDTO>> getAll() {

        return ResponseEntity.ok(
                companyService.getAllCompanies()
        );
    }
}
