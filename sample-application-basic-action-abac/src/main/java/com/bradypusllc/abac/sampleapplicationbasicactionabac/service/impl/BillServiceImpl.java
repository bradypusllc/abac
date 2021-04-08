package com.bradypusllc.abac.sampleapplicationbasicactionabac.service.impl;

import com.bradypusllc.abac.api.PolicyEnforcementPoint;
import com.bradypusllc.abac.sampleapplicationbasicactionabac.model.Bill;
import com.bradypusllc.abac.sampleapplicationbasicactionabac.service.BillService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillServiceImpl implements BillService {

    private final PolicyEnforcementPoint pep;

    public BillServiceImpl(PolicyEnforcementPoint pep) {
        this.pep = pep;
    }

    @Override
    public List<Bill> getBills() {
        pep.canAccess(null, null, "LIST_BILLS");

        return null;
    }
}
