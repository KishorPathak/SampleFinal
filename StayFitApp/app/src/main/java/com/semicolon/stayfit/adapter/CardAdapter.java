package com.semicolon.stayfit.adapter;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.semicolon.stayfit.MenuItem;
import com.semicolon.stayfit.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ahmed_anwar on 11/17/2015.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    List<MenuItem> mItems;

    public CardAdapter() {
        super();
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());

        mItems = new ArrayList<MenuItem>();
        MenuItem menu = new MenuItem();
        menu.setName("Samosa : 252 cals");
        menu.setDes("The only healthy way to eat samosas is to have them with healthy chicken or vegetable based flavourings and yes, steaming or baking instead of frying (but of course, where's the fun in that!.");
        menu.setDate("Date : " + df.format(c.getTime()));
        menu.setWieght("HIGH");
        menu.setThumbnail(R.drawable.samosa);
        mItems.add(menu);

        menu = new MenuItem();
        menu.setName("Corn Chaat : 90 cals");
        menu.setDes("Spicy chaat recipe for the monsoon time. corn chaat is an easy snack which you can prepare quickly or whenever you are short of time. even bachelors can make it as it does not require any cooking expertise");
        menu.setThumbnail(R.drawable.corn);
        c.add(Calendar.DAY_OF_WEEK, 1);
        menu.setWieght("LOW");
        menu.setDate("Date : " + df.format(c.getTime()));
        mItems.add(menu);

        menu = new MenuItem();
        menu.setName("dhokla : 150 cals");
        menu.setDes("Dhokla's are steamed and therefore healthy, unless they're fried. However, be careful when you are giving en extra tadka of oil, mustard seed and a whole lot of other spices to them.");
        menu.setThumbnail(R.drawable.dhokla);
        menu.setWieght("MEDIUM");
        c.add(Calendar.DAY_OF_WEEK, 1);
        menu.setDate("Date : " + df.format(c.getTime()));
        mItems.add(menu);

        menu = new MenuItem();
        menu.setName("Sambhar vada : 140  cals");
        menu.setDes("Sambhar vada is a medium sized cookie shaped deep fried dumpling prepared with a mixture of channa dal, curry leaves, ginger and green chillies. A healthy way to prepare masala vadas is to steam or bake them.");
        menu.setThumbnail(R.drawable.wada);
        menu.setWieght("MEDIUM");
        c.add(Calendar.DAY_OF_WEEK, 1);
        menu.setDate("Date : "+df.format(c.getTime()));
        mItems.add(menu);



    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view_card_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        MenuItem menu = mItems.get(i);
        viewHolder.tvmenu.setText(menu.getName());
        viewHolder.tvDate.setText(menu.getDate());
        viewHolder.tvDesmenu.setText(menu.getDes());
        viewHolder.imgThumbnail.setImageResource(menu.getThumbnail());

        viewHolder.tvmenu.setBackgroundColor(menu.getWieght().equals("HIGH")?Color.parseColor("#BBFF0000"):
                menu.getWieght().equals("LOW")?Color.parseColor("#BB00FF00"):Color.parseColor("#666600"));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imgThumbnail;
        public TextView tvmenu;
        public TextView tvDate;
        public TextView tvDesmenu;

        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = (ImageView)itemView.findViewById(R.id.img_thumbnail);
            tvmenu = (TextView)itemView.findViewById(R.id.tv_menu);
            tvDesmenu = (TextView)itemView.findViewById(R.id.tv_des_menu);
            tvDate = (TextView)itemView.findViewById(R.id.tv_date);
        }
    }
}