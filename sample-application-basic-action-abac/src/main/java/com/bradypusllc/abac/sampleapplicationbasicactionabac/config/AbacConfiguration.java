package com.bradypusllc.abac.sampleapplicationbasicactionabac.config;

import com.bradypusllc.abac.api.PolicyEnforcementPoint;
import com.bradypusllc.abac.core.BasicPolicyEnforcementPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AbacConfiguration {

    @Bean
    public PolicyEnforcementPoint policyEnforcementPoint() {
        return new BasicPolicyEnforcementPoint();
    }
}
