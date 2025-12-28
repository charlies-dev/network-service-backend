package com.infy.recruitment.service;

import java.util.List;

import com.infy.recruitment.dto.request.CompanyCreateRequestDTO;
import com.infy.recruitment.dto.request.CompanyUpdateRequestDTO;
import com.infy.recruitment.dto.response.CompanyResponseDTO;

public interface CompanyService {

     Long addNewCompany(CompanyCreateRequestDTO requestDTO);

    void updateCompany(Long companyId, CompanyUpdateRequestDTO requestDTO);

    void removeCompany(Long companyId);

    CompanyResponseDTO getCompanyByCompanyId(Long companyId);

    List<CompanyResponseDTO> getAllCompanies();
}
