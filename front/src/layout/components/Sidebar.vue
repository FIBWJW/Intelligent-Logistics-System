<template>
  <div class="sidebar-container">
    <el-menu
      :default-active="activeMenu"
      background-color="#304156"
      text-color="#bfcbd9"
      active-text-color="#409EFF"
      :collapse="isCollapse"
      :collapse-transition="false"
      router
    >
      <el-menu-item index="/dashboard/index">
        <el-icon><HomeFilled /></el-icon>
        <span>首页</span>
      </el-menu-item>

      <el-menu-item index="/user/list">
        <el-icon><User /></el-icon>
        <span>用户信息管理</span>
      </el-menu-item>

      <el-menu-item index="/order/list">
        <el-icon><Document /></el-icon>
        <span>订单管理</span>
      </el-menu-item>

      <el-menu-item index="/employee/list">
        <el-icon><UserFilled /></el-icon>
        <span>员工管理</span>
      </el-menu-item>

      <el-menu-item index="/vehicle/list">
        <el-icon><Van /></el-icon>
        <span>车辆管理</span>
      </el-menu-item>

      <el-menu-item index="/logistics/list">
        <el-icon><Box /></el-icon>
        <span>物流信息管理</span>
      </el-menu-item>

      <el-menu-item index="/analysis/list">
        <el-icon><PieChart /></el-icon>
        <span>指标分析</span>
      </el-menu-item>

      <el-menu-item index="/logs/list">
        <el-icon><Document /></el-icon>
        <span>日志管理</span>
      </el-menu-item>

      <el-menu-item index="/bigdata/view">
        <el-icon><PieChart /></el-icon>
        <span>大数据展示</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
import {
  HomeFilled,
  User,
  Document,
  UserFilled,
  Van,
  Box,
  PieChart
} from '@element-plus/icons-vue'

export default {
  name: 'Sidebar',
  components: {
    HomeFilled,
    User,
    Document,
    UserFilled,
    Van,
    Box,
    PieChart
  },
  props: {
    isCollapse: {
      type: Boolean,
      default: false
    }
  },
  computed: {
    activeMenu() {
      return this.$route.path
    }
  }
}
</script>

<style scoped>
.el-menu {
  border-right: none;
}

.el-menu:not(.el-menu--collapse) {
  width: 210px;
}
</style> 