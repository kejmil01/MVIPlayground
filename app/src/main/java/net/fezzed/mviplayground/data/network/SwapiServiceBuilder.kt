package net.fezzed.mviplayground.data.network

import net.fezzed.mviplayground.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object SwapiServiceBuilder {

	fun build(): SwapiService {
		val client = buildOkHttpClient()
		val retrofit = buildRetrofit(client)
		return retrofit.create(SwapiService::class.java)
	}

	private fun buildOkHttpClient() = OkHttpClient
		.Builder()
		.build()

	private fun buildRetrofit(okHttpClient: OkHttpClient): Retrofit =
		Retrofit.Builder()
			.addConverterFactory(GsonConverterFactory.create())
			.addCallAdapterFactory(RxJava3CallAdapterFactory.create())
			.baseUrl(BuildConfig.BASE_URL)
			.client(okHttpClient)
			.build()
}