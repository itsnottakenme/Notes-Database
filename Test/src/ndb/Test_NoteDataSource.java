package ndb;

import android.content.Context;
import android.test.AndroidTestCase;
import junit.framework.Assert;
import ndb.db.NDBTableMaster;
import ndb.db.NoteDataSource;
import ndb.types.Note;
import ndb.types.Notebook;
import ndb.types.NotebookTagPair;
import ndb.types.Tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 27/03/13
 * Time: 3:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class Test_NoteDataSource extends AndroidTestCase
{
  private NoteDataSource mDataSource;
  private Context context;


  @Override
  protected void setUp() throws Exception
  {
    super.setUp();    //To change body of overridden methods use File | Settings | File Templates.
    context= getContext();
    mDataSource = new NoteDataSource(context, NDBTableMaster.DEBUG_NOTES_DB);
    mDataSource.open();

    return;
  }


  @Override
  protected void tearDown() throws Exception
  {
    super.tearDown();    //To change body of overridden methods use File | Settings | File Templates.

    mDataSource.close();
    context.deleteDatabase(NDBTableMaster.DEBUG_NOTES_DB);

    return;
  }


  ////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////TEST METHODS//////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////


  /**
   * This cannot be duplicated in Robolectric currently as default ShadowSQLiteDatabase does not support file writing
   * @throws Throwable
   */
  public void testDebugDbCreate() throws Throwable
  {
    List<String> fileList;
    fileList= Arrays.asList(context.databaseList());
    Assert.assertTrue( fileList.contains(NDBTableMaster.DEBUG_NOTES_DB) );     //assert DB file created

    return;
  }




  public void testisDbChangedBeforeEndOfTransaction()
  {
    Assert.assertTrue(mDataSource.isDbChangedBeforeEndOfTransaction());
    //RESULT:  uncommitted changes are applied to database before end of transaction   (they are unapplied if rollback() is called)
  }









}     ////END CLASS////