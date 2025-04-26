import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import axios from 'axios'

const app = createApp(App)

// 注册所有图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}

// 先挂载路由
app.use(router)
app.use(ElementPlus)

// 配置 axios
axios.defaults.baseURL = 'http://localhost:9009'
app.config.globalProperties.$axios = axios

app.mount('#app') 