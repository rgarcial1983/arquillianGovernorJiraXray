package org.arquillian.extension.governor.jira.xray.api.validation;

import java.util.Date;

import com.atlassian.jira.rest.client.api.domain.Issue;

import es.cuatrogatos.jira.xray.rest.client.api.XrayJiraRestClient;
import es.cuatrogatos.jira.xray.rest.client.api.domain.TestRun;
import es.cuatrogatos.jira.xray.rest.client.api.domain.TestRun.Status;

public interface IJiraXrayUtils {
    
    /**
     * 
     * @param restClient
     * @param keyTest
     * @return
     */
    public Iterable<TestRun> getTestRunsByTestKey(XrayJiraRestClient restClient, String keyTest);
    
    /**
     * 
     * @param jiraIssue
     * @param restClient
     * @param status
     */
    public void  updateStatusTestRun (XrayJiraRestClient restClient, String keyTest, Status status);
    
    /**
     * 
     * @param restClient
     * @param testRun
     * @return
     */
    Issue getTestExectionByKeyTestExec (XrayJiraRestClient restClient, String keyTestExec);
    
    /**
     * 
     * @param restClient
     * @param keyTestExec
     * @return
     */
    Date getStartedOnTestExecution (XrayJiraRestClient restClient,  String keyTestExec);
    
    /**
     * 
     * @param restClient
     * @param keyTestExec
     * @return
     */
    Date getFinishedOnTestExecution (XrayJiraRestClient restClient,  String keyTestExec);
    
    
}
