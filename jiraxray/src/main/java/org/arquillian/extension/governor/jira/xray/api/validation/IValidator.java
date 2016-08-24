package org.arquillian.extension.governor.jira.xray.api.validation;



public interface IValidator<T> {

    boolean validate();
    public IValidator setAnd(IValidator newAnd);
    public IValidator setOr(IValidator newOr);

}
