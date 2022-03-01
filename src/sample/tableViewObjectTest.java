package sample;

public class tableViewObjectTest {
    public int testId,amount;
    public String testName,description;
    public boolean collectorAvailability;

     tableViewObjectTest(int testId, String testName, int amount, boolean collectorAvailability,String description ) {
        this.testId = testId;
        this.testName = testName;
        this.amount = amount;
        this.description = description;
        this.collectorAvailability = collectorAvailability;
    }

    public int getTestId() {
        return testId;
    }

    public String getTestName() {
        return testName;
    }

    public int getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCollectorAvailability() {
        return collectorAvailability;
    }
}
