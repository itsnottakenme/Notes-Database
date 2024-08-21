package ndb.types;

import android.content.Context;
import android.content.res.XmlResourceParser;
import junit.framework.Assert;
import kanana.notesdatabase.R;
import ndb.ui.activities.NotebookListActivity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 02/11/13
 * Time: 2:26 PM
 * To change this template use File | Settings | File Templates.
 */

@RunWith(RobolectricTestRunner.class)
public class re_Notebook
{
  Context mContext;

  public re_Notebook()
  {
    mContext= Robolectric.getShadowApplication().getApplicationContext();
  }

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
  public void test_loadNotebook() throws Exception
  {
    XmlResourceParser parser;

    List<Notebook> defaultNotebooks;

    /**
     * Put default notebooks in database
     */
    /////////////////////////////////////////////////
    parser= mContext.getResources().getXml(R.xml.default_notebooks);
    defaultNotebooks= Notebook.loadNotebooks(parser);

    Assert.assertEquals(2, defaultNotebooks.size());
    Assert.assertEquals(0, defaultNotebooks.get(0).getGuid());
    Assert.assertEquals(-10, defaultNotebooks.get(1).getGuid());
    return;
  }









}   ////END CLASS////




