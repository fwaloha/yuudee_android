package mingci;

import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import com.easychange.admin.smallrain.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class MingciIdeaOneActivityTest {



    //默认在测试之前启动该Activity
    @Rule
    public ActivityTestRule<MingciIdeaOneActivity> mActivityRule =
            new ActivityTestRule<MingciIdeaOneActivity>(MingciIdeaOneActivity.class){

                @Override
                protected Intent getActivityIntent() {
                    Intent intent = new Intent();
                    intent.putExtra("position",0);
                    intent.putExtra("groupId","");
                    
                    return intent;
                }

                @Override
                protected void beforeActivityLaunched() {
                    super.beforeActivityLaunched();
                }
            };
    private MingciIdeaOneActivity activity;
    private HandlerThread th;
    private Handler handler;

//    @Before
//    public void setup(){
//        activity = mActivityRule.getActivity();
//        th = new HandlerThread("test");
//        th.start();;
//        handler = new Handler(th.getLooper());
//    }
    
    
    @Test
    public void handTest(){
        
//        Espresso.onView(withId(R.id.rl_hand))
//                .perform(click());
    }
}