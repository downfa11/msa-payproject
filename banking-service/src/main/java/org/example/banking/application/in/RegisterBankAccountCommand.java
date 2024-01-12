package org.example.banking.application.in;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.common.SelfValidating;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)

public class RegisterBankAccountCommand extends SelfValidating<RegisterBankAccountCommand> {
// CRUD에서 c,u,d를 위한 기능을 위한 Command
    @NotNull
    private final String membershipId;
    @NotNull
    private final String bankName;
    @NotNull
    @NotBlank
    private final String bankAccountNumber;

    private final boolean isValid;

    public RegisterBankAccountCommand(String membershipId, String bankName, String bankAccountNumber, boolean isValid) {
        this.membershipId = membershipId;
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;
        this.isValid = isValid;

        this.validateSelf();
    }
}
