package com.testing.notessimpleapp

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.testing.notessimpleapp.databinding.ActivityNoteFormBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NoteFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteFormBinding
    private val titleInput by lazy { binding.noteTitleInput }
    private val descInput by lazy { binding.noteDescInput }
    private val charCountText by lazy { binding.charCount }
    private val saveButton by lazy { binding.topAppBar.menu.findItem(R.id.menu1)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewBinding()
        setupEdgeToEdge()
        setupToolbar()
        setupDateDisplay()
        setupFocusListeners()
        setupCharacterCount()
        setupSaveButtonValidation()
    }

    private fun setupViewBinding() {
        binding = ActivityNoteFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupEdgeToEdge() {
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupToolbar() {
        binding.topAppBar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupDateDisplay() {
        val currentDate = getCurrentDate()
        binding.noteDateCreated.text = currentDate
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val currentDate = Date()
        return "${dateFormat.format(currentDate)}   ${timeFormat.format(currentDate)}"
    }

    private fun setupFocusListeners() {
        setupHintToggle(titleInput, R.string.title)
        setupHintToggle(descInput, R.string.isi_catatan)
    }

    private fun setupHintToggle(inputField: EditText, hintResId: Int) {
        inputField.setOnFocusChangeListener { _, hasFocus ->
            inputField.hint = if (hasFocus) "" else getString(hintResId)
        }
    }

    private fun setupCharacterCount() {
        val maxCharacter = 1500
        descInput.filters = arrayOf(InputFilter.LengthFilter(maxCharacter))
        descInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val charCount = s?.length ?: 0
                "$charCount karakter".also { charCountText.text = it }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupSaveButtonValidation() {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                validateSaveButton()
            }
        }

        titleInput.addTextChangedListener(textWatcher)
        descInput.addTextChangedListener(textWatcher)

        saveButton?.isEnabled = false
    }

    private fun validateSaveButton() {
        val title = titleInput.text.toString().trim()
        val description = descInput.text.toString().trim()

        saveButton?.isEnabled = title.isNotBlank() || description.isNotBlank()
    }
}
