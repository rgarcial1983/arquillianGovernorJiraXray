package org.arquillian.extension.governor.jira.xray.api.validation;

import org.arquillian.extension.governor.jira.xray.configuration.JiraPropertiesUtils;

import com.atlassian.jira.rest.client.api.domain.Issue;



public class TestExecStatusTodo extends AbstractValidateRule<Issue> {

    public TestExecStatusTodo(Issue o) {
        super(o);
    }
    
    @Override
    public boolean validate() {

        if (this.and != null && this.or != null) {
            throw new IllegalArgumentException("VALIDATION RULE MALFORMED");
        }
        Issue testExecRun = this.myObject;
        
        boolean myResult = testExecRun.getStatus().getName().equals(JiraPropertiesUtils.getInstance().getValorKey("jira.status.issue.todo"));
        return this.and != null ? myResult && this.and.validate() : (this.or != null ? myResult || this.or.validate() : myResult);
    }


}
