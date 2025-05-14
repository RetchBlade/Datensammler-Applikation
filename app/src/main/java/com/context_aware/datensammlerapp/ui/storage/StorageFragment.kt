package com.context_aware.datensammlerapp.ui.storage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.context_aware.datensammlerapp.R
import com.context_aware.datensammlerapp.data.CSVExporter
import com.context_aware.datensammlerapp.data.SensorDataCollector
import java.io.File
import java.io.FileReader

class StorageFragment : Fragment() {

    private lateinit var fileListContainer: LinearLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_storage, container, false)
        fileListContainer = view.findViewById(R.id.file_list_container)

        val exportAccelButton = Button(requireContext()).apply {
            text = "Accelerometer CSV speichern & teilen"
            setOnClickListener {
                val file = CSVExporter.exportAccel(requireContext())
                previewAndShare(file, "Accelerometer")
            }
        }

        val exportGyroButton = Button(requireContext()).apply {
            text = "Gyroskop CSV speichern & teilen"
            setOnClickListener {
                val file = CSVExporter.exportGyro(requireContext())
                previewAndShare(file, "Gyroskop")
            }
        }

        fileListContainer.addView(exportAccelButton)
        fileListContainer.addView(exportGyroButton)

        return view
    }

    private fun previewAndShare(file: File, label: String) {
        val preview = TextView(requireContext())
        preview.text = getFilePreview(file)
        preview.textSize = 14f
        preview.setPadding(0, 16, 0, 16)

        fileListContainer.addView(TextView(requireContext()).apply {
            text = "Vorschau: $label-Daten"
            textSize = 16f
        })

        fileListContainer.addView(preview)

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
        SensorDataCollector.clearAll()
    }

    private fun getFilePreview(file: File): String {
        return try {
            FileReader(file).useLines { lines ->
                lines.take(4).joinToString(" ")
            }
        } catch (e: Exception) {
            "Fehler beim Lesen der Vorschau: ${e.localizedMessage}"
        }
    }
}
