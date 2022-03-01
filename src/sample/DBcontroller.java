package sample;

import javafx.collections.ObservableList;

import javax.sound.midi.Soundbank;
import java.security.DrbgParameters;
import java.sql.*;
import java.util.LinkedList;
import java.util.Queue;

public class DBcontroller {

    public static int isSuccess;

    public  static void insertPatient(patient p){
        try {
            Connection conn=DriverManager.getConnection("jdbc:postgresql://localhost/PathLab","postgres","killer125");
            System.out.println("connection success");
            Statement stmt=conn.createStatement();
            String locIdSQL = "select id from location where area = '" +p.area + "' and thana = '" + p.thana + "' and district = '" + p.district + "'";

            ResultSet rs = stmt.executeQuery(locIdSQL);
            int locId;
            rs.next();
            locId =rs.getInt("id");
            System.out.println(locId);

            String SQL="insert into patient(name,username,password,age,gender,contact_number,address,location_id)"
                    + "values('"+p.name + "','" + p.username + "','" + p.password + "','" + p.age + "','" + p.sex + "','"
                    + p.contactNumber
                    + "','" +p.address + "','" + locId + "')";
            //System.out.println(SQL);
            System.out.println(stmt.executeUpdate(SQL));


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public  static void insertCollector(patient p){
        try {
            Connection conn=DriverManager.getConnection("jdbc:postgresql://localhost/PathLab","postgres","killer125");
            System.out.println("connection success");
            Statement stmt=conn.createStatement();
            String locIdSQL = "select id from location where area = '" +p.area
                    + "' and thana = '" + p.thana + "' and district = '" + p.district + "'";

            ResultSet rs = stmt.executeQuery(locIdSQL);
            int locId;
            rs.next();
            locId =rs.getInt("id");
            System.out.println(locId);

            String SQL="insert into collector(name,username,password,contact_number,location_id,availability)"
                    + "values('"+p.name + "','" + p.username + "','" + p.password + "','"
                    + p.contactNumber
                    + "','"+ locId + "','"
                    + "true" +"')";
            //System.out.println(SQL);
            System.out.println(stmt.executeUpdate(SQL));

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void insertTest(String name,String organ,String disease,
            String sample,String amount,String duration , String availability,String description){
        int organId;
        int diseaseId;
        int sampleId;
        try {
            Connection conn=DriverManager.getConnection("jdbc:postgresql://localhost/PathLab","postgres","killer125");
            System.out.println("connection success");
            Statement stmt=conn.createStatement();
            String organIdSQL = "select id from organ where name = '" +organ + "'";
            String diseaseIdSQL ="select id from disease where name = '" +disease + "'";
            String sampleIdSQL = "select id from samples where name = '" + sample + "'";
            ResultSet rs = stmt.executeQuery(organIdSQL);
            rs.next();
            organId =rs.getInt(1);

            rs = stmt.executeQuery(diseaseIdSQL);
            rs.next();
            diseaseId =rs.getInt(1);

            rs = stmt.executeQuery(sampleIdSQL);
            System.out.println(rs.next());
            sampleId =rs.getInt(1);

            String SQL="insert into test(name,organ_id,disease_id,sample_id,amount,duration" +
                    ",collector_availability,description)"
                    + "values('"+ name + "','" + organId + "','" + diseaseId + "','"
                    + sampleId
                    + "','"+ amount + "','" + duration + "','" + availability + "','"
                    + description +"')";
            //System.out.println(SQL);
            System.out.println(stmt.executeUpdate(SQL));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void insertOrgan(String organName) throws SQLException {
        Connection conn=DriverManager.getConnection("jdbc:postgresql://localhost/PathLab","postgres","killer125");
        Statement stmt=conn.createStatement();
        String SQL = "insert into organ(name) values('" + organName + "')";
        stmt.executeUpdate(SQL);

    }
    public static void insertSample(String sampleName) throws SQLException {
        Connection conn=DriverManager.getConnection("jdbc:postgresql://localhost/PathLab","postgres","killer125");
        Statement stmt=conn.createStatement();
        String SQL = "insert into samples(name) values('" + sampleName + "')";
        stmt.executeUpdate(SQL);

    }
    public static void insertDisease(String DiseaseName) throws SQLException {
        Connection conn=DriverManager.getConnection("jdbc:postgresql://localhost/PathLab","postgres","killer125");
        Statement stmt=conn.createStatement();
        String SQL = "insert into disease(name) values('" + DiseaseName + "')";
        stmt.executeUpdate(SQL);

    }
    public static void addTestIntoTempTable(String username,int testId,String test_name) throws SQLException {
        Connection conn=DriverManager.getConnection("jdbc:postgresql://localhost/PathLab","postgres","killer125");
        Statement stmt=conn.createStatement();
        String duplicate="select id from temporary_selected_tests where patient_username = '" + username + "' and test_id = '" +
                testId + "'";
        ResultSet rs=stmt.executeQuery(duplicate);
        if(rs.next() == true) return;
        String SQL = "insert into temporary_selected_tests (test_id,patient_username,test_name)" +
                "values('" + testId + "','" + username + "','" + test_name + "')";
        stmt.executeUpdate(SQL);
    }

    public static void deleteTestFromTempTable(String username,int testId) throws SQLException {
        Connection conn=DriverManager.getConnection("jdbc:postgresql://localhost/PathLab","postgres","killer125");
        Statement stmt=conn.createStatement();
        String SQL = "delete from temporary_selected_tests where patient_username= '" +
                username + "' and test_id = '" + testId + "'";
        stmt.executeUpdate(SQL);
    }
    public static void insertTestIntoOrderTable(String patientUsername) throws SQLException {
        Connection conn=DriverManager.getConnection("jdbc:postgresql://localhost/PathLab","postgres","killer125");
        Statement stmt=conn.createStatement();

        int packageId = 0,locId;

        String packageSQL= "insert into package(name) values('a')";
        stmt.executeUpdate(packageSQL);
        packageSQL="select location_id from patient where username ='" + patientUsername + "'";
        ResultSet rs= stmt.executeQuery(packageSQL);
        rs.next();
        locId=rs.getInt(1);
        packageSQL="select id from package";
        rs=stmt.executeQuery(packageSQL);

        while(rs.next())
        {
            packageId=rs.getInt(1);
        }
        String insertionSQL="select test_id,test_name from temporary_selected_tests where patient_username = '" +
                patientUsername + "'";
        rs=stmt.executeQuery(insertionSQL);
        Queue<Integer> q =new LinkedList<>();
        Queue<String> name=new LinkedList<>();
        while(rs.next())
        {
            q.add(rs.getInt(1));
            name.add(rs.getString(2));
        }
        while(q.size() !=0)
        {
            insertionSQL = "insert into orders(package_id,test_name,patient_username,test_id,collector_taken" +
                    ",sample_collected,request_date,report_ready,proposed_location)" +
                    "values('" + packageId + "','"+ name.remove()+"','" + patientUsername + "','" +
                    q.remove() + "','false','false',current_timestamp,'false','" + locId + "')";
            stmt.executeUpdate(insertionSQL);

        }
        String deletionSQL = "delete from temporary_selected_tests where patient_username = '" + patientUsername + "'";
        stmt.executeUpdate(deletionSQL);

    }

    public static void insertIntoReportTable(String orderNumber,String report,String comment) throws SQLException {
        Connection conn=DriverManager.getConnection("jdbc:postgresql://localhost/PathLab","postgres","killer125");
        Statement stmt=conn.createStatement();
        String SQL="insert into result(order_id,result,comment) " +
                "values('" + orderNumber + "','" + report + "','" + comment + "')";
        stmt.executeUpdate(SQL);
       // SQL = "update orders set report_ready = 'true' where order_id ='" + orderNumber + "'";
        //stmt.executeUpdate(SQL);
    }


    public static ResultSet searchTestForOrgan(String organ) throws SQLException {
        Connection conn=DriverManager.getConnection("jdbc:postgresql://localhost/PathLab","postgres","killer125");
        Statement stmt=conn.createStatement();
        String organIdSql="select id from organ where lower(name)=lower('" + organ + "')";
        ResultSet rs=stmt.executeQuery(organIdSql);
        rs.next();
        int organId=rs.getInt(1);
        String SQL = "select id,name,amount,collector_availability,description from test where organ_id = '" + organId + "'";
        rs=stmt.executeQuery(SQL);

        return rs;
    }
    public static ResultSet searchTestForDisease(String disease) throws SQLException {
        Connection conn=DriverManager.getConnection("jdbc:postgresql://localhost/PathLab","postgres","killer125");
        Statement stmt=conn.createStatement();
        String organIdSql="select id from disease where lower(name)=lower('" + disease + "')";
        ResultSet rs=stmt.executeQuery(organIdSql);
        rs.next();
        int diseaseId=rs.getInt(1);
        String SQL = "select id,name,amount,collector_availability,description from test where disease_id = '" + diseaseId + "'";
        rs=stmt.executeQuery(SQL);

        return rs;
    }
    public static ResultSet searchTestForText(String searchedText) throws SQLException {
        Connection conn=DriverManager.getConnection("jdbc:postgresql://localhost/PathLab","postgres","killer125");
        Statement stmt=conn.createStatement();
        String SQL = "select id,name,amount,collector_availability,description from test where lower(name) like lower('%" + searchedText + "%')";
        ResultSet rs=stmt.executeQuery(SQL);

        return rs;
    }



    public static ResultSet searchTestByPatientUsername(String username) throws SQLException {
        Connection conn=DriverManager.getConnection("jdbc:postgresql://localhost/PathLab","postgres","killer125");
        Statement stmt=conn.createStatement();
        String SQL = "select test.id,name,amount,collector_availability,description from test,temporary_selected_tests where" +
                " temporary_selected_tests.patient_username = '" + username +
                "' and test.id = temporary_selected_tests.test_id" ;
        ResultSet rs=stmt.executeQuery(SQL);

        return rs;
    }

    public static ResultSet searchTestFromOrderList(String username) throws SQLException {
        Connection conn=DriverManager.getConnection("jdbc:postgresql://localhost/PathLab","postgres","killer125");
        Statement stmt=conn.createStatement();
        String SQL="select test_name,request_date,collector_taken,sample_collected,report_ready " +
                "from orders where patient_username = '" + username + "'";
        ResultSet rs=stmt.executeQuery(SQL);
        return  rs;
    }

    public static boolean isValidOrderNumber(String orderNumber) throws SQLException {
        Connection conn=DriverManager.getConnection("jdbc:postgresql://localhost/PathLab","postgres","killer125");
        Statement stmt=conn.createStatement();
        String SQL="select * from orders where order_id = '" + orderNumber + "'";
        ResultSet rs=stmt.executeQuery(SQL);
        return rs.next();
    }
    public static boolean isResultGiven(String orderNumber) throws SQLException {
        Connection conn=DriverManager.getConnection("jdbc:postgresql://localhost/PathLab","postgres","killer125");
        Statement stmt=conn.createStatement();
        String SQL="select report_ready from orders where order_id = '" + orderNumber + "'";
        ResultSet rs=stmt.executeQuery(SQL);
        rs.next();
        return rs.getBoolean(1);

    }

    public static boolean isSampleCollected(String orderNumber) throws SQLException {
        Connection conn=DriverManager.getConnection("jdbc:postgresql://localhost/PathLab","postgres","killer125");
        Statement stmt=conn.createStatement();
        String SQL="select sample_collected from orders where order_id = '" + orderNumber + "'";
        ResultSet rs=stmt.executeQuery(SQL);
        rs.next();
        return rs.getBoolean(1);
    }

    public static boolean matchUsernamePatient(String username) throws SQLException {

        Connection conn=DriverManager.getConnection("jdbc:postgresql://localhost/PathLab","postgres","killer125");
        Statement stmt=conn.createStatement();
        String userIdSQL = "select id from patient where username = '" +username + "'";

        ResultSet rs = stmt.executeQuery(userIdSQL);

        return rs.next();
    }

    public static boolean matchUsernameCollector(String username) throws SQLException {

        Connection conn=DriverManager.getConnection("jdbc:postgresql://localhost/PathLab","postgres","killer125");
        Statement stmt=conn.createStatement();
        String userIdSQL = "select id from collector where username = '" +username + "'";

        ResultSet rs = stmt.executeQuery(userIdSQL);

        return rs.next();
    }

    public static  boolean matchTestname(String testName,String organ,String disease) throws SQLException {

        Connection conn=DriverManager.getConnection("jdbc:postgresql://localhost/PathLab","postgres","killer125");
        Statement stmt=conn.createStatement();
        int organID,diseaseID;
        String organIdSQL = "select id from organ where name = '" +organ + "'";
        String diseaseIdSQL ="select id from disease where name = '" +disease + "'";
        ResultSet rs = stmt.executeQuery(organIdSQL);
        rs.next();
        organID =rs.getInt(1);

        rs = stmt.executeQuery(diseaseIdSQL);
        rs.next();
        diseaseID =rs.getInt(1);

        String testIdSQL = "select id from test where lower(name) = lower('" +testName + "') and organ_id = '" + organID
                + "'and disease_id = '" + diseaseID+"'";

         rs = stmt.executeQuery(testIdSQL);
        return rs.next();

    }
    public static boolean matchOrganName(String organ) throws SQLException {
        Connection conn=DriverManager.getConnection("jdbc:postgresql://localhost/PathLab","postgres","killer125");
        Statement stmt=conn.createStatement();
        String organIdSQL = "select id from organ where lower(name) = lower('" +organ + "')";
        ResultSet rs = stmt.executeQuery(organIdSQL);
        return rs.next();
    }
    public static boolean matchDiseaseName(String disease) throws SQLException {
        Connection conn=DriverManager.getConnection("jdbc:postgresql://localhost/PathLab","postgres","killer125");
        Statement stmt=conn.createStatement();
        String diseaseIdSQL ="select id from disease where lower(name) = lower('" +disease + "')";
        ResultSet rs = stmt.executeQuery(diseaseIdSQL);
        return rs.next();
    }
    public static boolean matchSampleName(String sample) throws SQLException {
        Connection conn=DriverManager.getConnection("jdbc:postgresql://localhost/PathLab","postgres","killer125");
        Statement stmt=conn.createStatement();
        String sampleIdSQL = "select id from samples where lower(name) = lower('" + sample + "')";
        ResultSet rs = stmt.executeQuery(sampleIdSQL);
        return rs.next();
    }







}
