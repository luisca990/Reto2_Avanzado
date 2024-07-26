package com.example.proyectate.Presentation.Dash.Home.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.proyectate.Models.Project;
import com.example.proyectate.databinding.ItemProjectsBinding;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
        ItemProjectsBinding view = ItemProjectsBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
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
        private final TextView title;
        private final TextView dateInit;
        private final TextView dateEnd;
        public ViewHolder(ItemProjectsBinding itemView) {
            super(itemView.getRoot());
            imageView = itemView.ivCharacter;
            title = itemView.tvTitle;
            dateInit = itemView.tvDateInit;
            dateEnd = itemView.tvDateEnd;

        }
        @SuppressLint("SetTextI18n")
        void setDetailCategoria(Project project){
            title.setText(project.getTitle());
            dateInit.setText("Fecha Inicial: "+project.getDateInit());
            dateEnd.setText("Fecha Finalización: "+project.getDateEnd());
            convertImage(project.getImage());
            itemView.setOnClickListener(v -> listener.onItemClick(project));
        }
        private void convertImage(String image) {
            try {
                Uri uri = Uri.parse(image);
                InputStream inputStream = context.getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                // Puedes manejar errores aquí, por ejemplo, mostrar una imagen de error.
            } catch (Exception e) {
                e.printStackTrace();
                // Puedes manejar errores aquí, por ejemplo, mostrar una imagen de error.
            }
        }
    }
}
