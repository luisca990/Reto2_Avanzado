package com.example.proyectate.Presentation.Dash.Home.Interfaces;

import com.example.proyectate.Models.Pedido;
import com.example.proyectate.Models.Product;

import java.util.List;

public interface IHomeView {
    void showGetAllProductsSuccess(List<Product> products);
    void showGetLastPedidoSuccess(Pedido pedido);
}
