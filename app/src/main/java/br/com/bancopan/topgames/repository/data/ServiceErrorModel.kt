package br.com.bancopan.topgames.repository.data

data class ServiceErrorModel(var isConnectException: Boolean = false) {
    var httpCode: Int = 0
    var throwable: Throwable? = null
    var errorMessage: String = ""
}