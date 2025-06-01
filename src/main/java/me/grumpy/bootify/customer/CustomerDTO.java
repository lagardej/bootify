package me.grumpy.bootify.customer;

import jakarta.validation.constraints.Size;


public record CustomerDTO(
        Long id,
        @Size(max = 255) String firstName,
        @Size(max = 255) String lastName,
        @Size(max = 255) String email,
        Integer age
) {
}
