package ndb.types;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 26/10/13
 * Time: 3:32 PM
 * To change this template use File | Settings | File Templates.
 */


@RunWith(RobolectricTestRunner.class)
public class re_Note
{
  @Before
  public void setUp() throws Exception
  {


    return;
  }


  @After
  public void tearDown() throws Exception
  {


    return;
  }



  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////////Begin tests//////////////////////////////////////////////////////////////////////////////////
  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  @Test
  public void testEquals()
  {
    Note note1,
            note2;

    note1= new Note();
    note2= new Note();

    Assert.assertEquals(note1, note2);

  }


  @Test
  public void test_getTagsAsList()
  {
    Note note= new Note();
    List<String> tagList;

    note.setTags("  bob i   ate  ham ");

    tagList= note.getTagsAsList();

    Assert.assertEquals(4, tagList.size());
    Assert.assertTrue(tagList.contains("bob"));
    Assert.assertTrue(tagList.contains("i"));
    Assert.assertTrue(tagList.contains("ate"));
    Assert.assertTrue(tagList.contains("ham"));

   return;
  }








}   ////END CLASS////





