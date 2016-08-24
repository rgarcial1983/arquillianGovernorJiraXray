package org.arquillian.extension.governor.jira.xray.api.validation;

public abstract class AbstractValidateRule<T> implements IValidator {
    protected T myObject;
    protected IValidator<T> and;
    protected IValidator<T> or;
    
    public AbstractValidateRule(T o) {
        this.myObject=o;        
    }
    public abstract boolean validate();
    
    public IValidator setAnd(IValidator i){
        this.and=i;
        return this;
    }
    
    public IValidator setOr(IValidator i){
        this.or=i;
        return this;
    }
}
