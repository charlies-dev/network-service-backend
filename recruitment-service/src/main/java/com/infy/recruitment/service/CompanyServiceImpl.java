package com.infy.recruitment.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.infy.recruitment.dto.request.CompanyCreateRequestDTO;
import com.infy.recruitment.dto.request.CompanyUpdateRequestDTO;
import com.infy.recruitment.dto.response.CompanyResponseDTO;
import com.infy.recruitment.entity.Company;
import com.infy.recruitment.exception.InfyLinkedInException;
import com.infy.recruitment.repository.CompanyRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;

    /* ================= ADD COMPANY ================= */

    @Override
    @Transactional
    public Long addNewCompany(CompanyCreateRequestDTO dto) {

        if (companyRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new InfyLinkedInException("Company already exists");
        }

        Company company = modelMapper.map(dto, Company.class);

        return companyRepository.save(company).getId();
    }

    /* ================= UPDATE COMPANY ================= */

    @Override
    @Transactional
    public void updateCompany(
            Long companyId,
            CompanyUpdateRequestDTO dto) {

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() ->
                        new InfyLinkedInException("Company not found"));

        if (dto.getName() != null)
            company.setName(dto.getName());

        if (dto.getLocation() != null)
            company.setLocation(dto.getLocation());

        if (dto.getIndustry() != null)
            company.setIndustry(dto.getIndustry());
    }

    /* ================= REMOVE COMPANY ================= */

    @Override
    @Transactional
    public void removeCompany(Long companyId) {

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() ->
                        new InfyLinkedInException("Company not found"));

        companyRepository.delete(company);
    }

    /* ================= GET BY ID ================= */

    @Override
    public CompanyResponseDTO getCompanyByCompanyId(Long companyId) {

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() ->
                        new InfyLinkedInException("Company not found"));

        return modelMapper.map(company, CompanyResponseDTO.class);
    }

    /* ================= GET ALL ================= */

    @Override
    public List<CompanyResponseDTO> getAllCompanies() {

        List<Company> companies = companyRepository.findAll();

        if (companies.isEmpty()) {
            throw new InfyLinkedInException("No companies found");
        }

        return companies.stream()
                .map(c -> modelMapper.map(c, CompanyResponseDTO.class))
                .toList();
    }
}
