package ndb.ui.activities;

import android.content.Context;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 06/08/13
 * Time: 5:32 PM
 * To change this template use File | Settings | File Templates.
 */

@RunWith(RobolectricTestRunner.class)
public class re_EditNoteActivity
{
  Context context;
  @Before
  public void setUp() throws Exception
  {
    EditNoteActivity editNoteActivity;

 //   editNoteActivity= new EditNoteActivity();
 //   editNoteActivity.onCreate(new Bundle());


    context= Robolectric.buildActivity(EditNoteActivity.class).get();


    return;
  }

  @After
  public void tearDown() throws Exception
  {



    return;
  }


  ////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////TEST METHODS//////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////

@Test
public void loadActivity()
{

  return;
}



  @Test
  public void aTest()                 //todo this won't work until I fix sqliteDatabase with Roboelectric
  {
//
//
//            Activity activity= new Activity();
//
//
//    Intent intent;
//    Note note;
//
//
//    //Toast.makeText(getApplicationContext(), "View note", Toast.LENGTH_SHORT).show();
//    intent = new Intent(getApplicationContext(), EditNoteActivity.class);
//
//    //get selected item from listView and pack into intent
//    note = (Note) mListView.getItemAtPosition(position);
//
//
//
//    //assert: nbGuid exists in note
//    intent.putExtra(Note.GUID, note.getGuid());
//    intent.putExtra(NdbIntent.TYPE, NdbIntent.EDIT_NOTE);
//    intent.putExtra(NdbIntent.DATABASE_NAME, mDbFilename); //todo does called activity use this?
//    startActivity(intent);  //todo: find out more about result code
//
//
//
//
//
//
//
//    activity.startActivity(new Intent());     //todo put intent in here
  }

}          ///END CLASS///
