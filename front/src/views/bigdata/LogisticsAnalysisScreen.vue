<template>
  <div class="logistics-analysis-screen">
    <div class="row row-top">
      <div class="card card-bubble">
        <div class="chart-title">各车辆类型数量统计</div>
        <v-chart :option="bubbleOption" autoresize style="width:100%;height:150px;" />
      </div>
    </div>
    <div class="row row-middle">
      <div class="card card-map">
        <div class="chart-title">物流情况</div>
        <v-chart :option="mapOption" autoresize style="width:100%;height:350px;" />
      </div>
    </div>
    <div class="row row-bottom">
      <div class="card card-bar">
        <div class="chart-title">各省物流达人人数统计</div>
        <v-chart :option="barOption" autoresize style="width:100%;height:90%;" />
      </div>
      <div class="card card-ring">
        <div class="chart-title">各年龄段占比</div>
        <v-chart :option="ringOption" autoresize style="width:100%;height:90%;" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import VChart from 'vue-echarts'
import { use } from 'echarts/core'
import { BarChart, PieChart, LineChart, ScatterChart, MapChart, EffectScatterChart, LinesChart } from 'echarts/charts'
import { TitleComponent, TooltipComponent, LegendComponent, GridComponent, VisualMapComponent, GeoComponent } from 'echarts/components'
import { CanvasRenderer } from 'echarts/renderers'
import * as echarts from 'echarts'
import axios from 'axios'

use([
  BarChart, PieChart, LineChart, ScatterChart, MapChart, EffectScatterChart, LinesChart,
  TitleComponent, TooltipComponent, LegendComponent, GridComponent, VisualMapComponent, GeoComponent, CanvasRenderer
])

const bubbleOption = ref({
  color: ['#00cfff', '#ffb800', '#ff6b6b', '#ff7cae', '#a0ff00', '#ff00e6', '#00ffb7', '#ffae00', '#ff00a6', '#00aeff'],
  tooltip: {},
  legend: {
    data: [],
    top: 0,
    textStyle: { color: '#fff', fontSize: 12 },
    itemWidth: 12,
    itemHeight: 12
  },
  grid: { top: 40, bottom: 30, left: 60, right: 30 },
  xAxis: {
    type: 'category',
    data: [],
    axisLabel: { color: '#aaa', fontSize: 12 }
  },
  yAxis: {
    type: 'value',
    min: 0,
    max: null,
    splitNumber: 6,
    axisLabel: { color: '#aaa', fontSize: 12 }
  },
  series: []
})

const mapOption = ref({
  title: { text: '物流地区流通地图', left: 'center', top: 10, textStyle: { color: '#fff', fontSize: 18 } },
  geo: {
    map: 'china',
    roam: true,
    label: { show: true, color: '#00cfff', fontSize: 14 },
    itemStyle: { areaColor: '#0b1c2c', borderColor: '#00cfff' },
    emphasis: { itemStyle: { areaColor: '#1a2b4c' } }
  },
  series: [
    {
      name: 'Top10',
      type: 'effectScatter',
      coordinateSystem: 'geo',
      data: [
        { name: '武汉', value: [114.31, 30.52, 120] },
        { name: '北京', value: [116.46, 39.92, 100] },
        { name: '上海', value: [121.48, 31.22, 90] },
        { name: '广州', value: [113.23, 23.16, 80] },
        { name: '深圳', value: [114.07, 22.62, 70] },
        { name: '成都', value: [104.06, 30.67, 60] },
        { name: '重庆', value: [106.54, 29.59, 50] },
        { name: '西安', value: [108.95, 34.27, 40] },
        { name: '沈阳', value: [123.38, 41.8, 30] },
        { name: '哈尔滨', value: [126.63, 45.75, 20] }
      ],
      symbolSize: val => Math.sqrt(val[2]) * 2.5,
      showEffectOn: 'render',
      rippleEffect: { brushType: 'stroke' },
      hoverAnimation: true,
      itemStyle: { color: '#00cfff', shadowBlur: 10, shadowColor: '#333' },
      zlevel: 1
    },
    {
      name: '流向',
      type: 'lines',
      coordinateSystem: 'geo',
      zlevel: 2,
      effect: { show: true, period: 6, trailLength: 0.7, color: '#fff', symbolSize: 3 },
      lineStyle: { color: '#00cfff', width: 2, opacity: 0.6, curveness: 0.2 },
      data: [
        { coords: [[114.31, 30.52], [116.46, 39.92]] },
        { coords: [[114.31, 30.52], [121.48, 31.22]] },
        { coords: [[114.31, 30.52], [113.23, 23.16]] },
        { coords: [[114.31, 30.52], [114.07, 22.62]] },
        { coords: [[114.31, 30.52], [104.06, 30.67]] },
        { coords: [[114.31, 30.52], [106.54, 29.59]] },
        { coords: [[114.31, 30.52], [108.95, 34.27]] },
        { coords: [[114.31, 30.52], [123.38, 41.8]] },
        { coords: [[114.31, 30.52], [126.63, 45.75]] }
      ]
    }
  ]
})

// const barOption = ref({
//   tooltip: {},
//   legend: { data: ['男性', '女性'], textStyle: { color: '#fff' } },
//   xAxis: { type: 'category', data: ['6月', '7月', '8月', '9月', '10月', '11月'] },
//   yAxis: { type: 'value' },
//   series: [
//     {
//       name: '男性',
//       type: 'bar',
//       data: [301, 320, 301, 330, 390, 350],
//       itemStyle: { color: '#4fc3f7' },
//       label: { show: true, position: 'top', color: '#4fc3f7', fontSize: 14 }
//     },
//     {
//       name: '女性',
//       type: 'bar',
//       data: [182, 210, 201, 220, 310, 280],
//       itemStyle: { color: '#ff7cae' },
//       label: { show: true, position: 'top', color: '#ff7cae', fontSize: 14 }
//     }
//   ]
// })

const barOption = ref({
  tooltip: {},
  legend: {
    data: ['物流达人数量'],
    textStyle: { color: '#fff' }
  },
  xAxis: {
    type: 'category',
    data: []
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      name: '物流达人数量',
      type: 'bar',
      data: [],
      itemStyle: { color: '#4fc3f7' },
      label: {
        show: true,
        position: 'top',
        color: '#4fc3f7',
        fontSize: 14
      }
    }
  ]
})

const ringOption = ref({
  tooltip: { trigger: 'item' },
  legend: { bottom: 0, textStyle: { color: '#fff' } },
  series: [{
    name: '年龄段',
    type: 'pie',
    radius: ['60%', '80%'],
    center: ['50%', '50%'],
    data: [],
    label: { color: '#fff', fontSize: 15, formatter: '{b}: {d}%' },
    labelLine: { lineStyle: { color: '#fff' } }
  }]
})

const fetchBubbleData = async () => {
  try {
    const res = await axios.get('http://localhost:8080/api/sugar/data/zongshuju/pt10')
    const data = res.data
    bubbleOption.value.legend.data = data.map(item => item.car_type)
    bubbleOption.value.xAxis.data = data.map(item => item.car_type)
    const max = Math.max(...data.map(item => Number(item.count)))
    bubbleOption.value.yAxis.max = Math.ceil(max / 10) * 10
    bubbleOption.value.series = data.map((item, idx) => ({
      name: item.car_type,
      type: 'scatter',
      data: [[item.car_type, item.count, item.count]],
      symbolSize: function (data) { return Math.sqrt(data[2]) * 0.8 + 10 },
      emphasis: { focus: 'series' }
    }))
  } catch (e) {
    console.error('获取车型气泡图数据失败', e)
  }
}

const fetchRingData = async () => {
  try {
    const res = await axios.get('http://localhost:8080/api/sugar/data/zongshuju/pt6')
    ringOption.value.series[0].data = res.data.map(item => ({
      value: Number(item.percentage),
      name: item.age_group
    }))
  } catch (e) {
    console.error('获取各年龄段占比数据失败', e)
  }
}

const fetchBarData = async () => {
  // try {
  //   const res = await axios.get('http://localhost:8080/api/sugar/data/zongshuju/pt12')
  //   const data = res.data

  //   // 假设返回的是数组对象：[{ region: '山东', count: 10 }, ...]
  //   barOption.value.xAxis.data = data.map(item => item.region)
  //   barOption.value.series[0].data = data.map(item => item.count)
  // } catch (e) {
  //   console.error('获取省份物流达人数量失败', e)
  // }
  try {
    const res = await axios.get('http://localhost:8080/api/sugar/data/zongshuju/pt12')
    if (Array.isArray(res.data)) {
      barOption.value.xAxis.data = res.data.map(item => item.region)
      barOption.value.series[0].data = res.data.map(item => Number(item.sum))
    }
  } catch (e) {
    console.error('各省物流达人排行获取失败', e)
  }
}

onMounted(() => {
  fetchBubbleData()
  fetchRingData()
  fetchBarData()
})
</script>

<style scoped>
.logistics-analysis-screen {
  background: #0b1c2c;
  color: #fff;
  min-height: 100vh;
  padding: 18px 10px;
  box-sizing: border-box;
}
.row {
  display: flex;
  gap: 14px;
  margin-bottom: 14px;
}
.row-top {
  height: 180px;
}
.row-middle {
  height: 380px;
}
.row-bottom {
  height: 220px;
}
.card {
  background: rgba(30,40,60,0.98);
  border-radius: 10px;
  box-shadow: 0 4px 24px rgba(0,0,0,0.18);
  padding: 14px 14px 8px 14px;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  overflow: hidden;
}
.card-bubble {
  flex: 1;
  min-width: 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
.card-map {
  flex: 1;
  min-width: 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
.card-bar, .card-ring {
  flex: 1;
  min-width: 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
.chart-title {
  font-size: 16px;
  color: #4fc3f7;
  margin-bottom: 8px;
}
.chart-placeholder {
  width: 100%;
  height: 80%;
  background: rgba(80,120,180,0.08);
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #4fc3f7;
  font-size: 18px;
}
</style> 