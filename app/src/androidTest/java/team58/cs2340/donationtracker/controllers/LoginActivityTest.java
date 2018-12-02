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

/**
 * Tests functionality of the Login Activity
 * @author Saahil Yechuri
 */
public class LoginActivityTest {
    private final String validUsername = "testuser@test.app";
    private final String validPassword = "password";

    private final int TIMEOUT = 2000;

    @Rule
    public final ActivityTestRule<LoginActivity> mActivityRule
            = new ActivityTestRule<>(LoginActivity.class);

    /**
     * Runs before automation
     */
    @Before
    public void setUp() {
        Intents.init();
    }

    /**
     * Tests null blank username input
     */
    @Test
    public void blankUsername() {
        onView(withId(R.id.password))
                .perform(typeText(validPassword));
        onView(withId(R.id.login))
                .perform(click());
        onView(withId(R.id.email)).check(matches(hasErrorText(
                "Email is required!")));
    }

    /**
     * Tests null blank password input
     */
    @Test
    public void blankPassword() {
        onView(withId(R.id.email))
                .perform(typeText(validUsername));
        onView(withId(R.id.login))
                .perform(click());
        onView(withId(R.id.password)).check(matches(hasErrorText(
                "Password is required!")));
    }

    /**
     * Tests invalid username that is not in an email-address format
     */
    @Test
    public void invalidUsername() {
        String invalidUsername = "saahilyechuri";
        onView(withId(R.id.email))
                .perform(typeText(invalidUsername));
        onView(withId(R.id.password))
                .perform(typeText(validPassword));
        onView(withId(R.id.login))
                .perform(click());
        onView(withId(R.id.email)).check(matches(hasErrorText(
                "Please enter a valid email!")));
    }

    /**
     * Tests input of short password < 6 characters
     */
    @Test
    public void shortPassword() {
        onView(withId(R.id.email))
                .perform(typeText(validUsername));
        String shortPassword = "12345";
        onView(withId(R.id.password))
                .perform(typeText(shortPassword));
        onView(withId(R.id.login))
                .perform(click());
        onView(withId(R.id.password)).check(matches(hasErrorText(
                "Password must be at least 6 characters long!")));
    }

    /**
     * Tests unauthentic (fake) login credentials
     * @throws java.lang.InterruptedException
     */
    @Test
    public void unsuccessfulLogin() throws InterruptedException {
        String fakeUsername = "fake@unreal.scam";
        onView(withId(R.id.email))
                .perform(typeText(fakeUsername));
        String fakePassword = "drowssap";
        onView(withId(R.id.password))
                .perform(typeText(fakePassword));
        onView(withId(R.id.login))
                .perform(click());
        Thread.sleep(TIMEOUT);

        LoginActivity activity = mActivityRule.getActivity();
        String unsuccessfulToastMessage = "Login unsuccessful! :(";
        onView(withText(unsuccessfulToastMessage)).
                inRoot(withDecorView(not(is(activity.getWindow().getDecorView())))).
                check(matches(isDisplayed()));
    }

    /**
     * Tests authentic login credentials
     * @throws java.lang.InterruptedException
     */
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

    /**
     * Runs after automation
     */
    @After
    public void tearDown() {
        Intents.release();
    }
}