package ndb.ui.activities;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import com.jayway.android.robotium.solo.Solo;
import kanana.notesdatabase.R;
import ndb.db.NDBTableMaster;
import ndb.types.NdbIntent;
import ndb.types.Notebook;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 24/10/13
 * Time: 8:17 PM
 * To change this template use File | Settings | File Templates.
 *
 * Test:
 * all different kinds of intents
 * eg. todo vs note
 *
 */
public class rm_EditNoteActivity extends ActivityInstrumentationTestCase2<EditNoteActivity>
{

  private Solo solo;
  private Intent mIntent;

  public rm_EditNoteActivity()
  {
    super("notesdatabase", EditNoteActivity.class);
  }


  public void setUp() throws Exception
  {
    super.setUp();

    return;

  }




//  public void testTrial()
//  {
//    Intent intent= new Intent();
//
//    intent.putExtra(NdbIntent.ACTION_TYPE, NdbIntent.CREATE);
//    intent.putExtra(Note.GUID_EXTRA, Note.TEMPORARY_NOTE_ID);
//    intent.putExtra(NdbIntent.VIEW_TYPE, NdbIntent.NOTE_VIEW);
//    intent.putExtra(NdbIntent.DATABASE_NAME, NDBTableMaster.DEBUG_NOTES_DB);
//    intent.putExtra(Notebook.GUID_EXTRA, 0L);
//
//
//
//
//
//    solo= new Solo(getInstrumentation(), getActivity(intent));
//
//    return;
//  }
//

//  public void test2()
//  {
//    solo= new Solo(getInstrumentation(), getActivity());
//  }


  /**
   * NdbIntent.VIEW_TYPE can't be taken from intent for some reason... so CANNOT TEST IT
   * It just gets assigned null in EditNoteActivity and then defaults to NOTE_VIEW
   */

  public void _TEMPORARILY_DISABLE_testLoadCreateNote()
  {
    Intent intent= new Intent();

    intent.putExtra(NdbIntent.ACTION_TYPE, NdbIntent.CREATE);
    //intent.putExtra(Note.GUID_EXTRA, Note.TEMPORARY_NOTE_ID);
    intent.putExtra(NdbIntent.VIEW_TYPE, Notebook.ViewType.NORMAL /*Notebook.ViewType.TODO*/);
    intent.putExtra(NdbIntent.DATABASE_NAME, NDBTableMaster.DEBUG_NOTES_DB);
    intent.putExtra(Notebook.GUID_EXTRA, 0L);    //This is "No Category" notebook
    //intent.putExtra("donk", "doiunieuw");


    //todo fails as notebookId never gets set...

    solo= new Solo(getInstrumentation(), getActivity(intent));
    assertEquals(View.GONE, solo.getView(R.id.todo_items_layout).getVisibility());


    return;
  }




//  public void testCahedNoteRetrieval()
//  {
//    EditText etTitle;
//
//    Intent intent= new Intent();
//
//    intent.putExtra(NdbIntent.ACTION_TYPE, NdbIntent.CREATE);
//    intent.putExtra(NdbIntent.VIEW_TYPE, Notebook.ViewType.NORMAL /*Notebook.ViewType.TODO*/);
//    intent.putExtra(NdbIntent.DATABASE_NAME, NDBTableMaster.DEBUG_NOTES_DB);
//    intent.putExtra(Notebook.GUID_EXTRA, 0L);    //This is "No Category" notebook
//
//
//
//    //todo fails as notebookId never gets set...
//
//    solo= new Solo(getInstrumentation(), getActivity(intent));
//    etTitle= (EditText)solo.getView(R.id.note_title);
//    etTitle.setText(sTitle);
//
//
//
//    return;
//  }
















  /**
   * Works!
   * @param intent
   * @return
   */
  public EditNoteActivity getActivity(Intent intent)
  {
    setActivityIntent(intent);
    return super.getActivity();    //To change body of overridden methods use File | Settings | File Templates.
  }
}     ////END CLASS////
