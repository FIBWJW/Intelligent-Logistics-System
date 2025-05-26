<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>用户总数</span>
            </div>
          </template>
          <div class="card-content">
            <el-statistic :value="statistics.userCount">
              <template #prefix>
                <el-icon><User /></el-icon>
              </template>
            </el-statistic>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>订单总数</span>
            </div>
          </template>
          <div class="card-content">
            <el-statistic :value="statistics.orderCount">
              <template #prefix>
                <el-icon><List /></el-icon>
              </template>
            </el-statistic>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>员工总数</span>
            </div>
          </template>
          <div class="card-content">
            <el-statistic :value="statistics.employeeCount">
              <template #prefix>
                <el-icon><UserFilled /></el-icon>
              </template>
            </el-statistic>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>车辆总数</span>
            </div>
          </template>
          <div class="card-content">
            <el-statistic :value="statistics.vehicleCount">
              <template #prefix>
                <el-icon><Van /></el-icon>
              </template>
            </el-statistic>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="12">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>最近订单</span>
            </div>
          </template>
          <el-table :data="recentOrders" style="width: 100%">
            <el-table-column prop="orderNo" label="订单编号" width="180" />
            <el-table-column prop="createTime" label="创建时间" width="180" />
            <el-table-column prop="status" label="状态">
              <template #default="scope">
                <el-tag :type="scope.row.status === '1' ? 'warning' : 'success'">
                  {{ scope.row.status === '1' ? '待发货' : '已发货' }}
                </el-tag>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>车辆状态</span>
            </div>
          </template>
          <el-table :data="vehicleStatus" style="width: 100%">
            <el-table-column prop="plateNumber" label="车牌号" width="120" />
            <el-table-column prop="driver" label="司机" width="100" />
            <el-table-column prop="status" label="状态">
              <template #default="scope">
                <el-tag :type="getVehicleStatusType(scope.row.status)">
                  {{ getVehicleStatusText(scope.row.status) }}
                </el-tag>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const statistics = ref({})
const recentOrders = ref([
  {
    orderNo: 'DD20240104001',
    createTime: '2024-01-04 10:00:00',
    status: '1'
  },
  {
    orderNo: 'DD20240104002',
    createTime: '2024-01-04 09:30:00',
    status: '2'
  }
])

const vehicleStatus = ref([
  {
    plateNumber: '京A12345',
    driver: '张三',
    status: '1'
  },
  {
    plateNumber: '京B67890',
    driver: '李四',
    status: '2'
  }
])

const getVehicleStatusText = (status) => {
  const statusMap = {
    '1': '空闲',
    '2': '在途',
    '3': '维修'
  }
  return statusMap[status] || '未知状态'
}

const getVehicleStatusType = (status) => {
  const typeMap = {
    '1': 'success',
    '2': 'warning',
    '3': 'danger'
  }
  return typeMap[status] || 'info'
}

const fetchDashboardData = async () => {
  try {
    // 获取统计数据
    const statsRes = await axios.get('/api/dashboard/statistics')
    statistics.value = statsRes.data.data
    
  } catch (error) {
    console.error('获取仪表盘数据失败:', error)
  }
}


onMounted(() => {
  fetchDashboardData()
})
</script>

<style scoped>
.dashboard {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-content {
  text-align: center;
  padding: 20px 0;
}

.el-row {
  margin-bottom: 20px;
}
</style> 