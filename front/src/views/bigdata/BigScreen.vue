<template>
  <div class="bigscreen-container">
    <!-- 第一行：三块 -->
    <div class="row row-top">
      <div class="card card-info">
        <h3>基本信息</h3>
        <div class="info-desc">当你征服一座山峰时，它已经在你脚下了，你必须再找一座山峰去征服，否则，你只有下山，走下坡路了</div>
        <div class="info-data">
          <div class="info-row">
            <div class="info-item">成交额：<span>4250万</span></div>
            <div class="info-item">成交量：<span>3545万件</span></div>
          </div>
          <div class="info-row">
            <div class="info-item">在线总数：<span>1254万</span></div>
            <div class="info-item">总包裹量：<span>7854万</span></div>
          </div>
          <div class="info-row">
            <div class="info-item">在线商家：<span>1254万</span></div>
            <div class="info-item">已发件数：<span>7854万</span></div>
          </div>
        </div>
      </div>
      <div class="card card-pie">
        <h3>商品分类占比</h3>
        <div class="pie-chart-wrapper">
          <v-chart :option="pieOption" autoresize />
        </div>
      </div>
      <div class="card card-platform">
        <h3>各平台占比</h3>
        <div class="pie-chart-wrapper">
          <v-chart :option="platformOption" autoresize />
        </div>
      </div>
    </div>
    <!-- 第二行：地图+柱状图 -->
    <div class="row row-middle">
      <div class="card card-map">
        <h3>物流平台</h3>
        <v-chart :option="mapOption" autoresize />
      </div>
      <div class="card card-citybar">
        <h3>数据统计情况</h3>
        <v-chart :option="cityBarOption" autoresize />
      </div>
    </div>
    <!-- 第三行：销售排行+包裹量 -->
    <div class="row row-bottom">
      <div class="card card-bar">
        <h3>商品销售排行</h3>
        <div class="bar-chart-wrapper">
          <v-chart :option="barOption" autoresize />
        </div>
      </div>
      <div class="card card-list">
        <h3>包裹量排名</h3>
        <ul>
          <li v-for="(item, idx) in packageRank" :key="idx">{{ item }}</li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { use } from 'echarts/core'
import VChart from 'vue-echarts'
import { CanvasRenderer } from 'echarts/renderers'
import { PieChart, BarChart, MapChart } from 'echarts/charts'
import { TitleComponent, TooltipComponent, LegendComponent, GridComponent, VisualMapComponent, GeoComponent } from 'echarts/components'
import * as echarts from 'echarts'
import chinaJson from '@/assets/china.json'

use([
  CanvasRenderer,
  PieChart,
  BarChart,
  MapChart,
  TitleComponent,
  TooltipComponent,
  LegendComponent,
  GridComponent,
  VisualMapComponent,
  GeoComponent
])

echarts.registerMap('china', chinaJson)

const pieOption = ref({
  tooltip: { trigger: 'item' },
  legend: {
    top: 'bottom',
    textStyle: { fontSize: 13, color: '#fff', fontWeight: 'normal' },
    itemWidth: 20,
    itemHeight: 10
  },
  series: [{
    name: '商品分类',
    type: 'pie',
    radius: ['55%', '80%'],
    label: {
      fontSize: 13,
      color: '#fff',
      fontWeight: 'normal',
      formatter: '{b}'
    },
    labelLine: {
      length: 18,
      length2: 10,
      lineStyle: { color: '#fff', width: 1 }
    },
    data: []
  }]
})

const platformOption = ref({
  tooltip: { trigger: 'item' },
  legend: {
    top: 'bottom',
    textStyle: { fontSize: 13, color: '#fff', fontWeight: 'normal' },
    itemWidth: 20,
    itemHeight: 10
  },
  series: [{
    name: '平台',
    type: 'pie',
    radius: ['55%', '80%'],
    label: {
      fontSize: 13,
      color: '#fff',
      fontWeight: 'normal',
      formatter: '{b}'
    },
    labelLine: {
      length: 18,
      length2: 10,
      lineStyle: { color: '#fff', width: 1 }
    },
    data: []
  }]
})

onMounted(async () => {
  // 平台占比数据
  try {
    const res = await axios.get('http://localhost:8080/api/sugar/data/zongshuju/pt')
    if (Array.isArray(res.data)) {
      platformOption.value.series[0].data = res.data.map(item => ({
        name: item.pt,
        value: item.total_xl
      }))
    }
  } catch (e) {
    console.error('平台占比数据获取失败', e)
  }
  // 商品分类占比数据
  try {
    const res = await axios.get('http://localhost:8080/api/sugar/data/zongshuju/pt1')
    if (Array.isArray(res.data)) {
      pieOption.value.series[0].data = res.data.map(item => ({
        name: item.zl,
        value: Number(item.total_xl)
      }))
    }
  } catch (e) {
    console.error('商品分类占比数据获取失败', e)
  }
  // 商品销售排行数据
  try {
    const res = await axios.get('http://localhost:8080/api/sugar/data/zongshuju/pt2')
    if (Array.isArray(res.data)) {
      barOption.value.xAxis.data = res.data.map(item => item.pl)
      barOption.value.series[0].data = res.data.map(item => Number(item.total_xl))
    }
  } catch (e) {
    console.error('商品销售排行数据获取失败', e)
  }

  try {
    const res = await axios.get('http://localhost:8080/api/sugar/data/zongshuju/pt9')
    if (Array.isArray(res.data)) {
      // 按值降序排序
      const sortedData = res.data.sort((a, b) => b.value - a.value)
      
      cityBarOption.value.yAxis.data = sortedData.map(item => item.province)
      cityBarOption.value.series[0].data = sortedData.map(item => item.count)
      
      // 自动调整Y轴标签显示
      cityBarOption.value.yAxis.axisLabel = {
        color: '#fff',
        interval: 0,
        formatter: (value) => {
          const index = sortedData.findIndex(item => item.province === value)
          return `${index + 1}. ${value}`
        }
      }
    }
  } catch (e) {
    console.error('城市数据获取失败', e)
  }
})

const barOption = ref({
  tooltip: {},
  xAxis: { type: 'category', data: [] },
  yAxis: { type: 'value', max: 'dataMax' },
  series: [{
    type: 'bar',
    data: [],
    itemStyle: { color: '#409EFF' }
  }]
})

const packageRank = ref([
  'NO1 湖南，包裹累计188万件',
  'NO2 河南，包裹累计165万件',
  'NO3 贵州，包裹累计159万件',
  'NO4 上海，包裹累计152万件',
  'NO5 北京，包裹累计147万件'
])

const mapOption = ref({
  title: { text: '物流平台', left: 'center', textStyle: { color: '#fff' } },
  tooltip: {},
  geo: {
    map: 'china',
    roam: true,
    label: { show: false },
    itemStyle: { areaColor: '#0b1c2c', borderColor: '#409EFF' },
    emphasis: { itemStyle: { areaColor: '#409EFF' } }
  },
  series: [] // 这里可后续补充流向线等
})

// 城市柱状图配置
const cityBarOption = ref({
  tooltip: {
    trigger: 'axis',
    axisPointer: { type: 'shadow' },
    formatter: '{b}: {c}'
  },
  grid: {
    left: '3%',
    right: '4%',
    bottom: '3%',
    containLabel: true
  },
  xAxis: { 
    type: 'value',
    axisLabel: { color: '#fff' },
    axisLine: { lineStyle: { color: '#666' } },
    splitLine: { lineStyle: { color: '#333' } }
  },
  yAxis: {
    type: 'category',
    axisLabel: { color: '#fff' },
    axisLine: { show: false },
    data: []
  },
  series: [{
    name: '数量',
    type: 'bar',
    data: [],
    itemStyle: {
      color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
        { offset: 0, color: '#00c0ff' },
        { offset: 1, color: '#3a8bff' }
      ])
    },
    label: {
      show: true,
      position: 'right',
      color: '#fff',
      formatter: '{c}'
    }
  }]
})


</script>

<style scoped>
.bigscreen-container {
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
  height: 340px;
}
.row-middle {
  height: 400px;
}
.row-bottom {
  height: 320px;
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
.card-info, .card-pie, .card-platform {
  flex: 1;
  min-width: 0;
}
.card-info {
  margin-right: 0;
}
.card-pie, .card-platform {
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.pie-chart-wrapper {
  flex: 1;
  min-height: 0;
  min-width: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
}
.card-map {
  flex: 2.2;
  min-width: 0;
}
.card-citybar {
  flex: 1;
  min-width: 0;
}
.card-bar, .card-list {
  flex: 1;
  min-width: 0;
}
.card-bar {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
}
.bar-chart-wrapper {
  flex: 1;
  min-height: 0;
  min-width: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
}
.card-list ul {
  margin: 0;
  padding: 0 0 0 10px;
  list-style: none;
  font-size: 18px;
  line-height: 2.2;
}
h3 {
  font-size: 18px;
  font-weight: 500;
  margin-bottom: 8px;
  color: #4fc3f7;
  letter-spacing: 1px;
}
.info-desc {
  font-size: 13px;
  color: #4fc3f7;
  margin-bottom: 8px;
  font-family: '微软雅黑', 'Microsoft YaHei', Arial, sans-serif;
}
.info-data {
  margin-top: 8px;
  display: flex;
  flex-direction: column;
  gap: 8px;
}
.info-row {
  display: flex;
  flex-direction: row;
  gap: 32px;
  margin-bottom: 2px;
}
.info-item {
  font-size: 15px;
  color: #fff;
  min-width: 160px;
}
.info-item span {
  font-weight: 500;
  color: #fff;
}
.v-chart {
  width: 100% !important;
  height: 100% !important;
}
@media (max-width: 1200px) {
  .row { flex-direction: column; height: auto !important; }
  .card { min-width: 0 !important; }
}
</style> 