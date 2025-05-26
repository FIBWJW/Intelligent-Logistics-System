<template>
  <div class="app-wrapper">
    <!-- 侧边栏 -->
    <div class="sidebar-container" :class="{'is-hidden': isCollapse}">
      <Sidebar :is-collapse="isCollapse" />
    </div>

    <!-- 主要内容区 -->
    <div class="main-container" :class="{'is-fullscreen': isCollapse}">
      <!-- 顶部操作栏 -->
      <div class="top-bar">
        <div class="left-area">
          <div class="sidebar-toggle" @click="toggleSidebar">
            <el-icon>
              <Expand v-if="isCollapse"/>
              <Fold v-else/>
            </el-icon>
          </div>
        </div>
        <div class="right-area">
          <el-dropdown @command="handleCommand">
            <span class="user-info">
              <el-avatar :size="32" :icon="UserFilled" />
              <span class="username">{{ username }}</span>
              <el-icon class="el-icon--right">
                <CaretBottom />
              </el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </div>
      <router-view />
    </div>
  </div>
</template>

<script>
import { ref, provide,watchEffect } from 'vue'
import { Fold, Expand, CaretBottom, UserFilled } from '@element-plus/icons-vue'
import { useRouter,useRoute } from 'vue-router'
import Sidebar from './components/Sidebar.vue'

export default {
  components: {
    Sidebar,
    Fold,
    Expand,
    CaretBottom,
    UserFilled
  },
  setup() {
  const isCollapse = ref(false)
  const router = useRouter()
  const route = useRoute()

  const username = ref('用户')

  const updateUsername = () => {
    const storedName = localStorage.getItem('username')
    if (storedName) {
      username.value = storedName
    }
  }

  // 每次路由跳转时检查更新 username
  watchEffect(() => {
    route.fullPath // 触发依赖
    updateUsername()
  })

  const toggleSidebar = () => {
    isCollapse.value = !isCollapse.value
  }

  const handleCommand = (command) => {
    if (command === 'logout') {
      localStorage.removeItem('token')
      localStorage.removeItem('username')
      router.push('/login')
    }
  }

  provide('isCollapse', isCollapse)

  return {
    isCollapse,
    toggleSidebar,
    username,
    handleCommand
  }
}
}
</script>

<style scoped>
.app-wrapper {
  position: relative;
  height: 100vh;
  width: 100%;
}

.sidebar-container {
  position: fixed;
  top: 0;
  left: 0;
  bottom: 0;
  width: 210px;
  background-color: #304156;
  transition: all 0.3s;
  z-index: 1001;
  transform: translateX(0);
}

.sidebar-container.is-hidden {
  transform: translateX(-100%);
}

.main-container {
  position: relative;
  margin-left: 210px;
  min-height: 100%;
  transition: all 0.3s;
}

.main-container.is-fullscreen {
  margin-left: 0;
}

.top-bar {
  position: fixed;
  top: 0;
  right: 0;
  left: 210px;
  height: 60px;
  background-color: #fff;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  z-index: 1000;
  transition: all 0.3s;
}

.main-container.is-fullscreen .top-bar {
  left: 0;
}

.left-area {
  display: flex;
  align-items: center;
}

.right-area {
  display: flex;
  align-items: center;
}

.sidebar-toggle {
  width: 40px;
  height: 40px;
  background-color: #1f2d3d;
  border-radius: 4px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: all 0.3s;
}

.sidebar-toggle:hover {
  background-color: #263445;
}

.sidebar-toggle .el-icon {
  font-size: 20px;
  color: #fff;
}

.user-info {
  display: flex;
  align-items: center;
  cursor: pointer;
  padding: 0 8px;
}

.username {
  margin: 0 8px;
  color: #606266;
  font-size: 14px;
}
</style> 