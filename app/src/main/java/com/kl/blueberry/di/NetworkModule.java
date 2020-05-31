package com.kl.blueberry.di;

import com.kl.blueberry.api.ApiService;

import dagger.Module;
import dagger.Provides;
import dagger.Reusable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import kotlin.jvm.JvmStatic;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by Kaltrina Lubovci on 30,May,2020
 */
@Module
public class NetworkModule {
    /**
     * Provides the Post service implementation.
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the Post service implementation.
     */
    @Provides
    @Reusable
    @JvmStatic
    public ApiService providePostApi(Retrofit retrofit){
        return retrofit.create(ApiService.class);
    }

    /**
     * Provides the Retrofit object.
     * @return the Retrofit object
     */
    @Provides
    @Reusable
    @JvmStatic
    public Retrofit provideRetrofitInterface(){
       OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

       return new Retrofit.Builder()
               .baseUrl("https://deezerdevs-deezer.p.rapidapi.com")
               .client(client)
               .addConverterFactory(MoshiConverterFactory.create())
               .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
               .build();
    }
}
