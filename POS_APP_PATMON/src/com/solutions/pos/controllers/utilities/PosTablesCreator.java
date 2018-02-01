package com.solutions.pos.controllers.utilities;

import com.solutions.entorno.utilities.SystemVariables;
import static com.solutions.entorno.utilities.SystemVariables.statement;
import static com.solutions.pos.controllers.utilities.PosVariables.ADD_EXPENDITURE;
import static com.solutions.pos.controllers.utilities.PosVariables.CUSTOMERS;
import static com.solutions.pos.controllers.utilities.PosVariables.EXPENDITUTE_REG;
import static com.solutions.pos.controllers.utilities.PosVariables.INVOICES;
import static com.solutions.pos.controllers.utilities.PosVariables.INVOICESKU;
import static com.solutions.pos.controllers.utilities.PosVariables.POS_CODES_TABLE;
import static com.solutions.pos.controllers.utilities.PosVariables.PURCHASE_INVOICES;
import static com.solutions.pos.controllers.utilities.PosVariables.PURCHASE_ORDERS;
import static com.solutions.pos.controllers.utilities.PosVariables.PURCHASE_ORDER_ITEMS;
import static com.solutions.pos.controllers.utilities.PosVariables.QUOATATIONS;
import static com.solutions.pos.controllers.utilities.PosVariables.QUOTATION_SKU;
import static com.solutions.pos.controllers.utilities.PosVariables.SKUS_CATEGORIES_REGISTRATION;
import static com.solutions.pos.controllers.utilities.PosVariables.SKUS_REGISTRATION;
import static com.solutions.pos.controllers.utilities.PosVariables.SKUS_STOCKS;
import static com.solutions.pos.controllers.utilities.PosVariables.SKU_ORDERS;
import static com.solutions.pos.controllers.utilities.PosVariables.SKU_SALES;
import static com.solutions.pos.controllers.utilities.PosVariables.SUPPLIERS;
import java.sql.SQLException;

/**
 *
 * @author shaddie
 */
public class PosTablesCreator {

    public PosTablesCreator() {
        POS_codes();
        sku_category_registration();
        sku_registration();
        Supliers();
        customer_registration();
        PurchaseOrderItems();
        PurchaseInvoice();
        PurchaseOrders();
        Invoice();
        invoiceSku();
        // Customers();
        expenditureRegistration();
        expenditures();

        sku_stocks();
        sku_orders();
        sku_sales();
        quotation();
        sku_quotation();
       
    }
     private void POS_codes() {
        try {
            statement.executeQuery("SELECT * FROM " + POS_CODES_TABLE + "");
        } catch (SQLException e) {
            String sql = "CREATE TABLE " + POS_CODES_TABLE + "("
                    + "table_name varchar(120) unique not null,"
                    + "next_code int default 1000,"
                    + "max_code int default 9999999,"
                    + " PRIMARY KEY(table_name))";
            try {
                statement.executeUpdate(sql);
                sql = "INSERT INTO "+POS_CODES_TABLE+" (table_name,next_code) VALUES"
                        + "('"+SKUS_CATEGORIES_REGISTRATION+"',1000),"
                        + "('"+SKUS_REGISTRATION+"',2000),"
                        + "('"+SKUS_STOCKS+"',3000),"
                        + "('"+SKU_ORDERS+"',4000),"
                        + "('"+SKU_SALES+"',5000),"
                        + "('"+QUOATATIONS+"',6000),"
                        + "('"+QUOTATION_SKU+"',7000),"
                       // + "('"+ORDERS+"',8000),"
                        + "('"+SUPPLIERS+"',9000),"
                        + "('"+CUSTOMERS+"',1000),"
                        + "('"+EXPENDITUTE_REG+"',2000),"
                        + "('"+ADD_EXPENDITURE+"',3000),"
                        + "('"+INVOICES+"',4000),"
                        + "('"+PURCHASE_ORDERS+"',5000),"
                        + "('"+PURCHASE_ORDER_ITEMS+"',6000),"
                        + "('"+PURCHASE_INVOICES+"',7000),"
                        + "('"+INVOICESKU+"',8000)";
                statement.executeUpdate(sql);
            } catch (SQLException ex) {
                System.out.println("Could not create " + POS_CODES_TABLE + " table");
                ex.printStackTrace();
            }
        }
    }
    private void Invoice() {
        try {
            statement.executeQuery("SELECT * FROM " + INVOICES + "");
        } catch (SQLException e) {
            String sql = "CREATE TABLE " + INVOICES + "("
                    + "invoiceId varchar(20),"
                    + "quatationId VARCHAR(20) NOT NULL,"
                     + "total_amount decimal (15,2),"
                    + "total_vat decimal(10,2),"
                    + "regdate varchar(24),"
                    + "lastUpdated VARCHAR (24),"
                    + "doneBy VARCHAR (20),"
                    + "status int, PRIMARY KEY(invoiceId,quatationId))";
            try {
                statement.executeUpdate(sql);
            } catch (SQLException ex) {
                System.out.println("Could not create " + INVOICES + " table");
            }
        }
    }

    //transport
    private void PurchaseOrders() {
        //new ScriptRunnerUtil();
        try {
            statement.executeQuery("SELECT * FROM " + PURCHASE_ORDERS + " ");

        } catch (SQLException e) {
            String query = "CREATE TABLE " + PURCHASE_ORDERS + "("
                    + "pOrderId VARCHAR(10),"
                    + "supplierId VARCHAR(10),"
                    + "lastUpdated VARCHAR (20),"
                    + "doneBy VARCHAR (20),"
                    + "status smallint, PRIMARY KEY(pOrderId),"
                    + "FOREIGN KEY (supplierId) REFERENCES " + SUPPLIERS + " (supplierId)"
                    + "ON UPDATE CASCADE ON DELETE RESTRICT )";
            try {
                statement.executeUpdate(query);
            } catch (SQLException e1) {
                System.out.println("  " + PURCHASE_ORDERS + " " + e1);
            }
        }

    }

    private void PurchaseOrderItems() {
        try {
            statement.executeQuery("SELECT * FROM " + PURCHASE_ORDER_ITEMS + " ");

        } catch (SQLException e) {
            String query = "CREATE TABLE " + PURCHASE_ORDER_ITEMS + "("
                    + "pOrderId VARCHAR(10),"
                    + "skuId int,"
                    + "quantity int,"
                    + "lastUpdated VARCHAR (20),"
                    + "doneBy VARCHAR (20),"
                    + "status smallint"
                    + ")";
            try {
                statement.executeUpdate(query);
            } catch (SQLException e1) {
                e1.printStackTrace();
                System.out.println("  " + PURCHASE_ORDER_ITEMS + " " + e1);
            }
        }

    }

    private void PurchaseInvoice() {
        try {
            statement.executeQuery("SELECT * FROM " + PURCHASE_INVOICES + " ");

        } catch (SQLException e) {
            String query = "CREATE TABLE " + PURCHASE_INVOICES + "("
                    + "invoiceId VARCHAR(10),"
                    + "pOrderId VARCHAR(20),"
                    + "lastUpdated VARCHAR (20),"
                    + "doneBy VARCHAR (20),"
                    + "status VARCHAR (5), PRIMARY KEY(InvoiceId,pOrderId))";
//                    + "FOREIGN KEY (invoiceId) REFERENCES " + INVOICES + " (invoiceId) "
//                    + "ON UPDATE CASCADE ON DELETE RESTRICT)"
//                    + "";
            try {
                statement.executeUpdate(query);
            } catch (SQLException e1) {
                System.out.println("  " + PURCHASE_INVOICES + " " + e1);
            }
        }

    }

    private void invoiceSku() {
        try {
            statement.executeQuery("SELECT * FROM " + INVOICESKU + " ");

        } catch (SQLException e) {
            String query = "CREATE TABLE " + INVOICESKU + "("
                    + "invoiceId VARCHAR(10),"
                    + "skuId int,"
                    + "quantity int,"
                    + "unitPrice decimal(8,2),"
                     +"vat decimal(8,2),"
                    + "regdate VARCHAR (24),"
                    + "lastUpdated VARCHAR (24),"
                    + "doneBy VARCHAR (20),"
                    + "status smallint)";
//                    + "FOREIGN KEY (skuId) REFERENCES " + PURCHASE_ORDER_ITEMS + " (skuId) "
//                    + "ON UPDATE CASCADE ON DELETE RESTRICT"
//                    + ") ";
            try {
                statement.executeUpdate(query);
            } catch (SQLException e1) {
                System.out.println("  " + INVOICESKU + " " + e1);
            }
        }

    }

    private void Supliers() {
        try {
            statement.executeQuery("SELECT * FROM " + SUPPLIERS + "");
        } catch (SQLException e) {
            String sql = "CREATE TABLE " + SUPPLIERS + "("
                    + "supplierId VARCHAR(100),"
                    + "suplierName varchar(100),"
                    + "idno int,"
                    + "phoneNo VARCHAR(15),"
                    + "gender VARCHAR(10),"
                    + "Company text,"
                    + "address text,"
                    + "email VARCHAR(50),"
                    + "regdate VARCHAR (24),"
                    + "lastUpdated VARCHAR (24),"
                    + "doneBy VARCHAR (20),"
                    + "status smallint , PRIMARY KEY(supplierId))";
            try {
                statement.executeUpdate(sql);
            } catch (SQLException ex) {
                System.out.println("Could not create " + SUPPLIERS + " table");
            }
        }
    }

    
    private void expenditureRegistration() {
        try {
            statement.executeQuery(" SELECT * FROM " + EXPENDITUTE_REG + "");
        } catch (SQLException e) {
            String sql = " CREATE TABLE " + EXPENDITUTE_REG + "("
                    + "expenditureid varchar(20),"
                    + "expenditureName text NOT NULL,"
                    + "description TEXT,"
                     + "regdate VARCHAR (24),"
                    + "lastUpdated VARCHAR (24),"
                    + "doneBy VARCHAR (24),"
                    + "status smallint ,PRIMARY KEY(expenditureid))";
            try {
                statement.executeUpdate(sql);
            } catch (SQLException ex) {
                System.out.println("Could not create " + EXPENDITUTE_REG + " table");
                e.printStackTrace();
            }
        }
    }

    private void expenditures() {
        try {
            statement.executeQuery(" SELECT * FROM " + ADD_EXPENDITURE + "");
        } catch (SQLException e) {
            String sql = " CREATE TABLE " + ADD_EXPENDITURE + "("
                    + "actualExpenseid varchar(20),"
                    + "expenditureid varchar(20),"
                    + "amount decimal(8,2),"
                    + "description TEXT,"
                    + "datepaid VARCHAR (20),"
                    + "lastUpdated VARCHAR (20),"
                    + "doneBy VARCHAR (20),"
                    + "status SMALLINT,PRIMARY KEY(expenditureid))";
            try {
                statement.executeUpdate(sql);
            } catch (SQLException ex) {
                System.out.println("Could not create " + ADD_EXPENDITURE + " table");
                e.printStackTrace();
            }
        }
    }
    private void customer_registration() {
        try {
            statement.executeQuery("SELECT * FROM " + CUSTOMERS + "");
        } catch (SQLException e) {
            String sql = "CREATE TABLE " + CUSTOMERS + "("
                    + "customerId varchar(10),"
                    + "customername varchar(20),"
                    + "idNo INT default 0,"
                    + "phoneNo varchar(15),"
                    + "address varchar(30),"
                    + "gender varchar(15),"
                    + "email varchar(30),"
                    + "status INT,"
                    + "regdate varchar(15),"
                    + "last_update varchar(15),"
                    + "userId varchar(15),"
                    + "PRIMARY KEY(customerId))";
            try {
                statement.executeUpdate(sql);
            } catch (SQLException ex) {
                System.out.println("Could not create " + CUSTOMERS + " table");
            }
        }
    }

    //Sku category registration table

    private void sku_category_registration() {
        try {
            statement.executeQuery("SELECT * FROM " + SKUS_CATEGORIES_REGISTRATION + "");
        } catch (SQLException e) {
            String sql = "CREATE TABLE " + SKUS_CATEGORIES_REGISTRATION + "(skucategoryId int,"
                    + "skucategoryname text,"
                    + "description text,"
                    + "regdate varchar(24),"
                    + "last_update varchar(24),"
                    + "userId varchar(24),"
                    + "PRIMARY KEY(skucategoryId))";
            try {
                statement.executeUpdate(sql);
            } catch (SQLException ex) {
                System.out.println("Could not create " + SKUS_CATEGORIES_REGISTRATION + " table");
            }
        }
    }

    //Skus  Registration table

    private void sku_registration() {
        try {
            statement.executeQuery("SELECT * FROM " + SKUS_REGISTRATION + "");
        } catch (SQLException e) {
            String sql = "CREATE TABLE " + SKUS_REGISTRATION + "("
                    + "skuId int,"
                    + "skucategoryId INT,"
                    + "skuname text,"
                    + "description TEXT,"
                    + "sku_photo varchar(50),"
                    + "retailprice decimal(8,2),"
                    + "wholesaleprice decimal(8,2),"
                    + "vat int not null,"
                    + "reorderlevel int,"
                    + "regdate varchar(24),"
                    + "last_update varchar(24),"
                    + "userId varchar(24),"
                    + "PRIMARY KEY(skuId),FOREIGN KEY(skucategoryId) REFERENCES " + SKUS_CATEGORIES_REGISTRATION + "(skucategoryId))";
            try {
                statement.executeUpdate(sql);
                if(SystemVariables.CLASS_NAME.equalsIgnoreCase("org.postgresql.Driver")){
                statement.executeUpdate("ALTER TABLE "+SKUS_CATEGORIES_REGISTRATION+" add sku_photo_col bytea");
                }
                else if(SystemVariables.CLASS_NAME.equalsIgnoreCase("com.mysql.jdbc.Driver")){
                     statement.executeUpdate("ALTER TABLE "+SKUS_CATEGORIES_REGISTRATION+" add sku_photo_col longblob");
                }
            } catch (SQLException ex) {
                System.out.println("Could not create " + SKUS_REGISTRATION + " table");
                ex.printStackTrace();
            }
        }
    }

    //Skus stock table

    private void sku_stocks() {
        try {
            statement.executeQuery("SELECT * FROM " + SKUS_STOCKS + "");
        } catch (SQLException e) {
            String sql = "CREATE TABLE " + SKUS_STOCKS + "("
                    + "skuId INT,"
                    + "dateofpurchase varchar(24),"
                    + "quantity INT,"
                    + "priceperunit decimal(8,2),"
                    + "batchno varchar(50),"
                    + "regdate varchar(15),"
                    + "last_update varchar(15),"
                    + "userId varchar(15),"
                    + "stockid int primary key,"
                    + "FOREIGN KEY (skuId) REFERENCES " + SKUS_REGISTRATION + "(skuId))";
            try {
                statement.executeUpdate(sql);
            } catch (SQLException ex) {
                System.out.println("Could not create " + SKUS_STOCKS + " table    " + ex.getMessage());
            }
        }
    }

    //sku orders table

    private void sku_orders() {
        try {
            statement.executeQuery("SELECT * FROM " + SKU_ORDERS + "");
        } catch (SQLException e) {
            String sql = "CREATE TABLE " + SKU_ORDERS + "("
                    + "customerId varchar(20),"
                    + "orderId Varchar(20),"
                    + "status smallint,"
                    + "receiptno Varchar(20),"
                    + "total_amount decimal (15,2),"
                    + "total_vat decimal(10,2),"
                    + "regdate varchar(24),"
                    + "last_update varchar(24),"
                    + "userId varchar(30),"
                     + "PRIMARY KEY(orderId))";
                  //  + "FOREIGN KEY(customerId) REFERENCES " + CUSTOMERS + "(customerId))";
            try {
                statement.executeUpdate(sql);
            } catch (SQLException ex) {
                System.out.println("Could not create " + SKU_ORDERS + " table  " + ex.getMessage());
            }
        }
    }

    //sku sales table

    private void sku_sales() {
        try {
            statement.executeQuery("SELECT * FROM " + SKU_SALES + "");
        } catch (SQLException e) {
            String sql = "CREATE TABLE " + SKU_SALES + "("
                    + "orderId Varchar(20),"
                    + "skuId INT,"
                    + "quantity INT,"
                    + "priceperunit decimal(8,2),"
                     +"vat decimal(8,2),"
                    + "mode varchar(20),"
                    + "regdate varchar(15),"
                    + "last_update varchar(15),"
                    + "userId varchar(15),"
                    + "buyingprice decimal(8,2)"
                    + "FOREIGN KEY(orderId) REFERENCES " + SKU_ORDERS + "(orderId))";
            try {
                statement.executeUpdate(sql);
            } catch (SQLException ex) {
                System.out.println("Could not create " + SKU_SALES + " table  " + ex.getMessage());
            }
        }
    }

    //table for quotation

    private void quotation() {
        try {
            statement.executeQuery("SELECT * FROM " + QUOATATIONS + "");
        } catch (SQLException e) {
            String sql = "CREATE TABLE " + QUOATATIONS + "("
                    + "quotationId varchar(30),"
                    + "customerId varchar(15),"
                    + "status SMALLINT DEFAULT 1,"
                     + "total_amount decimal (15,2),"
                    + "total_vat decimal(10,2),"
                    + "regdate varchar(15),"
                    + "last_update varchar(15),"
                    + "userId varchar(15),"
                    + "PRIMARY KEY(quotationId), "
                    + "FOREIGN KEY(customerId) REFERENCES " + CUSTOMERS + "(customerId))";
            try {
                statement.executeUpdate(sql);
            } catch (SQLException ex) {
                System.out.println("Could not create " + QUOATATIONS + " table  " + ex.getMessage());
            }
        }
    }

    //QUOTATION_SKU 

    private void sku_quotation() {
        try {
            statement.executeQuery("SELECT * FROM " + QUOTATION_SKU + "");
        } catch (SQLException e) {
            String sql = "CREATE TABLE " + QUOTATION_SKU + "("
                    + "quotationId Varchar(30),"
                    + "skuid INT,"
                    + "quantity INT,"
                    + "priceperunit decimal(8,2),"
                     +"vat decimal(8,2),"
                    + "mode varchar(20),"
                    + "regdate varchar(15),"
                    + "last_update varchar(15),"
                    + "userId varchar(15),"
                    // + "PRIMARY KEY(idNo),"
                    + "FOREIGN KEY(skuId) REFERENCES " + SKUS_REGISTRATION + "(skuId),"
                    + "FOREIGN KEY(quotationId) REFERENCES " + QUOATATIONS + "(quotationId))";
            try {
                statement.executeUpdate(sql);
            } catch (SQLException ex) {
                System.out.println("Could not create " + QUOTATION_SKU + " table  " + ex.getMessage());
            }
        }
    }

 
  
}
