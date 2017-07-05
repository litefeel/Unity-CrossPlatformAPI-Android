package com.litefeel.crossplatformapi.testapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;

import com.litefeel.crossplatformapi.android.AndroidPlatform;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private static final String TAG = ExampleInstrumentedTest.class.getSimpleName();
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    private UiDevice mDevice;

    @Before
    public void startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());


//        // Start from the home screen
//        mDevice.pressHome();
//
//        // Wait for launcher
//        final String launcherPackage = mDevice.getLauncherPackageName();
//        assertThat(launcherPackage, notNullValue());
//        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)),
//                LAUNCH_TIMEOUT);
//
//        // Launch the app
//        Context context = InstrumentationRegistry.getContext();
//        final Intent intent = context.getPackageManager()
//                .getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE);
//        // Clear out any previous instances
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        context.startActivity(intent);
//
//        // Wait for the app to appear
//        mDevice.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)),
//                LAUNCH_TIMEOUT);
    }

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Log.d(TAG, "useAppContext");
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.litefeel.crossplatformapi.testapp", appContext.getPackageName());
    }

    @Test
    public void copyAndPaset() throws Exception {
        String pasteStr = "this is paste text!";
        AndroidPlatform.pasteToClipboard(pasteStr);
        String copyedStr = AndroidPlatform.copyFromClipboard();
        assertEquals(pasteStr, copyedStr);
    }

    @Test
    public void saveToAlbum() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        String path = "android.resource://" + appContext.getPackageName() + "/mipmap/" + "ic_launcher";
        AndroidPlatform.saveToAlbum(path);
    }

    @Test
    public void shareText() throws Exception {
        Log.d(TAG, "before share text");
        AndroidPlatform.nativeShareText("this is share text");
        Log.d(TAG, "after share text");

        UiObject button = mDevice.findObject(new UiSelector().text("JUST ONCE"));
        Log.d(TAG, "shareText button " + button.exists());
        if (button.exists() && button.isEnabled()) {
            button.click();
        }
        // 出现系统提示框，会阻塞主线程，点按home键防止阻塞主线程
        mDevice.pressHome();
    }
}