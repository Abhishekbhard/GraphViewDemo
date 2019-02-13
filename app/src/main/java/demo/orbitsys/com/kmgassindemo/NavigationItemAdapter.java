package demo.orbitsys.com.kmgassindemo;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import demo.orbitsys.com.kmgassindemo.util.InitializeDrawerLayout;

import static android.content.Context.DOWNLOAD_SERVICE;

public class NavigationItemAdapter extends RecyclerView.Adapter<NavigationItemAdapter.MenuAdapterViewHolder>
        implements InitializeDrawerLayout {


    private static final String TAG = "NavigationItemAdapter";

    public static List<LinearLayout> menuOptionView;
    public static String currentMenuOptionClicked;
    static DrawerLayout drawerLayout;
    private static DownloadManager downloadManager;
    Context context;
    List<String> customMenu;
    AppCompatActivity activity;
    AppCompatActivity instanceBaseActivity;
    private SharedPreferences preferences;
    private long File_DownloadId;


    public NavigationItemAdapter(List<String> customMenu, Context context) {

        this.customMenu = customMenu;
        this.context = context;


        menuOptionView = new ArrayList<>();
    }

    public NavigationItemAdapter() {

    }

    @Override
    public MenuAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.navigation_item_row, parent, false);
        MenuAdapterViewHolder holder = new MenuAdapterViewHolder(row);

        return holder;

    }

    @Override
    public void onBindViewHolder(final MenuAdapterViewHolder holder, final int position) {

        holder.item_text.setText(customMenu.get(position));
       /* if(HomeScreen.fileCompleteDownloadFlag){
            holder.mainLayout.setAlpha(1f);
        }
*/
       /* holder.item_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context, "Option selected", Toast.LENGTH_SHORT).show();

            }
        });*/
        if (currentMenuOptionClicked != null) {
            if (customMenu.get(position).equalsIgnoreCase(currentMenuOptionClicked)) {
                // to set first option selected and change text color
                //dataSheetOptionView.get(position).setBackgroundColor((ContextCompat.getColor(context, ResourceBasedOnBrand.getResourceColor(context))));
                holder.navArrowIcon.setVisibility(View.VISIBLE);
                // ends here
            }
        }
        holder.mainLayout.setOnClickListener(view -> changeLayoutColor(holder.mainLayout, holder.navArrowIcon, position));
    }

    private void displayScreen(int pos, LinearLayout linearLayout) {

        switch (pos) {

            case 0:
                Intent intent=new Intent(context,MainActivity.class);
                intent.putExtra("calledBy","bar_chart");
                context.startActivity(intent);
                drawerLayout.closeDrawers();
                break;


            case 1:
                drawerLayout.closeDrawers();

                break;

            case 2:


                drawerLayout.closeDrawers();
                break;

            case 3:


                break;

            case 4:

                drawerLayout.closeDrawers();
                break;

            case 5:

                drawerLayout.closeDrawers();
                break;

            case 6:

                drawerLayout.closeDrawers();

                    break;




            case 7:

                drawerLayout.closeDrawers();
                break;

            case 8:

                drawerLayout.closeDrawers();
                break;

            default:
                break;
        }
    }

    public void changeLayoutColor(LinearLayout linearLayout, ImageView navArrow, int position) {

        //  Toast.makeText(context, "Clicked On Item " + String.valueOf(position), Toast.LENGTH_SHORT).show();

        for (int i = 0; i < menuOptionView.size(); i++) {
            if (linearLayout.hashCode() == menuOptionView.get(i).hashCode()) {
                //linearLayout.setBackgroundColor((ContextCompat.getColor(context, ResourceBasedOnBrand.getResourceColor(context))));
                // textView.setTextColor((ContextCompat.getColor(context, R.color.white))); // to change selected option text to white


            } else if (linearLayout.hashCode() != menuOptionView.get(i).hashCode()) {

                LinearLayout layout = menuOptionView.get(i);



            }

        }
        displayScreen(position, linearLayout);
    }

    @Override
    public int getItemCount() {
        return customMenu.size();
    }

    @Override
    public void initializeDrawerLayout(DrawerLayout drawerLayout) {
        NavigationItemAdapter.drawerLayout = drawerLayout;

    }






    public static class MenuAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView item_text;
        ImageView navArrowIcon;
        LinearLayout mainLayout;


        public MenuAdapterViewHolder(View itemView) {
            super(itemView);
            item_text = itemView.findViewById(R.id.item_name);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            menuOptionView.add(mainLayout);

        }

    }
}



