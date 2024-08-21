package ndb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import ndb.db.MyShadowSQLiteDatabase;
import ndb.db.NDBTableMaster;
import ndb.db.NoteDataSource;
import ndb.types.NdbIntent;
import ndb.types.Note;
import ndb.types.Notebook;
import ndb.ui.activities.EditNoteActivity;
import ndb.ui.activities.EditNotebookActivity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 16/10/13
 * Time: 7:00 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(RobolectricTestRunner.class)
public class re_Experiments
{


  private Activity mActivity=  new EditNoteActivity();//new Activity(); //
  private NoteDataSource mDatasource;
  private Context mContext;

  //private NDBTableHelper;


  @Before
  public void setUp() throws Exception
  {
    //super.setUp();    //To change body of overridden methods use File | Settings | File Templates.


    mContext = Robolectric.getShadowApplication().getApplicationContext();
    mContext.getFilesDir();
    //TODO: MAYBE CAN COPY IN DB HERE SO db is kept
    mDatasource = new NoteDataSource(mContext, NDBTableMaster.DEBUG_NOTES_DB);

    mDatasource.open();


    return;
  }

  @After
  public void tearDown() throws Exception
  {
    //super.tearDown();    //To change body of overridden methods use File | Settings | File Templates.

    mDatasource.close();
    mContext.deleteDatabase(NDBTableMaster.DEBUG_NOTES_DB);

    return;
  }

  @Test
  public void test()
  {
    return;
  }


  /**
   * todo: for now probably easiest to just add a couple notes and notebooks to db and only test those cases
   */

  //@Test
 // @Config(shadows = {MyShadowSQLiteDatabase.class})         //replaces SQLiteDatabase with MyShadowSQLiteDatabase.class
  public void openAndLoadEditNoteActivity()
  {
    Intent editNoteActivityIntent = new Intent(mContext, EditNoteActivity.class);

    editNoteActivityIntent.putExtra(Note.GUID_EXTRA, Note.TEMPORARY_NOTE_ID);
    editNoteActivityIntent.putExtra(NdbIntent.ACTION_TYPE, NdbIntent.CREATE);
    editNoteActivityIntent.putExtra(NdbIntent.VIEW_TYPE, Notebook.ViewType.NORMAL);
    editNoteActivityIntent.putExtra(Notebook.GUID_EXTRA, 0);
    editNoteActivityIntent.putExtra(NdbIntent.DATABASE_NAME, NDBTableMaster.DEBUG_NOTES_DB);



    EditNoteActivity editNoteActivity=  Robolectric.buildActivity(EditNoteActivity.class)
            .withIntent(editNoteActivityIntent)
            .create()                                                 // calls onCreate()
            .get();




    return;
  }


  public void addInitialDatabaseRecords()
  {
    Note note;

    return;
  }





}   ////END CLASS////
