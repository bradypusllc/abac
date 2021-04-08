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
public class JsonFileNamePolicy {

    private String description;
    private String targetEvaluationType;
    private String target;
    private List<String> ruleRefs;
    private String combiningMethodName;
}
