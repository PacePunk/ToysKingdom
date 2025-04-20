package com.example.toyskingdom.interfaces;

import com.example.toyskingdom.model.DataProduct;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Product {
    @GET("get_product.php")
    Call<List<DataProduct>> view();
}
