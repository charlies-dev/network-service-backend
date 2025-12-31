package com.infy.recruitment.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.infy.recruitment.dto.request.CompanyCreateRequestDTO;
import com.infy.recruitment.dto.response.CompanyResponseDTO;
import com.infy.recruitment.service.CompanyService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyResponseDTO> getById(
            @PathVariable Long companyId) {

        return ResponseEntity.ok(
                companyService.getCompanyByCompanyId(companyId)
        );
    }

    @GetMapping
    public ResponseEntity<List<CompanyResponseDTO>> getAll() {

        return ResponseEntity.ok(
                companyService.getAllCompanies()
        );
    }
    @PostMapping
    public ResponseEntity<Long> createCompany(
            @Valid @RequestBody CompanyCreateRequestDTO dto) {

        return ResponseEntity.ok(
                companyService.addNewCompany(dto)
        );
    }
}
