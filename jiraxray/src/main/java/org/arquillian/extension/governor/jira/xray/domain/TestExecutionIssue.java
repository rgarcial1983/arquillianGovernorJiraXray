package org.arquillian.extension.governor.jira.xray.domain;

import java.net.URI;
import java.util.Date;

import com.atlassian.jira.rest.client.api.domain.BasicIssue;
import com.atlassian.jira.rest.client.api.domain.Status;

public class TestExecutionIssue extends BasicIssue {

    private Date startedOn;
    private Date finishedOn;
    private Status status;    

    
    
    public TestExecutionIssue(URI self, String key, Long id, Status status, Date startedOn, Date finishedOn) {
        super(self, key, id);
        this.finishedOn = finishedOn;
        this.startedOn = startedOn;
        this.status = status;
    }
    
    

    public Date getStartedOn() {
        
        return startedOn;
    }
    
    
    public void setStartedOn(Date startedOn) {
        
        this.startedOn = startedOn;
    }
    
    
    public Date getFinishedOn() {
        
        return finishedOn;
    }
    
    
    public void setFinishedOn(Date finishedOn) {
        
        this.finishedOn = finishedOn;
    }
    
    
    public Status getStatus() {
        
        return status;
    }
    
    
    public void setStatus(Status status) {
        
        this.status = status;
    }
    

}
