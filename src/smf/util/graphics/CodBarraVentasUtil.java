/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smf.util.graphics;


import java.awt.Color;
import java.awt.SystemColor;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Map;
import smf.util.NumbersUtil;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author manuel.japon
 */
public class CodBarraVentasUtil {
    
    
    public static ChartPanel getChart(String chartTitle, Map<Integer, BigDecimal> mapDatos){
        JFreeChart barChart = ChartFactory.createBarChart(
         chartTitle,           
         "Día",
         "Ventas",
         createDataset(mapDatos),          
         PlotOrientation.VERTICAL,           
         true, true, false);
        
        CategoryPlot cplot = (CategoryPlot)barChart.getPlot();
        cplot.setBackgroundPaint(SystemColor.inactiveCaption);//change background color

        ((BarRenderer)cplot.getRenderer()).setBarPainter(new StandardBarPainter());
        BarRenderer r = (BarRenderer)barChart.getCategoryPlot().getRenderer();
        r.setSeriesPaint(0, new Color(81, 135, 227));
         
        ChartPanel chartPanel = new ChartPanel( barChart );        
        chartPanel.setPreferredSize(new java.awt.Dimension( 560 , 367 ) );      
        return chartPanel;
    }
    
    private static CategoryDataset createDataset(Map<Integer, BigDecimal> mapDatos ) {
        final String fiat = "ventas";        
        /*final String audi = "AUDI";        
        final String ford = "FORD";
        final String speed = "Speed";        
        final String millage = "Millage";        
        final String userrating = "User Rating";        
        final String safety = "safety";        */
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        
        BigDecimal lunValue = mapDatos.containsKey(Calendar.MONDAY)? mapDatos.get(Calendar.MONDAY):BigDecimal.ZERO;
        BigDecimal marValue = mapDatos.containsKey(Calendar.TUESDAY)? mapDatos.get(Calendar.TUESDAY):BigDecimal.ZERO;
        BigDecimal mierValue = mapDatos.containsKey(Calendar.WEDNESDAY)? mapDatos.get(Calendar.WEDNESDAY):BigDecimal.ZERO;
        BigDecimal jueValue = mapDatos.containsKey(Calendar.THURSDAY)? mapDatos.get(Calendar.THURSDAY):BigDecimal.ZERO;
        BigDecimal vierValue = mapDatos.containsKey(Calendar.FRIDAY)? mapDatos.get(Calendar.FRIDAY):BigDecimal.ZERO;
        BigDecimal sabValue = mapDatos.containsKey(Calendar.SATURDAY)? mapDatos.get(Calendar.SATURDAY):BigDecimal.ZERO;
        BigDecimal domValue = mapDatos.containsKey(Calendar.SUNDAY)? mapDatos.get(Calendar.SUNDAY):BigDecimal.ZERO;
        
        dataset.addValue( NumbersUtil.round2(lunValue) , fiat , "Lun" );
        dataset.addValue( NumbersUtil.round2(marValue) , fiat , "Mar" );
        dataset.addValue( NumbersUtil.round2(mierValue) , fiat , "Mi" );
        dataset.addValue( NumbersUtil.round2(jueValue) , fiat , "Ju" );
        dataset.addValue( NumbersUtil.round2(vierValue) , fiat , "Vi" );
        dataset.addValue( NumbersUtil.round2(sabValue) , fiat , "Sáb" );
        dataset.addValue( NumbersUtil.round2(domValue) , fiat , "Dom" );
        
        return  dataset;
    }
}