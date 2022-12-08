package com.fastturtle.androshow.network;

import com.fastturtle.androshow.models.DoctorSongsResponse;
import com.fastturtle.androshow.models.RingtonesResponse;
import com.fastturtle.androshow.models.VideosResponse;
import com.fastturtle.androshow.models.WallpapersModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIIntegrationHelper {

    private static APIIntegrationHelper sIntance;
    private ApiServices mApiServices;

    private APIIntegrationHelper() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkKeys.ANDROSHOW_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApiServices = retrofit.create(ApiServices.class);
    }

    public static APIIntegrationHelper getsIntance() {
        if (sIntance == null) {
            sIntance = new APIIntegrationHelper();
        }
        return sIntance;
    }

    public void doctorSongsData(Callback<List<DoctorSongsResponse>> docSongsResponseCallback) {
        Call<List<DoctorSongsResponse>> getDoctorSongsData = mApiServices.getDoctorSongs();
        getDoctorSongsData.enqueue(docSongsResponseCallback);
    }

    public void ringtonesData(Callback<List<RingtonesResponse>> ringtonesCallback) {
        Call<List<RingtonesResponse>> getRingtonesData = mApiServices.getRingtonesData();
        getRingtonesData.enqueue(ringtonesCallback);
    }

    public void videosData(Callback<List<VideosResponse>> videosCallback) {
        Call<List<VideosResponse>> getSongsData = mApiServices.getVideosData();
        getSongsData.enqueue(videosCallback);
    }

    public void wallpaperBg(Callback<List<WallpapersModel>> wallpaperBgCallback) {
        Call<List<WallpapersModel>> getWallpaperBg = mApiServices.getBGWallpapers();
        getWallpaperBg.enqueue(wallpaperBgCallback);
    }

}
