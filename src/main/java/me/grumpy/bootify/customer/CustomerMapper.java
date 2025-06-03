package me.grumpy.bootify.customer;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CustomerMapper {

    CustomerDTO toDto(Customer customer);

    @Mapping(target = "id", ignore = true)
    Customer updateCustomer(CustomerDTO customerDTO, @MappingTarget Customer customer);

}
