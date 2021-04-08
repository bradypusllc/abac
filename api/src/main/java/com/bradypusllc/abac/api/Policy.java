package com.bradypusllc.abac.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Policy {

    private String description;
    private String targetEvaluationType;
    private String target;
    private List<Rule> rules;
    private CombiningMethod combiningMethod;
}
