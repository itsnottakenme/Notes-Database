package ndb.util;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 14/09/13
 * Time: 5:00 PM
 * To change this template use File | Settings | File Templates.
 */

@RunWith(RobolectricTestRunner.class)
public class re_DateUtil
{

  @Test
  public void testGetNext() throws Exception
  {
     Calendar cDate= Calendar.getInstance();

    Date dateBefore,
         dateAfter,
         dateExpectedMonday;
    SimpleDateFormat dateFormat= new SimpleDateFormat();
    //Note.RepeatingDate repeatingDueDate= new Note.RepeatingDate();               //todo why did I have to make it a static inner class?
    int     field,
            value;
    field= Calendar.DAY_OF_WEEK;
    value= Calendar.MONDAY;

    //date= new Date


    /**
     * Test Monday gets advanced to following Monday
     */
    cDate.set(2013, Calendar.SEPTEMBER, 23);         //Monday, September 23, 2013
    dateExpectedMonday= cDate.getTime();

    cDate.set(2013, Calendar.SEPTEMBER, 16);         //Monday, September 16 2013
    dateBefore= cDate.getTime();

    dateAfter= new Date( DateUtil.getNext(dateBefore.getTime(), field, value) );
    Assert.assertEquals(dateExpectedMonday, dateAfter);

    /**
     * Test Tuesday gets advanced to following Monday
     */
    cDate.set(2013, Calendar.SEPTEMBER, 17);         //Tuesday, September 17 2013
    dateBefore= cDate.getTime();

    dateAfter= new Date( DateUtil.getNext(dateBefore.getTime(), field, value) );
    Assert.assertEquals(dateExpectedMonday, dateAfter);




    return;
  }

}
