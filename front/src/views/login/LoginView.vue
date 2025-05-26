<template>
  <div class="login-container">
    <div class="login-box">
      <div class="login-title">
        <h2>物流管理系统</h2>
      </div>
      <el-form
        ref="loginFormRef"
        :model="loginForm"
        :rules="loginRules"
        class="login-form"
      >
        <el-form-item prop="username">
          <el-input
            v-model="loginForm.username"
            placeholder="用户名"
            prefix-icon="User"
          />
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            type="password"
            placeholder="密码"
            prefix-icon="Lock"
            show-password
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="loading" class="login-button" @click="handleLogin">
            登录
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: 'LoginView',
  data() {
    return {
      loginForm: {
        username: '',
        password: ''
      },
      loading: false,
      loginRules: {
        // ... 规则配置
      }
    }
  },
  methods: {
    // ... 方法保持不变
  }
}
</script>

<style scoped>
.login-container {
  height: 100vh;
  width: 100vw;
  display: flex;
  justify-content: center;
  align-items: center;
  background: url('/Image.png') center/cover no-repeat;
  position: relative;
  overflow: hidden;
}

.login-container::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: linear-gradient(
    rgba(0, 0, 0, 0.5),
    rgba(0, 0, 0, 0.3)
  );
  backdrop-filter: blur(2px);
}

.login-box {
  width: 400px;
  padding: 40px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 8px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
  position: relative;
  z-index: 1;
  animation: slideIn 0.6s cubic-bezier(0.23, 1, 0.32, 1);
}

.login-title {
  text-align: center;
  margin-bottom: 30px;
}

.login-title h2 {
  font-size: 28px;
  color: #303133;
  margin: 0;
  font-weight: 500;
  letter-spacing: 1px;
}

.login-form :deep(.el-input__wrapper) {
  background-color: rgba(255, 255, 255, 0.8);
  border: none;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);
  transition: all 0.3s ease;
}

.login-form :deep(.el-input__wrapper:hover),
.login-form :deep(.el-input__wrapper.is-focus) {
  background-color: rgba(255, 255, 255, 0.95);
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.login-button {
  width: 100%;
  height: 40px;
  font-size: 16px;
  letter-spacing: 1px;
  background: linear-gradient(135deg, #409EFF, #007FFF);
  border: none;
  transition: all 0.3s ease;
}

.login-button:hover {
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(64, 158, 255, 0.3);
}

@keyframes slideIn {
  from {
    opacity: 0;
    transform: translateY(30px);
    filter: blur(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
    filter: blur(0);
  }
}

/* 响应式设计 */
@media screen and (max-width: 480px) {
  .login-box {
    width: 90%;
    padding: 30px 20px;
  }
  
  .login-title h2 {
    font-size: 24px;
  }
}
</style> 