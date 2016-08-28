package org.arquillian.extension.governor.jira.xray;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

public class RafaTest {
	private static final String CUSTOM_DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

	public static void main(String[] args) throws ParseException {
		String result = "NO EXECUTE";
		// create 3 dates
		String date1 = "20/08/2016 17:00:00";
		String date2 = "30/08/2016 20:00:00";
		String now = new DateTime().toString(DateTimeFormat.forPattern(CUSTOM_DATE_FORMAT));

		Date dateStartedOn = DateUtils.parseDate(date1, CUSTOM_DATE_FORMAT);
		Date dateFinishedOn = DateUtils.parseDate(date2, CUSTOM_DATE_FORMAT);
		Date dateExecution = DateUtils.parseDate(now, CUSTOM_DATE_FORMAT);

		if (dateStartedOn.equals(dateExecution) || (dateStartedOn.before(dateExecution)
				&& (dateFinishedOn == null || (dateFinishedOn.after(dateExecution))))) {
			result = "EXECUTE";
		}

		System.out.println(result);

	}

}
