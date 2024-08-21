package ndb;

import android.content.Context;
import android.test.AndroidTestCase;
import ndb.db.NDBTableMaster;
import ndb.db.NoteDataSource;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 06/04/13
 * Time: 9:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class Test_ImportDbHelper extends AndroidTestCase
{

  //NotebookDataSource notebookDs;
  private NoteDataSource noteDs;
  private Context context;


  @Override
  protected void setUp() throws Exception
  {
    super.setUp();    //To change body of overridden methods use File | Settings | File Templates.


    context= getContext();
    noteDs= new NoteDataSource(context, NDBTableMaster.DEBUG_NOTES_DB);
    noteDs.open();


    return;
  }

  @Override
  protected void tearDown() throws Exception
  {
    super.tearDown();    //To change body of overridden methods use File | Settings | File Templates.

    noteDs.close();
    context.deleteDatabase(NDBTableMaster.DEBUG_NOTES_DB);
    context.deleteDatabase(NDBTableMaster.DEBUG_TEMP_DB);

    return;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////TEST METHODS//////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////




  //todo on import test number of notes and notebooks are correct

  /**
   * 1) notesdb is empty before import
   * 2) notesdb is non-empty before import
   * 3) import is repeated multiple times for same data (notes should duplicate, but not notes)
   */
  public void testImport()
  {         //todo commented out because takes too long to run
//    String importDb= "/storage/emulated/0/_import/temp/notebase.db";
//
//    //1) assess intiaal condition of newly created db
//    Assert.assertEquals(0, noteDs.getNoteCount());
//    Assert.assertEquals(1, noteDs.getNotebookCount());
//
//
//    //2) Import db and make sure count is correct
//    ImportDbHelper.importAwesomeNoteDbTestMethod( getContext(), importDb,
//                                                  NDBTableHelper.DEBUG_TEMP_DB, NDBTableHelper.DEBUG_NOTES_DB);
//
//    Assert.assertEquals(521, noteDs.getNoteCount());
//    Assert.assertEquals(15+1, noteDs.getNotebookCount());
//
//
//    //3) Import again. notebooks should stay the same but notes should be doubled
//    ImportDbHelper.importAwesomeNoteDbTestMethod( getContext(), importDb,
//            NDBTableHelper.DEBUG_TEMP_DB, NDBTableHelper.DEBUG_NOTES_DB);
//
//    Assert.assertEquals(521*2, noteDs.getNoteCount());
//    Assert.assertEquals(15+1, noteDs.getNotebookCount());
//
//
//    return;
  }

}      ///////////////////END CLASS///////////////////
