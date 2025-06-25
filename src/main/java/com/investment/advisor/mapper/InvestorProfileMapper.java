package com.investment.advisor.mapper;

import com.investment.advisor.entity.InvestorProfile;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface InvestorProfileMapper {

    /**
     * ✅ Partial update from source → target entity.
     * ✅ Ignores nulls (for PATCH-like behavior)
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateInvestorProfile(@MappingTarget InvestorProfile target, InvestorProfile source);
}
