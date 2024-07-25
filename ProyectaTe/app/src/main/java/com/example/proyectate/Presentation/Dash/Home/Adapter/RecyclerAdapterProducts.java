package com.example.proyectate.Presentation.Dash.Home.Adapter;

import static com.example.proyectate.Utils.Util.convertImageService;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectate.Models.Project;
import com.example.proyectate.R;
import com.example.proyectate.databinding.ItemProductsBinding;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapterProducts extends RecyclerView.Adapter<RecyclerAdapterProducts.ViewHolder> {

    private List<Project> products;
    private final Context context;
    private final OnItemClickListenerProduct listener;

    @SuppressLint("NotifyDataSetChanged")
    public void updateList(List<Project> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    public RecyclerAdapterProducts(Context context, List<Project> products, OnItemClickListenerProduct listener) {
        this.products = products;
        this.context = context;
        this.listener = listener;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductsBinding view = ItemProductsBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setDetailCategoria(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filter(String text) {
        List<Project> filter = new ArrayList<>();
        text = text.toLowerCase();
        for (Project product : products) {
            if (product.getTitle().toLowerCase().contains(text)) {
                filter.add(product);
            }
        }
        products = filter;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView name;
        private final TextView precio;
        public ViewHolder(ItemProductsBinding itemView) {
            super(itemView.getRoot());
            imageView = itemView.ivCharacter;
            name = itemView.tvName;
            precio = itemView.tvPrecio;

        }
        @SuppressLint("SetTextI18n")
        void setDetailCategoria(Project product){
            name.setText(product.getTitle());
            precio.setText(product.getDateInit());
            convertImageService(product.getImage(), imageView, 150);
            imageView.setOnClickListener(v -> listener.onItemClick(product));
        }
    }
}
