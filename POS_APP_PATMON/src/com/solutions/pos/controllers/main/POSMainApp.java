package com.solutions.pos.controllers.main;

import com.solutions.entorno.classes.MainClass;
import com.solutions.entorno.utilities.SystemFunctions;
import static com.solutions.entorno.utilities.SystemFunctions.pathname;
import com.solutions.entorno.utilities.SystemVariables;
import com.solutions.pos.controllers.utilities.FXMLLoaderUtil;
import com.solutions.pos.controllers.utilities.PosTablesCreator;
import static com.solutions.pos.controllers.utilities.PosVariables.VAT_VALUES;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author dell
 */
public class POSMainApp extends Application {

    public POSMainApp() {

        try {
            new PosTablesCreator();
            VAT_VALUES.put(1, "VATABLE");
            VAT_VALUES.put(2, "ZERO RATED");
            VAT_VALUES.put(3, "EXEMPTED");

            FXMLLoaderUtil.SALES = (AnchorPane) FXMLLoader.load(getClass().getResource("/com/solutions/pos/views/SalesOperationsHome.fxml"));
            FXMLLoaderUtil.STOCK = (AnchorPane) FXMLLoader.load(getClass().getResource("/com/solutions/pos/views/StockOperationHome.fxml"));
            FXMLLoaderUtil.SETUP = (AnchorPane) FXMLLoader.load(getClass().getResource("/com/solutions/pos/views/SetupHome.fxml"));
            FXMLLoaderUtil.EXPENSE = (AnchorPane) FXMLLoader.load(getClass().getResource("/com/solutions/pos/views/ExpenseHome.fxml"));
            FXMLLoaderUtil.REPORTS = (AnchorPane) FXMLLoader.load(getClass().getResource("/com/solutions/pos/views/ReportsHome.fxml"));
            FXMLLoaderUtil.OTHER_OPERATIONS = (AnchorPane) FXMLLoader.load(getClass().getResource("/com/solutions/pos/views/OtherPosOperations.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(POSMainApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/solutions/pos/views/MainUI_1.fxml"));
        URL url = this.getClass().getResource("/com/solutions/pos/views/css/JMetroLightTheme.css");
        //JMetroLightTheme
        if (url == null) {
            System.out.println("Resource not found. Aborting.");
            System.exit(-1);
        }
        String css = url.toExternalForm();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(css);
         stage.setTitle("POINT OF SALE");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MainClass.getInstance();
        new POSMainApp();
        File file = pathname();
        if (!file.exists()) {
            file.mkdirs();
        }
        file = SystemFunctions.reportspath("WHIZPOINT/REPORTS");
        if (!file.exists()) {
            file.mkdirs();
        }
        SystemVariables.REPORT_FOLDER = SystemFunctions.reportspath("WHIZPOINT/REPORTS").getAbsolutePath() + "/";
        launch(args);
    }

}
