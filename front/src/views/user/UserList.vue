<template>
  <div class="app-container">
    <div class="search-container">
      <el-card shadow="never">
        <el-form :inline="true" :model="searchForm" class="search-form">
          <el-form-item label="用户名">
            <el-input v-model="searchForm.name" placeholder="请输入用户名" clearable />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleSearch">查询</el-button>
            <el-button @click="resetSearch">重置</el-button>
            <el-button type="success" @click="handleAdd">新增用户</el-button>
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
          <el-table-column prop="username" label="用户名" width="120" />
          <el-table-column prop="phone" label="手机号" width="120" />
          <el-table-column prop="role" label="角色" width="100">
            <template #default="scope">
              <el-tag :type="scope.row.role === 'ADMIN' ? 'danger' : ''">
                {{ scope.row.role === 'ADMIN' ? '管理员' : '普通用户' }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="createdat" label="创建时间" width="180">
            <template #default="scope">
              {{ formatDate(scope.row.createdat) }}
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

    <!-- 用户表单对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogType === 'add' ? '新增用户' : '编辑用户'"
      width="500px"
    >
      <el-form
        :model="userForm"
        :rules="rules"
        ref="userFormRef"
        label-width="100px"
        class="dialog-form"
      >
        <el-form-item label="用户名" prop="username">
          <el-input v-model="userForm.username" />
        </el-form-item>
        <el-form-item label="手机号" prop="phone">
          <el-input v-model="userForm.phone" />
        </el-form-item>
        <el-form-item label="密码" prop="password" v-if="dialogType === 'add'">
          <el-input v-model="userForm.password" type="password" show-password />
        </el-form-item>
        <el-form-item label="角色" prop="role">
          <el-select v-model="userForm.role" style="width: 100%">
            <el-option label="管理员" value="ADMIN" />
            <el-option label="普通用户" value="USER" />
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

// 对话框相关
const dialogVisible = ref(false)
const dialogType = ref('add')
const userFormRef = ref()
const userForm = reactive({
  username: '',
  phone: '',
  password: '',
  role: 'USER'
})

// 表单验证规则
const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能小于6位', trigger: 'blur' }
  ],
  role: [
    { required: true, message: '请选择角色', trigger: 'change' }
  ]
}

// 格式化日期
const formatDate = (dateStr) => {
  if (!dateStr) return ''
  const date = new Date(dateStr)
  return date.toLocaleString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
    second: '2-digit'
  })
}

const allSearchResults = ref([]) // 保存模糊查询的所有结果
// 搜索方法
const handleSearch = async () => {
  currentPage.value = 1
  loading.value = true
  try {
    const response = await axios.get(`/api/sugar/user/list/name?name=${searchForm.name}`)
    if (Array.isArray(response.data)) {
      tableData.value = response.data.slice(0, pageSize.value)
      total.value = response.data.length
      allSearchResults.value = response.data // 保存所有结果用于分页
    } else {
      tableData.value = []
      total.value = 0
      ElMessage.warning('未找到相关用户')
    }
  } catch (error) {
    console.error('查询用户失败:', error)
    ElMessage.error('查询失败')
    tableData.value = []
    total.value = 0
  } finally {
    loading.value = false
  }
}


const fullUserList = ref([]) // 所有用户数据

const fetchUserList = async () => {
  loading.value = true
  try {
    const response = await axios.get('/api/sugar/user/list')
    if (Array.isArray(response.data)) {
      fullUserList.value = response.data
      total.value = response.data.length
      updatePagedData()
    } else {
      console.error('接口返回数据格式错误:', response.data)
      ElMessage.error('获取数据失败')
    }
  } catch (error) {
    console.error('获取用户列表失败:', error)
    ElMessage.error('获取用户列表失败')
  } finally {
    loading.value = false
  }
}

// 重置搜索
const resetSearch = () => {
  searchForm.name = ''
  fetchUserList() // 重置时显示所有用户列表
}

// 新增用户
const handleAdd = () => {
  dialogType.value = 'add'
  dialogVisible.value = true
  userForm.username = ''
  userForm.phone = ''
  userForm.password = ''
  userForm.role = 'USER'
}

// 编辑用户
const handleEdit = (row) => {
  dialogType.value = 'edit'
  dialogVisible.value = true
  Object.assign(userForm, row)
  userForm.password = '' // 编辑时不显示密码
}

// 删除用户
const handleDelete = (row) => {
  ElMessageBox.confirm(
    '确认删除该用户吗？',
    '警告',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  ).then(async () => {
    try {
      await axios.delete(`/api/sugar/user/delete/${row.id}`)
      ElMessage.success('删除成功')
      fetchUserList()
    } catch (error) {
      console.error('删除用户失败:', error)
      ElMessage.error('删除用户失败')
    }
  })
}

// 提交表单
const handleSubmit = async () => {
  if (!userFormRef.value) return
  await userFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        if (dialogType.value === 'add') {
          await axios.post('/api/sugar/user/add', userForm)
          ElMessage.success('添加成功')
        } else {
          await axios.put('/api/sugar/user/update', userForm)
          ElMessage.success('修改成功')
        }
        dialogVisible.value = false
        fetchUserList()
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
  updatePagedData()
}

const handleCurrentChange = (val) => {
  currentPage.value = val
  updatePagedData()
}

const updatePagedData = () => {
  const start = (currentPage.value - 1) * pageSize.value
  const end = start + pageSize.value
  if (searchForm.name) {
    tableData.value = allSearchResults.value.slice(start, end)
  } else {
    tableData.value = fullUserList.value.slice(start, end)
  }
}

// 初始化加载数据
fetchUserList()
</script>

<style lang="scss" scoped>
@import '@/styles/common.scss';

/* 页面特定样式 */
.dialog-footer {
  padding-top: 20px;
  text-align: right;
}
</style> 