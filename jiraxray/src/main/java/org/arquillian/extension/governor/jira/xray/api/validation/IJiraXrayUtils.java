package org.arquillian.extension.governor.jira.xray.api.validation;

import java.util.Date;
import java.util.List;
import java.util.Map;

import es.cuatrogatos.jira.xray.rest.client.api.XrayJiraRestClient;
import es.cuatrogatos.jira.xray.rest.client.api.domain.TestExecutionIssue;
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
     * @param mapTestRunValidationPass 
     */
    public void updateStatusTestRun(XrayJiraRestClient restClient, String keyTest, Status status, Map<String, List<TestRun>> mapTestRunValidationPass);

    /**
     * 
     * @param restClient
     * @param testRun
     * @return
     */
    TestExecutionIssue getTestExectionByKeyTestExec(XrayJiraRestClient restClient, String keyTestExec);

    /**
     * 
     * @param restClient
     * @param keyTestExec
     * @return
     */
    Date getStartedOnTestExecution(XrayJiraRestClient restClient, String keyTestExec);

    /**
     * 
     * @param restClient
     * @param keyTestExec
     * @return
     */
    Date getFinishedOnTestExecution(XrayJiraRestClient restClient, String keyTestExec);

}
