package org.example.membership.application.in;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.common.SelfValidating;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)

public class RegisterMembershipCommand extends SelfValidating<RegisterMembershipCommand> {
// CRUD에서 c,u,d를 위한 기능을 위한 Command
    @NotNull
    private final String name;
    @NotNull
    private final String email;
    @NotNull
    @NotBlank
    private final String address;

    @AssertTrue
    private final boolean isValid;


    private final boolean isCorp;

    public RegisterMembershipCommand(String name, String email, String address, boolean isValid, boolean isCorp) {
        this.name = name;
        this.email = email;
        this.address = address;
        this.isValid = isValid;
        this.isCorp = isCorp;

        this.validateSelf();
    }
}
