package sample;

import java.sql.Date;

public class orderListObjectTableView {
    public String testName;
    public Date submittedDate;
    public boolean collAvailability;
    public boolean sampleSubmitted;
    public boolean reportComplete;

     orderListObjectTableView(String testName, Date submittedDate, boolean collAvailability,
                                    boolean sampleSumitted, boolean reportComplete) {
        this.testName = testName;
        this.submittedDate = submittedDate;
        this.collAvailability = collAvailability;
        this.sampleSubmitted = sampleSumitted;
        this.reportComplete = reportComplete;
    }

    public String getTestName() {
        return testName;
    }


    public Date getSubmittedDate() {
        return submittedDate;
    }

    public boolean isCollAvailability() {
        return collAvailability;
    }

    public boolean isSampleSubmitted() {
        return sampleSubmitted;
    }

    public boolean isReportComplete() {
        return reportComplete;
    }

}
