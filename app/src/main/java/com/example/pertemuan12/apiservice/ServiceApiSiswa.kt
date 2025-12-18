 package com.example.pertemuan12.apiservice

import com.example.pertemuan12.modeldata.DataSiswa
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ServiceApiSiswa {
    @GET("bacaTeman.php")
    suspend fun getSiswa(): List<DataSiswa>

    @POST("insertTM.php")
    suspend fun postSiswa(@Body dataSiswa: DataSiswa): retrofit2.Response<Void>
}
class JaringanRepositoryDataSiswa(
    private val serviceApiSiswa: ServiceApiSiswa
):RepositoryDataSiswa{
    override suspend fun getDataSiswa() : List<DataSiswa> =serviceApiSiswa.getSiswa()
    override suspend fun  postDataSiswa(dataSiswa: DataSiswa):retrofit2.Response<Void> = serviceApiSiswa.postSiswa(dataSiswa)

}