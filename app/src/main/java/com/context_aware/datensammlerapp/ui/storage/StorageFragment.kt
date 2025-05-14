package com.context_aware.datensammlerapp.ui.storage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.context_aware.datensammlerapp.R
import java.io.File

class StorageFragment : Fragment() {

    private lateinit var fileListContainer: LinearLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_storage, container, false)
        fileListContainer = view.findViewById(R.id.file_list_container)
        listFiles()
        return view
    }

    private fun listFiles() {
        val dir = requireContext().getExternalFilesDir(null) ?: return
        val csvFiles = dir.listFiles { file -> file.extension == "csv" } ?: return

        fileListContainer.removeAllViews()

        for (file in csvFiles) {
            val fileView = layoutInflater.inflate(R.layout.storage_file_entry, fileListContainer, false)

            val nameView = fileView.findViewById<TextView>(R.id.file_name)
            val shareButton = fileView.findViewById<Button>(R.id.btn_share)

            nameView.text = file.name
            shareButton.setOnClickListener {
                shareFile(file)
            }

            fileListContainer.addView(fileView)
        }
    }

    private fun shareFile(file: File) {
        val uri: Uri = FileProvider.getUriForFile(
            requireContext(),
            requireContext().packageName + ".fileprovider",
            file
        )

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/csv"
            putExtra(Intent.EXTRA_STREAM, uri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startActivity(Intent.createChooser(shareIntent, "Datei teilen mit"))
    }
}
