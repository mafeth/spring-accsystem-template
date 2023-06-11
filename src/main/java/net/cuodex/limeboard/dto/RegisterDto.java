package net.cuodex.limeboard.dto;


import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RegisterDto {
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String email;
}
