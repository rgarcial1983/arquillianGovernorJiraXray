package org.arquillian.extension.governor.jira.xray.api.validation;

import org.arquillian.extension.governor.jira.xray.configuration.JiraPropertiesUtils;

import es.cuatrogatos.jira.xray.rest.client.api.domain.TestExecutionIssue;



public class TestExecStatusTodo extends AbstractValidateRule<TestExecutionIssue> {

    public TestExecStatusTodo(TestExecutionIssue o) {
        super(o);
    }
    
    @Override
    public boolean validate() {

        if (this.and != null && this.or != null) {
            throw new IllegalArgumentException("VALIDATION RULE MALFORMED");
        }
        TestExecutionIssue testExecRun = this.myObject;
        
        boolean myResult = testExecRun.getStatus().getName().equals(JiraPropertiesUtils.getInstance().getValorKey("jira.status.issue.todo"));
        return this.and != null ? myResult && this.and.validate() : (this.or != null ? myResult || this.or.validate() : myResult);
    }


}
