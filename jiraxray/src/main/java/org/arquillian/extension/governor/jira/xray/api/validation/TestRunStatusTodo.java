package org.arquillian.extension.governor.jira.xray.api.validation;


import es.cuatrogatos.jira.xray.rest.client.api.domain.TestRun;
import es.cuatrogatos.jira.xray.rest.client.api.domain.TestRun.Status;

public class TestRunStatusTodo extends AbstractValidateRule<TestRun> {

    public TestRunStatusTodo(TestRun o) {
        super(o);
    }


    @Override
    public boolean validate() {

        if (this.and != null && this.or != null) {
            throw new IllegalArgumentException("VALIDATION RULE MALFORMED");
        }
        TestRun testRun = this.myObject;
        
        boolean myResult = testRun.getStatus().equals(Status.TODO);
        return this.and != null ? myResult && this.and.validate() : (this.or != null ? myResult || this.or.validate() : myResult);
    }
}
