package com.alaukikapps.movtronix.network;

import com.alaukikapps.movtronix.models.DoctorSongsResponse;
import com.alaukikapps.movtronix.models.RingtonesResponse;
import com.alaukikapps.movtronix.models.VideosResponse;
import com.alaukikapps.movtronix.models.WallpapersModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiServices {
    @GET(NetworkKeys.SONGS_ENDPOINT)
    Call<List<DoctorSongsResponse>> getDoctorSongs();

    @GET(NetworkKeys.RINGTONES_ENDPOINT)
    Call<List<RingtonesResponse>> getRingtonesData();

    @GET(NetworkKeys.VIDEOS_ENDPOINT)
    Call<List<VideosResponse>> getVideosData();

    @GET(NetworkKeys.WALLPAPER_BG_ENDPOINT)
    Call<List<WallpapersModel>> getBGWallpapers();
}
