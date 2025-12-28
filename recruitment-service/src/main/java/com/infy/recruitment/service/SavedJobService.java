package com.infy.recruitment.service;

import java.util.List;

import com.infy.recruitment.dto.request.SavedJobRequestDTO;
import com.infy.recruitment.dto.response.SavedJobResponseDTO;

public interface SavedJobService {

    void saveJob(SavedJobRequestDTO requestDTO);

    void removeSavedJob(Long userId, Long jobId);

    List<SavedJobResponseDTO> getSavedJobByUserId(Long userId);
}
