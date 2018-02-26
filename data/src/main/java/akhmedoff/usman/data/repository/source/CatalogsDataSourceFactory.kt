package akhmedoff.usman.data.repository.source

import akhmedoff.usman.data.api.VkApi
import akhmedoff.usman.data.model.Catalog
import android.arch.paging.DataSource

class CatalogsDataSourceFactory(private val vkApi: VkApi) : DataSource.Factory<String, Catalog> {

    override fun create() = CatalogsPageKeyedDataSource(vkApi)
}