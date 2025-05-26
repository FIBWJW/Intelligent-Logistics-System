<template>
    
    <div ref="chartRef" style="width: 100%; height: 400px;"></div>
  </template>
  
  <script setup>
  import { onMounted, ref, watch, onBeforeUnmount } from 'vue'
  import * as echarts from 'echarts'
  
  const chartRef = ref(null)
  let myChart = null
  
  onMounted(() => {
    myChart = echarts.init(chartRef.value)
  
    const option = {
      tooltip: {
        trigger: 'item'
      },
      legend: {
        top: '5%',
        left: 'center'
      },
      series: [
        {
          name: '车辆状态',
          type: 'pie',
          radius: ['40%', '70%'],
          avoidLabelOverlap: false,
          label: {
            show: false,
            position: 'center'
          },
          emphasis: {
            label: {
              show: true,
              fontSize: 24,
              fontWeight: 'bold'
            }
          },
          labelLine: {
            show: false
          },
          data: [
            { value: 7, name: 'IN_USE' },
            { value: 5, name: 'AVAILABLE' }
          ]
        }
      ]
    }
  
    myChart.setOption(option)
  
    window.addEventListener('resize', () => {
      myChart && myChart.resize()
    })
  })
  
  onBeforeUnmount(() => {
    myChart && myChart.dispose()
  })
  </script>
  