package demo.orbitsys.com.kmgassindemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import demo.orbitsys.com.kmgassindemo.util.InitializeDrawerLayout;

public class NavMenuFragment extends Fragment implements InitializeDrawerLayout {
     static DrawerLayout drawerLayout;
    RecyclerView recycerview_navItem;
    List<String> customNavItem = new ArrayList<>();

    public NavMenuFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // TODO confirm Knowledge base spelling
        customNavItem.add("Image1");
        customNavItem.add("Image2");
        customNavItem.add("Image3");
        customNavItem.add("Image4");
        customNavItem.add("Image5");
        customNavItem.add("Image6");

    }

    public void initView(View view) {
        recycerview_navItem = view.findViewById(R.id.recycerview_navItem);

    }


    @Override
    public void initializeDrawerLayout(DrawerLayout drawerLayout) {
        NavMenuFragment.drawerLayout = drawerLayout;
        InitializeDrawerLayout initializeDrawerLayout = new NavigationItemAdapter();
        initializeDrawerLayout.initializeDrawerLayout(NavMenuFragment.drawerLayout);


    }

    private void setNavItemAdapter() {
        recycerview_navItem.setLayoutManager(new LinearLayoutManager(getActivity()));
        NavigationItemAdapter adapter = new NavigationItemAdapter(customNavItem, getActivity());
        recycerview_navItem.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nav_menu, container, false);

        initView(view);
        setNavItemAdapter();

        return view;
    }

}
