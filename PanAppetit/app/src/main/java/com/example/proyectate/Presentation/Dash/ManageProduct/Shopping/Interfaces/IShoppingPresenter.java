package com.example.proyectate.Presentation.Dash.ManageProduct.Shopping.Interfaces;

import com.example.proyectate.Models.Venta;

public interface IShoppingPresenter {
    void insertVenta(Venta venta, int idPedido);
    void deleteDetallePedido(int idProduct, int idPedido);
}
