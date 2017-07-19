package com.sandyr.demo.gettyimages.gallery.ui.activity;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.sandyr.demo.gettyimages.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class GalleryActivityTest {

    @Rule
    public ActivityTestRule<GalleryActivity> mActivityTestRule = new ActivityTestRule<>(GalleryActivity.class);

    @Test
    public void galleryActivityTest2() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.search), isDisplayed()));
        appCompatEditText.perform(replaceText("sandy"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.search), withText("sandy"), isDisplayed()));
        appCompatEditText2.perform(pressImeActionButton());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.images_list),
                        withParent(allOf(withId(R.id.root),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(1, click()));

    }

}
