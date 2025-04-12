package za.ac.iie.mealfinder

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var timeInput: EditText
    private lateinit var suggestButton: Button
    private lateinit var clearButton: Button
    private lateinit var resultTextView: TextView
    private lateinit var mealImageView: ImageView
    private lateinit var acceptableInputsTextView: TextView

    private val breakfastMeals = listOf("omelette", "pancakes", "granola_bowl", "french_toast")
    private val midMorningSnacks = listOf("fruit_yogurt", "banana", "granola_bar", "smoothie")
    private val lunchMeals = listOf("chicken_wrap", "grilled_sandwich", "buddha_bowl", "sushi")
    private val midAfternoonSnacks = listOf("nuts_raisins", "apple_slices", "cheese_crackers", "popcorn")
    private val dinnerMeals = listOf("roast_chicken", "pasta_alfredo", "stir_fry", "curry_rice")
    private val afterDinnerSnacks = listOf("dark_chocolate", "frozen_yogurt", "warm_milk", "fruit_salad")

    private val acceptedInputs = listOf(
        "Morning",
        "Mid-morning snack",
        "Afternoon",
        "Mid-afternoon snack",
        "Dinner",
        "After dinner snack"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)

        timeInput = findViewById(R.id.timeInput)
        suggestButton = findViewById(R.id.suggestButton)
        clearButton = findViewById(R.id.clearButton)
        resultTextView = findViewById(R.id.resultTextView)
        mealImageView = findViewById(R.id.mealImageView)
        acceptableInputsTextView = findViewById(R.id.acceptableInputsTextView)

        acceptableInputsTextView.text = """
            ✅ Acceptable time inputs:
            - Morning
            - Mid-morning snack
            - Afternoon
            - Mid-afternoon snack
            - Dinner
            - After dinner snack
        """.trimIndent()

        suggestButton.setOnClickListener {
            val timeOfDay = timeInput.text.toString().trim().lowercase()

            val (mealList, displayTime) = when (timeOfDay) {
                "morning" -> breakfastMeals to "Morning"
                "mid-morning snack" -> midMorningSnacks to "Mid-morning snack"
                "afternoon" -> lunchMeals to "Afternoon"
                "mid-afternoon snack" -> midAfternoonSnacks to "Mid-afternoon snack"
                "dinner" -> dinnerMeals to "Dinner"
                "after dinner snack" -> afterDinnerSnacks to "After dinner snack"
                else -> {
                    Toast.makeText(
                        this,
                        "Invalid input. Please enter one of the following:\n${acceptedInputs.joinToString("\n- ", prefix = "- ") }",
                        Toast.LENGTH_LONG
                    ).show()
                    return@setOnClickListener
                }
            }

            val suggestion = mealList.random()
            val imageResId = getImageResId(suggestion)

            resultTextView.text = "Suggested meal for $displayTime:\n\n${formatMealName(suggestion)}"

            if (imageResId != 0) {
                mealImageView.setImageResource(imageResId)
            } else {
                mealImageView.setImageResource(android.R.color.transparent)
                Toast.makeText(this, "No image found for $suggestion.", Toast.LENGTH_SHORT).show()
            }
        }

        clearButton.setOnClickListener {
            timeInput.text.clear()
            resultTextView.text = ""
            mealImageView.setImageDrawable(null)
        }
    }

    private fun getImageResId(meal: String): Int {
        return resources.getIdentifier(meal, "drawable", packageName)
    }

    private fun formatMealName(meal: String): String {
        return meal.replace("_", " ")
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }
}
