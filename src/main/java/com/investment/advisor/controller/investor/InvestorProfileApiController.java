package com.investment.advisor.controller.investor;

import com.investment.common.apiresponse.ApiResponse;
import com.investment.advisor.api.investor.InvestorProfileApi;
import com.investment.advisor.entity.InvestorProfile;
import com.investment.advisor.mapper.InvestorProfileMapper;
import com.investment.advisor.dto.InvestorProfileDTO;
import com.investment.advisor.service.interfaces.InvestorProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class InvestorProfileApiController implements InvestorProfileApi {

    private final InvestorProfileService service;

    private final InvestorProfileMapper investorProfileMapper;

    @Override
    public ResponseEntity<ApiResponse<InvestorProfileDTO>> createProfile(@RequestBody InvestorProfileDTO investorProfileDTO) {
        InvestorProfile profile = investorProfileMapper.convertDTOToEntity(investorProfileDTO);
        InvestorProfile savedProfile = service.saveProfile(profile);
        InvestorProfileDTO responseDto = investorProfileMapper.convertEntityToDTO(savedProfile);
        ApiResponse<InvestorProfileDTO> response = ApiResponse.<InvestorProfileDTO>builder()
                .status("success")
                .message("Investor profile created successfully")
                .data(responseDto)
                .build();
        return ResponseEntity.ok(response);
    }


    @Override
    public ResponseEntity<ApiResponse<List<InvestorProfileDTO>>> getAllProfiles() {
        List<InvestorProfileDTO> dtos = service.getAllProfiles().stream()
                .map(investorProfileMapper::convertEntityToDTO)
                .toList();
        ApiResponse<List<InvestorProfileDTO>> response = ApiResponse.<List<InvestorProfileDTO>>builder()
                .status("success")
                .message("All investor profiles retrieved successfully")
                .data(dtos)
                .build();
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ApiResponse<InvestorProfileDTO>> getProfileById(Long id) {
        return service.getProfileById(id)
                .map(profile -> {
                    InvestorProfileDTO dto = investorProfileMapper.convertEntityToDTO(profile);
                    ApiResponse<InvestorProfileDTO> response = ApiResponse.<InvestorProfileDTO>builder()
                            .status("success")
                            .message("Investor profile retrieved successfully")
                            .data(dto)
                            .build();
                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.status(404).body(
                        ApiResponse.<InvestorProfileDTO>builder()
                                .status("error")
                                .message("Investor profile not found")
                                .build()
                ));
    }

    @Override
    public ResponseEntity<ApiResponse<InvestorProfileDTO>> updateProfile(Long id, InvestorProfile profile) {
        InvestorProfile investorProfileUpdated = service.updateProfile(id, profile);
        InvestorProfileDTO responseDto = investorProfileMapper.convertEntityToDTO(investorProfileUpdated);
        ApiResponse<InvestorProfileDTO> response = ApiResponse.<InvestorProfileDTO>builder()
                .status("success")
                .message("Investor profile updated successfully")
                .data(responseDto)
                .build();

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ApiResponse<Void>> deleteProfile(Long id) {
        service.deleteProfile(id);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .status("success")
                .message("Investor profile deleted successfully")
                .build();
        return ResponseEntity.ok(response);
    }
}
