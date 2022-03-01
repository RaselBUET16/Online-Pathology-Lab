package sample;

import java.sql.Date;

public class packageTableViewObject {
    String testName;
    Date orderedDate;
    String sampleType;

     packageTableViewObject(String testName, Date orderedDate,String sampleType) {
        this.testName = testName;
        this.orderedDate = orderedDate;
        this.sampleType=sampleType;
    }

    public String getSampleType() {
        return sampleType;
    }

    public String getTestName() {
        return testName;
    }


    public Date getOrderedDate() {
        return orderedDate;
    }
}
