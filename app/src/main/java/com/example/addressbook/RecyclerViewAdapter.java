package com.example.addressbook;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.addressbook.R.layout.list_items;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<String> mName;
    private ArrayList<Integer> mImage;
    private Context mContext;

    RecyclerViewAdapter(Context context, ArrayList<String> names, ArrayList<Integer> imageUrls) {
        mName = names;
        mImage = imageUrls;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(list_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        Glide.with(mContext)
                .asBitmap()
                .load(mImage.get(position))
                .into(holder.p_image);

        holder.p_name.setText(mName.get(position));

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked on an image: " + mName.get(position));
                Toast.makeText(mContext, mName.get(position), Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public int getItemCount() {
        return mName.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView p_image;
        TextView p_name;
        RelativeLayout relativeLayout;

        ViewHolder(View itemView) {
            super(itemView);
            p_image = itemView.findViewById(R.id.image);
            p_name = itemView.findViewById(R.id.person_name);
            relativeLayout=itemView.findViewById(R.id.relative_parent_layout);
        }
    }


}
