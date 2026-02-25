package dev.rajesh.mobile_banking.download.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.rajesh.mobile_banking.download.domain.model.DownloadFile
import dev.rajesh.mobile_banking.download.domain.usecase.DownloadFileUseCase
import dev.rajesh.mobile_banking.download.platform.IFileDownloader
import dev.rajesh.mobile_banking.logger.AppLogger
import kotlinx.coroutines.launch

class DownloadViewModel(
    private val downloadFileUse: DownloadFileUseCase,
    private val fileDownloader: IFileDownloader
) : ViewModel() {
    fun download(url: String, fileName: String) {
        viewModelScope.launch {
            val result = downloadFileUse(
                DownloadFile(url, fileName)
            )

            result.onSuccess {
                AppLogger.d("DownloadViewModel", "Saved file at: $it")
                //fileDownloader.openFile(it)
            }.onFailure {
                AppLogger.d("DownloadViewModel", "Saved file at: Error: ${it.message}")
            }
        }
    }
}