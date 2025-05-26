import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import axios from 'axios'
import * as echarts from 'echarts'
// import 'echarts/map/js/china'

import App from './App.vue'
import router from './router'

const app = createApp(App)

// 注册所有图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}

app.use(createPinia())
app.use(router)
app.use(ElementPlus, {
  locale: zhCn,
  size: 'default',
  zIndex: 3000
})

// 配置 axios
axios.defaults.baseURL = process.env.NODE_ENV === 'production' ? '' : '/'
axios.defaults.timeout = 5000
axios.defaults.headers.post['Content-Type'] = 'application/json'

// 请求拦截器
axios.interceptors.request.use(
  config => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers['token'] = token  
    }
    return config
  },
  error => Promise.reject(error)
)

// 响应拦截器
axios.interceptors.response.use(
  response => response,
  error => {
    if (error.response) {
      switch (error.response.status) {
        case 401:
          localStorage.removeItem('token')
          ElMessage.error('登录状态失效，请重新登录')
          router.push('/login')
          break
        case 403:
          ElMessage.error('没有权限进行此操作')
          break
        case 404:
          ElMessage.error('请求的资源不存在')
          break
        case 500:
          ElMessage.error('服务器错误')
          break
        default:
          ElMessage.error('网络错误')
      }
    } else {
      ElMessage.error('网络连接失败')
    }
    return Promise.reject(error)
  }
)

function loadBMapGLScript(ak) {
  return new Promise((resolve, reject) => {
    if (window.BMapGL) {
      return resolve()
    }

    const script = document.createElement('script')
    script.type = 'text/javascript'
    script.src = `https://api.map.baidu.com/api?v=1.0&type=webgl&ak=${ak}`
    script.onload = resolve
    script.onerror = reject
    document.head.appendChild(script)
  })
}


app.config.globalProperties.$echarts = echarts

app.mount('#app')
