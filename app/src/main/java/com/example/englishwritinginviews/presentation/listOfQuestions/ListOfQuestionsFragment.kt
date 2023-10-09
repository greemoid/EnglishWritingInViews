package com.example.englishwritinginviews.presentation.listOfQuestions

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
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
    private val listOfDifficulties = mutableListOf<String>()
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


        adapter.setOnItemClickListener { question ->
            val bundle = Bundle()
            bundle.putSerializable("question", question)
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

        }


        binding.btnFilterMenu.setOnClickListener {
            openBottomSheetDialog()
        }

        binding.btnAccount.setOnClickListener {
            findNavController().navigate(R.id.action_listOfQuestionsFragment_to_accountFragment)
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
                        listOfDifficulties.add("Easy")
                        setOfDifficulties.add("Easy")
                        checkedCheckBoxes.add(R.id.is_easy)
                    } else {
                        checkedCheckBoxes.remove(R.id.is_easy)
                        setOfDifficulties.remove("Easy")
                    }
                }

                R.id.is_medium -> {
                    if (isChecked) {
                        listOfDifficulties.add("Medium")
                        setOfDifficulties.add("Medium")
                        checkedCheckBoxes.add(R.id.is_medium)
                    } else {
                        checkedCheckBoxes.remove(R.id.is_medium)
                        setOfDifficulties.remove("Medium")
                    }
                }

                R.id.is_hard -> {
                    if (isChecked) {
                        listOfDifficulties.add("Hard")
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
            Log.d("asac", setOfDifficulties.toString())
            viewModel.getQuestions(setOfDifficulties.toList())
            dialog.dismiss()
        }


    }


}