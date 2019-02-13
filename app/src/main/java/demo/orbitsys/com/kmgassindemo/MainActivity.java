package demo.orbitsys.com.kmgassindemo;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import demo.orbitsys.com.kmgassindemo.util.ApplicationController;
import demo.orbitsys.com.kmgassindemo.util.InitializeDrawerLayout;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView ivNav;
    Animation animation;
    DrawerLayout drawerLayout;
    private LinearLayout nav_linear_layout;
    String calledBy="";
    FrameLayout frameLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ApplicationController.setFullScreen(this);
        initView();
        Intent intent=getIntent();
        if(intent!=null) {
            calledBy = intent.getStringExtra("calledBy");
        }

        clickListener();
        animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        InitializeDrawerLayout initializeDrawerLayout = new NavMenuFragment();
        initializeDrawerLayout.initializeDrawerLayout(drawerLayout);
        drawerLayout.requestLayout();
        nav_linear_layout.bringToFront();

        loadDrawerContent();
        if(calledBy!=null)
        if(calledBy.equalsIgnoreCase("bar_chart")){

        }



    }

    private void initView() {
        ivNav=findViewById(R.id.ivNav);
        drawerLayout=findViewById(R.id.drawer_layout);
        nav_linear_layout = (LinearLayout) findViewById(R.id.nav_linear_layout);
        findViewById(R.id.frameContainerFrag).setOnTouchListener(new MyTouchListener());

        findViewById(R.id.topleft).setOnDragListener(new MyDragListener());
        findViewById(R.id.topright).setOnDragListener(new MyDragListener());
        findViewById(R.id.bottomleft).setOnDragListener(new MyDragListener());
        findViewById(R.id.bottomright).setOnDragListener(new MyDragListener());
 findViewById(R.id.centerLeft).setOnDragListener(new MyDragListener());
        findViewById(R.id.centerRight).setOnDragListener(new MyDragListener());

    }
    public void loadDrawerContent() {
        Fragment fragment = new NavMenuFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_frame_container, fragment);

            fragmentTransaction.commit();


    }
    private void clickListener() {
        ivNav.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id){
            case R.id.ivNav:
                view.startAnimation(animation);

                //   setJobOverFlagForDataSheetMenu(drawerLayout);
                if (!drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    drawerLayout.openDrawer(Gravity.LEFT);
                    nav_linear_layout.bringToFront();
                    drawerLayout.requestLayout();
                } else {
                    drawerLayout.closeDrawers();
                }

                break;
        }

    }

    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                        view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }

    class MyDragListener implements View.OnDragListener {

        Drawable normalShape = getResources().getDrawable(R.drawable.shape);

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                  //  v.setBackgroundDrawable(enterShape);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    v.setBackgroundDrawable(normalShape);
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    FrameLayout container = (FrameLayout) v;
                    container.addView(view);
                    view.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.setBackgroundDrawable(normalShape);
                default:
                    break;
            }
            return true;
        }
    }
}