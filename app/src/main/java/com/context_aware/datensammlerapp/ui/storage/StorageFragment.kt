package com.context_aware.datensammlerapp.ui.storage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.context_aware.datensammlerapp.R
import com.context_aware.datensammlerapp.databinding.FragmentStorageBinding

class StorageFragment : Fragment() {
    private var _binding: FragmentStorageBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_storage, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
