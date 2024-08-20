package com.mshdabiola.testing.repository

import com.mshdabiola.data.repository.INetworkRepository

internal class FakeNetworkRepository : INetworkRepository {
    override suspend fun get() {
        TODO("Not yet implemented")
    }

    override suspend fun gotoGoogle(): String {
        TODO("Not yet implemented")
    }
}
