package sample;

import java.sql.Date;

public class takenJobTableObject {
    String testName,patientContactNumber,patientAddress,packageId;
    Date takenDate,submittedDate;
    int orderNumber;

     takenJobTableObject(String testName, String patientContactNumber, String patientAddress,String packageId,Date takenDate,int orderNumber) {
        this.testName = testName;
        this.patientContactNumber = patientContactNumber;
        this.patientAddress = patientAddress;
        this.packageId=packageId;
        this.takenDate=takenDate;
        this.orderNumber=orderNumber;
    }


    takenJobTableObject(String testName, String patientContactNumber, String patientAddress, String packageId, Date takenDate, Date submittedDate,int orderNumber) {
        this.testName = testName;
        this.patientContactNumber = patientContactNumber;
        this.patientAddress = patientAddress;
        this.packageId=packageId;
        this.takenDate=takenDate;
        this.submittedDate=submittedDate;
        this.orderNumber=orderNumber;
    }

    takenJobTableObject(String testName, String packageId, Date takenDate,int orderNumber) {
        this.testName = testName;
        this.packageId = packageId;
        this.takenDate = takenDate;
        this.orderNumber=orderNumber;
    }

    public int getOrderNumber() {
        return orderNumber;
    }


    public Date getTakenDate() {
        return takenDate;
    }

    public Date getSubmittedDate() {
        return submittedDate;
    }

    public String getTestName() {
        return testName;
    }

    public String getPatientContactNumber() {
        return patientContactNumber;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public String getPackageId() {
        return packageId;
    }
}
