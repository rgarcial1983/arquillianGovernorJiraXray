package org.arquillian.extension.governor.jira.xray;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.arquillian.extension.governor.jira.xray.api.validation.IJiraXrayUtils;
import org.arquillian.extension.governor.jira.xray.impl.JiraXrayUtilsImpl;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.Before;
import org.junit.Test;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.IssueField;

import es.cuatrogatos.jira.xray.rest.client.api.XrayJiraRestClient;
import es.cuatrogatos.jira.xray.rest.client.api.domain.TestRun;
import es.cuatrogatos.jira.xray.rest.client.core.internal.async.AsyncXrayJiraRestClient;
import es.cuatrogatos.jira.xray.rest.client.core.internal.async.XrayRestAsyncRestClientFactory;

public class JiraXrayUtilsImplTest {

    private static final String uriLocation = System.getenv("JIRA_URI");
    private static final String username = System.getenv("JIRA_USER");
    private static final String password = System.getenv("JIRA_PASSWORD");
    private static final String TEST_EXEC_KEY = "PBT-27";
    private static final String TEST_KEY = "PBT-26";
    private static final Long TEST_ID = 16378L;
    private static final String FIELD_STARTED_ON = "customfield_10117";
    private static final String FIELD_FINISHED_ON = "customfield_10118";
    private static final String CUSTOM_DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

    private final XrayRestAsyncRestClientFactory factory = new XrayRestAsyncRestClientFactory();
    private XrayJiraRestClient restClient;
    private IJiraXrayUtils jutils;

    @Before
    public void setUp() throws Exception {
        
        restClient = (AsyncXrayJiraRestClient) factory.createWithBasicHttpAuthentication(new URI(uriLocation), username, password);
        jutils = new JiraXrayUtilsImpl();
    }

    @Test
    public void getTestExectionByKeyTestExec() throws Exception {
        
        Issue issue = restClient.getIssueClient().getIssue(TEST_EXEC_KEY).claim();
        IssueField fieldStartedOn = issue.getField(FIELD_STARTED_ON);

        assertEquals(issue.getKey(), TEST_EXEC_KEY);
        assertEquals(issue.getId(), TEST_ID);

        DateTimeFormatter dtf = ISODateTimeFormat.dateTime();
        DateTime dtFieldStartedOn = dtf.parseDateTime(fieldStartedOn.getValue().toString());

        assertEquals(new SimpleDateFormat(CUSTOM_DATE_FORMAT).parse(dtFieldStartedOn.toString(DateTimeFormat.forPattern(CUSTOM_DATE_FORMAT))),
                new SimpleDateFormat(CUSTOM_DATE_FORMAT).parse("29/08/2016 10:30:00"));

    }
    
    @Test
    public void getDateCustomField() throws Exception {        
        
        Date startedOn = jutils.getStartedOnTestExecution(restClient, TEST_EXEC_KEY);
        Date finishedOn = jutils.getFinishedOnTestExecution(restClient, TEST_EXEC_KEY);
        
        assertEquals(startedOn, new SimpleDateFormat(CUSTOM_DATE_FORMAT).parse("29/08/2016 10:30:00"));
        assertEquals(finishedOn, null);
    }
    
    @Test
    public void updateStatusTestRun() throws Exception {
        Map<String, List<TestRun>> mapTestRunValidationPass = new HashMap<String, List<TestRun>>();
        List<TestRun> listTestRun = new ArrayList<TestRun>();        
        
        TestRun testRun = restClient.getTestRunClient().getTestRun(TEST_EXEC_KEY, TEST_KEY).claim();
        testRun.setTestExecKey(TEST_EXEC_KEY);
        listTestRun.add(testRun);
        mapTestRunValidationPass.put(TEST_KEY, listTestRun);
        
        // UPDATE STATUS to EXECUTING
        jutils.updateStatusTestRun(restClient, TEST_KEY, TestRun.Status.EXECUTING, mapTestRunValidationPass);        
        // RESTORE STATUS
        jutils.updateStatusTestRun(restClient, TEST_KEY, testRun.getStatus(), mapTestRunValidationPass);
    }
}
