package ndb.ui;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 18/07/13
 * Time: 12:07 PM
 * To change this template use File | Settings | File Templates.
 */


/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 17/07/13
 * Time: 2:13 PM
 * To change this template use File | Settings | File Templates.
 */

import android.app.Activity;


//package notesdatabase.ui;

import android.content.Context;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.style.BulletSpan;
import android.view.KeyEvent;
import android.view.View;
import junit.framework.Assert;
import ndb.ui.activities.EditNoteActivity;
import ndb.ui.spans.ListItemSpan;
import ndb.ui.views.RichEditText;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 03/05/13
 * Time: 7:28 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(RobolectricTestRunner.class)
public class re_RichEditText
{

  private RichEditText mEt;
  private Context context;
  private Editable mContent;
  private SpannableStringBuilder mSsbText;


  @Before
  public void setUp() throws Exception
  {
    //super.setUp();    //To change body of overridden methods use File | Settings | File Templates.
    //context = new Activity();//context= getContext();          //todo :UNKNOWN CONSEQUENCES!!!!!!!!!!!!
    context=  Robolectric.buildActivity(Activity.class)
            .create()
            .get();



    mEt = new RichEditText(context);


    return;
  }

  @After
  public void tearDown() throws Exception
  {
    // super.tearDown();    //To change body of overridden methods use File | Settings | File Templates.

    return;
  }

  @Test
  public void testGetSpansInclusive()
  {
    List<ListItemSpan> listItemSpans;

    mEt.setText(new SpannableStringBuilder("012345\n789\nabc"));


    //Test 1) ListItemSpan is inserted at correct position

    mEt.setSelection(0);
    mEt.toggleBullet();                   //first instantion ListItemSpan seems to be causing this
    mEt.setSelection(8);
    mEt.toggleBullet();

    Editable text = mEt._DEPRECATED_getCleanedText();

    //Test: only 1 span @ (0, 5)
    Assert.assertEquals(1, mEt.getSpansInclusive(0, 6, ListItemSpan.class).size());

    //Test: only 1 span @ (7, 9)
    Assert.assertEquals(1, mEt.getSpansInclusive(7, 10, ListItemSpan.class).size());

    //Test: 0 span cases
    Assert.assertEquals(0, mEt.getSpansInclusive(0, 5, BulletSpan.class).size());
    Assert.assertEquals(0, mEt.getSpansInclusive(0, 4, ListItemSpan.class).size());
    Assert.assertEquals(0, mEt.getSpansInclusive(1, 5, ListItemSpan.class).size());
    Assert.assertEquals(0, mEt.getSpansInclusive(3, 9, ListItemSpan.class).size());
    Assert.assertEquals(0, mEt.getSpansInclusive(0, 9, ListItemSpan.class).size());

    //test case span at end of text


    return;
  }


  //3 cases:
  // a) (0,\n)
  // b) (\n, \n)
  // c) (\n, end)

  //Boundary cases: cursor at index 0 and last index
  //3 cases: (0,\n), (\n, \n), (\n, end)
  //Test 1) ListItemSpan is inserted at correct position
  @Test
  public void testInsertBullet()
  {
    List<ListItemSpan> listItemSpans;
    ListItemSpan[] listItemSpanArray;
    int count,
            cursor,
            cursor1,
            cursor2;

    int expected;


    mEt.setText(new SpannableStringBuilder("012345\n789\nabc"));


    //Test 1) ListItemSpan is inserted at correct position


    //insert bullet into MIDDLE of block. check that its there
    mEt.setText(new SpannableStringBuilder("012345\n789\nabc"));
    mEt.setSelection(3);
    mEt.toggleBullet();
    cursor1 = mEt.getSelectionStart();
    mEt.toggleBullet();
    mContent = mEt._DEPRECATED_getCleanedText();

    // insertBullet called twice on same block so should be no bullets
    listItemSpanArray = mContent.getSpans(1, mContent.length() - 1, ListItemSpan.class);
    Assert.assertEquals(0, listItemSpanArray.length);


    //test insert bullet twice at BEGINNING of block. Should be 0 ListItemSpans after
    mEt.setText(new SpannableStringBuilder("012345\n789\nabc"));
    cursor = 0;

    mEt.setSelection(cursor);
    mEt.toggleBullet();
    cursor1 = mEt.getSelectionStart();
    mEt.toggleBullet();
    cursor2 = mEt.getSelectionStart();
    mContent = mEt._DEPRECATED_getCleanedText();

    //Cursor position should not change
    Assert.assertEquals(cursor, cursor1);
    Assert.assertEquals(cursor, cursor2);

    // insertBullet called twice on same block so should be no bullets
    listItemSpanArray = mContent.getSpans(0, mContent.length() - 1, ListItemSpan.class);
    Assert.assertEquals(0, listItemSpanArray.length);


    //insert 2 bulletS into MIDDLE of block. Should be 0 ListItemSpans after
    mEt.setText(new SpannableStringBuilder("012345\n789\nabc"));
    cursor = 3;

    mEt.setSelection(cursor);
    mEt.toggleBullet();
    cursor1 = mEt.getSelectionStart();
    mEt.toggleBullet();
    cursor2 = mEt.getSelectionStart();
    mContent = mEt._DEPRECATED_getCleanedText();

    //Cursor position should not change
    Assert.assertEquals(cursor, cursor1);
    Assert.assertEquals(cursor, cursor2);

    // insertBullet called twice on same block so should be no bullets
    listItemSpanArray = mContent.getSpans(0, mContent.length() - 1, ListItemSpan.class);
    Assert.assertEquals(0, listItemSpanArray.length);


    //test insert bullet twice at END of block. Should be 0 ListItemSpans after
    mEt.setText(new SpannableStringBuilder("012345\n789\nabc"));
    cursor = 5;

    mEt.setSelection(cursor);
    mEt.toggleBullet();
    cursor1 = mEt.getSelectionStart();
    mEt.toggleBullet();
    cursor2 = mEt.getSelectionStart();
    mContent = mEt._DEPRECATED_getCleanedText();

    //Cursor position should not change
    Assert.assertEquals(cursor, cursor1);
    Assert.assertEquals(cursor, cursor2);

    // insertBullet called twice on same block so should be no bullets
    listItemSpanArray = mContent.getSpans(0, mContent.length() - 1, ListItemSpan.class);
    Assert.assertEquals(0, listItemSpanArray.length);


    //test insert bullet twice at length()-1 of block. Should be 0 ListItemSpans after
    mEt.setText(new SpannableStringBuilder("012345"));
    cursor = 5;

    mEt.setSelection(cursor);
    mEt.toggleBullet();
    cursor1 = mEt.getSelectionStart();
    mEt.toggleBullet();
    cursor2 = mEt.getSelectionStart();
    mContent = mEt._DEPRECATED_getCleanedText();

    //Cursor position should not change
    Assert.assertEquals(cursor, cursor1);
    Assert.assertEquals(cursor, cursor2);

    // insertBullet called twice on same block so should be no bullets
    listItemSpanArray = mContent.getSpans(0, mContent.length(), ListItemSpan.class);
    Assert.assertEquals(0, listItemSpanArray.length);


    //test insert bullet twice at  length() of block. Should be 0 ListItemSpans after
    mEt.setText(new SpannableStringBuilder("012345"));
    cursor = 6;

    mEt.setSelection(cursor);
    mEt.toggleBullet();
    mEt.toggleBullet();
    mContent = mEt._DEPRECATED_getCleanedText();


    // insertBullet called twice on same block so should be no bullets
    listItemSpanArray = mContent.getSpans(0, mContent.length(), ListItemSpan.class);
    Assert.assertEquals(0, listItemSpanArray.length);


    /////todo START HERE!!!!!!!!!!!!!!!!!!!!!!!!////////
    // INSERTbULLET() SEEMS TO BE WORKING. pROBLEM APPEARS TO BE WITH tEXTwATCHERlISTENER.  test that!!!!!


    return;
  }


  //todo is the cursor at the end of text at length() or length()-1 ?     LENGTH()-1!!!
  ////////////////////////////////////////////////////////////////////////////////////
  ////////TODO: text has a final empty charcter at length() which is where typed characters get inserted
  /////////////////////////////////////////////////////////////////////////////////////////////////////
  public void _TEMPORARILY_DISABLED_testOnKeyListener()
  {
    List<ListItemSpan> listItemSpans;
    ListItemSpan[] listItemSpanArray;
    int     count,
            cursor,
            cursor1,
            cursor2;

    int expected;


    View.OnKeyListener onKeyListener;

    //test: cursor can be 1 after input (ie final character at length() NOT length-1 )
    mEt.setText(new SpannableStringBuilder("012345"));
    mEt.setSelection(mEt.getText().length());
    cursor = mEt.getSelectionStart();


    Assert.assertEquals(mEt.length(), cursor);


    //test enter line. Add bullet then press enter. Should be a bullet on second line too (2 bullets in all)
    mEt.setText(new SpannableStringBuilder("012345"));
    mEt.setSelection(mEt.getText().length());
    mEt.toggleBullet();
    mContent = mEt._DEPRECATED_getCleanedText();
    listItemSpanArray = mContent.getSpans(0, mContent.length(), ListItemSpan.class);
    Assert.assertEquals(1, listItemSpanArray.length);

    onKeyListener = mEt.getOnKeyListener();    //simulating ENTER key press
    onKeyListener.onKey(mEt, KeyEvent.KEYCODE_ENTER, new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
    mContent = mEt._DEPRECATED_getCleanedText();
    listItemSpanArray = mContent.getSpans(0, mContent.length(), ListItemSpan.class);

    Assert.assertEquals(2, listItemSpanArray.length);


    //todo test when no \n


    return;
  }

  @Deprecated
  //@Test
  public void testByteStreams()
  {


    List<ListItemSpan> listItemSpans;
    Editable text;
    byte[] spansAsBytes;
    String string;


    mEt.setText(new SpannableStringBuilder("012345\n789\nabc"));


    //Test 1) ListItemSpan is inserted at correct position

    mEt.setSelection(0);
    mEt.toggleBullet();                   //first instantion ListItemSpan seems to be causing this
    mEt.setSelection(8);
    mEt.toggleBullet();


    mEt.clearComposingText();
    spansAsBytes = mEt._DEPRECATED_getByteArrayOfSpans();
    string = mEt._DEPRECATED_getCleanedText().toString();


    mEt._DEPRECATED_setText(string, spansAsBytes);


    //Test 1 - see the 2 spans saved to a byte[] and reloaded work exactly as expected

    //Test: only 1 span @ (0, 5)
    Assert.assertEquals(1, mEt.getSpansInclusive(0, 6, ListItemSpan.class).size());

    //Test: only 1 span @ (7, 9)
    Assert.assertEquals(1, mEt.getSpansInclusive(7, 10, ListItemSpan.class).size());

    //Test: 0 span cases
    Assert.assertEquals(0, mEt.getSpansInclusive(0, 5, BulletSpan.class).size());
    Assert.assertEquals(0, mEt.getSpansInclusive(0, 4, ListItemSpan.class).size());
    Assert.assertEquals(0, mEt.getSpansInclusive(1, 5, ListItemSpan.class).size());
    Assert.assertEquals(0, mEt.getSpansInclusive(3, 9, ListItemSpan.class).size());
    Assert.assertEquals(0, mEt.getSpansInclusive(0, 9, ListItemSpan.class).size());

    //test case span at end of text


    return;
  }


  /**
   * This method tests Json related functionality. It tests the methods:
   *
   * public void setText(String text, String spansAsJson)
   * public String getSpansAsJson()
   *
   */
  @Test
  public void test_Json()
  {


    List<ListItemSpan> listItemSpans;
    Editable text;
    String spansAsJson;
    String string;


    mEt.setText(new SpannableStringBuilder("012345\n789\nabc"));


    /**
     *    Test 1) Test that 2 ListItemSpans are regenerated correctly from Json
     *
     */


    mEt.setSelection(0);
    mEt.toggleBullet();                   //first instantion ListItemSpan seems to be causing this
    mEt.setSelection(8);
    mEt.toggleBullet();


    mEt.clearComposingText();
    spansAsJson = mEt.getSpansAsJson();                   //  <------
    string = mEt._DEPRECATED_getCleanedText().toString();


    mEt.setText(null);
    mEt.setText(string, spansAsJson);                     //  <------


    //Test 1 - see the 2 spans saved to a byte[] and reloaded work exactly as expected

    //Test: only 1 span @ (0, 5)
    Assert.assertEquals(1, mEt.getSpansInclusive(0, 6, ListItemSpan.class).size());

    //Test: only 1 span @ (7, 9)
    Assert.assertEquals(1, mEt.getSpansInclusive(7, 10, ListItemSpan.class).size());

    //Test: 0 span cases
    Assert.assertEquals(0, mEt.getSpansInclusive(0, 5, BulletSpan.class).size());
    Assert.assertEquals(0, mEt.getSpansInclusive(0, 4, ListItemSpan.class).size());
    Assert.assertEquals(0, mEt.getSpansInclusive(1, 5, ListItemSpan.class).size());
    Assert.assertEquals(0, mEt.getSpansInclusive(3, 9, ListItemSpan.class).size());
    Assert.assertEquals(0, mEt.getSpansInclusive(0, 9, ListItemSpan.class).size());

    //test case span at end of text


    /**
     *  Test 2) There are no spans
     */

    mEt.setText(new SpannableStringBuilder("012345\n789\nabc"));
    Assert.assertEquals("[]", mEt.getSpansAsJson());  //this is an empty JSON list





  }


//  @Test
  public void testInsertImage() //!!!!!!!! :)
  {

    return;
  }


}    ////////END CLASS///////







