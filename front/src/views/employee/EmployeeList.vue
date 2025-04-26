<template>
  <div class="app-container">
    <div class="search-container">
      <el-card shadow="never">
        <el-form :inline="true" :model="searchForm" class="search-form">
          <el-form-item label="员工名">
            <el-input v-model="searchForm.name" placeholder="请输入员工名" clearable />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleSearch">查询</el-button>
            <el-button @click="resetSearch">重置</el-button>
            <el-button type="success" @click="handleAdd">新增员工</el-button>
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
          <el-table-column prop="id" label="工号" width="100" />
          <el-table-column prop="name" label="姓名" width="120" />
          <el-table-column prop="gender" label="性别" width="80">
            <template #default="scope">
              {{ scope.row.gender === 'MALE' ? '男' : '女' }}
            </template>
          </el-table-column>
          <el-table-column prop="contactInfo" label="电话号码" width="120" />
          <el-table-column prop="position" label="职位" width="120" />
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

    <!-- 员工表单对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogType === 'add' ? '新增员工' : '编辑员工'"
      width="500px"
    >
      <el-form
        :model="employeeForm"
        :rules="rules"
        ref="employeeFormRef"
        label-width="100px"
        class="dialog-form"
      >
        <el-form-item label="姓名" prop="name">
          <el-input v-model="employeeForm.name" />
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="employeeForm.gender">
            <el-radio label="MALE">男</el-radio>
            <el-radio label="FEMALE">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="电话号码" prop="contactInfo">
          <el-input v-model="employeeForm.contactInfo" />
        </el-form-item>
        <el-form-item label="职位" prop="position">
          <el-select v-model="employeeForm.position" style="width: 100%">
            <el-option label="司机" value="司机" />
            <el-option label="配送员" value="配送员" />
            <el-option label="仓库管理员" value="仓库管理员" />
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
  name: ''
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
const employeeFormRef = ref()
const employeeForm = reactive({
  name: '',
  gender: 'MALE',
  contactInfo: '',
  position: ''
})

// 表单验证规则
const rules = {
  name: [
    { required: true, message: '请输入姓名', trigger: 'blur' }
  ],
  gender: [
    { required: true, message: '请选择性别', trigger: 'change' }
  ],
  contactInfo: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
  ],
  position: [
    { required: true, message: '请选择职位', trigger: 'change' }
  ]
}

// 前端分页方法
const getPagedData = (data) => {
  const start = (currentPage.value - 1) * pageSize.value
  const end = start + pageSize.value
  return data.slice(start, end)
}

// 获取员工列表
const fetchEmployeeList = async () => {
  loading.value = true
  try {
    const response = await axios.get('/api/sugar/employee/list')
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
    console.error('获取员工列表失败:', error)
    ElMessage.error('获取员工列表失败')
  } finally {
    loading.value = false
  }
}

// 搜索方法
const handleSearch = async () => {
  currentPage.value = 1
  loading.value = true
  try {
    const response = await axios.get(`/api/sugar/employee/list/name?name=${searchForm.name}`)
    if (response.data) {
      fullData.value = [response.data]
      total.value = 1
      tableData.value = getPagedData(fullData.value)
    } else {
      fullData.value = []
      tableData.value = []
      total.value = 0
      ElMessage.warning('未找到相关员工')
    }
  } catch (error) {
    console.error('查询员工失败:', error)
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
  searchForm.name = ''
  currentPage.value = 1
  fetchEmployeeList()
}

// 新增员工
const handleAdd = () => {
  dialogType.value = 'add'
  dialogVisible.value = true
  employeeForm.name = ''
  employeeForm.gender = 'MALE'
  employeeForm.contactInfo = ''
  employeeForm.position = ''
}

// 编辑员工
const handleEdit = (row) => {
  dialogType.value = 'edit'
  dialogVisible.value = true
  Object.assign(employeeForm, row)
}

// 删除员工
const handleDelete = (row) => {
  ElMessageBox.confirm(
    '确认删除该员工吗？',
    '警告',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  ).then(async () => {
    try {
      await axios.delete(`/api/sugar/employee/delete/${row.id}`)
      ElMessage.success('删除成功')
      fetchEmployeeList()
    } catch (error) {
      console.error('删除员工失败:', error)
      ElMessage.error('删除员工失败')
    }
  }).catch(() => {
    // 取消删除操作
  })
}

// 提交表单
const handleSubmit = async () => {
  if (!employeeFormRef.value) return
  await employeeFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        if (dialogType.value === 'add') {
          await axios.post('/api/sugar/employee/add', employeeForm)
          ElMessage.success('添加成功')
        } else {
          await axios.put('/api/sugar/employee/update', employeeForm)
          ElMessage.success('修改成功')
        }
        dialogVisible.value = false
        fetchEmployeeList()
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
    const response = await axios.get(`/api/sugar/employee/${row.id}`)
    detailData.value = response.data
    detailVisible.value = true
  } catch (error) {
    console.error('获取员工详情失败:', error)
    ElMessage.error('获取员工详情失败')
  }
}

// 初始化加载数据
fetchEmployeeList()
</script>

<style lang="scss" scoped>
@import '@/styles/common.scss';

/* 页面特定样式 */
.dialog-footer {
  padding-top: 20px;
  text-align: right;
}
</style> 