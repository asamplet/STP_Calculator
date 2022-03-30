package com.example.stp_calculator.ui.slideshow

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import com.example.stp_calculator.R
import com.example.stp_calculator.databinding.FragmentSlideshowBinding
import com.example.stp_calculator.ui.Functions
import com.example.stp_calculator.ui.Operation
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SlideshowFragment : Fragment() {

	private lateinit var slideshowViewModel: SlideshowViewModel
	private var _binding: FragmentSlideshowBinding? = null

	// This property is only valid between onCreateView and
	// onDestroyView.
	private val binding get() = _binding!!

	private var btns = listOf<Button>()

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		slideshowViewModel = ViewModelProvider(this).get(SlideshowViewModel::class.java)
		_binding = FragmentSlideshowBinding.inflate(inflater, container, false)
		val root: View = binding.root

		btns = with(binding) {
			listOf(b0, b1, b2, b3, b4, b5, b6, b7, b8, b9)
		}

		buttonSet()

		slideshowViewModel.history.onEach {
			binding.text.text = it
		}.launchIn(lifecycle.coroutineScope)

		slideshowViewModel.text.observe(viewLifecycleOwner, Observer {
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
				slideshowViewModel.addNumber(i)
			}
		}

		with(binding) {
			bMc.setOnClickListener {
				slideshowViewModel.memoryClear()
				bMc.isEnabled = false
				bMr.isEnabled = false
			}
			bMr.setOnClickListener { slideshowViewModel.memoryRead() }
			bMp.setOnClickListener {
				if (slideshowViewModel.memoryAdd()) {
					bMc.isEnabled = true
					bMr.isEnabled = true
				}
			}
			bMs.setOnClickListener {
				if (slideshowViewModel.memorySave()) {
					bMc.isEnabled = true
					bMr.isEnabled = true
				}
			}

			bCl.setOnClickListener { slideshowViewModel.clear() }
			bClr.setOnClickListener { slideshowViewModel.erase() }

			bDrob.setOnClickListener { slideshowViewModel.addPlus() }
			bI.setOnClickListener { slideshowViewModel.addI() }
			bDot.setOnClickListener { slideshowViewModel.addDot() }

			bPlus.setOnClickListener { slideshowViewModel.makeOperation(Operation.PLUS) }
			bMinus.setOnClickListener { slideshowViewModel.makeOperation(Operation.MINUS) }
			bMul.setOnClickListener { slideshowViewModel.makeOperation(Operation.MUL) }
			bDiv.setOnClickListener { slideshowViewModel.makeOperation(Operation.DIV) }

			bSqr.setOnClickListener { slideshowViewModel.makeFunctions(Functions.SQR) }
			bRev.setOnClickListener { slideshowViewModel.makeFunctions(Functions.REV) }

			bEx.setOnClickListener { slideshowViewModel.equalButton() }
		}
	}
}