package ndb.types;

import junit.framework.Assert;
import ndb.ui.spans.ListItemSpan;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 26/07/13
 * Time: 12:11 PM
 * To change this template use File | Settings | File Templates.
 */

@RunWith(RobolectricTestRunner.class)
public class re_JsonMapList
{


  JsonAdapter mJsonAdapter;


  @Before
  public void setUp() throws Exception
  {
    mJsonAdapter = new JsonAdapter();

    return;
  }

  @After
  public void tearDown() throws Exception
  {
    mJsonAdapter = null;

    return;
  }

  @Test
  public void addAll()
  {
    List<SpanWrapper> spanWrappers,
                      spanWrappers2;

    SpanWrapper tempSpanWrapper;

    spanWrappers = new ArrayList();



    /**
     * Test 1: See that 2 ListItemSpans are added properly
     */

    //add first element - ListItemSpam
    tempSpanWrapper = new SpanWrapper();
    tempSpanWrapper.span = new ListItemSpan();
    tempSpanWrapper.start = 0;
    tempSpanWrapper.end = 2;
    tempSpanWrapper.flags = 7;
    spanWrappers.add(tempSpanWrapper);

    //add second element - ListItemSpam
    tempSpanWrapper = new SpanWrapper();
    tempSpanWrapper.span = new ListItemSpan();
    tempSpanWrapper.start = 5;
    tempSpanWrapper.end = 10;
    tempSpanWrapper.flags = 99;
    spanWrappers.add(tempSpanWrapper);

    //add spanWrappers
//    mJsonAdapter.addAll(spanWrappers);
//
//
//    Assert.assertEquals(2, mJsonAdapter.size());


    /**
     * Test 2: See that the 2 ListItemSpans from Test 1 are output and read back from Json properly
     */

    String json= JsonAdapter.toJson(spanWrappers);
    spanWrappers2= JsonAdapter.fromJson(json);


    Assert.assertEquals(2, spanWrappers.size());
    Assert.assertEquals(2, spanWrappers2.size());

    //check that the first spanWrapper comes out identical        todo make debugEquals() for
    Assert.assertEquals(spanWrappers.get(0).span.getClass(), spanWrappers2.get(0).span.getClass());
    Assert.assertEquals(spanWrappers.get(0).start, spanWrappers2.get(0).start);
    Assert.assertEquals(spanWrappers.get(0).end, spanWrappers2.get(0).end);
    Assert.assertEquals(spanWrappers.get(0).flags, spanWrappers2.get(0).flags);





    return;
  }


}       ////END CLASS////
