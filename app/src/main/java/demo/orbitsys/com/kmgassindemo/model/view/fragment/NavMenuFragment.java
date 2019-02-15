package demo.orbitsys.com.kmgassindemo.model.view.fragment;

import android.content.Context;
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

import demo.orbitsys.com.kmgassindemo.model.adapter.NavigationItemAdapter;
import demo.orbitsys.com.kmgassindemo.R;
import demo.orbitsys.com.kmgassindemo.util.InitializeDrawerLayout;

public class NavMenuFragment extends Fragment implements InitializeDrawerLayout {
    private static DrawerLayout drawerLayout;
    private RecyclerView recycerview_navItem;
    private List<String> customNavItem = new ArrayList<>();
    private Context context;

    public NavMenuFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customNavItem.add("Avg Cost Per Claim");
        customNavItem.add("Customer Satisfaction");
        customNavItem.add("Claims Ratio");
        customNavItem.add("Top Brokers");
        customNavItem.add("Avg. Time to Settle");
        customNavItem.add("Policy sales Growth");


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
        adapter.setiLoadChart((NavigationItemAdapter.ILoadChart) context);
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


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }
}
