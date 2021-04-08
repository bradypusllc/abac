package com.bradypusllc.abac.core.source.filename;

import com.bradypusllc.abac.api.PolicySetSourceException;
import org.junit.jupiter.api.Test;

public class FileNamePolicySetSourceTest {

    @Test
    public void test() throws PolicySetSourceException {
        FileNamePolicySetSource fileNamePolicySetSource = new FileNamePolicySetSource();
        fileNamePolicySetSource.setPolicySourceDirectory("admin-only-policies");
        fileNamePolicySetSource.getRootPolicySet();
    }
}
