package com.example.stp_calculator.ui.home

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
import com.example.stp_calculator.databinding.FragmentHomeBinding
import com.example.stp_calculator.ui.Functions
import com.example.stp_calculator.ui.Operation
import com.google.android.material.slider.Slider
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class HomeFragment : Fragment() {

	private lateinit var homeViewModel: HomeViewModel
	private var _binding: FragmentHomeBinding? = null

	private val binding get() = _binding!!

	private var btns = listOf<Button>()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
		_binding = FragmentHomeBinding.inflate(inflater, container, false)
		val root: View = binding.root

		btns = with(binding) {
			listOf(b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, bA, bB, bC, bD, bE, bF)
		}

		buttonSet()

		homeViewModel.history.onEach {
			binding.text.text = it
		}.launchIn(lifecycle.coroutineScope)

		homeViewModel.text.observe(viewLifecycleOwner, Observer {
			binding.edit.setText(it)
		})

		return root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

	private fun buttonSet(){
		binding.edit.inputType = InputType.TYPE_NULL

		for (i in 0..15){
			btns[i].setOnClickListener {
				homeViewModel.addNumber(i)
			}
		}

		with(binding){
			bMc.setOnClickListener {
				homeViewModel.memoryClear()
				bMc.isEnabled = false
				bMr.isEnabled = false
			}
			bMr.setOnClickListener { homeViewModel.memoryRead() }
			bMp.setOnClickListener {
				if(homeViewModel.memoryAdd()){
					bMc.isEnabled = true
					bMr.isEnabled = true
				}
			}
			bMs.setOnClickListener {
				if(homeViewModel.memorySave()){
					bMc.isEnabled = true
					bMr.isEnabled = true
				}
			}

			bCl.setOnClickListener { homeViewModel.clear() }
			bClr.setOnClickListener { homeViewModel.erase() }

			bDot.setOnClickListener { homeViewModel.addDot() }

			bPlus.setOnClickListener { homeViewModel.makeOperation(Operation.PLUS) }
			bMinus.setOnClickListener { homeViewModel.makeOperation(Operation.MINUS) }
			bMul.setOnClickListener { homeViewModel.makeOperation(Operation.MUL) }
			bDiv.setOnClickListener { homeViewModel.makeOperation(Operation.DIV) }

			bSqr.setOnClickListener { homeViewModel.makeFunctions(Functions.SQR) }
			bRev.setOnClickListener { homeViewModel.makeFunctions(Functions.REV) }

			bEx.setOnClickListener { homeViewModel.equalButton() }

			slide.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
				override fun onStartTrackingTouch(slider: Slider) {
				}

				override fun onStopTrackingTouch(slider: Slider) {
					val slideVal = binding.slide.value.toInt()
					homeViewModel.setBase(slideVal)
					binding.slideText.text = slideVal.toString()
					for (i in 1..15) {
						btns[i].isEnabled = i < slideVal
					}
				}
			})
		}
	}
}