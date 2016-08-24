package org.arquillian.extension.governor.jira.xray.api.validation;

import es.cuatrogatos.jira.xray.rest.client.api.domain.Example.Status;
import es.cuatrogatos.jira.xray.rest.client.api.domain.TestRun;

public class TestRunStatusTodo extends AbstractValidateRule<TestRun> {

    public TestRunStatusTodo(TestRun o) {
        super(o);
    }

//    @Override
//    public boolean validate(Object obj) {
//        boolean result = false;
//        TestRun field = (TestRun) obj;
//        
//        if(field != null && field.getStatus().equals(TestRun.Status.TODO)) {
//            result = true;
//        }
//        return result;
//    }

    @Override
    public boolean validate() {
        if(this.and!=null && this.or!=null){
            throw new IllegalArgumentException("VALIDATION RULE MALFORMED");
        }
        TestRun testRun=this.myObject;
        boolean myResult=testRun.getStatus().equals(Status.TODO);
        return this.and!=null?myResult&&this.and.validate():(this.or!=null?myResult||this.or.validate():myResult);
        }
}
