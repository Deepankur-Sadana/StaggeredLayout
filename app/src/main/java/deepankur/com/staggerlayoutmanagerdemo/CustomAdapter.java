package deepankur.com.staggerlayoutmanagerdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by deepankur on 11/14/16.
 */

class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomRecyclerViewHolder> {

    private int[] imgList = {R.drawable.ship, R.drawable.hilary, R.drawable.jet, R.drawable.four,
            R.drawable.five, R.drawable.six, R.drawable.seven, R.drawable.eight,
            R.drawable.nine, R.drawable.ten};

    private String[] nameList = {"One", "Two", "Three", "Four", "Five", "Six",
            "Seven", "Eight", "Nine", "Ten"};

    CustomAdapter(Context context) {
    }

    @Override
    public CustomRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        CustomRecyclerViewHolder masonryView = new CustomRecyclerViewHolder(layoutView);
        return masonryView;
    }

    @Override
    public void onBindViewHolder(CustomRecyclerViewHolder holder, int position) {
        holder.imageView.setImageResource(imgList[position]);
        holder.textView.setText(nameList[position]);
    }

    @Override
    public int getItemCount() {
        return nameList.length;
    }

    class CustomRecyclerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        CustomRecyclerViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.img);
            textView = (TextView) itemView.findViewById(R.id.img_name);

        }
    }
}
