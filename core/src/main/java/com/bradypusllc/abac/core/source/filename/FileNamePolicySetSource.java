package com.bradypusllc.abac.core.source.filename;

import com.bradypusllc.abac.api.CombiningMethod;
import com.bradypusllc.abac.api.Policy;
import com.bradypusllc.abac.api.PolicySet;
import com.bradypusllc.abac.api.Rule;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import com.bradypusllc.abac.api.EffectType;
import com.bradypusllc.abac.api.PolicySetSource;
import com.bradypusllc.abac.api.PolicySetSourceException;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An implementation of the PolicySetSource that will read a (flat) directory of JSON files.  File names follow a
 * naming convention of &#123;name&#125;-policy-set.json, &#123;name&#125;-policy.json, &#38; &#123;name&#125;-rule.json.
 * The names will be used as refs to build the tree of PolicySets and Policies.
 */
@Slf4j
public class FileNamePolicySetSource implements PolicySetSource {

    private static final String ROOT_POLICY_SET_FILE = "root-policy-set.json";
    private static final String DEFAULT_POLICY_SOURCE_DIRECTORY = "policies";

    private String policySourceDirectory = DEFAULT_POLICY_SOURCE_DIRECTORY;

    private List<File> policySetFiles;
    private List<File> policyFiles;
    private List<File> ruleFiles;

    private final Map<String, File> policySetFileMap = new HashMap<>();
    private final Map<String, File> policyFileMap = new HashMap<>();
    private final Map<String, File> ruleFileMap = new HashMap<>();

    private final ObjectMapper objectMapper;

    private File rootPolicySetFile;
    private JsonFileNamePolicySet rootJsonFilePolicySet;
    private PolicySet rootPolicySet;

    public FileNamePolicySetSource() {
        this.objectMapper = new ObjectMapper();
    }

    public void setPolicySourceDirectory(String policySourceDirectory) {
        this.policySourceDirectory = policySourceDirectory;
    }

    @Override
    public PolicySet getRootPolicySet() throws PolicySetSourceException {
        try {
            loadSourceFileLists();
            loadRootPolicySetFile();
            buildFileMaps();
            loadPolicySetsFromRoot();
        } catch (IOException e) {
            if(e instanceof PolicySetSourceException) {
                throw (PolicySetSourceException)e;
            } else {
                throw new PolicySetSourceException("Unhandled IOException", e);
            }
        }
        return null;
    }

    private void loadSourceFileLists() throws IOException {
        try {
            policySetFiles = getAllFilesByPattern(policySourceDirectory, "-policy-set.json");
            policyFiles = getAllFilesByPattern(policySourceDirectory, "-policy.json");
            ruleFiles = getAllFilesByPattern(policySourceDirectory, "-rule.json");
        } catch (FileNotFoundException e) {
            LOG.error(e.getMessage());
        }
    }

    private void loadRootPolicySetFile() throws PolicySetSourceException {
        // Find a file in the PolicySets called 'root-policy-set.json'.   This is non-negotiable.
        for(File file : policySetFiles) {
            if(file.getAbsolutePath().endsWith(ROOT_POLICY_SET_FILE)) {
                rootPolicySetFile = file;
                break;
            }
        }

        if(rootPolicySetFile == null) {
            throw new PolicySetSourceException("Could not find Root Policy Set");
        }

        LOG.info("rootPolicySetFileName: {}", rootPolicySetFile);

        try {
            rootJsonFilePolicySet = getJsonFileNamePolicySet(rootPolicySetFile);
        } catch (IOException e) {
            throw new PolicySetSourceException("Failure to read Root Policy Set", e);
        }
    }

    private void buildFileMaps() {
        // When we build the maps, we want only the part of the file name that will be the ref.  To do this, we need
        // to figure out the path prefix.  We already know the suffixes.  We can use the root file to do this.
        String prefix = rootPolicySetFile.getParent() + "/";
        LOG.info("prefix: {}", prefix);
        int prefixSize = prefix.length();
        int suffixSize = "-policy-set.json".length();

        for(File file : policySetFiles) {
            String absolutePath = file.getAbsolutePath();
            String ref = absolutePath.substring(prefixSize, absolutePath.length() - suffixSize);
            LOG.info("ref: {}", ref);
            policySetFileMap.put(ref, file);
        }

        suffixSize = "-policy.json".length();

        for(File file : policyFiles) {
            String absolutePath = file.getAbsolutePath();
            String ref = absolutePath.substring(prefixSize, absolutePath.length() - suffixSize);
            LOG.info("ref: {}", ref);
            policyFileMap.put(ref, file);
        }

        suffixSize = "-rule.json".length();

        for(File file : ruleFiles) {
            String absolutePath = file.getAbsolutePath();
            String ref = absolutePath.substring(prefixSize, absolutePath.length() - suffixSize);
            LOG.info("ref: {}", ref);
            ruleFileMap.put(ref, file);
        }
    }

    private void loadPolicySetsFromRoot() throws IOException {
        LOG.info("rootPolicySet: {}", rootJsonFilePolicySet);
        rootPolicySet = recursePolicySetFromFile(rootJsonFilePolicySet);
    }

    private PolicySet recursePolicySetFromFile(JsonFileNamePolicySet jsonFileNamePolicySet) throws IOException {
        PolicySet policySet = new PolicySet();
        policySet.setDescription(jsonFileNamePolicySet.getDescription());
        policySet.setTargetEvaluationType(jsonFileNamePolicySet.getTargetEvaluationType());
        policySet.setTarget(jsonFileNamePolicySet.getTarget());
        policySet.setCombiningMethod(CombiningMethod.valueOf(jsonFileNamePolicySet.getCombiningMethodName())); // TODO: IAE?

        List<PolicySet> policySets = new ArrayList<>();

        for(String policySetRef : jsonFileNamePolicySet.getPolicySetRefs()) {
            LOG.info("policySetRef: {}", policySetRef);
            JsonFileNamePolicySet jsonFileNamePolicySet1 = getJsonFileNamePolicySetByRef(policySetRef);
            PolicySet policySet1 = recursePolicySetFromFile(jsonFileNamePolicySet1);
            policySets.add(policySet1);
        }

        policySet.setPolicySets(policySets);

        List<Policy> policies = new ArrayList<>();

        for(String policyRef : jsonFileNamePolicySet.getPolicyRefs()) {
            LOG.info("policyRef: {}", policyRef);
            JsonFileNamePolicy jsonFileNamePolicy1 = getJsonFileNamePolicyByRef(policyRef);
            Policy policy1 = recursePolicyFromFile(jsonFileNamePolicy1);
            policies.add(policy1);
        }

        policySet.setPolicies(policies);

        return policySet;
    }

    private Policy recursePolicyFromFile(JsonFileNamePolicy jsonFileNamePolicy) throws IOException {
        Policy policy = new Policy();
        policy.setDescription(jsonFileNamePolicy.getDescription());
        policy.setTargetEvaluationType(jsonFileNamePolicy.getTargetEvaluationType());
        policy.setTarget(jsonFileNamePolicy.getTarget());
        policy.setCombiningMethod(CombiningMethod.valueOf(jsonFileNamePolicy.getCombiningMethodName())); // TODO: IAE?

        List<Rule> rules = new ArrayList<>();

        for(String ruleRef : jsonFileNamePolicy.getRuleRefs()) {
            LOG.info("ruleRef: {}", ruleRef);
            JsonFileNameRule jsonFileNameRule = getJsonFileNameRuleByRef(ruleRef);
            Rule rule = recurseRuleFromFile(ruleRef, jsonFileNameRule);
            rules.add(rule);
        }

        policy.setRules(rules);

        return policy;
    }

    private Rule recurseRuleFromFile(String name, JsonFileNameRule jsonFileNameRule) {
        Rule rule = new Rule();
        rule.setName(name);
        rule.setDescription(jsonFileNameRule.getDescription());
        rule.setConditionEvaluationType(jsonFileNameRule.getConditionEvaluationType());
        rule.setCondition(jsonFileNameRule.getCondition());
        rule.setEffect(EffectType.valueOf(jsonFileNameRule.getEffectTypeName())); // TODO: IAE?

        return rule;
    }

    private JsonFileNamePolicySet getJsonFileNamePolicySetByRef(String ref) throws IOException {
        File file = policySetFileMap.get(ref);

        if(file == null) {
            throw new PolicySetSourceException("Referenced PolicySet file '" + ref + "' does not exist");
        }

        return getJsonFileNamePolicySet(file);
    }

    private JsonFileNamePolicySet getJsonFileNamePolicySet(File file) throws IOException {
        return objectMapper.readValue(file, JsonFileNamePolicySet.class);
    }

    private JsonFileNamePolicy getJsonFileNamePolicyByRef(String ref) throws IOException {
        File file = policyFileMap.get(ref);

        if(file == null) {
            throw new PolicySetSourceException("Referenced Policy file '" + ref + "' does not exist");
        }

        return getJsonFileNamePolicy(file);
    }

    private JsonFileNamePolicy getJsonFileNamePolicy(File file) throws IOException {
        return objectMapper.readValue(file, JsonFileNamePolicy.class);
    }

    private JsonFileNameRule getJsonFileNameRuleByRef(String ref) throws IOException {
        File file = ruleFileMap.get(ref);

        if(file == null) {
            throw new PolicySetSourceException("Referenced Rule file '" + ref + "' does not exist");
        }

        return getJsonFileNameRule(file);
    }

    private JsonFileNameRule getJsonFileNameRule(File file) throws IOException {
        return objectMapper.readValue(file, JsonFileNameRule.class);
    }

    private List<File> getAllFilesByPattern(String policyDirectory, String pattern) throws IOException {
        LOG.trace("policyDirectory: {}", policyDirectory);
        LOG.trace("pattern: {}", pattern);

        File dir = ResourceUtils.getFile("classpath:" + policyDirectory);

        LOG.trace("dir: {}", dir);

        File [] matchingFiles = dir.listFiles((dir1, name) -> {
            LOG.trace("dir: {}", dir1);
            LOG.trace("name: {}", name);

            return name.endsWith(pattern);
        });

        if(matchingFiles == null) {
            return Collections.emptyList();
        }

        if(LOG.isTraceEnabled()) {
            for (File matchingFile : matchingFiles) {
                LOG.trace("matchingFile: {}", matchingFile.getAbsolutePath());
                LOG.trace("matchingFile: {}", matchingFile.getParent());
                LOG.trace("matchingFile: {}", matchingFile.getName());
            }
        }

        return Arrays.asList(matchingFiles);
    }
}
