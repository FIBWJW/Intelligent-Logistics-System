<template>
    <div class="app-container">
      <!-- 搜索区域 -->
      <el-card shadow="never">
        <el-form :inline="true" :model="searchForm" class="search-form">
          <el-form-item label="操作人">
            <el-input v-model="searchForm.username" placeholder="请输入操作人" clearable />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleSearch">查询</el-button>
            <el-button @click="resetSearch">重置</el-button>
          </el-form-item>
        </el-form>
      </el-card>
  
      <!-- 日志表格 -->
      <el-card shadow="never" style="margin-top: 20px">
        <el-table
          :data="tableData"
          style="width: 100%"
          border
          stripe
          v-loading="loading"
        >
          <el-table-column prop="id" label="ID" width="80" />
          <el-table-column prop="username" label="操作人" width="120" />
          <el-table-column prop="action" label="操作内容" />
          <el-table-column prop="timestamp" label="操作时间" width="180">
            <template #default="scope">
              {{ formatDate(scope.row.timestamp) }}
            </template>
          </el-table-column>
        </el-table>
  
        <!-- 分页器 -->
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
  </template>
  
  <script setup>
import { ElMessage } from 'element-plus'
import { ref, reactive, onMounted } from 'vue'
import axios from 'axios'
  
  const searchForm = reactive({
    username: ''
  })
  
  const tableData = ref([])
  const loading = ref(false)
  const currentPage = ref(1)
  const pageSize = ref(10)
  const total = ref(0)
  
  // 格式化时间
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
  
  // 获取日志列表（分页+查询）
  const fetchLogs = async () => {
    loading.value = true
    try {
      const response = await axios.get('/api/logs/list', {
        params: {
          username: searchForm.username,
          page: currentPage.value,
          pageSize: pageSize.value
        }
      })
      tableData.value = response.data.records || []
      total.value = response.data.total || 0
    } catch (error) {
      console.error('获取日志失败:', error)
      ElMessage.error('获取日志失败')
    } finally {
      loading.value = false
    }
  }
  
  // 搜索
  const handleSearch = () => {
    currentPage.value = 1
    fetchLogs()
  }
  
  // 重置
  const resetSearch = () => {
    searchForm.username = ''
    currentPage.value = 1
    fetchLogs()
  }
  
  // 分页
  const handleSizeChange = (val) => {
    pageSize.value = val
    fetchLogs()
  }
  const handleCurrentChange = (val) => {
    currentPage.value = val
    fetchLogs()
  }
  
  // 初始加载
  onMounted(() => {
    fetchLogs()
  })
  </script>
  
  <style lang="scss" scoped>
  .pagination-container {
    margin-top: 20px;
    text-align: right;
  }

  </style>
  