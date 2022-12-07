package com.fastturtle.androshow.network;

import com.fastturtle.androshow.models.DoctorSongsResponse;
import com.fastturtle.androshow.models.RingtonesResponse;
import com.fastturtle.androshow.models.VideosResponse;
import com.fastturtle.androshow.models.WallpapersModel;

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
