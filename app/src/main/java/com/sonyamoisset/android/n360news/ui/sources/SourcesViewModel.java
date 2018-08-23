package com.sonyamoisset.android.n360news.ui.sources;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;

import com.sonyamoisset.android.n360news.data.model.Source;
import com.sonyamoisset.android.n360news.data.model.SourcesResponse;
import com.sonyamoisset.android.n360news.repository.DataRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SourcesViewModel extends AndroidViewModel {

    private DataRepository dataRepository;
    private MutableLiveData<List<Source>> sourcesMutableLiveData;
    private MutableLiveData<Integer> statusCodeMutableLiveData;

    public SourcesViewModel(@NonNull Application application) {
        super(application);

        dataRepository =
                DataRepository.getInstance(application.getApplicationContext());
        sourcesMutableLiveData = new MutableLiveData<>();
        statusCodeMutableLiveData = new MutableLiveData<>();
    }

    public LiveData<List<Source>> getSourcesFromNewsWebService() {
        dataRepository.getSourcesFromNewsWebService()
                .enqueue(new Callback<SourcesResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<SourcesResponse> call,
                                           @NonNull Response<SourcesResponse> response) {
                        List<Source> sources =
                                Objects.requireNonNull(response.body()).getSources();
                        sourcesMutableLiveData.postValue(sources);
                    }

                    @Override
                    public void onFailure(@NonNull Call<SourcesResponse> call,
                                          @NonNull Throwable t) {
                        statusCodeMutableLiveData.postValue(0);
                    }
                });

        return sourcesMutableLiveData;
    }

    public LiveData<List<Source>> filterNewsSourcesBy(String category) {
        if (category.equals(SourcesCategories.EVERYTHING)) {
            return sourcesMutableLiveData;

        } else {
            return Transformations.map(sourcesMutableLiveData, input -> {
                List<Source> filteredSources = new ArrayList<>();

                for (Source filter : input) {
                    if (filter.getCategory().equals(category.toLowerCase())) {
                        filteredSources.add(filter);
                    }
                }

                return filteredSources;
            });
        }
    }

    public MutableLiveData<Integer> getStatusCodeMutableLiveData() {
        return statusCodeMutableLiveData;
    }
}
