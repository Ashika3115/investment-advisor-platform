package com.investment.advisor.api.admin;


import com.investment.common.apiresponse.ApiResponse;
import com.investment.common.enums.InvestorSubscriptionType;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;


@Tag(name = "Admin Profile API", description = "Operations for approval/rejection investor profiles")
@RequestMapping("/api/admin")
public interface AdminProfileApi {


     ResponseEntity<ApiResponse<String>> approveInvestor(Long id, InvestorSubscriptionType subscription);
     ResponseEntity<ApiResponse<String>> rejectInvestor(Long id);

}
