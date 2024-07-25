package com.example.proyectate.DataAccess.Repositories;

import com.example.proyectate.DataAccess.Services;
import com.example.proyectate.Models.MessageResponse;

public interface IRepository {
    void onSuccessResponse(Object objectResponse, Services services);
    void onFailedResponse(MessageResponse response, Services services);
}
