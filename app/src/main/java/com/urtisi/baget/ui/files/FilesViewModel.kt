package com.urtisi.baget.ui.files

import android.net.Uri
import android.os.Handler
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.owncloud.android.lib.common.OwnCloudClient
import com.owncloud.android.lib.common.OwnCloudClientFactory
import com.owncloud.android.lib.common.OwnCloudCredentialsFactory
import com.owncloud.android.lib.common.operations.OnRemoteOperationListener
import com.owncloud.android.lib.common.operations.RemoteOperation
import com.owncloud.android.lib.common.operations.RemoteOperationResult
import com.owncloud.android.lib.resources.files.FileUtils
import com.owncloud.android.lib.resources.files.ReadFileRemoteOperation
import com.owncloud.android.lib.resources.files.ReadFolderRemoteOperation
import com.owncloud.android.lib.resources.files.model.RemoteFile
import com.urtisi.baget.R
import com.urtisi.baget.application.BagetApplication

class FilesViewModel : ViewModel(), OnRemoteOperationListener {

    //val fileName = MutableLiveData<String>()
    val fileName = ObservableField<String>()

    private lateinit var mClient: OwnCloudClient
    private lateinit var mHandler: Handler
    private lateinit var ncUri: String
    private lateinit var ncUsername: String
    private lateinit var ncPassword: String

    /**
     * init loading data from nextcloud
     */
    fun loadData() {

        val context = BagetApplication().getContext()
        ncUri = context.resources.getString(R.string.ncURI)
        ncUsername = context.resources.getString(R.string.ncUsername)
        ncPassword = context.resources.getString(R.string.ncPassword)

        /**
         * creating client and handler for connection
         */
        mHandler = Handler()
        val serverUri = Uri.parse(ncUri)
        mClient = OwnCloudClientFactory.createOwnCloudClient(serverUri, context, true)
        mClient.credentials = OwnCloudCredentialsFactory.newBasicCredentials(ncUsername, ncPassword)

        fileName.set("files here")

        startRefresh()
    }

    /**
     * refresh files list
     */
    private fun startRefresh() {
        val refreshOperation = ReadFolderRemoteOperation(FileUtils.PATH_SEPARATOR)
        refreshOperation.execute(mClient, this, mHandler)
    }

    /**
     * select completed operation type
     */
    override fun onRemoteOperationFinish(caller: RemoteOperation?, result: RemoteOperationResult?) {
        if (!result!!.isSuccess){
            Log.e("REFRESH", result.logMessage, result.exception)
            fileName.set("cheta ne tak")
        } else when (caller) {
            is ReadFolderRemoteOperation -> onSuccessfulRefresh(result)
            is ReadFileRemoteOperation -> {}
        }
    }

    /**
     * update files list
     */
    private fun onSuccessfulRefresh(result: RemoteOperationResult) {

        val files = ArrayList<RemoteFile>()
        for (obj in result.data){
            files.add(obj as RemoteFile)
        }

        if (files.size > 0){
            fileName.set(files[0].remotePath)
        } else {
            fileName.set("failav netu ((")
        }

    }
}