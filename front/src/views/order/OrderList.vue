<template>
  <div class="app-container">
    <div class="search-container">
      <el-card shadow="never">
        <el-form :inline="true" :model="searchForm" class="search-form">
          <el-form-item label="订单编号">
            <el-input v-model="searchForm.orderNumber" placeholder="请输入订单编号" clearable />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleSearch">查询</el-button>
            <el-button @click="resetSearch">重置</el-button>
            <el-button type="success" @click="handleAdd">新增订单</el-button>
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
          <el-table-column prop="orderNumber" label="订单编号" width="150" />
          <el-table-column prop="recipientName" label="收件人" width="120" />
          <el-table-column prop="recipientPhone" label="收件人电话" width="130" />
          <el-table-column prop="address" label="收件地址" min-width="250" />
          <el-table-column prop="createdAt" label="创建时间" width="180">
            <template #default="scope">
              {{ formatDate(scope.row.createdAt) }}
            </template>
          </el-table-column>
          <el-table-column prop="status" label="状态" width="100">
            <template #default="scope">
              <el-tag :type="getStatusType(scope.row.status)">
                {{ scope.row.status }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" fixed="right" width="250">
            <template #default="scope">
              <el-button size="small" type="primary" @click="handleView(scope.row)">查看</el-button>
              <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
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

    <!-- 订单表单对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogType === 'add' ? '新增订单' : '编辑订单'"
      width="500px"
    >
      <el-form
        :model="orderForm"
        :rules="rules"
        ref="orderFormRef"
        label-width="100px"
        class="dialog-form"
      >
        <el-form-item label="订单编号" prop="orderNumber">
          <el-input v-model="orderForm.orderNumber" :disabled="dialogType === 'edit'" />
        </el-form-item>
        <el-form-item label="收件人" prop="recipientName">
          <el-input v-model="orderForm.recipientName" />
        </el-form-item>
        <el-form-item label="收件人电话" prop="recipientPhone">
          <el-input v-model="orderForm.recipientPhone" />
        </el-form-item>
        <el-form-item label="收件地址" prop="address">
          <el-input v-model="orderForm.address" type="textarea" :rows="3" />
        </el-form-item>
        <el-form-item label="订单状态" prop="status">
          <el-select v-model="orderForm.status" style="width: 100%">
            <el-option label="待发货" value="待发货" />
            <el-option label="已发货" value="已发货" />
            <el-option label="已完成" value="已完成" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSubmit">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 详情对话框 -->
    <el-dialog
      v-model="detailVisible"
      title="订单详情"
      width="500px"
    >
      <el-descriptions :column="1" border>
        <el-descriptions-item label="订单编号">{{ detailData.orderNumber }}</el-descriptions-item>
        <el-descriptions-item label="收件人">{{ detailData.recipientName }}</el-descriptions-item>
        <el-descriptions-item label="收件人电话">{{ detailData.recipientPhone }}</el-descriptions-item>
        <el-descriptions-item label="收件地址">{{ detailData.address }}</el-descriptions-item>
        <el-descriptions-item label="订单状态">
          <el-tag :type="getStatusType(detailData.status)">{{ detailData.status }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="创建时间">{{ formatDate(detailData.createdAt) }}</el-descriptions-item>
      </el-descriptions>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="detailVisible = false">关闭</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import axios from 'axios'

// 搜索表单
const searchForm = reactive({
  orderNumber: ''
})

// 表格数据
const tableData = ref([])
const loading = ref(false)
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

// 保存完整数据的响应式引用
const fullData = ref([])

// 对话框相关
const dialogVisible = ref(false)
const dialogType = ref('add')
const orderFormRef = ref()
const orderForm = reactive({
  orderNumber: '',
  recipientName: '',
  recipientPhone: '',
  address: '',
  status: '待发货'
})

// 表单验证规则
const rules = {
  orderNumber: [
    { required: true, message: '请输入订单编号', trigger: 'blur' }
  ],
  recipientName: [
    { required: true, message: '请输入收件人', trigger: 'blur' }
  ],
  recipientPhone: [
    { required: true, message: '请输入收件人电话', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
  ],
  address: [
    { required: true, message: '请输入收件地址', trigger: 'blur' }
  ],
  status: [
    { required: true, message: '请选择订单状态', trigger: 'change' }
  ]
}

// 格式化日期
const formatDate = (dateStr) => {
  if (!dateStr) return ''
  const date = new Date(dateStr)
  return date.toLocaleString()
}

// 获取状态标签类型
const getStatusType = (status) => {
  switch (status) {
    case '待发货':
      return 'warning'
    case '已发货':
      return 'primary'
    case '已完成':
      return 'success'
    default:
      return 'info'
  }
}

// 前端分页方法
const getPagedData = (data) => {
  const start = (currentPage.value - 1) * pageSize.value
  const end = start + pageSize.value
  return data.slice(start, end)
}

// 获取订单列表
const fetchOrderList = async () => {
  loading.value = true
  try {
    const response = await axios.get('/api/sugar/orders/list')
    if (Array.isArray(response.data)) {
      // 保存完整数据
      fullData.value = response.data
      total.value = response.data.length
      // 获取当前页数据
      tableData.value = getPagedData(fullData.value)
    } else {
      console.error('接口返回数据格式错误:', response.data)
      ElMessage.error('获取数据失败')
    }
  } catch (error) {
    console.error('获取订单列表失败:', error)
    ElMessage.error('获取订单列表失败')
  } finally {
    loading.value = false
  }
}

// 搜索方法
const handleSearch = async () => {
  currentPage.value = 1
  loading.value = true
  try {
    const response = await axios.get(`/api/sugar/orders/list/name?name=${searchForm.orderNumber}`)
    if (response.data) {
      fullData.value = [response.data]
      total.value = 1
      tableData.value = getPagedData(fullData.value)
    } else {
      fullData.value = []
      tableData.value = []
      total.value = 0
      ElMessage.warning('未找到相关订单')
    }
  } catch (error) {
    console.error('查询订单失败:', error)
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
  searchForm.orderNumber = ''
  currentPage.value = 1
  fetchOrderList()
}

// 新增订单
const handleAdd = () => {
  dialogType.value = 'add'
  dialogVisible.value = true
  orderForm.orderNumber = ''
  orderForm.recipientName = ''
  orderForm.recipientPhone = ''
  orderForm.address = ''
  orderForm.status = '待发货'
}

// 编辑订单
const handleEdit = (row) => {
  dialogType.value = 'edit'
  dialogVisible.value = true
  Object.assign(orderForm, row)
}

// 删除订单
const handleDelete = (row) => {
  ElMessageBox.confirm(
    '确认删除该订单吗？',
    '警告',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  ).then(async () => {
    try {
      await axios.delete(`/api/sugar/orders/delete/${row.id}`)
      ElMessage.success('删除成功')
      fetchOrderList()
    } catch (error) {
      console.error('删除订单失败:', error)
      ElMessage.error('删除订单失败')
    }
  })
}

// 提交表单
const handleSubmit = async () => {
  if (!orderFormRef.value) return
  await orderFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        if (dialogType.value === 'add') {
          await axios.post('/api/sugar/orders/add', orderForm)
          ElMessage.success('添加成功')
        } else {
          await axios.put('/api/sugar/orders/update', orderForm)
          ElMessage.success('修改成功')
        }
        dialogVisible.value = false
        fetchOrderList()
      } catch (error) {
        console.error('操作失败:', error)
        ElMessage.error('操作失败')
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

// 详情相关
const detailVisible = ref(false)
const detailData = ref({})

// 查看详情
const handleView = async (row) => {
  try {
    const response = await axios.get(`/api/sugar/orders/${row.id}`)
    detailData.value = response.data
    detailVisible.value = true
  } catch (error) {
    console.error('获取订单详情失败:', error)
    ElMessage.error('获取订单详情失败')
  }
}

// 初始化加载数据
fetchOrderList()
</script>

<style lang="scss" scoped>
@import '@/styles/common.scss';

/* 页面特定样式 */
.dialog-footer {
  padding-top: 20px;
  text-align: right;
}
</style> 