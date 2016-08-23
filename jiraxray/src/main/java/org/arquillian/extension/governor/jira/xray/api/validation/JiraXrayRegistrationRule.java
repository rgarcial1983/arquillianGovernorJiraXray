/**
 * 
 */
package org.arquillian.extension.governor.jira.xray.api.validation;

import org.arquillian.extension.governor.jira.xray.impl.JiraXrayGovernorClient;

/**
 * @author rgarcial
 *
 */
public interface JiraXrayRegistrationRule {

    boolean validate(String jiraIssue, JiraXrayGovernorClient jiraGovernorClient);
}
