package com.blogspot.agusticar.miscuentasv2.components

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.documentfile.provider.DocumentFile
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.main.data.database.dao.EntryDao
import com.blogspot.agusticar.miscuentasv2.newamount.view.EntriesViewModel
import com.blogspot.agusticar.miscuentasv2.setting.SettingViewModel
import com.blogspot.agusticar.miscuentasv2.utils.Utils
import com.blogspot.agusticar.miscuentasv2.utils.Utils.Companion.readCsvFile
import com.blogspot.agusticar.miscuentasv2.utils.Utils.Companion.writeCsvFile
import okhttp3.internal.toImmutableList
@Composable
fun FileOperationsExport(entriesViewModel: EntriesViewModel, settingViewModel: SettingViewModel) {
    val fileName by settingViewModel.fileName.observeAsState("")
    val showExportDialog by settingViewModel.showExportDialog.observeAsState(false)
    val listOfEntries by entriesViewModel.listOfEntriesDataBase.observeAsState(listOf())
    entriesViewModel.getAllEntriesDataBase()
    val context = LocalContext.current
    var directory: DocumentFile? = null // Initialize as null

    // For handling export directory selection
    val pickerExportLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                directory = DocumentFile.fromTreeUri(context, uri) // Direct assignment
                if (directory != null && directory!!.isDirectory) {
                    settingViewModel.onShowExportDialog(true)
                }
            }
        }
    }

    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
    pickerExportLauncher.launch(intent)

    if (showExportDialog) {
        ModelDialogWithTextField(
            title = R.string.exportData,
            message = R.string.filename,
            showDialog = true,
            textFieldValue = fileName,
            onValueChange = { settingViewModel.onFileNameChanged(it) },
            onConfirm = {
                val entries = listOfEntries.toMutableList()

                // Ensure directory is not null before writing the file
                directory?.let {
                    writeCsvFile(entries, context, "$fileName.csv", it)
                }

                settingViewModel.onShowExportDialog(false)
            },
            onDismiss = {
                settingViewModel.onShowExportDialog(false)
            }
        )
    }
}
