package org.arquillian.extension.governor.jira.xray.api.validation;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import es.cuatrogatos.jira.xray.rest.client.api.domain.TestExecutionIssue;

public class TestExecStartDateOver extends AbstractValidateRule<TestExecutionIssue> {
	
	private static final String CUSTOM_DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
	
    
	public TestExecStartDateOver(TestExecutionIssue o) {
        super(o);
    }
    
    @Override
    public boolean validate() {
    	if (this.and != null && this.or != null) {
            throw new IllegalArgumentException("VALIDATION RULE MALFORMED");
        }
    	TestExecutionIssue testExecRun = this.myObject;
        
    	boolean myResult = false;
    	
    	String now = new DateTime().toString(DateTimeFormat.forPattern(CUSTOM_DATE_FORMAT));
    	
    	Date dateStartedOn = testExecRun.getStartedOn();
		Date dateFinishedOn = testExecRun.getFinishedOn();
		Date dateExecution;
		
		try {
			dateExecution = DateUtils.parseDate(now, CUSTOM_DATE_FORMAT);
			// If Execution Date between dateStartedOn and dateFinishedOn
			if (dateStartedOn.equals(dateExecution) || (dateStartedOn.before(dateExecution)
					&& (dateFinishedOn == null || (dateFinishedOn.after(dateExecution))))) {
				myResult = true;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return this.and != null ? myResult && this.and.validate() : (this.or != null ? myResult || this.or.validate() : myResult);
    }


}