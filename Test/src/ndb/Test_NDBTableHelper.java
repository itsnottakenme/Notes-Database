package ndb;

import android.content.Context;
import android.test.AndroidTestCase;
import ndb.db.NDBTableMaster;
import ndb.db.NoteDataSource;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 07/04/13
 * Time: 9:51 AM
 * To change this template use File | Settings | File Templates.
 */
public class Test_NDBTableHelper extends AndroidTestCase
{

  //NotebookDataSource notebookDs;
  private NoteDataSource noteDs;
  private Context context;

  //private NDBTableHelper;


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

    return;
  }


  ////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////TEST METHODS//////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////



  //  public void testDeleteAllTables()
  //  {
  //
  //    noteDs.getAllTableNames();
  //
  //    NDBTableHelper.deleteAllTables(context, NDBTableHelper.DEBUG_DB);
  //    noteDs.getAllTableNames();
  //
  //
  //    return;
  //  }






}    ///////////////////////////END CLASS//////////////////////////
