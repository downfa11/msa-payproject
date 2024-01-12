package org.example.membership;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.membership.adapter.in.web.RegisterMembershipRequest;
import org.example.membership.domain.Membership;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RegisterMembershipControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void testRegisterMembership() throws Exception{
        RegisterMembershipRequest request = new RegisterMembershipRequest("name","address","email",true);

        Membership membership = Membership.generateMember(
                new Membership.MembershipId("1"),
                new Membership.MembershipName("name"),
                new Membership.MembershipAddress("address"),
        new Membership.MembershipEmail("email"),
                new Membership.MembershipIsValid(true),
                new Membership.MembershipIsCorp(false)
        );

        mockMvc.perform(
            MockMvcRequestBuilders.post("/membership/register")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(request))
    ).andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(mapper.writeValueAsString(membership)));
    }
}
