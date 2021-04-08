package com.bradypusllc.abac.core.source.filename;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class JsonFileNameRule {

    private String description;
    private String conditionEvaluationType;
    private String condition;
    private String effectTypeName;
}
