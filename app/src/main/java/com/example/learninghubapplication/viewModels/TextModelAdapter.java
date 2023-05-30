package com.example.learninghubapplication.viewModels;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learninghubapplication.R;
import com.example.learninghubapplication.model.TextModel;

import java.util.List;

public class TextModelAdapter extends RecyclerView.Adapter<TextModelAdapter.MyViewHolder> {

    // variables
    private List<TextModel> textModelList;
    private Context context;
    private ItemClickListener clickListener; // interface global

    // constructor
    public TextModelAdapter(List<TextModel> textModelList, ItemClickListener clickListener,
                            Context context) {
        this.textModelList = textModelList;
        this.clickListener = (ItemClickListener) clickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.text_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TextModelAdapter.MyViewHolder holder, int position) {
        //find position
        holder.display_name.setText(textModelList.get(position).getName());
        holder.display_text.setText(textModelList.get(position).getText());

        //click listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(textModelList.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return textModelList == null ? 0 : textModelList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // variables
        private TextView display_name, display_text;
        public MyViewHolder(View itemView) {
            super(itemView);
            //set to view
            display_name = itemView.findViewById(R.id.display_name);
            display_text = itemView.findViewById(R.id.display_text);
        }
    }

    public interface ItemClickListener {
        void onItemClick(TextModel textModel);
    }
}
