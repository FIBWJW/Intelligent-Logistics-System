<template>
  <div class="login-container">
    <div class="login-box">
      <h2>物流管理系统</h2>
      <el-form :model="loginForm" :rules="rules" ref="loginFormRef">
        <el-form-item prop="username">
          <el-input 
            v-model="loginForm.username" 
            placeholder="请输入用户名"
            prefix-icon="User"
          />
        </el-form-item>
        <el-form-item prop="password">
          <el-input 
            v-model="loginForm.password" 
            type="password" 
            placeholder="请输入密码"
            prefix-icon="Lock"
            show-password
          />
        </el-form-item>
        <el-form-item>
          <el-checkbox v-model="loginForm.remember">记住密码</el-checkbox>
          <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
          <el-button type="primary" :loading="loading" @click="register" style="width: 20%">
            注册
          </el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="loading" @click="handleLogin" style="width: 100%">
            登录系统
          </el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" :loading="loading" @click="userLogin" style="width: 100%">
            普通用户登录
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import axios from 'axios'
import { compileString } from 'sass'


const router = useRouter()
const loading = ref(false)

const loginForm = reactive({
  username: '',
  password: '',
  remember: false
})

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ]
}

const loginFormRef = ref()

const register = async () => {
  router.push('/register')
}

const userLogin = async () =>{
  if (!loginFormRef.value) return
  await axios.post('/api/user/login', {
  username: loginForm.username,
  password: loginForm.password
})
.then(res => {
  if (res.data.code === 200) {
    ElMessage.success('登录成功')
    localStorage.setItem('token', res.data.data.token)
    localStorage.setItem('username', res.data.data.username) 
    router.push('/analysis/list')
  } else {
    ElMessage.error(res.data.msg)
  }
})
}

const handleLogin = async () => {
  if (!loginFormRef.value) return

  // 触发表单校验
  await loginFormRef.value.validate(async (valid) => {
    if (!valid) return

    loading.value = true

    try {
      const res = await axios.post('/api/user/login', {
        username: loginForm.username,
        password: loginForm.password
      })

      const { code, data, msg } = res.data

      if (code === 200) {
        // 检查角色是否为 ADMIN
        if (data.role !== 'ADMIN') {
          ElMessage.error('无权限登录，仅限管理员')
          loading.value = false
          return
        }

        ElMessage.success('登录成功')
        localStorage.setItem('token', data.token)
        localStorage.setItem('username', data.username)
        localStorage.setItem('role', data.role)
        router.push('/dashboard/index')
      } else {
        ElMessage.error(msg)
      }
    } catch (err) {
      ElMessage.error('登录请求失败')
    } finally {
      loading.value = false
    }
  })
}



</script>

<style scoped>
.login-container {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f5f5f5;
  background: url('backPicture.jpg') no-repeat center center;
}

.login-box {
  width: 400px;
  padding: 40px;
  background: #fff;
  border-radius: 4px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.login-box h2 {
  text-align: center;
  margin-bottom: 30px;
}

.forget-pwd {
  float: right;
  color: #409EFF;
  text-decoration: none;
}

.forget-pwd:hover {
  text-decoration: underline;
}
</style> 
