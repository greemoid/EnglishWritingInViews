package com.example.englishwritinginviews.presentation.listOfQuestions

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.englishwritinginviews.R
import com.example.englishwritinginviews.databinding.FragmentListOfQuestionsBinding
import com.example.englishwritinginviews.presentation.core.BaseFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ListOfQuestionsFragment :
    BaseFragment<FragmentListOfQuestionsBinding>(FragmentListOfQuestionsBinding::inflate) {

    private val viewModel: ListOfQuestionsViewModel by viewModels()
    private val setOfDifficulties = HashSet<String>()
    private val checkedCheckBoxes = HashSet<Int>()


    override fun init() {
        val adapter = QuestionListAdapter()
        val recyclerView = binding.rvQuestions
        recyclerView.adapter = adapter


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.questionsState.collect { list ->
                    adapter.differ.submitList(list)
                }
            }
        }

        handleHearts()


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.connectionState.collect { state ->
                    if (!state.isConnected) {
                        binding.progressConnection.visibility = View.VISIBLE
                        binding.tvConnectionStatus.visibility = View.VISIBLE
                        binding.tvConnectionStatus.text = state.message

                        binding.ivHeartFirst.visibility = View.INVISIBLE
                        binding.ivHeartSecond.visibility = View.INVISIBLE
                        binding.ivHeartThird.visibility = View.INVISIBLE

                    } else {
                        binding.progressConnection.visibility = View.INVISIBLE
                        binding.tvConnectionStatus.visibility = View.INVISIBLE

                        handleHearts()
                    }
                }
            }
        }

        adapter.setOnItemClickListener { question ->
            val bundle = Bundle()
            bundle.putSerializable("question", question)
            viewModel.isLifeAvailable.observe(viewLifecycleOwner) {
                if (it) {
                    if (question.isAnswered) {
                        findNavController().navigate(
                            R.id.action_listOfQuestionsFragment_to_answerFragment,
                            bundle
                        )
                    } else {
                        findNavController().navigate(
                            R.id.action_listOfQuestionsFragment_to_questionFragment,
                            bundle
                        )
                    }
                } else {
                    viewModel.timeDiff.observe(viewLifecycleOwner) {
                        Toast.makeText(
                            requireContext(),
                            "There are no hearts. Come back in $it",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

        }

        binding.btnStrick.setOnClickListener {
            findNavController().navigate(R.id.action_listOfQuestionsFragment_to_strikeFragment)
        }


        binding.btnFilterMenu.setOnClickListener {
            openBottomSheetDialog()
        }

        binding.btnAccount.setOnClickListener {
            findNavController().navigate(R.id.action_listOfQuestionsFragment_to_accountFragment)
        }
    }

    private fun handleHearts() {

        viewModel.livesCount.observe(viewLifecycleOwner) {
            when (it) {
                3 -> {
                    binding.ivHeartFirst.visibility = View.VISIBLE
                    binding.ivHeartSecond.visibility = View.VISIBLE
                    binding.ivHeartThird.visibility = View.VISIBLE
                }

                2 -> {

                    binding.ivHeartFirst.visibility = View.VISIBLE
                    binding.ivHeartSecond.visibility = View.VISIBLE
                    binding.ivHeartThird.visibility = View.INVISIBLE
                }

                1 -> {
                    binding.ivHeartFirst.visibility = View.VISIBLE
                    binding.ivHeartSecond.visibility = View.INVISIBLE
                    binding.ivHeartThird.visibility = View.INVISIBLE
                }

                else -> {
                    binding.ivHeartFirst.visibility = View.INVISIBLE
                    binding.ivHeartSecond.visibility = View.INVISIBLE
                    binding.ivHeartThird.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun openBottomSheetDialog() {
        val dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.bottom_sheet_filter_layout, null)
        val btnApply = view.findViewById<Button>(R.id.btn_apply)

        dialog.setCancelable(false)
        dialog.setContentView(view)
        dialog.show()

        val checkBoxListener = CompoundButton.OnCheckedChangeListener { checkBox, isChecked ->
            when (checkBox.id) {
                R.id.is_easy -> {
                    if (isChecked) {
                        setOfDifficulties.add("Easy")
                        checkedCheckBoxes.add(R.id.is_easy)
                    } else {
                        checkedCheckBoxes.remove(R.id.is_easy)
                        setOfDifficulties.remove("Easy")
                    }
                }

                R.id.is_medium -> {
                    if (isChecked) {
                        setOfDifficulties.add("Medium")
                        checkedCheckBoxes.add(R.id.is_medium)
                    } else {
                        checkedCheckBoxes.remove(R.id.is_medium)
                        setOfDifficulties.remove("Medium")
                    }
                }

                R.id.is_hard -> {
                    if (isChecked) {
                        setOfDifficulties.add("Hard")
                        checkedCheckBoxes.add(R.id.is_hard)
                    } else {
                        checkedCheckBoxes.remove(R.id.is_hard)
                        setOfDifficulties.remove("Hard")
                    }
                }

                else -> {
                    checkedCheckBoxes.clear()
                    setOfDifficulties.clear()
                }
            }


        }

        val checkbox1 = view.findViewById<CheckBox>(R.id.is_easy)
        val checkbox2 = view.findViewById<CheckBox>(R.id.is_medium)
        val checkbox3 = view.findViewById<CheckBox>(R.id.is_hard)

        checkbox1.setOnCheckedChangeListener(checkBoxListener)
        checkbox2.setOnCheckedChangeListener(checkBoxListener)
        checkbox3.setOnCheckedChangeListener(checkBoxListener)

        checkbox1.isChecked = checkedCheckBoxes.contains(checkbox1.id)
        checkbox2.isChecked = checkedCheckBoxes.contains(checkbox2.id)
        checkbox3.isChecked = checkedCheckBoxes.contains(checkbox3.id)



        btnApply.setOnClickListener {
            viewModel.getQuestions(setOfDifficulties)
            dialog.dismiss()
        }


    }


}
