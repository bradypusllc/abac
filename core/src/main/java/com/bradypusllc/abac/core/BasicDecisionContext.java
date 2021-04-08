package com.bradypusllc.abac.core;

import com.bradypusllc.abac.api.DecisionContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BasicDecisionContext implements DecisionContext {

    private Object subject;
    private Object resource;
    private Object environment;
}
