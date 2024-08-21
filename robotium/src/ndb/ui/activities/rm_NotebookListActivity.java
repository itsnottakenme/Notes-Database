package ndb.ui.activities;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import com.jayway.android.robotium.solo.Solo;
import kanana.notesdatabase.R;
import ndb.types.Note;
import ndb.types.Notebook;


/**
 * TODO:
 * - Make a notebook for each view type and use clickOnText() to open the appropriate one
 *
 */













/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class kanana.notesdatabase.ui.activities.NotebookListActivityTest \
 * kanana.notesdatabase.tests/android.test.InstrumentationTestRunner
 */
public class rm_NotebookListActivity extends ActivityInstrumentationTestCase2<NotebookListActivity>
{
  static final String sTitle= "first title";

  static final int DEFAULT_SLEEP= 200;



  static String
                NOTEBOOK_NORMAL= "Normal",
                NOTEBOOK_TODO= "Todo",
                NOTEBOOK_GROCERIES= "Groceries";





  private Solo mSolo;
  Activity mActivity;

  public rm_NotebookListActivity()
  {
    super("kanana.notesdatabase", NotebookListActivity.class);
  }


  public void setUp() throws Exception
  {
    super.setUp();

    return;
  }

  public void tearDown() throws Exception
  {
    mSolo.finishOpenedActivities();

    //mActivity.finish();
    super.tearDown();


    // relaunch your app by calling the same Activity as in the constructor
    // of your ActivityInstrumentationTestCase2
    //this.launchActivity("kanana.notesdatabase", NotebookListActivity.class, null);

    //solo= null;
    return;
  }

















  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /////////////////////////////////////////Begin Tests//////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * Tests that when a note is created, fields entered, NDB retarted, that all fields will be exactly as they were before restart
   *
   * Open No Category notebook
   * click new note
   * set title
   * close ndb and remove from memory
   * reopen ndb
   * assert: EditNoteActivity should be open and title should be what was assigned previously
   *
   * todo:
   * EditNoteActivity - create methods to return
   *
    */


  /**
   * Preconditions:
   * - "No Category" must be set as "Todo list" for date picker test to work
   * @throws Exception
   *
   * Tests:
   * - cached note is saved and restored when app removed from memory
   * (title and due date are checked specificly)
   *
   *
   */
  public void test_Cache_Note() throws Exception
  {
    long dueDateBefore,
          dueDateAfter;

    Note    noteBeforeCache,
            noteRecoveredFromCache;

    //solo= new Solo(getInstrumentation(), getActivity());

    mSolo = new Solo(getInstrumentation());
    mActivity = getActivity();


    /**
     * If cached note still exists (eg. due to failed test) cancel it to return to NotebookListActivity
     */
    cancelCachedNoteIfOpenAndReturnToNotebookListActivity();




    /**
     * NotebookListActivity
     * - click on list item "No Category" to open notebook
     */
    //solo.clickInList(0);
    //solo.sleep(2000);
    if (!mSolo.waitForActivity(NotebookListActivity.class))
    {
      throw new Exception("FAIL: solo.waitForActivity(NotebookActivity.class)");
    }
    mSolo.clickInList(0,0);


    /**
     * TodoListActivity
     * - click on add button create a new note
     */
    if (!mSolo.waitForActivity(TodoListActivity.class))
    {
      throw new Exception("FAIL: solo.waitForActivity(TodoListActivity.class)");
    }

    mSolo.clickOnActionBarItem(R.id.add_option);

    /**
     * EditNoteActivity
     * - add title
     * - close activity and process
     */
    if (!mSolo.waitForActivity(EditNoteActivity.class))
    {
      throw new Exception("FAIL: solo.waitForActivity(EditNoteActivity.class, 1000)");
    }

    mSolo.enterText((EditText) mSolo.getView(R.id.note_title), sTitle);
    mSolo.clickOnView(mSolo.getView(R.id.todo_due_date));
    mSolo.sleep(100);
    mSolo.setDatePicker(0, 1999, 5, 5);                         //set random date just to show it changed
    //solo.sleep(2000);
    mSolo.clickOnButton("Ok");

    //todo: insert code to fill in all fields, especially due date
    noteBeforeCache= ((EditNoteActivity) mSolo.getCurrentActivity()).activityToNote();

    mSolo.sleep(DEFAULT_SLEEP);
    // killing all your Activities manually if it doesn't by itself anyway
    mSolo.finishOpenedActivities();

    // relaunch your app by calling the same Activity as in the constructor
    // of your ActivityInstrumentationTestCase2
    this.launchActivity("kanana.notesdatabase", NotebookListActivity.class, null);
    assertEquals(sTitle, ((EditText) mSolo.getView(R.id.note_title)).getText().toString());
    noteRecoveredFromCache= ((EditNoteActivity) mSolo.getCurrentActivity()).activityToNote();
    assertEquals(noteBeforeCache, noteRecoveredFromCache);


    mSolo.clickOnActionBarItem(R.id.cancel_option);
    mSolo.waitForDialogToOpen();
    mSolo.sleep(DEFAULT_SLEEP);


    // do your testing
    mSolo.assertCurrentActivity("Expected EditNoteActivity", EditNoteActivity.class);
    mSolo.clickOnButton("Yes");
    mSolo.sleep(DEFAULT_SLEEP);


    return;
  }


  /**
   * Precondition: 1 note in No Category already exists
   *
   * - Open  note
   * - save note
   * - remove app from memory
   * - reopen app - test that is in NotebookListActivity
   * - delete newly created note
   * @throws Exception
   */
  public void test_not_cache_note() throws Exception
  {
//    mSolo = new Solo(getInstrumentation());
//    mActivity = getActivity();
    mSolo = new Solo(getInstrumentation());
    mActivity = getActivity();

    cancelCachedNoteIfOpenAndReturnToNotebookListActivity();
    setNoCategoryNotebookAsTodoList();

    /**
     * NotebookListActivity
     * - click on list item "No Category" to open notebook
     */
    if (!mSolo.waitForActivity(NotebookListActivity.class))
    {
      throw new Exception("FAIL: solo.waitForActivity(NotebookActivity.class)");
    }
    mSolo.clickInList(0,0);


    /**
     * TodoListActivity
     * - click on on list to edit first note note
     */
    if (!mSolo.waitForActivity(TodoListActivity.class))
    {
      throw new Exception("FAIL: solo.waitForActivity(TodoListActivity.class)");
    }
    mSolo.clickInList(0);

    /**
     * EditNoteActivity
     * - add title
     * - close activity and process
     */
    if (!mSolo.waitForActivity(EditNoteActivity.class))
    {
      throw new Exception("FAIL: solo.waitForActivity(EditNoteActivity.class)\n  Current Activity="
                          +mSolo.getCurrentActivity().getClass().toString());
    }

    //((EditText)mSolo.getView(R.id.note_title)).setText("testNoteCacheNote");
    mSolo.clearEditText((EditText) mSolo.getView(R.id.note_title));
    mSolo.enterText((EditText) mSolo.getView(R.id.note_title), "testNoteCacheNote");

    mSolo.goBack();

    /**
     * Close and relaunch app
     */
    mSolo.sleep(DEFAULT_SLEEP);
    // killing all your Activities manually if it doesn't by itself anyway
    mSolo.finishOpenedActivities();

    // relaunch your app by calling the same Activity as in the constructor
    // of your ActivityInstrumentationTestCase2
    this.launchActivity("kanana.notesdatabase", NotebookListActivity.class, null);
    mSolo.sleep(1000);
    assertEquals(NotebookListActivity.class, getActivity().getClass());

    /**
     * Delete note that was created
     */



    return;
  }





  /**
   * Modified date tests
   *
   *   note not changed
   *   note not changed -> note cached -> reloaded -> save (shouldn't save... so probably redundant case)
   *   note changed
   *   note changed -> note cached -> note reloaded -> note saved
   */



  /**
   * Opens note and then closes it without changing anything.
   * Expected result: Due date should not change
   * @throws Exception
   */
  public void test_date_due1/*note not changed*/() throws Exception
  {
    Note noteBefore,
         noteAfter;

    mSolo = new Solo(getInstrumentation());
    mActivity = getActivity();

    cancelCachedNoteIfOpenAndReturnToNotebookListActivity();
    loadEditNoteActivity();
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////Test 1//////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    //due date should not change
    /**
     * EditNoteActivity
     * - open
     * - close without edits
     * - due date should not change
     */
    if (!mSolo.waitForActivity(EditNoteActivity.class))
    {
      throw new Exception("FAIL: solo.waitForActivity(EditNoteActivity.class)");
    }
    noteBefore= ((EditNoteActivity)mSolo.getCurrentActivity()).activityToNote();
    mSolo.goBack();
    //mSolo.sleep(4000);
    mSolo.clickInList(0);
    if (!mSolo.waitForActivity(EditNoteActivity.class))
    {
      throw new Exception("FAIL: solo.waitForActivity(EditNoteActivity.class)");
    }
    noteAfter= ((EditNoteActivity)mSolo.getCurrentActivity()).activityToNote();

    assertEquals(noteBefore.getDateModified(), noteAfter.getDateModified());


//    /**
//     * Test 2
//     * Note is edited. due date should change
//     */
//    mSolo.clearEditText((EditText) mSolo.getView(R.id.note_title));
//    mSolo.enterText((EditText) mSolo.getView(R.id.note_title), "test_is_due_date_properly_updated_when_note_saved");
//    noteBefore= ((EditNoteActivity)mSolo.getCurrentActivity()).activityToNote();
//    mSolo.goBack();
//    mSolo.sleep(4000);
//    mSolo.clickInList(0);
//    if (!mSolo.waitForActivity(EditNoteActivity.class))
//    {
//      throw new Exception("FAIL: solo.waitForActivity(EditNoteActivity.class)");
//    }
//    noteAfter= ((EditNoteActivity)mSolo.getCurrentActivity()).activityToNote();
//    mSolo.goBack();
//    //assertTrue(noteBefore.getDateModified() != noteAfter.getDateModified());
//
//




    return;
  }


  /**
   *
   * @throws Exception
   */
  public void test_date_due2/*note changed*/() throws Exception
  {
    Note    noteBefore,
            noteAfter;

    mSolo = new Solo(getInstrumentation());
    mActivity = getActivity();


    loadEditNoteActivity(); // includes call to cancelCachedNoteIfOpenAndReturnToNotebookListActivity();
    if (!mSolo.waitForActivity(EditNoteActivity.class))
    {
      throw new Exception("FAIL: solo.waitForActivity(EditNoteActivity.class)");
    }

    /**
     * Test 2
     * Note is edited. due date should change
     */
    //mSolo.clearEditText((EditText) mSolo.getView(R.id.note_title));
    mSolo.enterText((EditText) mSolo.getView(R.id.note_title), "note changed");     //clear is disabled to ensure old title != new title
    noteBefore= ((EditNoteActivity)mSolo.getCurrentActivity()).activityToNote();
    mSolo.goBack();
   // mSolo.sleep(4000);
    mSolo.clickInList(0);
    if (!mSolo.waitForActivity(EditNoteActivity.class))
    {
      throw new Exception("FAIL: solo.waitForActivity(EditNoteActivity.class)");
    }
    noteAfter= ((EditNoteActivity)mSolo.getCurrentActivity()).activityToNote();
    mSolo.goBack();
    assertTrue(noteBefore.getDateModified() != noteAfter.getDateModified());        //todo: fails here but not in app... why?
    mSolo.goBack();





    return;
  }

  public void test_date_due3/*note not changed (from cached note)*/() throws Exception
  {
    Note    noteBefore,
            noteAfter;

    mSolo = new Solo(getInstrumentation());
    mActivity = getActivity();

    loadEditNoteActivity();
    noteBefore= ((EditNoteActivity)mSolo.getCurrentActivity()).activityToNote();
    closeEditNoteActivityAndReloadAsCachedNote();
    save_current_open_note_and_reload();
    noteAfter= ((EditNoteActivity)mSolo.getCurrentActivity()).activityToNote();
    assertEquals(noteBefore.getDateModified(), noteAfter.getDateModified());
    return;
  }

  /**
   * - edit note
   * - cache note
   * - destroy app
   * - rerun app (loads cached note)
   * - save note
   * - reload note
   * - assert: modifiedDate should be changed
   * @throws Exception
   */

  public void test_date_due4/*note changed -> note cached -> note reloaded -> note saved*/() throws Exception
  {
    Note    noteBefore,
            noteAfter;

    mSolo = new Solo(getInstrumentation());
    mActivity = getActivity();

    loadEditNoteActivity();
    mSolo.enterText((EditText) mSolo.getView(R.id.note_title), "+note ");
    noteBefore= ((EditNoteActivity)mSolo.getCurrentActivity()).activityToNote();
    closeEditNoteActivityAndReloadAsCachedNote();
    save_current_open_note_and_reload();
    noteAfter= ((EditNoteActivity)mSolo.getCurrentActivity()).activityToNote();


    //TODO: THIS FAILS ON DEVICE BUT PASSES HERE.... WHATS THE DIFFERENCE??
    assertTrue(noteBefore.getDateModified() != noteAfter.getDateModified());



    return;
  }



  public void test_Click_sub_views()
  {
    mSolo = new Solo(getInstrumentation());
    mActivity = getActivity();

    mSolo.clickOnText("No Category");
    assertTrue(mSolo.waitForActivity(TodoListActivity.class));
    return;
  }

  public void test_waitForActivity()
  {
    mSolo = new Solo(getInstrumentation());
    mActivity = getActivity();
    mSolo.sleep(2000);
    assertTrue(mSolo.waitForActivity(NotebookListActivity.class));
    return;


  }

  ///////////////////////////////
  ////HELPER METHODS/////////////
  ///////////////////////////////


  /**
   * saves note in EditNoteActivity and then loads it in EditNoteActivity again
   *
   * no caching is done
   * @throws Exception
   */
  public void save_current_open_note_and_reload() throws Exception
  {
    mSolo.goBack();
    mSolo.clickInList(0);
    if (!mSolo.waitForActivity(EditNoteActivity.class))
    {
      throw new Exception("FAIL: solo.waitForActivity(EditNoteActivity.class, 1000)");
    }


    return;
  }

  public void closeEditNoteActivityAndReloadAsCachedNote() throws Exception
  {
    mSolo.sleep(DEFAULT_SLEEP);
    // killing all your Activities manually if it doesn't by itself anyway
   // mSolo.goBack();
    mSolo.finishOpenedActivities();
   // mSolo.sleep(DEFAULT_SLEEP);
    //getActivity();
    // relaunch your app by calling the same Activity as in the constructor
    // of your ActivityInstrumentationTestCase2
    this.launchActivity("kanana.notesdatabase", NotebookListActivity.class, null);             //TODO**************
//    if (!mSolo.waitForActivity(EditNoteActivity.class))
//    {
//      throw new Exception("FAIL: solo.waitForActivity(EditNoteActivity.class)");
//    }   //todo disabled because gets the wrong activity
    mSolo.sleep(200);

    return;
  }

  public void loadCachedNote() throws Exception
  {

    /**
     * If cached note still exists (eg. due to failed test) cancel it to return to NotebookListActivity
     */
    cancelCachedNoteIfOpenAndReturnToNotebookListActivity();

    /**
     * NotebookListActivity
     * - click on list item "No Category" to open notebook
     */
    if (!mSolo.waitForActivity(NotebookListActivity.class))
    {
      throw new Exception("FAIL: solo.waitForActivity(NotebookActivity.class)");
    }
    mSolo.clickInList(0,0);


    /**
     * AbstractNoteListActivity
     * - open first Item
     */
//    if (!mSolo.waitForActivity(AbstractNoteListActivity.class))
//    {
//      throw new Exception("FAIL: solo.waitForActivity(AbstractNoteListActivity.class)");
//    }
    if (!mSolo.waitForActivity(TodoListActivity.class))
    {
      throw new Exception("FAIL: solo.waitForActivity(TodoListActivity.class)");
    }

    mSolo.clickInList(1);


    /**
     * EditNoteActivity
     * - add title
     * - close activity and process
     */
    if (!mSolo.waitForActivity(EditNoteActivity.class))
    {
      throw new Exception("FAIL: solo.waitForActivity(EditNoteActivity.class, 1000)");
    }

    mSolo.sleep(DEFAULT_SLEEP);
    // killing all your Activities manually if it doesn't by itself anyway
    mSolo.finishOpenedActivities();

    // relaunch your app by calling the same Activity as in the constructor
    // of your ActivityInstrumentationTestCase2
    this.launchActivity("kanana.notesdatabase", NotebookListActivity.class, null);
    if (!mSolo.waitForActivity(EditNoteActivity.class))
    {
      throw new Exception("FAIL: solo.waitForActivity(EditNoteActivity.class, 1000)");
    }

    return;
  }

  /**
   * Opens note
   * Precondtions: Notebook view is TODO LIST
   * @throws Exception
   */
  public void loadEditNoteActivity/*open First Note In First Notebook*/() throws Exception
  {
    cancelCachedNoteIfOpenAndReturnToNotebookListActivity();
    /**
     * NotebookListActivity
     * - click on list item "No Category" to open notebook
     */
    if (!mSolo.waitForActivity(NotebookListActivity.class, 5000))
    {
      throw new Exception("FAIL: solo.waitForActivity(NotebookActivity.class)\n" +
              "  Current Activity="+mSolo.getCurrentActivity().getClass().toString());
    }
//    assertTrue(mSolo.waitForActivity(NotebookListActivity.class, 5000));
    mSolo.clickInList(0,0);


    /**
     * AbstractNoteListActivity
     * - open first Item
     */
    if (!mSolo.waitForActivity(TodoListActivity.class))
    {
      throw new Exception("FAIL: solo.waitForActivity(TodoListActivity.class)");
    }
    mSolo.clickInList(0);
    if (!mSolo.waitForActivity(EditNoteActivity.class))
    {
      throw new Exception("FAIL: solo.waitForActivity(EditNoteActivity.class)\n  Current Activity="+mSolo.getCurrentActivity().getClass().toString());
    }



    return;
  }


  /**
   * Cancels any note currently being edited in EditNoteActivity
   * Precondition: EditNoteActivity is loading/loaded
   */
  public void cancelCachedNoteIfOpenAndReturnToNotebookListActivity()
  {
    /**
     * If cached note still exists (eg. due to failed test) cancel it to return to NotebookListActivity
     */
    if (mSolo.waitForActivity(EditNoteActivity.class, DEFAULT_SLEEP))
    {
      mSolo.clickOnActionBarItem(R.id.cancel_option);
      mSolo.waitForDialogToOpen();
      mSolo.clickOnButton("Yes");
    }
    return;
  }


  /**
   * Precondition: NotebookActivity is loading/loaded
   */
  public void setNoCategoryNotebookAsTodoList()
  {
    assertTrue((mSolo.waitForActivity(NotebookListActivity.class, DEFAULT_SLEEP)));
//    if (mSolo.waitForActivity(NotebookListActivity.class, DEFAULT_SLEEP))
//    {
      mSolo.clickLongInList(0);
      mSolo.clickOnText("Edit");
      mSolo.waitForActivity(EditNotebookActivity.class);
      mSolo.clickOnView(mSolo.getView(R.id.notebook_view_type_spinner));
      mSolo.clickOnText(Notebook.ViewType.TODO.toString());
      mSolo.clickOnText("Ok");
      mSolo.waitForActivity(NotebookListActivity.class);
//    }
    return;
  }







}      ////END CLASS////




