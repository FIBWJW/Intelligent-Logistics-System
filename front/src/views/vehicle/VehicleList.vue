<template>
  <div class="app-container">
    <div class="search-container">
      <el-card shadow="never">
        <el-form :inline="true" :model="searchForm" class="search-form">
          <el-form-item label="车牌号">
            <el-input v-model="searchForm.licensePlate" placeholder="请输入车牌号" clearable />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleSearch">查询</el-button>
            <el-button @click="resetSearch">重置</el-button>
            <el-button type="success" @click="handleAdd">新增车辆</el-button>
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
          <el-table-column prop="id" label="ID" width="80" />
          <el-table-column prop="licensePlate" label="车牌号" width="120" />
          <el-table-column prop="model" label="车型" width="120" />
          <el-table-column prop="status" label="状态" width="100">
            <template #default="scope">
              <el-tag :type="scope.row.status === 'IN_USE' ? 'warning' : 'success'">
                {{ scope.row.status === 'IN_USE' ? '使用中' : '空闲' }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" fixed="right" width="200">
            <template #default="scope">
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

    <!-- 车辆表单对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogType === 'add' ? '新增车辆' : '编辑车辆'"
      width="500px"
    >
      <el-form
        :model="vehicleForm"
        :rules="rules"
        ref="vehicleFormRef"
        label-width="100px"
        class="dialog-form"
      >
        <el-form-item label="车牌号" prop="licensePlate">
          <el-input v-model="vehicleForm.licensePlate" />
        </el-form-item>
        <el-form-item label="车型" prop="model">
          <el-select v-model="vehicleForm.model" style="width: 100%">
            <el-option label="货车" value="货车" />
            <el-option label="卡车" value="卡车" />
            <el-option label="面包车" value="面包车" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="vehicleForm.status" style="width: 100%">
            <el-option label="使用中" value="IN_USE" />
            <el-option label="空闲" value="AVAILABLE" />
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
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import axios from 'axios'

// 搜索表单
const searchForm = reactive({
  licensePlate: ''
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
const vehicleFormRef = ref()
const vehicleForm = reactive({
  licensePlate: '',
  model: '',
  status: 'AVAILABLE'
})

// 表单验证规则
const rules = {
  licensePlate: [
    { required: true, message: '请输入车牌号', trigger: 'blur' },
    { pattern: /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领][A-Z][A-Z0-9]{5}$/, 
      message: '请输入正确的车牌号', trigger: 'blur' }
  ],
  model: [
    { required: true, message: '请选择车型', trigger: 'change' }
  ],
  status: [
    { required: true, message: '请选择状态', trigger: 'change' }
  ]
}

// 前端分页方法
const getPagedData = (data) => {
  const start = (currentPage.value - 1) * pageSize.value
  const end = start + pageSize.value
  return data.slice(start, end)
}

// 获取车辆列表
const fetchVehicleList = async () => {
  loading.value = true
  try {
    const response = await axios.get('/api/sugar/vehicle/list')
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
    console.error('获取车辆列表失败:', error)
    ElMessage.error('获取车辆列表失败')
  } finally {
    loading.value = false
  }
}

// 搜索方法
const handleSearch = async () => {
  currentPage.value = 1
  loading.value = true
  try {
    const response = await axios.get(`/api/sugar/vehicle/list/name?name=${searchForm.licensePlate}`)
    if (response.data) {
      fullData.value = [response.data]
      total.value = 1
      tableData.value = getPagedData(fullData.value)
    } else {
      fullData.value = []
      tableData.value = []
      total.value = 0
      ElMessage.warning('未找到相关车辆')
    }
  } catch (error) {
    console.error('查询车辆失败:', error)
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
  searchForm.licensePlate = ''
  currentPage.value = 1
  fetchVehicleList()
}

// 新增车辆
const handleAdd = () => {
  dialogType.value = 'add'
  dialogVisible.value = true
  vehicleForm.licensePlate = ''
  vehicleForm.model = ''
  vehicleForm.status = 'AVAILABLE'
}

// 编辑车辆
const handleEdit = (row) => {
  dialogType.value = 'edit'
  dialogVisible.value = true
  Object.assign(vehicleForm, row)
}

// 删除车辆
const handleDelete = (row) => {
  ElMessageBox.confirm(
    '确认删除该车辆吗？',
    '警告',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  ).then(async () => {
    try {
      await axios.delete(`/api/sugar/vehicle/delete/${row.id}`)
      ElMessage.success('删除成功')
      fetchVehicleList()
    } catch (error) {
      console.error('删除车辆失败:', error)
      ElMessage.error('删除车辆失败')
    }
  }).catch(() => {
    // 取消删除操作
  })
}

// 提交表单
const handleSubmit = async () => {
  if (!vehicleFormRef.value) return
  await vehicleFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        if (dialogType.value === 'add') {
          await axios.post('/api/sugar/vehicle/add', vehicleForm)
          ElMessage.success('添加成功')
        } else {
          await axios.put('/api/sugar/vehicle/update', vehicleForm)
          ElMessage.success('修改成功')
        }
        dialogVisible.value = false
        fetchVehicleList()
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

// 查看详情
const handleView = async (row) => {
  try {
    const response = await axios.get(`/api/sugar/vehicle/${row.id}`)
    detailData.value = response.data
    detailVisible.value = true
  } catch (error) {
    console.error('获取车辆详情失败:', error)
    ElMessage.error('获取车辆详情失败')
  }
}

// 初始化加载数据
fetchVehicleList()
</script>

<style lang="scss" scoped>
@import '@/styles/common.scss';

/* 页面特定样式 */
.dialog-footer {
  padding-top: 20px;
  text-align: right;
}
</style> 