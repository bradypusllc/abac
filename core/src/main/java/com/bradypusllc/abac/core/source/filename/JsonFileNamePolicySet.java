package com.bradypusllc.abac.core.source.filename;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class JsonFileNamePolicySet {

    private String description;
    private String targetEvaluationType;
    private String target;
    private List<String> policySetRefs;
    private List<String> policyRefs;
    private String combiningMethodName;
}
