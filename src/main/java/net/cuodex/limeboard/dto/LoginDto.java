package net.cuodex.limeboard.dto;


import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class LoginDto {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
