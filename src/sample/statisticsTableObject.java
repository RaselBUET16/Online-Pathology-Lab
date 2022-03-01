package sample;

import java.sql.Date;

public class statisticsTableObject {
    Date from,to;int collectorName;

    statisticsTableObject(Date from, Date to, int collectorName) {
        this.from = from;
        this.to = to;
        this.collectorName = collectorName;

    }

    public Date getFrom() {
        return from;
    }

    public Date getTo() {
        return to;
    }

    public int getCollectorName() {
        return collectorName;
    }
}
