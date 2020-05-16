package com.avijit.rms1.data.remote.api;

import com.avijit.rms1.data.remote.model.ReliefSchedule;
import com.avijit.rms1.data.remote.responses.ReliefScheduleResponse;
import com.avijit.rms1.data.remote.responses.ReliefScheduleStoreResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ReliefScheduleApi {
    @GET("completed-schedules/{id}")
    Call<ReliefScheduleResponse> completedSchedules(@Path("id") String id);
    @GET("pending-schedules/{id}")
    Call<ReliefScheduleResponse> pendingSchedules(@Path("id") String id);

    @POST("schedule-create")
    Call<ReliefScheduleStoreResponse> storeSchedule(@Body ReliefSchedule reliefSchedule);

}
