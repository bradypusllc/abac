package com.bradypusllc.abac.sampleapplicationbasicactionabac.rest;

import com.bradypusllc.abac.sampleapplicationbasicactionabac.model.Bill;
import com.bradypusllc.abac.sampleapplicationbasicactionabac.service.BillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    private static final Logger LOG = LoggerFactory.getLogger(BillController.class);

    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public List<Bill> listBills() {
        LOG.info("listBills");
        List<Bill> result = billService.getBills();
        LOG.info("result: {}", result);
        return result;
    }
}
