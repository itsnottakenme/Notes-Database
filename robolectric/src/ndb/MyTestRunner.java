package ndb;

import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricTestRunner;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 19/10/13
 * Time: 2:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class MyTestRunner extends RobolectricTestRunner
{
  public MyTestRunner(Class<?> testClass)  throws InitializationError
  {
    super(testClass);
  }

//
//
//  @Override
//  public void beforeTest(Method method)
//  {
//    super.beforeTest(method);
//    // swaps in custom implementations of the sqlite android database class.
//    Robolectric.bindShadowClass(MyShadowSQLiteDatabase.class);
//  }



}