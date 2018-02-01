package com.solutions.pos.models.reports;


import static com.solutions.entorno.utilities.SystemVariables.resultSet;
import static com.solutions.entorno.utilities.SystemVariables.statement;
import com.solutions.pos.controllers.utilities.InternalTableViewRenderer;
import static com.solutions.pos.controllers.utilities.PosVariables.ADD_EXPENDITURE;
import static com.solutions.pos.controllers.utilities.PosVariables.EXPENDITUTE_REG;
import java.util.ArrayList;
import javafx.scene.control.TableView;




/**
 *
 * @author DELL
 */
public class Expenditures {
private String     addExpenseId;
private String expenditureid,expenditureName,datepaid ;
private Double amount,totalExpenditure ;
private String description ;
private String lastUpdated ;
private String doneBy ;
private int status ;
private static Double total;

public Expenditures(){
    
}

    public String getExpenditureid() {
        return expenditureid;
    }

    public void setExpenditureid(String expenditureid) {
        this.expenditureid = expenditureid;
    }

    public Double getAmount() {
        return amount;
    }

    
    public String getAddExpenseId() {
        return addExpenseId;
    }

    public String getExpenditureName() {
        return expenditureName;
    }

    public void setExpenditureName(String expenditureName) {
        this.expenditureName = expenditureName;
    }

    public void setAddExpenseId(String addExpenseId) {
        this.addExpenseId = addExpenseId;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    

    public  Double getTotalExpenditure() {
        return totalExpenditure;
    }

    public  void setTotalExpenditure(Double totalExpenditure) {
        this.totalExpenditure = totalExpenditure;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getDoneBy() {
        return doneBy;
    }

    public void setDoneBy(String doneBy) {
        this.doneBy = doneBy;
    }

    public String getDatepaid() {
        return datepaid;
    }

    public void setDatepaid(String datepaid) {
        this.datepaid = datepaid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

   
    


    public static  ArrayList readExpenditures(String month,String year) {
       ArrayList<Expenditures> exp = new ArrayList<>();
         Expenditures expenditure;
        String selectSQL = "SELECT  ex.expenditureid,er.expenditureName,ur.firstname, ex.amount, ex.description, ex.lastupdated, ex.doneby, ex.status" +
"  FROM " + ADD_EXPENDITURE + " ex ,"
                + " "+EXPENDITUTE_REG+" er, "
                + "user_user_registration ur  WHERE er.expenditureid=ex.actualexpenseid  AND ur.userid=ex.doneby "
                + " ";
        String theMonth = "AND ex.lastupdated LIKE  '%-"+month+"-%'";
        String theYear = "AND ex.lastupdated LIKE  '"+year+"-%'";
       
        
       
        try {
            if(month==null && year==null){
            }
             if(month!=null && year==null ){
              resultSet = statement.executeQuery(selectSQL+theMonth);
            }
            if(month!=null && year!=null ){
              resultSet = statement.executeQuery(selectSQL+theMonth+theYear);
            }
             total=0.0;
            while (resultSet.next()) {
                
                expenditure = new Expenditures();
                expenditure.setExpenditureid(resultSet.getString("expenditureid"));
                expenditure.setExpenditureName(resultSet.getString("expenditureName"));
                expenditure.setAmount(resultSet.getDouble("amount"));
                expenditure.setDescription(resultSet.getString("description"));
                expenditure.setLastUpdated(resultSet.getString("description"));
                expenditure.setDoneBy(resultSet.getString("firstname"));
                expenditure.setStatus(resultSet.getInt("status"));
                total+=expenditure.getAmount();
                expenditure.setTotalExpenditure(total);
               
                exp.add(expenditure);

            }
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return exp;
        

    }
    public static TableView expendituresData(String month,String year) {

        TableView tv;
        String[] headers = {"Expense ID", "Expense Name", "Description", "Amount" ,"Inccured By"};
        String[] property = {"expenditureid", "expenditureName", "description", "amount", "doneBy"};
        ArrayList<Object> model = readExpenditures(month,year);

       InternalTableViewRenderer tbl = new InternalTableViewRenderer(headers, model, property);

        tv = tbl.getTable();
        return tv;
    }
}
