package com.example.cloudproject.Fragments

import android.R.attr
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cloudproject.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_settings, container, false)

        val pieChart = root.findViewById<PieChart>(R.id.pieChart)
        val pieChart2 = root.findViewById<PieChart>(R.id.pieChart2)
        val pieChart3 = root.findViewById<PieChart>(R.id.pieChart3)


        //1
        val NoOfEmp1 = ArrayList<PieEntry>()
        NoOfEmp1.add(PieEntry(38F, "الفلسطنينين"))
        NoOfEmp1.add(PieEntry(62f, "اليهود"))

        val dataSet1 = PieDataSet(NoOfEmp1, "سكان القدس")

        dataSet1.setDrawIcons(false)
        dataSet1.sliceSpace = 3f
        dataSet1.iconsOffset = MPPointF(0F, 40F)
        dataSet1.selectionShift = 5f
        dataSet1.setColors(*ColorTemplate.COLORFUL_COLORS)

        val data1 = PieData(dataSet1)
        data1.setValueTextSize(16f)
        data1.setValueTextColor(Color.WHITE)
        pieChart.data = data1
        pieChart.highlightValues(null)
        pieChart.invalidate()
        pieChart.animateXY(5000, 5000)
        val legend: Legend = pieChart.legend
        legend.isEnabled = false
        pieChart.centerText = "السكان في القدس"
        pieChart.description.isEnabled = false

        //2
        val NoOfEmp2 = ArrayList<PieEntry>()
        NoOfEmp2.add(PieEntry(80F, "المساجد"))
        NoOfEmp2.add(PieEntry(10F, "الكنائس"))
        NoOfEmp2.add(PieEntry(10f, "المتاحف"))

        val dataSet2 = PieDataSet(NoOfEmp2, "المعالم في القدس")

        dataSet2.setDrawIcons(false)
        dataSet2.sliceSpace = 3f
        dataSet2.iconsOffset = MPPointF(0F, 40F)
        dataSet2.selectionShift = 5f
        dataSet2.setColors(*ColorTemplate.COLORFUL_COLORS)

        val data2 = PieData(dataSet2)
        data2.setValueTextSize(16f)
        data2.setValueTextColor(Color.WHITE)
        pieChart2.data = data2
        pieChart2.highlightValues(null)
        pieChart2.invalidate()
        pieChart2.animateXY(5000, 5000)
        val legend2: Legend = pieChart2.legend
        legend2.isEnabled = false
        pieChart2.centerText = "المعالم في القدس"
        pieChart2.description.isEnabled = false


        //3
        val NoOfEmp3 = ArrayList<PieEntry>()
        NoOfEmp3.add(PieEntry(7F, "مفتوحة"))
        NoOfEmp3.add(PieEntry(4f, " مقفلة"))

        val dataSet3 = PieDataSet(NoOfEmp3, "الابواب  في القدس")

        dataSet3.setDrawIcons(false)
        dataSet3.sliceSpace = 3f
        dataSet3.iconsOffset = MPPointF(0F, 40F)
        dataSet3.selectionShift = 5f
        dataSet3.setColors(*ColorTemplate.COLORFUL_COLORS)

        val data3 = PieData(dataSet3)
        data3.setValueTextSize(16f)
        data3.setValueTextColor(Color.WHITE)
        pieChart3.data = data3
        pieChart3.highlightValues(null)
        pieChart3.invalidate()
        pieChart3.animateXY(5000, 5000)
        val legend3: Legend = pieChart3.legend
        legend3.isEnabled = false
        pieChart3.centerText = "الابواب  في القدس"
        pieChart3.description.isEnabled = false
        return root
    }

}