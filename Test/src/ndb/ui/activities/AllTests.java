package ndb.ui.activities;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 26/03/13
 * Time: 1:26 PM
 * To change this template use File | Settings | File Templates.
 */





import junit.framework.Test;
import junit.framework.TestSuite;
import android.test.suitebuilder.TestSuiteBuilder;


/**
 * A test suite containing all tests for my application.
 */
public class AllTests extends TestSuite {
  public static Test suite() {
    return new TestSuiteBuilder(AllTests.class).includeAllPackagesUnderHere().build();
  }
}
