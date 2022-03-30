package com.example.stp_calculator.ui.gallery

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import com.example.stp_calculator.databinding.FragmentGalleryBinding
import com.example.stp_calculator.ui.Functions
import com.example.stp_calculator.ui.Operation
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class GalleryFragment : Fragment() {

	private lateinit var galleryViewModel: GalleryViewModel
	private var _binding: FragmentGalleryBinding? = null

	// This property is only valid between onCreateView and
	// onDestroyView.
	private val binding get() = _binding!!

	private var btns = listOf<Button>()

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		galleryViewModel = ViewModelProvider(this).get(GalleryViewModel::class.java)
		_binding = FragmentGalleryBinding.inflate(inflater, container, false)
		val root: View = binding.root

		btns = with(binding) {
			listOf(b0, b1, b2, b3, b4, b5, b6, b7, b8, b9)
		}

		buttonSet()

		galleryViewModel.history.onEach {
			binding.text.text = it
		}.launchIn(lifecycle.coroutineScope)

		galleryViewModel.text.observe(viewLifecycleOwner, Observer {
			binding.edit.setText(it)
		})

		return root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	private fun buttonSet() {
		binding.edit.inputType = InputType.TYPE_NULL

		for (i in 0..9) {
			btns[i].setOnClickListener {
				galleryViewModel.addNumber(i)
			}
		}

		with(binding) {
			bMc.setOnClickListener {
				galleryViewModel.memoryClear()
				bMc.isEnabled = false
				bMr.isEnabled = false
			}
			bMr.setOnClickListener { galleryViewModel.memoryRead() }
			bMp.setOnClickListener {
				if (galleryViewModel.memoryAdd()) {
					bMc.isEnabled = true
					bMr.isEnabled = true
				}
			}
			bMs.setOnClickListener {
				if (galleryViewModel.memorySave()) {
					bMc.isEnabled = true
					bMr.isEnabled = true
				}
			}

			bCl.setOnClickListener { galleryViewModel.clear() }
			bClr.setOnClickListener { galleryViewModel.erase() }

			bDrob.setOnClickListener { galleryViewModel.addDot() }

			bPlus.setOnClickListener { galleryViewModel.makeOperation(Operation.PLUS) }
			bMinus.setOnClickListener { galleryViewModel.makeOperation(Operation.MINUS) }
			bMul.setOnClickListener { galleryViewModel.makeOperation(Operation.MUL) }
			bDiv.setOnClickListener { galleryViewModel.makeOperation(Operation.DIV) }

			bSqr.setOnClickListener { galleryViewModel.makeFunctions(Functions.SQR) }
			bRev.setOnClickListener { galleryViewModel.makeFunctions(Functions.REV) }

			bEx.setOnClickListener { galleryViewModel.equalButton() }
		}
	}
}