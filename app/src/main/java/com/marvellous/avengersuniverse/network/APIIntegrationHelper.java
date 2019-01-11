package com.marvellous.avengersuniverse.network;

import com.marvellous.avengersuniverse.models.DoctorSongsResponse;
import com.marvellous.avengersuniverse.models.RingtonesResponse;
import com.marvellous.avengersuniverse.models.SongsGuardiansResponse;
import com.marvellous.avengersuniverse.models.VideosResponse;
import com.marvellous.avengersuniverse.models.WallpapersModel;

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
                .baseUrl(NetworkKeys.AVENGERS_BASE_URL)
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

    public void guardianSongsData(Callback<List<SongsGuardiansResponse>> guardiansSongsCallback) {
        Call<List<SongsGuardiansResponse>> getGuardianSongsData = mApiServices.getGuardiansSongs();
        getGuardianSongsData.enqueue(guardiansSongsCallback);
    }

    public void videosData(Callback<List<VideosResponse>> videosCallback) {
        Call<List<VideosResponse>> getSongsData = mApiServices.getVideosData();
        getSongsData.enqueue(videosCallback);
    }

    public void wallpaperBg(Callback<List<WallpapersModel>> wallpaperBgCallback) {
        Call<List<WallpapersModel>> getWallpaperBg = mApiServices.getBGWallpapers();
        getWallpaperBg.enqueue(wallpaperBgCallback);
    }

    public void wallpaperBlackwidow(Callback<List<WallpapersModel>> wallpaperBlackWidowCallback) {
        Call<List<WallpapersModel>> getWallpaperBlackwidow = mApiServices.getBlackWidowWallpapers();
        getWallpaperBlackwidow.enqueue(wallpaperBlackWidowCallback);
    }

    public void captainWallpapers(Callback<List<WallpapersModel>> captainWallpapersCallback) {
        Call<List<WallpapersModel>> getCaptainWallpapers = mApiServices.getCaptainWallpapers();
        getCaptainWallpapers.enqueue(captainWallpapersCallback);
    }

    public void wallpapersDoctor(Callback<List<WallpapersModel>> wallpapersDoctorCallback) {
        Call<List<WallpapersModel>> getWallpapersDoctor = mApiServices.getDoctorWalpapers();
        getWallpapersDoctor.enqueue(wallpapersDoctorCallback);
    }

    public void wallpaperIronman(Callback<List<WallpapersModel>> wallpaperIronManCallback) {
        Call<List<WallpapersModel>> getWallpaperIronman = mApiServices.getIronmanWallpapers();
        getWallpaperIronman.enqueue(wallpaperIronManCallback);
    }

    public void wallpaperLoki(Callback<List<WallpapersModel>> wallpaperLokiCallback) {
        Call<List<WallpapersModel>> getWallpaperLoki = mApiServices.getLokiWallpapers();
        getWallpaperLoki.enqueue(wallpaperLokiCallback);
    }

    public void wallpaperMisc(Callback<List<WallpapersModel>> wallpaperMiscCallback) {
        Call<List<WallpapersModel>> getWallpaperMisc = mApiServices.getMiscWallpapers();
        getWallpaperMisc.enqueue(wallpaperMiscCallback);
    }

    public void wallpaperSpiderman(Callback<List<WallpapersModel>> wallpaperSpidermanCallback) {
        Call<List<WallpapersModel>> getWallpaperSpiderman = mApiServices.getSpidermanWallpapers();
        getWallpaperSpiderman.enqueue(wallpaperSpidermanCallback);
    }

    public void wallpaperTchaala(Callback<List<WallpapersModel>> wallpaperTchaalaCallback) {
        Call<List<WallpapersModel>> getWallpaperTchaala = mApiServices.getTchaalaWallpapers();
        getWallpaperTchaala.enqueue(wallpaperTchaalaCallback);
    }

    public void wallpaperThor(Callback<List<WallpapersModel>> wallpaperThorCallback) {
        Call<List<WallpapersModel>> getWallpaperThor = mApiServices.getThorWallpapers();
        getWallpaperThor.enqueue(wallpaperThorCallback);
    }

    public void wallpaperWanda(Callback<List<WallpapersModel>> wallpaperWandaCallback) {
        Call<List<WallpapersModel>> getWallpaperWanda = mApiServices.getWandaWallpapers();
        getWallpaperWanda.enqueue(wallpaperWandaCallback);
    }

}
