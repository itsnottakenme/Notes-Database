package ndb;

import android.test.ActivityInstrumentationTestCase2;
import ndb.ui.activities.NoteListActivity;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class kanana.notesdatabase.activities.NoteListActivityTest \
 * kanana.notesdatabase.tests/android.test.InstrumentationTestRunner
 */
public class NoteListActivityTest extends ActivityInstrumentationTestCase2<NoteListActivity>
{

  public NoteListActivityTest()
  {
    super("kanana.notesdatabase", NoteListActivity.class);
  }

}
