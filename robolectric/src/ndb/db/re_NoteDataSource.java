package ndb.db;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import junit.framework.Assert;
import ndb.types.NotebookTagPair;
import ndb.types.Tag;
import ndb.types.Note;
import ndb.types.Notebook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 27/03/13
 * Time: 3:05 PM
 * To change this template use File | Settings | File Templates.
 *
 *
 *
 *
 *
 *
 * todo This is currently broken. I must find out how to use roboelectric with SqliteDatabase
 *
 *
 *
 *
 *
 *
 *
 */


@RunWith(RobolectricTestRunner.class)
//@Config(shadows = {MyShadowSQLiteDatabase.class})         //replaces SQLiteDatabase with MyShadowSQLiteDatabase.class
public class re_NoteDataSource
{
  private NoteDataSource mDataSource;
  private Context context;


  private Activity activity;


  @Before
  public void setUp() throws Exception
  {

    context= Robolectric.getShadowApplication().getApplicationContext();//Robolectric.buildActivity(EditNoteActivity.class).get();
    mDataSource = new NoteDataSource(context, NDBTableMaster.DEBUG_NOTES_DB);
    mDataSource.open();


    return;
  }


  @After
  public void tearDown() throws Exception
  {
    //super.tearDown();    //To change body of overridden methods use File | Settings | File Templates.

    mDataSource.close();
 //   context.deleteDatabase(NDBTableMaster.DEBUG_NOTES_DB);

    return;
  }


  ////////////////////////////////////////////////////////////////////////////////////////////////////
  //////////////////////////////////////TEST METHODS//////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   *
   * tests initialization of NoteDatasource
   * - default notebooks
   *
   */
  @Test
  public void test_initialization()
  {
    SQLiteDatabase database;
    Notebook notebook;
    List<Notebook> notebooks;
    Note note= new Note();

    note.setTitle("bob");

    //context= Robolectric.buildActivity(EditNoteActivity.class).get();

    mDataSource = new NoteDataSource(context, NDBTableMaster.DEBUG_NOTES_DB);
    mDataSource.open();
    database= mDataSource.getDatabase();


    notebooks=  mDataSource.getAllNotebooks();
    notebook= mDataSource.getNotebook(0);
    Assert.assertNotNull(notebooks);             //the expected row is returned from database
    //Assert.assertNotNull(notebook);             //the expected row is returned from database
    note.setNbGuid(notebook.getGuid());
    mDataSource.createNote(note);        // THIS CAUSES the failure I believe

    return;
  }




















  //todo what happens if note is created with nbid that doesn't match any notebooks?
  //create test function findNotesWithInvalidNbids() in NoteDataSource


  /**
   * //Test 1) notebooks found at right nbid
   * //Test 2) Is notebook name case sensitive? Yes.
   * //Test 3) note book names that do not exist (substring and completely different)
   */

  @Test
  public void test_GetNotebook()
  {

    Notebook notebook;

    create2Notebooks();
    //Test 1) notebooks found at right nbid
    notebook= mDataSource.getNotebook("Food");
    Assert.assertEquals(2, notebook.getGuid());


    notebook= mDataSource.getNotebook("People");
    Assert.assertEquals(1, notebook.getGuid());

    //Test 2) Is notebook name case sensitive? Yes.
    notebook= mDataSource.getNotebook("food");
    Assert.assertEquals(null, notebook);

    //Test 3) note book names that do not exist (substring and completely different)
    notebook= mDataSource.getNotebook("Foo");
    Assert.assertEquals(null, notebook);

    notebook= mDataSource.getNotebook("yummy");
    Assert.assertEquals(null, notebook);


    return;
  }



  /**
   * Tests:
   * 1) No notebooks in table (except "No Category")
   * 2) Multiple notebooks in table
   *
   */
  @Test
  public void test_GetLargestNotebookGuid()
  {
    //Test 1) No notebooks in table (except "No Category")
    Assert.assertEquals(0, mDataSource.getLargestNotebookGuid());

    //Test 2) Multiple notebooks in table
    create2Notebooks();
    Assert.assertEquals(2, mDataSource.getLargestNotebookGuid()); // _id's start with 1 (not zero)


    return;
  }



  /**
   * Tests:
   * 1) No notes in table
   * 2) Multiple notes in table
   *
   */
  @Test
  public void test_GetLargestNoteGuid()
  {


    long largestGuid;

    Note    note1,
            note2,
            note3,
            note4;


    //Test 1) No notes in table
    Assert.assertEquals(0, mDataSource.getLargestNoteGuid());

    //Test 2) Multiple notes in table
    note1= new Note();
    note1.setTitle("Bob");
    note1.setTags("fat");
    note1.setNbGuid(1);
    mDataSource.createNote(note1);

    note2= new Note();
    note2.setTitle("Bill");
    note2.setTags("old");
    note2.setNbGuid(1);
    mDataSource.createNote(note2);

    note3= new Note();
    note3.setTitle("Bill");
    note3.setNbGuid(1);
    mDataSource.createNote(note3);

    //add a note to 0th notebook
    note4= new Note();
    note4.setTitle("Fabio");
    note4.setTags("fat");
    note4.setNbGuid(0);
    note4= mDataSource.createNote(note4);



    Assert.assertEquals(4, mDataSource.getLargestNoteGuid()); // _id's start with 1 (not zero)

    return;
  }



  /**
   * When notebooks are deleted checks:
   * 0)Notebook is deleted
   * 1)associated notes have their nb_id reset to zero
   * 2)notetagpairs for the notebook are all deleted
   * 3) notetagpairs for 0 are correct
   *
   * nb 0 before: fat
   * nb 0 after:  fat old
   */
  @Test
  public void test_DeleteNotebook()
  {
    Note  note0,
            note1,
            note2,
            note3;
    List<String> tagList,
            expectedTagList;

    create2Notebooks();

    //add a note to 0th notebook
    note0= new Note();
    note0.setTitle("Fabio");
    note0.setTags("fat");
    note0.setNbGuid(0);
    note0= mDataSource.createNote(note0);


    //add notes to first notebook
    note1= new Note();
    note1.setTitle("Bob");
    note1.setTags("fat");
    note1.setNbGuid(1);
    note1= mDataSource.createNote(note1);

    note2= new Note();
    note2.setTitle("Bill");
    note2.setTags("old");
    note2.setNbGuid(1);
    note2= mDataSource.createNote(note2);

    note3= new Note();
    note3.setTitle("Bill");
    note3.setNbGuid(1);
    note3= mDataSource.createNote(note3);


    //delete notebook 1
    mDataSource.deleteNotebook(1);

    // 0)see that notebook was deleted
    Assert.assertTrue( mDataSource.getNotebook(1) == null );

    // 1)see that note nbid's have become zero
    Assert.assertTrue( mDataSource.getNote(note1.getGuid()).getNbGuid() == 0 );
    Assert.assertTrue( mDataSource.getNote(note2.getGuid()).getNbGuid() == 0 );
    Assert.assertTrue( mDataSource.getNote(note3.getGuid()).getNbGuid() == 0 );

    // 2)check notetagpairs deleted from old notebook
    Assert.assertTrue( mDataSource.getTagsFromNotebook(1).size() == 0 );

    // 3)check notebooktagpairs of 0 are correct
    tagList= mDataSource.getTagsFromNotebook(0);


    expectedTagList= Tag.stringToList("fat   old");
    Assert.assertEquals(tagList.size(), 2);

    for (int i=0; i<expectedTagList.size();i++)
    {
      Assert.assertTrue( tagList.contains(expectedTagList.get(i)) );
    }                           //todo result implies that new notebooktagpairs not created correctly for 0


    return;
  }



  /**
   * Create 2 note books that share the same tags as well as have different ones then test to make
   * sure all tags in each notebook returned properly
   *
   * tags:  fat   old   tall  long  big   cute
   * ------------------------------------------
   * nb1:   fat   old   tall  long
   * nb2:   fat   old   tall        big   cute
   */
  @Test
  public void test_GetTagsFromNotebook()
  {

    List<String>  nb1Tags= new ArrayList(),
            nb2Tags= new ArrayList(),
            allTags= new ArrayList(),

            nb1TagsExpected= new ArrayList(),
            nb2TagsExpected= new ArrayList(),
            allTagsExpected= new ArrayList();


    Note note;

    create2Notebooks();

    //add notes to first notebook
    note= new Note();
    note.setTitle("Bob");
    note.setContent("Likes to eat");
    note.setTags("fat");
    note.setNbGuid(1);
    mDataSource.createNote(note);

    note= new Note();
    note.setTitle("Bill");
    note.setContent("Likes to eat");
    note.setTags("old");
    note.setNbGuid(1);
    mDataSource.createNote(note);

    note= new Note();
    note.setTitle("Bill");
    note.setContent("Likes to eat");
    note.setTags("tall long");
    note.setNbGuid(1);
    mDataSource.createNote(note);


    //add notes to second notebook
    note= new Note();
    note.setTitle("Sarah");
    note.setContent("Likes to eat");
    note.setTags("old fat");
    note.setNbGuid(2);
    mDataSource.createNote(note);

    note= new Note();
    note.setTitle("Stu");
    note.setContent("Likes to eat");
    note.setTags("big tall");
    note.setNbGuid(2);
    mDataSource.createNote(note);

    note= new Note();
    note.setTitle("Sue");
    note.setContent("Likes to eat");
    note.setTags("cute");
    note.setNbGuid(2);
    mDataSource.createNote(note);


    //check that each notebook has the right tags and correct number

    /////
    //nb1
    nb1Tags= mDataSource.getTagsFromNotebook(1);
    nb1TagsExpected= Tag.stringToList("fat   old   tall  long");
    Assert.assertEquals(nb1Tags.size(), 4);

    for (int i=0; i<nb1TagsExpected.size();i++)
    {
      Assert.assertTrue( nb1Tags.contains(nb1TagsExpected.get(i)) );
    }

    //////
    //nb2
    nb2Tags= mDataSource.getTagsFromNotebook(2);
    nb2TagsExpected= Tag.stringToList("fat   old   tall big   cute");
    Assert.assertEquals(nb2Tags.size(), 5);

    for (int i=0; i<nb2TagsExpected.size();i++)
    {
      Assert.assertTrue( nb2Tags.contains(nb2TagsExpected.get(i)) );
    }

    ////////////////
    //All notebooks
    allTags= mDataSource.getAllTags();
    allTagsExpected= Tag.stringToList("fat   old   tall  long  big   cute");
    Assert.assertEquals(allTags.size(), 6);

    for (int i=0; i<allTagsExpected.size();i++)
    {
      Assert.assertTrue( allTags.contains(allTagsExpected.get(i)) );
    }




    return;
  }


  /**
   * Test Cases
   * 1)Search notebookId ONLY
   * 2) tag only
   * 3) searchString only
   * 4) search by notebookId and tag
   * 5) searchString and noteBookId
   * 6) searchString and tag
   * 7) searchString, notebookId and tag
   *
   *
   */
  @Test
  public void test_GetNotes3Args()
  {
    List<Note> noteList;
    Note note;


    create2Notebooks();




    Note  note0,
            note1,
            note2,
            note3;
    List<String> tagList,
            expectedTagList;

    create2Notebooks();

    //add a note to 1th notebook
    note0= new Note();
    note0.setTitle("Fabio");
    note0.setTags("fat");
    note0.setContent("my but is big and sexy you knob!");
    note0.setNbGuid(1);
    note0= mDataSource.createNote(note0);


    //add notes to 2nd notebook
    note1= new Note();
    note1.setTitle("Bob");
    note1.setTags("tall fat");
    note1.setNbGuid(2);
    note1= mDataSource.createNote(note1);

    note2= new Note();
    note2.setTitle("Bill");
    note2.setTags("old fat grey");
    note2.setNbGuid(2);
    note2= mDataSource.createNote(note2);

    note3= new Note();
    note3.setTitle("Button");
    note0.setContent("corn on the cob people!");
    note3.setNbGuid(2);
    note3= mDataSource.createNote(note3);


    //Test 1) search by notebookId
    noteList= mDataSource.getNotes(null, 1, null, null, null);
    Assert.assertTrue(noteList.size() == 1);

    noteList= mDataSource.getNotes(null, 2, null, null, null);
    Assert.assertTrue(noteList.size() == 3);

    //Test 2) search by tag
    noteList= mDataSource.getNotes(null, -1, "fat", null, null);
    Assert.assertTrue(noteList.size() == 3);

    //Test 3) searchString only
    noteList= mDataSource.getNotes("but", -1, null, null, null);
    Assert.assertTrue(noteList.size() == 2);

    //Test 4) search by notebookId and tag
    noteList= mDataSource.getNotes(null, 2, "fat", null, null);
    Assert.assertTrue(noteList.size() == 2);

    //Test 5) searchString and noteBookId
    noteList= mDataSource.getNotes("but", 2, null, null, null);
    Assert.assertTrue(noteList.size() == 1);

    //Test 6) searchString and tag
    noteList= mDataSource.getNotes("but", -1, "fat", null, null);
    Assert.assertTrue(noteList.size() == 1);

    //Test 7) searchString, notebookId and tag
    noteList= mDataSource.getNotes("ob", 2, "fat", null, null);
    Assert.assertTrue(noteList.size() == 1);

    //Test 8) an empty return value
    noteList= mDataSource.getNotes("peopl", 1, "fat", null, null);
    Assert.assertEquals(0, noteList.size());


    return;
  }



  //@Test
  //todo: the default ShadowSQLiteDatabase does not write the db to disk. It only keeps it in memory
  // until I create a proper shadow, this method will always fail
  public void test_DebugDbCreate() throws Throwable
  {
    List<String> fileList;
    fileList= Arrays.asList(context.databaseList());
    Assert.assertTrue( fileList.contains(NDBTableMaster.DEBUG_NOTES_DB) );     //assert DB file created

    return;
  }




  @Test
  public void test_UpdateNote()
  {
    //todo make sure that notetagpairs change propoerly when tags added or deleted from a note
    Note note;
    NotebookTagPair nbtpFat,
            nbtpOld;

    this.create2Notebooks();
    note = new Note();


    note.setTitle("Bob");
    note.setContent("Likes to eat");
    note.setTags("fat");
    note.setNbGuid(1);

    note= mDataSource.createNote(note);

    //add a new tag
    note.setTags(note.getTags() + " old");
    mDataSource.updateNote(note, true);

    nbtpFat= new NotebookTagPair();
    nbtpOld= new NotebookTagPair();

    ////////////check that old tag and updated tag exist/////////
    nbtpFat.setNotebookId(1);
    nbtpFat.setTag("fat");
    Assert.assertTrue(mDataSource.notebookTagPairExists(nbtpFat));    // check that notetagpairs were created

    nbtpOld.setNotebookId(1);
    nbtpOld.setTag("old");
    Assert.assertTrue(mDataSource.notebookTagPairExists(nbtpOld));    // check that notetagpairs were created
    /////////////////////////////////////////////////////////////////////////

    //delete tag
    note.setTags("old"); //ie "fat" tag deleted
    mDataSource.updateNote(note, true);

    //after deleting tag was updated notes noteTagPairs correctly updated?
    Assert.assertFalse(mDataSource.notebookTagPairExists(nbtpFat));
    Assert.assertTrue(mDataSource.notebookTagPairExists(nbtpOld));



    return;
  }

  /**
   * Ensure tag pairs are moved properly
   */
  @Test
  public void test_ChangeNotebooks()
  {
    Note note;
    NotebookTagPair nbtpFat;


    this.create2Notebooks();
    note = new Note();


    note.setTitle("Bob");
    note.setContent("Likes to eat");
    note.setTags("fat");
    note.setNbGuid(1);

    note= mDataSource.createNote(note);

    /**
     * See that tag in notebook gets updated when note is saved to different notebook
     */
    Assert.assertEquals(1, mDataSource.getTagsFromNotebook(1).size());
    Assert.assertEquals(0, mDataSource.getTagsFromNotebook(2).size());

    note.setNbGuid(2);
    mDataSource.updateNote(note, true);

    Assert.assertEquals(0, mDataSource.getTagsFromNotebook(1).size());
    Assert.assertEquals(1, mDataSource.getTagsFromNotebook(2).size());

    return;
  }


  /**
   * TODO: ADD CASES FOR TODO LISTS AND GROCERY LISTS!!!!!!!!!!!!!!!! OR MAYBE JUST EDIT ACTIVITY TO DISALLOW THESE VIEWS????
   *
   */

  @Test
  public void test_ALL_NOTES_Notebook()
  {
    int n;
    create2Notebooks();

    Note note;
    List<Note> notesInput= new ArrayList();
    List<Note> noteList;



    for (int i= 0; i<5; i++)
    {
      note= new Note();
      note.setTitle(""+i);
      notesInput.add(note);
    }

    /**
     * Put first 3 notes in notebook 1 and next 2 notes in notebook 2
     */
    for (int i= 0; i<3; i++)
    {
      notesInput.get(i).setNbGuid(1);
      mDataSource.createNote(notesInput.get(i));
    }
    for (int i= 3; i<5; i++)
    {
      notesInput.get(i).setNbGuid(2);
      mDataSource.createNote(notesInput.get(i));
    }


    noteList= mDataSource.getNotes(null, Notebook.ALL_NOTES_ID, null, null, null);
    Assert.assertEquals(5, noteList.size());
    noteList= mDataSource.getNotesFromNotebook(Notebook.ALL_NOTES_ID);
    Assert.assertEquals(0, noteList.size());       //todo: is this what I really want?? Should i be returning all notes instead?


    return;
  }


  @Test
  public void test_UpdateNotebook()
  {
    //todo make sure that notetagpairs change propoerly when tags added or deleted from a note
    Notebook notebook,
            updatedNotebook;

    notebook= new Notebook();
    notebook.setTitle("Notebook");
    notebook.setHeaderColor(10);


    /**
     * Test: changes to notebook are update correctly (title and headerColor)
     */
    notebook= mDataSource.createNotebook(notebook);

    // Keep the GUID the same so it updates same record
    notebook.setTitle("updatedNotebook");
    notebook.setHeaderColor(11);

    updatedNotebook= mDataSource.updateNotebook(notebook);


    Assert.assertEquals("updatedNotebook", updatedNotebook.getTitle());
    Assert.assertEquals(11, updatedNotebook.getHeaderColor());

    return;
  }




  @Test
  public void test_CreateAndDeleteSingleNoteWithMultipleTags()
  {
    Note  originalNote,
            createdNote,
            queriedNote;
    NotebookTagPair nbtp;


    this.create2Notebooks();
    originalNote = new Note();

    originalNote.setTitle("Bob");
    originalNote.setContent("Likes to eat");
    originalNote.setTags("fat old skinny");
    originalNote.setNbGuid(1);

    createdNote = mDataSource.createNote(originalNote);

    queriedNote= mDataSource.getNote(createdNote.getGuid());

    Assert.assertTrue( createdNote.equals(queriedNote) );   //assert note was created


    //now test if corresponding notetagpair was created
    nbtp= new NotebookTagPair();

    nbtp.setNotebookId(1);
    nbtp.setTag("fat");
    Assert.assertTrue(mDataSource.notebookTagPairExists(nbtp));    // check that notetagpairs were created

    nbtp.setNotebookId(1);
    nbtp.setTag("old");
    Assert.assertTrue(mDataSource.notebookTagPairExists(nbtp));    // check that notetagpairs were created


    nbtp.setNotebookId(1);
    nbtp.setTag("skinny");
    Assert.assertTrue(mDataSource.notebookTagPairExists(nbtp));    // check that notetagpairs were created

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////Now delete note//////////////////////////////////////////////////

    Assert.assertTrue(true == mDataSource.deleteNote(createdNote));

    nbtp.setNotebookId(1);
    nbtp.setTag("fat");
    Assert.assertFalse(mDataSource.notebookTagPairExists(nbtp));    // check that notetagpairs were created

    nbtp.setNotebookId(1);
    nbtp.setTag("old");
    Assert.assertFalse(mDataSource.notebookTagPairExists(nbtp));    // check that notetagpairs were created


    nbtp.setNotebookId(1);
    nbtp.setTag("skinny");
    Assert.assertFalse(mDataSource.notebookTagPairExists(nbtp));    // check that notetagpairs were created






    return;
  }




  /**
   *
   * for testing  public Notebook createNotebook(Notebook notebook)
   */
  @Test
  public void test_CreateNotebook2()
  {


    Notebook notebook;

    notebook= new Notebook();
    notebook.setTitle("fruit");
    notebook.setNoteSort("sort");
    notebook.setViewType(Notebook.ViewType.NORMAL);

    notebook.setGuid(100);
    notebook.setDateCreated(5);
    notebook.setDateModified(6);
    notebook.setOrdinal(9);

    notebook= mDataSource.createNotebook(notebook);


    Assert.assertEquals("fruit", notebook.getTitle());
    Assert.assertEquals("sort", notebook.getNoteSort());
    Assert.assertEquals(Notebook.ViewType.NORMAL, notebook.getViewType());

    Assert.assertEquals(100,notebook.getGuid());
    Assert.assertEquals(5,notebook.getDateCreated());
    Assert.assertEquals(6, notebook.getDateModified());
    Assert.assertEquals(9, notebook.getOrdinal());

    notebook= new Notebook();

    notebook.setTitle("food");
    notebook= mDataSource.createNotebook(notebook);

    Assert.assertEquals("food", notebook.getTitle() );
    Assert.assertEquals(101, notebook.getGuid());  //101 since guid is max(guid)+1









    return;
  }

  @Test
  public void test_DeleteLast2NotesWithSameTag()    //tests notetagpairs delete
  {
    Note note1,
            note2;

    NotebookTagPair nbtp;


    this.create2Notebooks();

    note1 = new Note();
    note1.setTitle("Bob");
    note1.setContent("Likes to eat");
    note1.setTags("fat");
    note1.setNbGuid(1);

    note2 = new Note();
    note2.setTitle("Joe");
    note2.setContent("Likes to joke");
    note2.setTags("fat");
    note2.setNbGuid(1);

    note1= mDataSource.createNote(note1);
    note2= mDataSource.createNote(note2);

    nbtp = new NotebookTagPair();
    nbtp.setNotebookId(1);
    nbtp.setTag("fat");
    Assert.assertTrue(mDataSource.notebookTagPairExists(nbtp));    // check that notetagpairs were created

    //delete first note with fat tag

    Assert.assertTrue(mDataSource.deleteNote(note1));
    Assert.assertTrue(mDataSource.notebookTagPairExists(nbtp));             //THIS FAILS!!!!!!!!
    //dletes tag because can't find the other instance of it
    Assert.assertTrue(mDataSource.deleteNote(note2));
    Assert.assertTrue(false == mDataSource.notebookTagPairExists(nbtp));



    return;
  }





  ///////////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////NON-TEST METHODS///////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////

  public void create2Notebooks()
  {
    mDataSource.open();
    mDataSource.createNotebook("People");
    mDataSource.createNotebook("Food");

    return;
  }


} //////////////END CLASS//////////////
