package com.expense.expensemanager.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.WindowCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.expense.expensemanager.R
import com.expense.expensemanager.databinding.FragmentCreateSavingBinding
import com.expense.expensemanager.viewmodel.CreateGoalViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception


@AndroidEntryPoint
class CreateSavingSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentCreateSavingBinding? = null
    private lateinit var activityResultLauncherIntent: ActivityResultLauncher<Intent>
    private lateinit var activityResultLauncherPermission: ActivityResultLauncher<String>
    private var selectedBitmap : Bitmap? = null
    private val viewModel: CreateGoalViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCreateSavingBinding.inflate(inflater, container, false)
        return binding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.selectImage.setOnClickListener {
            selectImage(it)
        }
        setAdjustResize()
        registerLauncher()
        binding.createSavingGoalButton.setOnClickListener {
            selectedBitmap = if (selectedBitmap == null) {
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_launcher_background)?.toBitmap()
            } else {
                viewModel.makeSmallerBitmap(selectedBitmap!!, 300)
            }
            viewModel.makeGoal(
                name = binding.goalName.text.toString(),
                amount = binding.goalAmount.text.toString(),
                note = binding.goalNote.text.toString(),
                image = selectedBitmap!!
            )
            val action = CreateSavingSheetFragmentDirections.actionCreateSavingSheetFragmentToSavingPage()
            findNavController().navigate(action)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun selectImage(view: View) {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            // Rationale  use
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Snackbar.make(view, "Permission  needed", Snackbar.LENGTH_INDEFINITE)
                    .setAction("apply grant") {
                        // request permission
                        activityResultLauncherPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                    }.show()
            } else {
                //request permission
                activityResultLauncherPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        } else {
            val intentGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncherIntent.launch(intentGallery)
        }
    }
    private fun registerLauncher() {
        activityResultLauncherIntent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val intentFromResult = result.data
                if (intentFromResult != null) {
                    val imageUri = intentFromResult.data
                    if (imageUri != null) {
                        try {
                            //binding.addImage.setImageURI(imageUri)
                            if (Build.VERSION.SDK_INT >= 28) {
                                val source = ImageDecoder.createSource(requireActivity().contentResolver,imageUri)
                                selectedBitmap = ImageDecoder.decodeBitmap(source)
                                binding.selectImage.setImageBitmap(selectedBitmap)
                            } else {
                                selectedBitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, imageUri)
                                binding.selectImage.setImageBitmap(selectedBitmap)
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }
        activityResultLauncherPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            if (result) {
                val intentGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncherIntent.launch(intentGallery)
            } else {
                Toast.makeText(context, "Permission  Denied", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setAdjustResize() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireDialog().window?.let { WindowCompat.setDecorFitsSystemWindows(it, false) }
        } else {
            @Suppress("DEPRECATION")
            requireDialog().window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }
    }
}