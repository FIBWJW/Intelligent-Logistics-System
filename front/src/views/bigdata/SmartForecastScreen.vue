<template>
  <div class="smart-forecast-screen">
    <div class="row row-top">
      <div class="card card-stat">
        <div class="stat-title">数量统计</div>
        <div class="stat-value">
          365<span class="stat-unit">个</span>
        </div>
        <div class="stat-desc">较昨日增加200人</div>
      </div>
      <div class="card card-stat">
        <div class="stat-title">同比上升</div>
        <div class="stat-value">
          1312<span class="stat-unit">人</span>
        </div>
        <div class="stat-desc">较昨日增加200人</div>
      </div>
      <div class="card card-stat-large">
        <div class="stat-value stat-value-large">
          4561332<span class="stat-unit">件</span>
        </div>
      </div>
    </div>
    <div class="row row-middle">
      <div class="card card-bar">
        <div class="chart-title">不同年龄段快递消费群体</div>
        <v-chart :option="ageBarOption" autoresize style="width:100%;height:90%;" />
      </div>
      <div class="card card-pie">
        <div class="chart-title">物流企业TOP5市场份额</div>
        <v-chart :option="top5PieOption" autoresize style="width:100%;height:90%;" />
      </div>
    </div>
    <div class="row row-bottom">
      <div class="card card-line">
        <div class="chart-title">未来7天订单数量趋势预测</div>
        <v-chart :option="trendLineOption" autoresize style="width:100%;height:90%;" />
      </div>
      <div class="card card-pie2">
        <div class="chart-title">商品分类占比</div>
        <v-chart :option="goodsPieOption" autoresize style="width:100%;height:90%;" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import VChart from 'vue-echarts'
import { use } from 'echarts/core'
import { BarChart, PieChart, LineChart } from 'echarts/charts'
import { TitleComponent, TooltipComponent, LegendComponent, GridComponent } from 'echarts/components'
import { CanvasRenderer } from 'echarts/renderers'
import axios from 'axios'

use([BarChart, PieChart, LineChart, TitleComponent, TooltipComponent, LegendComponent, GridComponent, CanvasRenderer])

const statOption = ref({
  title: { show: false },
  xAxis: { show: false },
  yAxis: { show: false },
  grid: { left: 0, right: 0, top: 0, bottom: 0 },
  series: [{
    type: 'bar',
    data: [365],
    barWidth: 60,
    label: {
      show: true,
      position: 'inside',
      fontSize: 38,
      color: '#4fc3f7',
      fontFamily: 'DS-Digital,Arial',
      formatter: '{c}个'
    },
    itemStyle: { color: '#4fc3f7', borderRadius: 8 }
  }]
})
const riseOption = ref({
  title: { show: false },
  xAxis: { show: false },
  yAxis: { show: false },
  grid: { left: 0, right: 0, top: 0, bottom: 0 },
  series: [{
    type: 'bar',
    data: [1312],
    barWidth: 60,
    label: {
      show: true,
      position: 'inside',
      fontSize: 38,
      color: '#4fc3f7',
      fontFamily: 'DS-Digital,Arial',
      formatter: '{c}人'
    },
    itemStyle: { color: '#4fc3f7', borderRadius: 8 }
  }]
})
const bigNumOption = ref({
  title: { show: false },
  xAxis: { show: false },
  yAxis: { show: false },
  grid: { left: 0, right: 0, top: 0, bottom: 0 },
  series: [{
    type: 'bar',
    data: [4561332],
    barWidth: 120,
    label: {
      show: true,
      position: 'inside',
      fontSize: 48,
      color: '#4fc3f7',
      fontFamily: 'DS-Digital,Arial',
      formatter: '{c}件'
    },
    itemStyle: { color: '#4fc3f7', borderRadius: 8 }
  }]
})
const ageBarOption = ref({
  tooltip: {},
  xAxis: { type: 'category', data: [] },
  yAxis: { type: 'value' },
  series: [{
    data: [],
    type: 'bar',
    itemStyle: {
      color: '#ff7cae',
      shadowColor: '#fff',
      shadowBlur: 10
    },
    label: { show: true, position: 'top', color: '#fff', fontSize: 16 }
  }]
})
const top5PieOption = ref({
  tooltip: { trigger: 'item' },
  legend: { left: 'left', top: 'middle', orient: 'vertical', textStyle: { color: '#fff' } },
  series: [{
    name: '市场份额',
    type: 'pie',
    radius: '70%',
    center: ['60%', '50%'],
    data: [],
    label: { color: '#fff', fontSize: 15 },
    labelLine: { lineStyle: { color: '#fff' } }
  }]
})
const trendLineOption = ref({
  tooltip: {},
  xAxis: { type: 'category', data: ['P1', 'P2', 'P3', 'P4', 'P5', 'P6', 'P7'] },
  yAxis: { type: 'value' },
  series: [{
    data: [1, 10, 20, 30, 40, 60, 119],
    type: 'line',
    smooth: true,
    lineStyle: { color: '#ffea00', width: 4 },
    itemStyle: { color: '#ffea00' },
    areaStyle: { color: 'rgba(255,234,0,0.1)' },
    label: { show: true, color: '#fff', fontSize: 16 }
  }]
})
const goodsPieOption = ref({
  tooltip: { trigger: 'item' },
  legend: { bottom: 0, textStyle: { color: '#fff' } },
  series: [{
    name: '商品分类',
    type: 'pie',
    radius: ['50%', '70%'],
    data: [],
    label: { color: '#fff', fontSize: 15 },
    labelLine: { lineStyle: { color: '#fff' } }
  }]
})

const fetchAgeBarData = async () => {
  try {
    const res = await axios.get('http://localhost:8080/api/sugar/data/zongshuju/pt3')
    // 按年龄段排序
    const order = ['18-25岁', '26-35岁', '36-45岁', '46-55岁', '56岁以上']
    const data = res.data.sort((a, b) => order.indexOf(a.age_group) - order.indexOf(b.age_group))
    ageBarOption.value.xAxis.data = data.map(item => item.age_group)
    ageBarOption.value.series[0].data = data.map(item => Number(item.order_count))
  } catch (e) {
    console.error('获取年龄段快递消费群体数据失败', e)
  }
}

const fetchTop5PieData = async () => {
  try {
    const res = await axios.get('http://localhost:8080/api/sugar/data/zongshuju/pt4')
    // 转换接口数据为 ECharts 饼图格式
    top5PieOption.value.series[0].data = res.data.map(item => ({
      value: Number(item.sunn),
      name: item.up_date
    }))
  } catch (e) {
    console.error('获取物流企业TOP5市场份额数据失败', e)
  }
}

const fetchGoodsPieData = async () => {
  try {
    const res = await axios.get('http://localhost:8080/api/sugar/data/zongshuju/pt5')
    goodsPieOption.value.series[0].data = res.data.map(item => ({
      value: Number(item.sunn),
      name: item.pl
    }))
  } catch (e) {
    console.error('获取商品分类占比数据失败', e)
  }
}

// 新增：物流需求量趋势实时分析数据获取
const fetchTrendLineData = async () => {
  try {
    const res = await axios.get('http://localhost:8080/api/sugar/data/zongshuju/pt13')
    // 假设返回数据为 [{id:..., value:...}, ...]
    const data = res.data
    trendLineOption.value.xAxis.data = data.map(item => `P${item.id}`)
    trendLineOption.value.series[0].data = data.map(item => Number(item.value))
  } catch (e) {
    console.error('获取物流需求量趋势数据失败', e)
  }
}

onMounted(() => {
  fetchAgeBarData()
  fetchTop5PieData()
  fetchGoodsPieData()
  fetchTrendLineData()
})
</script>

<style scoped>
.smart-forecast-screen {
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
  height: 120px;
}
.row-middle {
  height: 320px;
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
.card-stat {
  flex: 1;
  min-width: 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
.card-stat-large {
  flex: 2;
  min-width: 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  font-size: 38px;
  font-weight: bold;
  color: #4fc3f7;
}
.stat-title {
  font-size: 18px;
  color: #4fc3f7;
  margin-bottom: 4px;
}
.stat-value {
  font-size: 38px;
  color: #4fc3f7;
  font-family: 'DS-Digital', Arial, sans-serif;
  font-weight: bold;
  line-height: 1.1;
}
.stat-value-large {
  font-size: 48px;
}
.stat-unit {
  font-size: 18px;
  margin-left: 4px;
  color: #4fc3f7;
  font-family: 'DS-Digital', Arial, sans-serif;
}
.stat-desc {
  font-size: 14px;
  color: #b0e0ff;
}
.card-bar, .card-pie, .card-line, .card-pie2 {
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