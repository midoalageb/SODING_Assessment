package android.soding.com.sodingapp;

import android.soding.com.sodingapp.Activities.MainActivity;
import android.soding.com.sodingapp.Helpers.Utils;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.action.EditorAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withHint;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {
    public static final String TEST_NAME = "NAME";
    public static final String TEST_NAME_2 = "NAME2";
    public static final String TEST_DESCRIPTION = "DESCRIPTION";
    public static final String TEST_DESCRIPTION_2 = "DESCRIPTION2";
    public static final String TEST_DATE_CREATED = "1980-01-25 23:00:00";
    public static final String TEST_DATE_CREATED_2 = "1981-01-25 23:00:00";
    public static final String TEST_DATE_UPDATED = "1980-01-26 23:00:00";
    public static final String TEST_DATE_UPDATED_2 = "1981-01-26 23:00:00";

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    /**
     * Gets String from TextView matching matcher
     * @param matcher Matcher to get view
     * @return String of the TextView
     */
    private String getText(final Matcher<View> matcher) {
        final String[] stringHolder = { null };
        onView(matcher).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(TextView.class);
            }

            @Override
            public String getDescription() {
                return "getting text from a TextView";
            }

            @Override
            public void perform(UiController uiController, View view) {
                TextView tv = (TextView)view; //Save, because of check in getConstraints()
                stringHolder[0] = tv.getText().toString();
            }
        });
        return stringHolder[0];
    }

    /**
     * Class to get the number of rows in a RecyclerView
     */
    public class RecyclerViewItemCountAssertion implements ViewAssertion {
        private final int expectedCount;

        public RecyclerViewItemCountAssertion(int expectedCount) {
            this.expectedCount = expectedCount;
        }

        @Override
        public void check(View view, NoMatchingViewException noViewFoundException) {
            if (noViewFoundException != null) {
                throw noViewFoundException;
            }

            RecyclerView recyclerView = (RecyclerView) view;
            RecyclerView.Adapter adapter = recyclerView.getAdapter();
            assertThat(adapter.getItemCount(), is(expectedCount));
        }
    }

    /**
     * Test UI for Add, list, update and delete Tasks
     * @throws Exception
     */
    @Test
    public void testTaskUI() throws Exception {
        onView(withId(R.id.Fab_main))
                .perform(ViewActions.click());
        check_dialog_visible();

        //Test save new Task
        Calendar calendar = Calendar.getInstance();

        onView(withId(R.id.ET_Dialog_Add_update_name)).perform(ViewActions.typeText(TEST_NAME));
        onView(withId(R.id.ET_Dialog_Add_update_description)).perform(ViewActions.typeText(TEST_DESCRIPTION));
        onView(withId(R.id.Btn_Dialog_Add_update_save)).perform(ViewActions.click());

        onView(withId(R.id.RV_main_contents)).perform(RecyclerViewActions.scrollToPosition(30));
        onView(withText(TEST_NAME)).check(matches(isDisplayed()));
        onView(withText(TEST_DESCRIPTION)).check(matches(isDisplayed()));
        String created = getText(withId(R.id.TV_item_created));
        String updated = getText(withId(R.id.TV_item_updated));

        Calendar test_Calendar = Calendar.getInstance();
        test_Calendar.setTime(Utils.get_Date_from_String(created));

        assertTrue(test_Calendar.getTimeInMillis() - calendar.getTimeInMillis() < 5000);

        test_Calendar.setTime(Utils.get_Date_from_String(updated));
        assertTrue(test_Calendar.getTimeInMillis() - calendar.getTimeInMillis() < 5000);

        onView(withId(R.id.RV_main_contents)).check(new RecyclerViewItemCountAssertion(1));

        //Test update
        onView(withId(R.id.IB_item_update)).perform(ViewActions.click());
        check_dialog_visible();

        Calendar calendar2 = Calendar.getInstance();

        onView(withId(R.id.ET_Dialog_Add_update_name)).perform(ViewActions.clearText());
        onView(withId(R.id.ET_Dialog_Add_update_name)).perform(ViewActions.typeText(TEST_NAME_2));
        onView(withId(R.id.ET_Dialog_Add_update_description)).perform(ViewActions.clearText());
        onView(withId(R.id.ET_Dialog_Add_update_description)).perform(ViewActions.typeText(TEST_DESCRIPTION_2));
        onView(withId(R.id.Btn_Dialog_Add_update_save)).perform(ViewActions.click());

        onView(withId(R.id.RV_main_contents)).check(new RecyclerViewItemCountAssertion(1));

        onView(withId(R.id.RV_main_contents)).perform(RecyclerViewActions.scrollToPosition(30));
        onView(withText(TEST_NAME_2)).check(matches(isDisplayed()));
        onView(withText(TEST_DESCRIPTION_2)).check(matches(isDisplayed()));

        updated = getText(withId(R.id.TV_item_updated));

        test_Calendar.setTime(Utils.get_Date_from_String(created));

        assertTrue(test_Calendar.getTimeInMillis() - calendar.getTimeInMillis() < 5000);

        test_Calendar.setTime(Utils.get_Date_from_String(updated));
        assertTrue(test_Calendar.getTimeInMillis() - calendar2.getTimeInMillis() < 5000);
        assertFalse(updated.equals(created));

        //Test delete
        onView(withId(R.id.IB_item_delete)).perform(ViewActions.click());
        onView(withId(R.id.RV_main_contents)).check(new RecyclerViewItemCountAssertion(0));
    }

    private void check_dialog_visible(){
        onView(withId(R.id.ET_Dialog_Add_update_name)).check(matches(isDisplayed()));
        onView(withId(R.id.ET_Dialog_Add_update_description)).check(matches(isDisplayed()));
        onView(withId(R.id.Btn_Dialog_Add_update_save)).check(matches(isDisplayed()));
    }


}
