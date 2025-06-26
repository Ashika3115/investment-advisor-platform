package com.investment.advisor.mapper;
import com.investment.advisor.entity.InvestorProfile;
import com.investment.advisor.request.InvestorProfileDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


@Mapper(componentModel = "spring")
public interface InvestorProfileMapper {

    // Automatically maps matching fields from DTO to entity using builder
    InvestorProfile convertDTOToEntity(InvestorProfileDTO dto);

    // Automatically maps entity to DTO
    InvestorProfileDTO convertEntityToDTO(InvestorProfile entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateInvestorProfile(@MappingTarget InvestorProfile target, InvestorProfile source);
}


