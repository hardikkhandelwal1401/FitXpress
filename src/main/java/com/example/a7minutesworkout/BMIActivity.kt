package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.a7minutesworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {
    companion object{
        private const val METRIC_UNITS_VIEW="METRIC_UNITS_VIEW"
        private const val US_UNITS_VIEW="US_UNITS_VIEW"
    }

    private var binding:ActivityBmiBinding?=null
    private var currentVisibleView:String= METRIC_UNITS_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarBmiActivity)
        if(supportActionBar!=null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title="CALCULATE BMI"
        }
        binding?.toolbarBmiActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        makeVisibleMetricUnitsView()

        binding?.rgUnits?.setOnCheckedChangeListener { _, checkedId:Int ->
            if(checkedId==R.id.rbMetricUnits){
                makeVisibleUSUnitsView()
            }else{
                makeVisibleUSUnitsView()
            }
        }



        binding?.btnCalculateUnits?.setOnClickListener {
            calculateUnits()
        }
    }


    private fun makeVisibleMetricUnitsView(){
        currentVisibleView= METRIC_UNITS_VIEW
        binding?.tilWeight?.visibility=View.VISIBLE
        binding?.tilHeight?.visibility=View.VISIBLE
        binding?.tilUsMetricUnitWeight?.visibility=View.INVISIBLE
        binding?.tilMetricUsUnitHeightFeet?.visibility=View.INVISIBLE
        binding?.tilMetricUsUnitHeightInch?.visibility=View.INVISIBLE

        binding?.etHeight?.text!!.clear()
        binding?.etWeight?.text!!.clear()

        binding?.llDisplayBMIResult?.visibility=View.INVISIBLE

    }

    private fun makeVisibleUSUnitsView(){
        currentVisibleView= US_UNITS_VIEW
        binding?.tilWeight?.visibility=View.INVISIBLE
        binding?.tilHeight?.visibility=View.INVISIBLE
        binding?.tilUsMetricUnitWeight?.visibility=View.VISIBLE
        binding?.tilMetricUsUnitHeightFeet?.visibility=View.VISIBLE
        binding?.tilMetricUsUnitHeightInch?.visibility=View.VISIBLE

        binding?.etUsMetricUnitHeightFeet?.text!!.clear()
        binding?.etUsMetricUnitHeightInch?.text!!.clear()
        binding?.etUsMetricUnitWeight?.text!!.clear()

        binding?.llDisplayBMIResult?.visibility=View.INVISIBLE

    }

    private fun displayBMIResults(bmi:Float){
        val bmiLabel:String
        val bmiDescription:String

        if(bmi.compareTo(15f)<=0){
            bmiLabel="Very Severly Underweight"
            bmiDescription="Oops! You really need to take better care of yourself! Eat More!"
        }
        else if (bmi.compareTo(15f)>0 && bmi.compareTo(16f)<=0){
            bmiLabel="Severly Underweight"
            bmiDescription="Oops! You really need to take better care of yourself! Eat More!"
        }
        else if (bmi.compareTo(16f)>0 && bmi.compareTo(18.5f)<=0){
            bmiLabel="Underweight"
            bmiDescription="Oops! You really need to take better care of yourself! Eat More!"
        }
        else if (bmi.compareTo(18.5f)>0 && bmi.compareTo(25f)<=0){
            bmiLabel="Normal"
            bmiDescription="Congratulations! You are in good shape!"
        }
        else{
            bmiLabel="Overweight!"
            bmiDescription="Oops! You really need to take better care of yourself! Workout!"
        }

        val bmiValue=BigDecimal(bmi.toDouble()).setScale(2,RoundingMode.HALF_EVEN).toString()
        binding?.llDisplayBMIResult?.visibility= View.VISIBLE
        binding?.tvBMIValue?.text=bmiValue
        binding?.tvBMIType?.text=bmiLabel
        binding?.tvBMIDescription?.text=bmiDescription
    }

    private fun validateMatricUnits():Boolean{
        var isValid=true
        if(binding?.etWeight?.text.toString().isEmpty()){
            isValid=false
        }
        else if(binding?.etHeight?.text.toString().isEmpty()){
            isValid=false
        }

        return isValid
    }

    private fun calculateUnits(){
        if(currentVisibleView== METRIC_UNITS_VIEW){
            if(validateMatricUnits()){
                val heightValue:Float=binding?.etHeight?.text.toString().toFloat()/100
                val weightValue:Float=binding?.etWeight?.text.toString().toFloat()
                val bmi=weightValue/(heightValue*heightValue)

                displayBMIResults(bmi)

            }else{
                Toast.makeText(this@BMIActivity,"Please enter valid values.",Toast.LENGTH_SHORT).show()
            }
        }
        else{
            if(validateUSUnits()){
                val heightValueInFeet:String=binding?.etUsMetricUnitHeightFeet?.text.toString()
                val heightValueInInch:String=binding?.etUsMetricUnitHeightInch?.text.toString()
                val usWeightValue:Float=binding?.etUsMetricUnitWeight?.text.toString().toFloat()

                val heightValue=heightValueInInch.toFloat()+heightValueInFeet.toFloat()*12

                val bmi=703*(usWeightValue/(heightValue*heightValue))

                displayBMIResults(bmi)
            }else{
                Toast.makeText(this@BMIActivity,"Please enter valid values.",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateUSUnits():Boolean{
        var isValid=true
        if(binding?.etUsMetricUnitHeightFeet?.text.toString().isEmpty()){
            isValid=false
        }
        else if(binding?.etUsMetricUnitHeightInch?.text.toString().isEmpty()){
            isValid=false
        }
        else if(binding?.etUsMetricUnitWeight?.text.toString().isEmpty()){
            isValid=false
        }

        return isValid
    }
}