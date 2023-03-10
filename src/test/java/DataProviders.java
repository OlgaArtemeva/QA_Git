import com.github.hemanthsridhar.CSVUtils;
import com.github.hemanthsridhar.ExcelUtils;
import com.github.hemanthsridhar.lib.ExtUtils;
import org.testng.annotations.DataProvider;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class DataProviders {

    @DataProvider
    public static Iterator<Object[]> getAllAuto() throws IOException {
        List<Object[]> list = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/all_auto.csv")));

        String line = reader.readLine();
        while (line != null) {
            String[] split = line.split(",");
            list.add(split);
            line = reader.readLine();
        }

        return list.iterator();
    }

//    @DataProvider
//    public Object[][] excelWrongDataRead() throws Exception {
//        ExtUtils ext = new ExcelUtils("src/test/resources/excelData.xlsx", "wrongData");
//        return ext.parseData();
//    }


    @DataProvider
        public Object[][] excelWrongDataRead() throws Exception {
            ExtUtils ext = new ExcelUtils("src/test/resources/excelData.xlsx", "wrongData");
            return ext.parseData();
    }
    @DataProvider
    public Object[][] excelCorrectDataRead() throws Exception {
        ExtUtils ext = new ExcelUtils("src/test/resources/excelData.xlsx", "correctData");
        return ext.parseData();
    }

    @DataProvider        //(parallel=true)
    public Object[][] csvWrongDataRead() throws Exception {
        String path = "src/test/resources/all_authorization_wrong_k.csv";
        ExtUtils ext = new CSVUtils(path, true);
        return ext.parseData();
    }

//    @DataProvider(parallel=true)

    @DataProvider
    public Object[][] csvCorrectDataRead() throws Exception {
        String path = "src/test/resources/all_authorization_correct_k.csv";
        ExtUtils ext = new CSVUtils(path, true);
        return ext.parseData();
    }

    @DataProvider   //(parallel=true)
    public Object[][] csvAllAuthorizationCorrectDataRead() throws Exception {
        String path = "src/test/resources/AllAuthorization.csv";
        ExtUtils ext = new CSVUtils(path, true);
        return ext.parseData();
    }

    @DataProvider
    public Object[][] excelAllAuthorizationCorrectDataRead() throws Exception {
        ExtUtils ext = new ExcelUtils("src/test/resources/excelData.xlsx", "allAuthorization");
        return ext.parseData();
    }
}