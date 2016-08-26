package org.arquillian.extension.governor.jira.xray.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import org.arquillian.extension.governor.jira.xray.api.validation.IJiraXrayUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.RestClientException;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.IssueField;
import com.google.common.base.Optional;
import com.google.common.collect.Iterables;

import es.cuatrogatos.jira.xray.rest.client.api.XrayJiraRestClient;
import es.cuatrogatos.jira.xray.rest.client.api.domain.Comment;
import es.cuatrogatos.jira.xray.rest.client.api.domain.TestRun;
import es.cuatrogatos.jira.xray.rest.client.api.domain.TestRun.Status;

public class JiraXrayUtilsImpl implements IJiraXrayUtils {

    private static final Logger LOG = Logger.getLogger(JiraXrayUtilsImpl.class.getName());

    private static final String FIELD_STARTED_ON = "customfield_10117";
    private static final String FIELD_FINISHED_ON = "customfield_10118";
    
    private static final String CUSTOM_DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

    public JiraXrayUtilsImpl() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void updateStatusTestRun(XrayJiraRestClient restClient, String keyTest, Status status) {

        try {
            // Get All testRun from keyTest
            Iterable<TestRun> result = getTestRunsByTestKey(restClient, keyTest);
            
            // TODO VER CÓMO VAMOS A LLEVAR ESTO CUANDO TENGAMOS VARIOS TESTRUN PARA 
            // Get only the first register
            TestRun firstTestRun = Iterables.get(result, 0);
            
            // Get testRun fully
            TestRun testRun = restClient.getTestRunClient().getTestRun(firstTestRun.getId()).claim();
            
            // Set new Status and Update status
            testRun.setStatus(status);
            testRun.setComment(new Comment("THIS IS A COMMENT FROM THE XRAYJIRA RESTCLIENT LIBRARY FOR JAVA","THIS IS A COMMENT FROM THE <blink>XRAYJIRA RESTCLIENT</blink> LIBRARY FOR JAVA"));
            
            // Call ApiRest for Update TestRun
            restClient.getTestRunClient().updateTestRun(testRun).claim();
            
        } catch (RestClientException e1) { // TODO: THE SERVER RETURN A 200 CODE AND A EMPTY RESPONSE, SO WE MUST EXTEND ABSTRACTRESTCLIENTO TO DEAL WITH IN PUT OPERATIONS
            if (!e1.getStatusCode().equals(Optional.absent()))
                throw e1;
        } catch (Exception e) {
            LOG.info("JiraXrayUtilsImpl | updateStatusTestRun: " + e.getMessage());
        }
    }

    

    @Override
    public Date getStartedOnTestExecution(XrayJiraRestClient restClient, String keyTestExec) {

        Date result = null;
        try {

            result = getDateCustomField(restClient, keyTestExec, FIELD_STARTED_ON);

        } catch (Exception e) {
            LOG.info("JiraXrayUtilsImpl | getStartedOnTestExecution: " + e.getMessage());
        }

        return result;
    }

    @Override
    public Date getFinishedOnTestExecution(XrayJiraRestClient restClient, String keyTestExec) {

        Date result = null;
        try {

            result = getDateCustomField(restClient, keyTestExec, FIELD_FINISHED_ON);

        } catch (Exception e) {
            LOG.info("JiraXrayUtilsImpl | getFinishedOnTestExecution: " + e.getMessage());
        }

        return result;
    }

    
    /**
     * Method auxiliary for getDateCustomField that you choose. 
     * @param restClient
     * @param keyTestExec
     * @param customFieldDate
     * @return
     * @throws ParseException
     */
    private Date getDateCustomField(XrayJiraRestClient restClient, String keyTestExec, String customFieldDate) throws ParseException {

        Date result = null;

        // Client for get custom field
        final IssueRestClient clientJira = restClient.getIssueClient();

        // Retrieve issue
        final Issue issue = clientJira.getIssue(keyTestExec).claim();

        IssueField fieldDate = issue.getField(customFieldDate);
        if (fieldDate != null && fieldDate.getValue() != null) {
            
            String dateStr = fieldDate.getValue().toString();

            DateTimeFormatter dtf = ISODateTimeFormat.dateTime();
            DateTime parsedDate = dtf.parseDateTime(dateStr);

            String dateWithCustomFormat = parsedDate.toString(DateTimeFormat.forPattern(CUSTOM_DATE_FORMAT));
            System.out.println(dateWithCustomFormat);
            
            result = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(dateWithCustomFormat);
        }
        return result;
    }

    @Override
    public Iterable<TestRun> getTestRunsByTestKey(XrayJiraRestClient restClient, String keyTest) {

        Iterable<TestRun> result = null;
        try {
            result = restClient.getTestRunClient().getTestRuns(keyTest).claim();
        } catch (Exception e) {
            LOG.info("JiraXrayUtilsImpl | getTestRunsByTestKey: " + e.getMessage());
        }

        return result;
    }

    @Override
    public Issue getTestExectionByKeyTestExec(XrayJiraRestClient restClient, String keyTestExec) {

        Issue result = null;
        try {
            
            // Client for get custom field
            IssueRestClient clientJira = restClient.getIssueClient();
            
            // Retrieve issue
            result = clientJira.getIssue(keyTestExec).claim();            
            
        } catch (Exception e) {
            LOG.info("JiraXrayUtilsImpl | getTestExectionByKeyTestExec: " + e.getMessage());
        }
        
        return result;
    }
}
