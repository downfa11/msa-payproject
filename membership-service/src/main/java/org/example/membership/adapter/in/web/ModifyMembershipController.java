package org.example.membership.adapter.in.web;

import lombok.RequiredArgsConstructor;
import org.example.common.WebAdapter;
import org.example.membership.application.port.in.ModifyMembershipCommand;
import org.example.membership.application.port.in.ModifyMembershipUseCase;
import org.example.membership.domain.Membership;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class ModifyMembershipController {

    private final ModifyMembershipUseCase modifyMembershipUseCase;

    @PostMapping(path="/membership/modify/")
    ResponseEntity<Membership> modifyMembershipByMemberId(@RequestBody ModifyMembershipRequest request){

        ModifyMembershipCommand command = ModifyMembershipCommand.builder()
                .membershipId(request.getMembershipId())
                .name(request.getName())
                .address(request.getAddress())
                .email(request.getEmail())
                .isValid(request.isValid())
                .isCorp(request.isCorp())
                .build();

        return ResponseEntity.ok(modifyMembershipUseCase.modifyMembership(command));
    }
}
