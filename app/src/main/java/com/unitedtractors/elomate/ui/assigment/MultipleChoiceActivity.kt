package com.unitedtractors.elomate.ui.assigment

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.unitedtractors.elomate.R

class MultipleChoiceActivity : AppCompatActivity() {

    // Declare CheckBoxes and Layouts
    private lateinit var checkBox1: CheckBox
    private lateinit var checkBox2: CheckBox
    private lateinit var checkBox3: CheckBox
    private lateinit var checkBox4: CheckBox
    private lateinit var checkBox5: CheckBox

    private lateinit var layoutCheck1: LinearLayout
    private lateinit var layoutCheck2: LinearLayout
    private lateinit var layoutCheck3: LinearLayout
    private lateinit var layoutCheck4: LinearLayout
    private lateinit var layoutCheck5: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multiple_choice)

        // Initialize CheckBoxes
        checkBox1 = findViewById(R.id.check1)
        checkBox2 = findViewById(R.id.check2)
        checkBox3 = findViewById(R.id.check3)
        checkBox4 = findViewById(R.id.check4)
        checkBox5 = findViewById(R.id.check5)

        // Initialize LinearLayouts
        layoutCheck1 = findViewById(R.id.layoutCheck1)
        layoutCheck2 = findViewById(R.id.layoutCheck2)
        layoutCheck3 = findViewById(R.id.layoutCheck3)
        layoutCheck4 = findViewById(R.id.layoutCheck4)
        layoutCheck5 = findViewById(R.id.layoutCheck5)

        // Set click listeners for LinearLayouts to toggle CheckBoxes
        setLinearLayoutClickListener(layoutCheck1, checkBox1)
        setLinearLayoutClickListener(layoutCheck2, checkBox2)
        setLinearLayoutClickListener(layoutCheck3, checkBox3)
        setLinearLayoutClickListener(layoutCheck4, checkBox4)
        setLinearLayoutClickListener(layoutCheck5, checkBox5)

        // Set single selection behavior for checkboxes
        setSingleSelectionBehavior(checkBox1, checkBox2, checkBox3, checkBox4, checkBox5)

        // Set click listener for the back button
        findViewById<View>(R.id.btn_close).setOnClickListener {
            finish() // Go back to the previous screen
        }
    }

    // Function to manage single selection behavior for the checkboxes
    private fun setSingleSelectionBehavior(vararg checkBoxes: CheckBox) {
        for (checkBox in checkBoxes) {
            checkBox.setOnCheckedChangeListener { selectedCheckBox, isChecked ->
                if (isChecked) {
                    // Uncheck all other checkboxes when one is checked
                    checkBoxes.forEach {
                        if (it != selectedCheckBox) {
                            it.isChecked = false
                        }
                    }
                }
            }
        }
    }

    // Function to toggle checkbox when the parent layout is clicked
    private fun setLinearLayoutClickListener(layout: LinearLayout, checkBox: CheckBox) {
        layout.setOnClickListener {
            checkBox.isChecked = !checkBox.isChecked // Toggle the checkbox state
        }
    }
}
