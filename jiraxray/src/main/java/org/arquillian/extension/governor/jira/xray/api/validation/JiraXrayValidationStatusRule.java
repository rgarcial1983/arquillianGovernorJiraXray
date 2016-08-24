package org.arquillian.extension.governor.jira.xray.api.validation;

import java.util.concurrent.ExecutionException;

import org.arquillian.extension.governor.jira.xray.impl.JiraXrayGovernorClient;
import org.arquillian.extension.governor.jira.xray.impl.JiraXrayTestExecutionDecider;
import org.jboss.arquillian.core.api.annotation.ApplicationScoped;
import org.jboss.arquillian.core.api.annotation.Inject;

import com.atlassian.util.concurrent.Promise;

import es.cuatrogatos.jira.xray.rest.client.api.XrayJiraRestClient;
import es.cuatrogatos.jira.xray.rest.client.api.domain.TestRun;

public class JiraXrayValidationStatusRule implements JiraXrayRegistrationRule {

    
    // TODO 
    @Inject
    @ApplicationScoped
    private JiraXrayGovernorClient jiraGovernorClientInject;
    
    // TODO 
    @Inject
    @ApplicationScoped
    private JiraXrayTestExecutionDecider jiraXrayTestExecutionDecider;
    

    // Default Constructor
    public JiraXrayValidationStatusRule() {
        super();
    }

    @Override
    public boolean validate(String testKey, JiraXrayGovernorClient jiraGovernorClient) {
        
        // Rest Client
        XrayJiraRestClient restClient = jiraGovernorClient.getRestClient();
        
        try {
            // Get TestsRun of Test
            Promise<Iterable<TestRun>> ListTestRuns = restClient.getTestRunClient().getTestRuns(testKey);
            Iterable<TestRun> future = ListTestRuns.get();
            for (TestRun testRun : future) {
                // Run each TestRun for get key                
                testRun.getStatus();
            }
        
        
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
        return false;
    }   
}
