package org.arquillian.extension.governor.jira.xray.api.validation;

import es.cuatrogatos.jira.xray.rest.client.api.domain.TestExecution;

public class TestExecStartDateOver extends AbstractValidateRule<TestExecution> {

    public TestExecStartDateOver(TestExecution o) {
        super(o);
    }
    
    @Override
    public boolean validate() {

        // TODO Auto-generated method stub
        return false;
    }


}