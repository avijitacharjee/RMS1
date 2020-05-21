package com.avijit.rms1.repository;

import androidx.lifecycle.MutableLiveData;

import com.avijit.rms1.data.remote.RetrofitService;
import com.avijit.rms1.data.remote.api.CoronaSummaryApi;
import com.avijit.rms1.data.remote.responses.CoronaSummaryResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoronaSummaryRepository {
    private static CoronaSummaryRepository coronaSummaryRepository;
    public static CoronaSummaryRepository getInstance()
    {
        if(coronaSummaryRepository == null)
        {
            coronaSummaryRepository = new CoronaSummaryRepository();
        }
        return coronaSummaryRepository;
    }
    private CoronaSummaryApi coronaSummaryApi;
    public CoronaSummaryRepository()
    {
        coronaSummaryApi = RetrofitService.createService(CoronaSummaryApi.class);
    }
    public MutableLiveData<CoronaSummaryResponse> getStudents()
    {
        final MutableLiveData<CoronaSummaryResponse> coronaSummaryResponseMutableLiveData = new MutableLiveData<>();
        coronaSummaryApi.getCoronaSummary().enqueue(new Callback<CoronaSummaryResponse>() {
            @Override
            public void onResponse(Call<CoronaSummaryResponse> call, Response<CoronaSummaryResponse> response) {
                if(response.isSuccessful())
                {
                    coronaSummaryResponseMutableLiveData.setValue(response.body());
                }
                else {
                    CoronaSummaryResponse err= new CoronaSummaryResponse();
                    err.setNetworkIsSuccessful(false);
                    coronaSummaryResponseMutableLiveData.setValue(err);
                }
            }

            @Override
            public void onFailure(Call<CoronaSummaryResponse> call, Throwable t) {
                /*CoronaSummaryResponse err= new CoronaSummaryResponse();
                err.setNetworkIsSuccessful(false);
                coronaSummaryResponseMutableLiveData.setValue(err);*/
                coronaSummaryResponseMutableLiveData.setValue(new CoronaSummaryResponse.Builder<CoronaSummaryResponse>(CoronaSummaryResponse.class).setNetworkIsSuccessful(false));
            }
        });
        return coronaSummaryResponseMutableLiveData;
    }

}
