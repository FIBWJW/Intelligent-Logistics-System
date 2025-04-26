<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!-- 地图占据整行 -->
      <el-col :span="24">
        <el-card shadow="hover" class="map-card">
          <template #header>
            <div class="card-header">
              <span>各省市订单分布</span>
            </div>
          </template>
          <div class="map-container" ref="chinaMapRef"></div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <!-- 饼图占据左半部分 -->
      <el-col :span="12">
        <el-card shadow="hover" class="chart-card">
          <template #header>
            <div class="card-header">
              <span>订单状态分布</span>
            </div>
          </template>
          <div class="pie-container" ref="pieChartRef"></div>
        </el-card>
      </el-col>
      
      <!-- 柱状图占据右半部分 -->
      <el-col :span="12">
        <el-card shadow="hover" class="chart-card">
          <template #header>
            <div class="card-header">
              <span>员工订单数量分布</span>
            </div>
          </template>
          <div class="bar-container" ref="barChartRef"></div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :span="24">
        <el-card shadow="hover" class="chart-card">
          <template #header>
            <div class="card-header">
              <span>车辆使用频次 TOP10</span>
            </div>
          </template>
          <div class="line-container" ref="lineChartRef"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import axios from 'axios'

export default {
  name: 'AnalysisList',
  data() {
    return {
      chinaMap: null,
      pieChart: null,
      barChart: null,
      lineChart: null
    }
  },
  mounted() {
    this.initMap()
    this.initPieChart()
    this.initBarChart()
    this.initLineChart()
  },
  beforeUnmount() {
    if (this.chinaMap) this.chinaMap.dispose()
    if (this.pieChart) this.pieChart.dispose()
    if (this.barChart) this.barChart.dispose()
    if (this.lineChart) this.lineChart.dispose()
  },
  methods: {
    async initMap() {
      try {
        const container = this.$refs.chinaMapRef
        if (!container) {
          throw new Error('地图容器不存在')
        }

        this.chinaMap = echarts.init(container)

        // 获取数据
        const response = await axios.get('/api/sugar/zhibiao01')
        console.log('原始数据:', response.data)

        // 处理数据
        const mapData = response.data.map(item => {
          // 直接使用原始省份名称
          return {
            name: item.province,
            value: item.order_count
          }
        })

        console.log('处理后的数据:', mapData)

        // 加载地图
        const chinaJson = await fetch('https://geo.datav.aliyun.com/areas_v3/bound/100000_full.json')
          .then(res => res.json())
        echarts.registerMap('china', chinaJson)

        // 配置地图
        const option = {
          title: {
            text: '各省市订单分布',
            left: 'center'
          },
          tooltip: {
            trigger: 'item',
            formatter: function(params) {
              return `${params.name}: ${params.value || 0}单`
            }
          },
          visualMap: {
            min: 0,
            max: Math.max(...mapData.map(item => item.value)),
            left: 'left',
            bottom: 'bottom',
            text: ['高', '低'],
            calculable: true,
            inRange: {
              color: ['#e0ffff', '#006edd']
            }
          },
          series: [{
            name: '订单数量',
            type: 'map',
            map: 'china',
            roam: true,
            data: mapData,
            label: {
              show: true,
              formatter: function(params) {
                return `${params.name}\n${params.value || 0}单`
              }
            },
            emphasis: {
              label: {
                show: true
              }
            }
          }]
        }

        this.chinaMap.setOption(option)

        window.addEventListener('resize', () => {
          this.chinaMap && this.chinaMap.resize()
        })

      } catch (error) {
        console.error('地图初始化失败:', error)
        this.$message.error('地图加载失败')
      }
    },
    async initPieChart() {
      try {
        const container = this.$refs.pieChartRef
        if (!container) {
          throw new Error('饼图容器不存在')
        }

        this.pieChart = echarts.init(container)

        // 获取数据
        const response = await axios.get('/api/sugar/zhibiao02')
        console.log('饼图数据:', response.data)

        // 处理数据
        const pieData = response.data.map(item => ({
          name: item.status,
          value: item.status_count
        }))

        // 计算总数
        const total = pieData.reduce((sum, item) => sum + item.value, 0)

        // 配置饼图
        const option = {
          tooltip: {
            trigger: 'item',
            formatter: '{b}: {c} ({d}%)'
          },
          legend: {
            orient: 'horizontal',
            bottom: 'bottom'
          },
          series: [{
            name: '订单状态',
            type: 'pie',
            radius: ['40%', '70%'],
            center: ['50%', '50%'],
            avoidLabelOverlap: true,
            itemStyle: {
              borderRadius: 10,
              borderColor: '#fff',
              borderWidth: 2
            },
            label: {
              show: true,
              formatter: '{b}\n{c}单 ({d}%)'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: '18',
                fontWeight: 'bold'
              }
            },
            data: pieData
          }]
        }

        this.pieChart.setOption(option)

        // 添加响应式调整
        window.addEventListener('resize', () => {
          this.pieChart && this.pieChart.resize()
        })

      } catch (error) {
        console.error('饼图初始化失败:', error)
        this.$message.error('饼图加载失败')
      }
    },
    async initBarChart() {
      try {
        const container = this.$refs.barChartRef
        if (!container) {
          throw new Error('柱状图容器不存在')
        }

        this.barChart = echarts.init(container)

        // 获取数据
        const response = await axios.get('/api/sugar/zhibiao03')
        console.log('柱状图数据:', response.data)

        // 处理数据 - 按订单数量降序排序
        const sortedData = [...response.data].sort((a, b) => b.order_count - a.order_count)
        const names = sortedData.map(item => item.employee_name)
        const values = sortedData.map(item => item.order_count)

        // 配置柱状图
        const option = {
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'shadow'
            }
          },
          grid: {
            left: '3%',
            right: '4%',
            bottom: '10%',
            containLabel: true
          },
          xAxis: {
            type: 'category',
            data: names,
            axisLabel: {
              interval: 0,
              rotate: 45
            }
          },
          yAxis: {
            type: 'value',
            name: '订单数量'
          },
          series: [{
            name: '订单数量',
            type: 'bar',
            data: values,
            itemStyle: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                { offset: 0, color: '#83bff6' },
                { offset: 0.5, color: '#188df0' },
                { offset: 1, color: '#188df0' }
              ])
            },
            emphasis: {
              itemStyle: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                  { offset: 0, color: '#2378f7' },
                  { offset: 0.7, color: '#2378f7' },
                  { offset: 1, color: '#83bff6' }
                ])
              }
            }
          }]
        }

        this.barChart.setOption(option)

        // 添加响应式调整
        window.addEventListener('resize', () => {
          this.barChart && this.barChart.resize()
        })

      } catch (error) {
        console.error('柱状图初始化失败:', error)
        this.$message.error('柱状图加载失败')
      }
    },
    async initLineChart() {
      try {
        const container = this.$refs.lineChartRef
        if (!container) {
          throw new Error('折线图容器不存在')
        }

        this.lineChart = echarts.init(container)

        // 获取数据
        const response = await axios.get('/api/sugar/zhibiao04')
        console.log('折线图数据:', response.data)

        // 处理数据 - 按使用次数降序排序并获取前10
        const sortedData = [...response.data]
          .sort((a, b) => b.usage_count - a.usage_count)
          .slice(0, 10)

        const plates = sortedData.map(item => item.license_plate)
        const counts = sortedData.map(item => item.usage_count)

        // 配置折线图
        const option = {
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross',
              label: {
                backgroundColor: '#6a7985'
              }
            }
          },
          grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
          },
          xAxis: {
            type: 'category',
            boundaryGap: false,
            data: plates,
            axisLabel: {
              interval: 0,
              rotate: 45
            }
          },
          yAxis: {
            type: 'value',
            name: '使用次数'
          },
          series: [{
            name: '使用次数',
            type: 'line',
            stack: 'Total',
            smooth: true,
            lineStyle: {
              width: 3,
              shadowColor: 'rgba(0,0,0,0.3)',
              shadowBlur: 10,
              shadowOffsetY: 8
            },
            showSymbol: true,
            areaStyle: {
              opacity: 0.8,
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {
                  offset: 0,
                  color: 'rgb(128, 255, 165)'
                },
                {
                  offset: 1,
                  color: 'rgb(1, 191, 236)'
                }
              ])
            },
            emphasis: {
              focus: 'series'
            },
            data: counts
          }]
        }

        this.lineChart.setOption(option)

        // 添加响应式调整
        window.addEventListener('resize', () => {
          this.lineChart && this.lineChart.resize()
        })

      } catch (error) {
        console.error('折线图初始化失败:', error)
        this.$message.error('折线图加载失败')
      }
    }
  }
}
</script>

<style scoped>
.app-container {
  padding: 20px;
  min-height: 100vh;
  background-color: #f0f2f5;
}

/* 当侧边栏收起时的样式 */
:deep(.main-container.is-fullscreen) .app-container {
  padding: 60px 20px 20px;
}

.el-row {
  margin-bottom: 20px;
}

.map-container {
  height: calc(100vh - 300px);  /* 调整地图高度 */
  min-height: 500px;
  width: 100%;
}

.pie-container,
.bar-container {
  height: 400px;
  width: 100%;
}

.line-container {
  height: 400px;
  width: 100%;
}

.chart-card {
  margin-bottom: 20px;
  background-color: #fff;
  border-radius: 4px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px;
  border-bottom: 1px solid #eee;
}

/* 响应式布局 */
@media screen and (min-width: 1920px) {
  .map-container {
    height: calc(100vh - 200px);
  }
  
  .pie-container,
  .bar-container,
  .line-container {
    height: 500px;
  }
}
</style> 