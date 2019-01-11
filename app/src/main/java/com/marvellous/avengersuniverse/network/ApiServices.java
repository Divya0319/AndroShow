package com.marvellous.avengersuniverse.network;

import com.marvellous.avengersuniverse.models.DoctorSongsResponse;
import com.marvellous.avengersuniverse.models.RingtonesResponse;
import com.marvellous.avengersuniverse.models.SongsGuardiansResponse;
import com.marvellous.avengersuniverse.models.VideosResponse;
import com.marvellous.avengersuniverse.models.WallpapersModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiServices {
    @GET(NetworkKeys.DOCTOR_STRANGE_SONGS_ENDPOINT)
    Call<List<DoctorSongsResponse>> getDoctorSongs();

    @GET(NetworkKeys.RINGTONES_ENDPOINT)
    Call<List<RingtonesResponse>> getRingtonesData();

    @GET(NetworkKeys.GUARDIANS_SONGS_ENDPOINT)
    Call<List<SongsGuardiansResponse>> getGuardiansSongs();

    @GET(NetworkKeys.VIDEOS_ENDPOINT)
    Call<List<VideosResponse>> getVideosData();

    @GET(NetworkKeys.WALLPAPER_BG_ENDPOINT)
    Call<List<WallpapersModel>> getBGWallpapers();

    @GET(NetworkKeys.WALLPAPER_BLACK_WIDOW_ENDPOINT)
    Call<List<WallpapersModel>> getBlackWidowWallpapers();

    @GET(NetworkKeys.WALLPAPER_CAPTAIN_ENDPOINT)
    Call<List<WallpapersModel>> getCaptainWallpapers();

    @GET(NetworkKeys.WALLPAPER_DOCTOR_ENDPOINT)
    Call<List<WallpapersModel>> getDoctorWalpapers();

    @GET(NetworkKeys.WALLPAPER_IRONMAN_ENDPOINT)
    Call<List<WallpapersModel>> getIronmanWallpapers();

    @GET(NetworkKeys.WALLPAPER_LOKI_ENDPOINT)
    Call<List<WallpapersModel>> getLokiWallpapers();

    @GET(NetworkKeys.WALLPAPER_MISC_ENDPOINT)
    Call<List<WallpapersModel>> getMiscWallpapers();

    @GET(NetworkKeys.WALLPAPER_SPIDERMAN_ENDPOINT)
    Call<List<WallpapersModel>> getSpidermanWallpapers();

    @GET(NetworkKeys.WALLPAPER_TCHAALA_ENDPOINT)
    Call<List<WallpapersModel>> getTchaalaWallpapers();

    @GET(NetworkKeys.WALLPAPER_THOR_ENDPOINT)
    Call<List<WallpapersModel>> getThorWallpapers();

    @GET(NetworkKeys.WALLPAPER_WANDA_ENDPOINT)
    Call<List<WallpapersModel>> getWandaWallpapers();


}
