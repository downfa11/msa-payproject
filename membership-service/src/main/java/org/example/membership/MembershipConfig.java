package org.example.membership;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.example.common") //common에 있는 걸 빈으로 등록하고 시작
public class MembershipConfig {
}
