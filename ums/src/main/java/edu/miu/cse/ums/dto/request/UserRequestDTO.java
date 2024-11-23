package edu.miu.cse.ums.dto.request;

import jakarta.validation.constraints.NotNull;

public record UserRequestDTO(String firstName, String lastName, @NotNull String username) {

}
