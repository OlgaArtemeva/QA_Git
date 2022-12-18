import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AllAuthorization extends TestBase {


    @DataProvider
    public Iterator<Object[]> getWrongLoginData() {
        List<Object[]> list = new ArrayList<>();

        list.add(new Object[]{"wrong email", "password"});
        list.add(new Object[]{"biley@example.com", "12345"});
        list.add(new Object[]{"biley_example.com", "123456"});

        return list.iterator();
    }

    @Test(dataProvider = "getWrongLoginData")
//    negative authorization check

    public void ourTestNegative(String email, String password) throws InterruptedException {
        logger.info("Starting negative authorization (method: managerAuthorization)");
        auth(email, password);
        authCheck(Boolean.TRUE);
    }

    // пример для скриншота
    @Test(dataProvider = "getWrongLoginData")
//    negative authorization check
    public void ourTestNegativeScreenshot(String email, String password) throws InterruptedException {
        logger.info("Starting negative authorization (method: managerAuthorization)");
        auth(email, password);
        authCheck(Boolean.TRUE);
        logoutIlya();
    }

    // пример для видео
    @Test(dataProvider = "getWrongLoginData")
//    negative authorization check
    public void ourTestNegativeVideo(String email, String password) throws InterruptedException {
        logger.info("Starting negative authorization (method: managerAuthorization)");
        startRecording();
        auth(email, password);
        authCheck(Boolean.TRUE);
//        logoutIlya();
        stopRecoding();
    }
        @Test(dataProvider = "getAllAuto", dataProviderClass = DataProviders.class) //имя и где описан
//    manager authorization check
//    public void positiveAuthorization(String email, String password, String positiveParam1, String positiveParam2, String positiveParam3,
//                                     String positiveParam4, String negativeParam1, String negativeParam2,
//                                     String negativeParam3, String negativeParam4)
        public void positiveAuthorization (String email, String password, String positiveParam1,
                String positiveParam2, String positiveParam3, String positiveParam4)
            throws InterruptedException {
            logger.info("Starting positive authorization (method: managerAuthorization)");
            auth(email, password);
//        authCheckAll("Invalid email or password", Boolean.FALSE);
//        logger.info("check negative authorization (method: authCheckAll), result: ");

            authCheckAll(positiveParam1, Boolean.TRUE);
            authCheckAll(positiveParam2, Boolean.TRUE);
            authCheckAll(positiveParam3, Boolean.TRUE);
            authCheckAll(positiveParam4, Boolean.TRUE);
            logoutIlya();
//        logger.info("logout manager authorization (method: logoutManagerConsultant)");

        }

        @Test
//    manager authorization check
        public void managerAuthorization () throws InterruptedException {
            logger.info("Starting manager authorization (method: managerAuthorization)");
            auth("billye@example.com", "123456");
            authCheckAll("Invalid email or password", Boolean.FALSE);
//        logger.info("check negative authorization (method: authCheckAll), result: ");

            authCheckAll("PROJECT OVERVIEW", Boolean.TRUE);
            authCheckAll("CLIENTS", Boolean.TRUE);
            authCheckAll("TEAM", Boolean.TRUE);
            authCheckAll("INVOICES", Boolean.TRUE);
            logoutManagerConsultant();
//        logger.info("logout manager authorization (method: logoutManagerConsultant)");

        }

        @Test
//    client authorization check
        public void clientAuthorization () throws InterruptedException {

            auth("lucie@example.com", "123456");
            authCheckAll("Invalid email or password", Boolean.FALSE);
            authCheckAll("PROJECTS OVERVIEW", Boolean.TRUE);
            authCheckAll("CLIENTS", Boolean.FALSE);
            authCheckAll("TEAM", Boolean.FALSE);
            authCheckAll("INVOICES", Boolean.TRUE);
            logoutClient();
        }

        @Test
//    consultant authorization check
        public void consultantAuthorization () throws InterruptedException {

            auth("edra@example.com", "123456");
            authCheckAll("Invalid email or password", Boolean.FALSE);
            authCheckAll("PROJECT OVERVIEW", Boolean.TRUE);
            authCheckAll("CLIENTS", Boolean.TRUE);
            authCheckAll("TEAM", Boolean.TRUE);
            authCheckAll("INVOICES", Boolean.TRUE);
            logoutManagerConsultant();
        }
        @AfterMethod(alwaysRun = true)
        public void afterMLogout (ITestResult result){
            if (!result.isSuccess()) {
                logger.error("Failed test: " + result.getMethod().getMethodName() + " screenshot: " + takeScreenshot());
            }

            try {
                logoutIlya();
            } catch (Exception e) {
                logger.info(e.getMessage());
            }

            logger.info("Running after method: logging current user out");
        }
    }


