package com.rikharthu.itunestopcharts

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.rikharthu.itunestopcharts.data.source.TracksDataSource
import com.rikharthu.itunestopcharts.data.source.local.TracksDatabase
import com.rikharthu.itunestopcharts.data.source.local.TracksLocalDataSource
import com.rikharthu.itunestopcharts.data.source.remote.TracksRemoteDataSource
import com.rikharthu.itunestopcharts.data.source.remote.deserializer.FeedDeserializer
import com.rikharthu.itunestopcharts.data.source.remote.model.FeedResponse
import com.rikharthu.itunestopcharts.data.source.remote.service.TopTracksService
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.eagerSingleton
import org.kodein.di.generic.instance
import org.kodein.di.generic.with
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class App : Application() {

    companion object {
        fun get(context: Context): App = context.applicationContext as App
    }

    lateinit var kodein: Kodein
        private set

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        val remoteModule = Kodein.Module("remote_module") {
            constant("base_url") with "http://ax.itunes.apple.com/"
            bind<Gson>() with eagerSingleton {
                GsonBuilder()
                        .registerTypeAdapter(FeedResponse::class.java, FeedDeserializer())
                        .create()
            }
            bind<TopTracksService>() with eagerSingleton {
                Retrofit.Builder()
                        .addConverterFactory(
                                GsonConverterFactory.create(GsonBuilder()
                                        .registerTypeAdapter(FeedResponse::class.java, FeedDeserializer())
                                        .create()))
                        .addCallAdapterFactory(CoroutineCallAdapterFactory())
                        .baseUrl(instance<String>("base_url"))
                        .build()
                        .create(TopTracksService::class.java)
            }
            bind<TracksDataSource>(tag = "remote") with eagerSingleton {
                TracksRemoteDataSource(instance())
            }
        }
        val localModule = Kodein.Module("local_module") {
            constant("database_name") with "hotTracks.db"
            bind<TracksDatabase>() with eagerSingleton {
                Room.databaseBuilder(applicationContext,
                        TracksDatabase::class.java, instance("database_name"))
                        .build()
            }
            bind<TracksDataSource>(tag = "local") with eagerSingleton {
                TracksLocalDataSource(instance<TracksDatabase>().tracksDao())
            }
        }

        val dataModule = Kodein.Module("data_module") {
            import(remoteModule)
            import(localModule)
//            bind<TracksRepositoryImpl>() with eagerSingleton {
//                TracksRepositoryImpl(instance(tag = "remote"), instance(tag = "local"))
//            }
        }

        val presentationModule = Kodein.Module("presentation_module") {
            //            bind<HotChartsViewModel>() with factory {
//                ViewModelProviderr
//            }
        }

        kodein = Kodein {
            import(dataModule)
//            bind<AppExecutors>() with singleton { AppExecutors() }
        }
    }
}