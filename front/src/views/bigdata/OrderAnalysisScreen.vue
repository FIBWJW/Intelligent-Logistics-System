<template>
  <div class="order-analysis-screen">
    <div class="row row-top">
      <div class="card card-line">
        <div class="chart-title">订单情况</div>
        <v-chart :option="lineOption" autoresize style="width:100%;height:90%;" />
      </div>
      <div class="card card-pie">
        <div class="chart-title">客户满意度</div>
        <v-chart :option="pieOption" autoresize style="width:100%;height:90%;" />
      </div>
    </div>
    <div class="row row-middle">
      <div class="card card-treemap">
        <div class="chart-title">城市快递业务量</div>
        <v-chart :option="treemapOption" autoresize style="width:100%;height:95%;" />
      </div>
    </div>
    <div class="row row-bottom">
      <div class="card card-bar">
        <div class="chart-title">订单下发时段</div>
        <v-chart :option="barOption" autoresize style="width:100%;height:90%;" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import VChart from 'vue-echarts'
import { use } from 'echarts/core'
import { LineChart, PieChart, TreemapChart, BarChart } from 'echarts/charts'
import { TitleComponent, TooltipComponent, LegendComponent, GridComponent, ToolboxComponent } from 'echarts/components'
import { CanvasRenderer } from 'echarts/renderers'
import axios from 'axios'

use([
  LineChart, PieChart, TreemapChart, BarChart,
  TitleComponent, TooltipComponent, LegendComponent, GridComponent, ToolboxComponent, CanvasRenderer
])

const lineOption = ref({
  tooltip: { trigger: 'axis' },
  legend: { data: ['订单量'], textStyle: { color: '#fff' } },
  xAxis: {
    type: 'category',
    data: [],
    axisLabel: { color: '#fff' }
  },
  yAxis: {
    type: 'value',
    axisLabel: { color: '#fff', formatter: '{value}个' }
  },
  series: [
    {
      name: '订单量',
      type: 'line',
      data: [],
      smooth: true,
      lineStyle: { color: '#ff4d4f', width: 2 },
      itemStyle: { color: '#ff4d4f' }
    }
  ]
})

const pieOption = ref({
  title: { text: '客户满意度', left: 'center', top: 10, textStyle: { color: '#fff', fontSize: 18 } },
  tooltip: { trigger: 'item', formatter: '{b}: {c} ({d}%)' },
  legend: { orient: 'vertical', left: 10, textStyle: { color: '#fff' } },
  series: [{
    name: '满意度',
    type: 'pie',
    radius: ['50%', '70%'],
    center: ['60%', '55%'],
    data: [
      { value: 35, name: '满意' },
      { value: 25, name: '一般' },
      { value: 15, name: '不满意' },
      { value: 6, name: '非常不满意' },
      { value: 20, name: '非常满意' }
    ],
    label: { color: '#fff', fontSize: 15 },
    labelLine: { lineStyle: { color: '#fff' } },
    emphasis: {
      itemStyle: { shadowBlur: 20, shadowOffsetX: 0, shadowColor: 'rgba(0, 0, 0, 0.5)' }
    }
  }]
})

const treemapOption = ref({
  title: { text: '城市卖家数量', left: 'center', top: 10, textStyle: { color: '#fff', fontSize: 18 } },
  tooltip: { formatter: '{b}: {c}' },
  series: [{
    type: 'treemap',
    roam: false,
    nodeClick: false,
    breadcrumb: { show: false },
    label: { show: true, color: '#fff', fontSize: 18, formatter: '{b}\n{c}' },
    itemStyle: { borderColor: '#fff', borderWidth: 2, gapWidth: 2 },
    data: []
  }]
})

const barOption = ref({
  tooltip: { trigger: 'axis' },
  grid: { left: 80, right: 30, top: 30, bottom: 30 },
  xAxis: {
    type: 'value',
    axisLabel: { color: '#fff' }
  },
  yAxis: {
    type: 'category',
    data: ['早上', '上午', '中午', '下午', '晚上'],
    axisLabel: { color: '#fff' }
  },
  series: [
    {
      name: '订单总数量',
      type: 'bar',
      stack: 'total',
      label: { show: true, position: 'inside', color: '#fff' },
      itemStyle: { color: '#00cfff' },
      data: [80, 120, 90, 160, 100]
    }
  ]
})


const fetchOrderLineData = async () => {
  try {
    const res = await axios.get('http://localhost:8080/api/sugar/data/zongshuju/pt7')
    // 返回格式为 [{month: '2025-01', order_count: 28}, ...]
    lineOption.value.xAxis.data = res.data.map(item => item.month)
    lineOption.value.series[0].data = res.data.map(item => Number(item.order_count))
  } catch (e) {
    console.error('获取订单量数据失败', e)
  }
}

const fetchTreemapData = async () => {
  try {
    const res = await axios.get('http://localhost:8080/api/sugar/data/zongshuju/pt8')
    treemapOption.value.series[0].data = res.data.map(item => ({
      name: item.name,
      value: Number(item.region)
    }))
  } catch (e) {
    console.error('获取城市卖家数量数据失败', e)
  }
}

const fetchTimeSlotData = async () => {
  try {
    const res = await axios.get('http://localhost:8080/api/sugar/data/zongshuju/pt11')
    // 假设返回格式: [{period: '早上', count: 80}, ...]
    const data = res.data.sort((a, b) => {
      // 按时间段顺序排序
      const order = ['早上', '上午', '中午', '下午', '晚上']
      return order.indexOf(a.time_period) - order.indexOf(b.time_period)
    })
    
    barOption.value.yAxis.data = data.map(item => item.time_period)
    barOption.value.series[0].data = data.map(item => Number(item.order_count))
  } catch (e) {
    console.error('获取时段订单数据失败', e)
    // 失败时使用默认数据
    barOption.value.yAxis.data = ['早上', '上午', '中午', '下午', '晚上']
    barOption.value.series[0].data = [80, 120, 90, 160, 100]
  }
}
onMounted(() => {
  fetchOrderLineData()
  fetchTreemapData()
  fetchTimeSlotData()
})
</script>

<style scoped>
.order-analysis-screen {
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
  height: 320px;
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
  flex: 1;
}
.card-line, .card-pie {
  flex: 1;
  min-width: 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
.card-treemap {
  flex: 1;
  min-width: 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}
.card-bar {
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