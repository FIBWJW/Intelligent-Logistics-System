<template>
  <div class="app-container">
    <div class="search-container">
      <el-card shadow="never">
        <el-form :inline="true" :model="searchForm" class="search-form">
          <el-form-item label="物流编号">
            <el-input v-model="searchForm.logisticsNumber" placeholder="请输入物流编号" clearable />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleSearch">查询</el-button>
            <el-button @click="resetSearch">重置</el-button>
            <el-button type="success" @click="handleAdd">新增物流</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </div>

    <div class="table-container">
      <el-card shadow="never">
        <el-table 
          :data="tableData" 
          style="width: 100%" 
          v-loading="loading"
          border
          stripe
          highlight-current-row
        >
          <el-table-column prop="logisticsNumber" label="物流单号" width="180" />
          <el-table-column prop="orderNumber" label="订单编号" width="180" />
          <el-table-column prop="vehiclecar" label="配送车辆" width="120" />
          <el-table-column prop="employeename" label="配送员" width="120" />
          <el-table-column prop="shoujianren" label="收件人" width="120" />
          <el-table-column prop="phone" label="联系电话" width="120" />
          <el-table-column prop="address" label="收件地址" min-width="200" />
          <el-table-column prop="statuslogistics" label="状态" width="100">
            <template #default="scope">
              <el-tag :type="getStatusType(scope.row.statuslogistics)">
                {{ scope.row.statuslogistics }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" fixed="right" width="200">
            <template #default="scope">
              <el-button 
                size="small" 
                type="warning" 
                @click="handleTrack(scope.row)"
              >跟踪</el-button>
              <el-button 
                size="small" 
                type="danger" 
                @click="handleDelete(scope.row)"
              >删除</el-button>
            </template>
          </el-table-column>
        </el-table>
        
        <div class="pagination-container">
          <el-pagination
            v-model:current-page="currentPage"
            v-model:page-size="pageSize"
            :page-sizes="[10, 20, 50, 100]"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
          />
        </div>
      </el-card>
    </div>

    <!-- 物流表单对话框 -->
    <el-dialog
      v-model="dialogVisible"
      title="新增物流"
      width="500px"
    >
      <el-form
        :model="logisticsForm"
        :rules="rules"
        ref="logisticsFormRef"
        label-width="100px"
        class="dialog-form"
      >
        <el-form-item label="订单编号" prop="orderId">
          <el-select 
            v-model="logisticsForm.orderId" 
            placeholder="请选择订单"
            style="width: 100%"
            filterable
          >
            <el-option 
              v-for="order in orderOptions" 
              :key="order.id" 
              :label="order.orderNumber" 
              :value="order.id"
            >
              <div style="display: flex; justify-content: space-between; align-items: center">
                <span>{{ order.orderNumber }}</span>
                <span style="color: #8492a6; font-size: 13px">
                  {{ order.recipientName }} | {{ order.recipientPhone }}
                </span>
              </div>
            </el-option>
          </el-select>
        </el-form-item>
        
        <el-form-item label="配送车辆" prop="vehicleId">
          <el-select 
            v-model="logisticsForm.vehicleId" 
            placeholder="请选择车辆"
            style="width: 100%"
          >
            <el-option 
              v-for="vehicle in vehicleOptions" 
              :key="vehicle.id" 
              :label="vehicle.licensePlate" 
              :value="vehicle.id"
            >
              <span>{{ vehicle.licensePlate }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">
                {{ vehicle.model }}
              </span>
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="配送员" prop="employeeId">
          <el-select 
            v-model="logisticsForm.employeeId" 
            placeholder="请选择配送员"
            style="width: 100%"
            filterable
          >
            <el-option 
              v-for="employee in employeeOptions" 
              :key="employee.id" 
              :label="employee.name" 
              :value="employee.id"
            >
              <span>{{ employee.name }}</span>
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="物流状态" prop="status">
          <el-select v-model="logisticsForm.status" style="width: 100%">
            <el-option label="待发货" value="待发货" />
            <el-option label="运输中" value="运输中" />
            <el-option label="已送达" value="已送达" />
          </el-select>
        </el-form-item>

        <el-form-item label="备注" prop="remark">
          <el-input 
            v-model="logisticsForm.remark" 
            type="textarea" 
            :rows="3" 
            placeholder="请输入备注信息"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 物流跟踪对话框 -->
    <el-dialog
      v-model="trackDialogVisible"
      title="物流跟踪"
      width="600px"
    >
      <el-timeline>
        <el-timeline-item
          v-for="(activity, index) in logisticsTrack"
          :key="index"
          :timestamp="activity.timestamp"
          :type="activity.type"
        >
          {{ activity.content }}
        </el-timeline-item>
      </el-timeline>
      <template #footer>
        <el-button type="primary" @click="handleAddTrack">新增跟踪</el-button>
      </template>
    </el-dialog>

    <!-- 新增跟踪记录对话框 -->
    <el-dialog
      v-model="addTrackDialogVisible"
      title="新增跟踪记录"
      width="500px"
      append-to-body
    >
      <el-form
        :model="trackForm"
        :rules="trackRules"
        ref="trackFormRef"
        label-width="100px"
        class="dialog-form"
      >
        <el-form-item label="跟踪内容" prop="content">
          <el-input 
            v-model="trackForm.content" 
            type="textarea" 
            :rows="3"
            placeholder="请输入跟踪内容"
          />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="trackForm.status" placeholder="请选择状态" style="width: 100%">
            <el-option label="待发货" value="1" />
            <el-option label="运输中" value="2" />
            <el-option label="已送达" value="3" />
            <el-option label="已签收" value="4" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="addTrackDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitTrack">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import axios from 'axios'

// 搜索表单
const searchForm = reactive({
  logisticsNumber: ''  // 只保留物流编号字段
})

// 表格数据
const tableData = ref([
  {
    logisticsNo: 'WL20240104001',
    orderNo: 'DD20240104001',
    vehicleNo: '京A12345',
    driver: '张三',
    receiverName: '李四',
    receiverPhone: '13900139000',
    receiverAddress: '北京市朝阳区xxx街道xxx号',
    status: '1'
  }
  // 更多模拟数据...
])

// 分页相关
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)
const loading = ref(false)

// 对话框相关
const dialogVisible = ref(false)
const dialogType = ref('add')
const logisticsFormRef = ref()
const logisticsForm = reactive({
  orderId: '',       // 订单ID
  vehicleId: '',     // 车辆ID
  employeeId: '',    // 配送员ID
  status: '待发货',
  remark: ''
})

// 物流跟踪相关
const trackDialogVisible = ref(false)
const addTrackDialogVisible = ref(false)
const trackFormRef = ref()
const trackForm = reactive({
  content: '',
  status: ''
})

const logisticsTrack = ref([
  {
    timestamp: '2024-01-04 10:00:00',
    content: '快件已被揽收',
    type: 'primary'
  },
  {
    timestamp: '2024-01-04 14:00:00',
    content: '快件正在运输中',
    type: 'warning'
  }
])

// 远程搜索相关
const orderLoading = ref(false)
const orderOptions = ref([])
const vehicleOptions = ref([])
const employeeOptions = ref([])

// 车辆和驾驶员列表
const vehicles = ref([
  { id: '1', plateNumber: '京A12345' },
  { id: '2', plateNumber: '京B67890' }
])

const drivers = ref([
  { id: '1', name: '张三' },
  { id: '2', name: '李四' }
])

// 表单验证规则
const rules = {
  orderId: [
    { required: true, message: '请选择订单', trigger: 'change' }
  ],
  vehicleId: [
    { required: true, message: '请选择配送车辆', trigger: 'change' }
  ],
  employeeId: [
    { required: true, message: '请选择配送员', trigger: 'change' }
  ],
  status: [
    { required: true, message: '请选择物流状态', trigger: 'change' }
  ]
}

const trackRules = {
  content: [
    { required: true, message: '请输入跟踪内容', trigger: 'blur' }
  ],
  status: [
    { required: true, message: '请选择状态', trigger: 'change' }
  ]
}

// 获取状态文本和类型
const getStatusText = (status) => {
  const statusMap = {
    '1': '待发货',
    '2': '运输中',
    '3': '已送达',
    '4': '已签收'
  }
  return statusMap[status] || '未知状态'
}

const getStatusType = (status) => {
  switch (status) {
    case '待发货':
      return 'warning'
    case '运输中':
      return 'primary'
    case '已送达':
      return 'success'
    default:
      return 'info'
  }
}

// 远程搜索订单
const searchOrders = (query) => {
  if (query) {
    orderLoading.value = true
    // TODO: 实现远程搜索逻辑
    setTimeout(() => {
      orderOptions.value = [
        { orderNo: 'DD20240104001' },
        { orderNo: 'DD20240104002' }
      ]
      orderLoading.value = false
    }, 200)
  } else {
    orderOptions.value = []
  }
}

// 前端分页方法
const getPagedData = (data) => {
  const start = (currentPage.value - 1) * pageSize.value
  const end = start + pageSize.value
  return data.slice(start, end)
}

// 保存完整数据的响应式引用
const fullData = ref([])

// 获取物流列表
const fetchLogisticsList = async () => {
  loading.value = true
  try {
    const response = await axios.get('/api/sugar/logistics/list')
    if (Array.isArray(response.data)) {
      // 保存完整数据到 fullData
      fullData.value = response.data
      total.value = response.data.length
      // 获取当前页数据
      tableData.value = getPagedData(fullData.value)
    } else {
      console.error('接口返回数据格式错误:', response.data)
      ElMessage.error('获取数据失败')
    }
  } catch (error) {
    console.error('获取物流列表失败:', error)
    ElMessage.error('获取物流列表失败')
  } finally {
    loading.value = false
  }
}

// 搜索方法
const handleSearch = async () => {
  currentPage.value = 1
  loading.value = true
  try {
    const response = await axios.get(`/api/sugar/logistics/list/name?name=${searchForm.logisticsNumber}`)
    if (response.data) {
      // 保存查询结果到 fullData
      fullData.value = [response.data]
      total.value = 1
      tableData.value = getPagedData(fullData.value)
    } else {
      fullData.value = []
      tableData.value = []
      total.value = 0
      ElMessage.warning('未找到相关物流信息')
    }
  } catch (error) {
    console.error('查询物流失败:', error)
    ElMessage.error('查询失败')
    fullData.value = []
    tableData.value = []
    total.value = 0
  } finally {
    loading.value = false
  }
}

// 重置搜索
const resetSearch = () => {
  searchForm.logisticsNumber = ''
  currentPage.value = 1
  fetchLogisticsList()
}

// 新增物流
const handleAdd = () => {
  dialogType.value = 'add'
  dialogVisible.value = true
  logisticsForm.orderId = ''
  logisticsForm.vehicleId = ''
  logisticsForm.employeeId = ''
  logisticsForm.status = '待发货'
  logisticsForm.remark = ''
}

// 查看跟踪
const handleTrack = (row) => {
  trackDialogVisible.value = true
  // TODO: 加载跟踪记录
}

// 新增跟踪
const handleAddTrack = () => {
  addTrackDialogVisible.value = true
  trackForm.content = ''
  trackForm.status = ''
}

// 提交跟踪记录
const submitTrack = async () => {
  if (!trackFormRef.value) return
  await trackFormRef.value.validate((valid) => {
    if (valid) {
      // TODO: 实现提交逻辑
      ElMessage.success('添加跟踪记录成功')
      addTrackDialogVisible.value = false
      // 刷新跟踪记录
    }
  })
}

// 删除物流
const handleDelete = (row) => {
  ElMessageBox.confirm(
    '确认删除该物流记录吗？',
    '警告',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  ).then(async () => {
    try {
      // 使用物流单号调用删除接口
      await axios.delete(`/api/sugar/logistics/delete/name?name=${row.logisticsNumber}`)
      ElMessage.success('删除成功')
      fetchLogisticsList()
    } catch (error) {
      console.error('删除物流失败:', error)
      ElMessage.error('删除物流失败')
    }
  }).catch(() => {
    // 取消删除操作
  })
}

// 提交表单
const handleSubmit = async () => {
  if (!logisticsFormRef.value) return
  await logisticsFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        await axios.post('/api/sugar/logistics/add', logisticsForm)
        ElMessage.success('添加成功')
        dialogVisible.value = false
        // 重新加载列表
        fetchLogisticsList()
      } catch (error) {
        console.error('添加物流失败:', error)
        ElMessage.error('添加物流失败')
      }
    }
  })
}

// 分页方法
const handleSizeChange = (val) => {
  pageSize.value = val
  tableData.value = getPagedData(fullData.value)
}

const handleCurrentChange = (val) => {
  currentPage.value = val
  tableData.value = getPagedData(fullData.value)
}

// 获取订单列表
const fetchOrders = async () => {
  try {
    const response = await axios.get('/api/sugar/orders/list')
    // 只显示待发货的订单
    orderOptions.value = response.data.filter(order => order.status === '待发货')
  } catch (error) {
    console.error('获取订单列表失败:', error)
    ElMessage.error('获取订单列表失败')
  }
}

// 当选择订单时，自动填充收件人信息
const handleOrderChange = (orderId) => {
  const selectedOrder = orderOptions.value.find(order => order.id === orderId)
  if (selectedOrder) {
    // 可以在这里保存订单相关的其他信息，如果后端需要的话
    console.log('选中的订单信息:', selectedOrder)
  }
}

// 获取可用车辆列表
const fetchVehicles = async () => {
  try {
    const response = await axios.get('/api/sugar/vehicle/list')
    vehicleOptions.value = response.data.filter(vehicle => vehicle.status === 'AVAILABLE')
  } catch (error) {
    console.error('获取车辆列表失败:', error)
    ElMessage.error('获取车辆列表失败')
  }
}

// 获取配送员列表
const fetchEmployees = async () => {
  try {
    const response = await axios.get('/api/sugar/employee/list')
    // 只显示职位是配送员的员工
    employeeOptions.value = response.data.filter(employee => employee.position === '配送员')
  } catch (error) {
    console.error('获取配送员列表失败:', error)
    ElMessage.error('获取配送员列表失败')
  }
}

// 组件挂载时加载数据
onMounted(() => {
  fetchLogisticsList()  // 初始化时获取物流列表
  fetchOrders()
  fetchVehicles()
  fetchEmployees()
})
</script>

<style lang="scss" scoped>
@import '@/styles/common.scss';

/* 页面特定样式 */
.dialog-footer {
  padding-top: 20px;
  text-align: right;
}

.el-timeline {
  padding: 20px;
}
</style> 