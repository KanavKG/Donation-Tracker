package team58.cs2340.donationtracker.controllers;

import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import team58.cs2340.donationtracker.R;


public class LoginActivityTest {
    private final String validUsername = "probator@test.app";
    private final String validPassword = "password";

    private final String invalidUsername = "saahilyechuri";
    private final String shortPassword = "12345";

    private final String fakeUsername = "fake@unreal.scam";
    private final String fakePassword = "drowssap";

    private final String unsuccessfulToastMessage = "Login unsuccessful! :(";

    private final int TIMEOUT = 2000;

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule
            = new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @Test
    public void blankUsername() {
        onView(withId(R.id.password))
                .perform(typeText(validPassword));
        onView(withId(R.id.login))
                .perform(click());
        onView(withId(R.id.email)).check(matches(hasErrorText("Email is required!")));
    }

    @Test
    public void blankPassword() {
        onView(withId(R.id.email))
                .perform(typeText(validUsername));
        onView(withId(R.id.login))
                .perform(click());
        onView(withId(R.id.password)).check(matches(hasErrorText("Password is required!")));
    }

    @Test
    public void invalidUsername() {
        onView(withId(R.id.email))
                .perform(typeText(invalidUsername));
        onView(withId(R.id.password))
                .perform(typeText(validPassword));
        onView(withId(R.id.login))
                .perform(click());
        onView(withId(R.id.email)).check(matches(hasErrorText("Please enter a valid email!")));
    }

    @Test
    public void shortPassword() {
        onView(withId(R.id.email))
                .perform(typeText(validUsername));
        onView(withId(R.id.password))
                .perform(typeText(shortPassword));
        onView(withId(R.id.login))
                .perform(click());
        onView(withId(R.id.password)).check(matches(hasErrorText("Password must be at least 6 characters long!")));
    }

    @Test
    public void unsuccessfulLogin() throws InterruptedException {
        onView(withId(R.id.email))
                .perform(typeText(fakeUsername));
        onView(withId(R.id.password))
                .perform(typeText(fakePassword));
        onView(withId(R.id.login))
                .perform(click());
        Thread.sleep(TIMEOUT);

        LoginActivity activity = mActivityRule.getActivity();
        onView(withText(unsuccessfulToastMessage)).
                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
                check(matches(isDisplayed()));
    }

    @Test
    public void successfulLogin() throws InterruptedException {
        onView(withId(R.id.email))
                .perform(typeText(validUsername));
        onView(withId(R.id.password))
                .perform(typeText(validPassword));
        onView(withId(R.id.login))
                .perform(click());
        Thread.sleep(TIMEOUT);
        intended(hasComponent(HomeScreenActivity.class.getName()));
    }

    @After
    public void tearDown() {
        Intents.release();
    }
}