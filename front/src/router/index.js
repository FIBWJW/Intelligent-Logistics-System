import { createRouter, createWebHistory } from 'vue-router'
import Layout from '../layout/Layout.vue'

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/Register.vue')
  },
  {
    path: '/dashboard',
    component: Layout,
    redirect: '/dashboard/index',
    children: [
      {
        path: 'index',
        name: 'Dashboard',
        component: () => import('../views/Dashboard.vue'),
        meta: { title: '首页', requireAuth: true }
      }
    ]
  },
  {
    path: '/user',
    component: Layout,
    children: [
      {
        path: 'list',
        name: 'UserList',
        component: () => import('../views/user/UserList.vue'),
        meta: { title: '用户信息管理', requireAuth: true }
      }
    ]
  },
  {
    path: '/order',
    component: Layout,
    children: [
      {
        path: 'list',
        name: 'OrderList',
        component: () => import('../views/order/OrderList.vue'),
        meta: { title: '订单管理', requireAuth: true }
      }
    ]
  },
  {
    path: '/employee',
    component: Layout,
    children: [
      {
        path: 'list',
        name: 'EmployeeList',
        component: () => import('../views/employee/EmployeeList.vue'),
        meta: { title: '员工管理', requireAuth: true }
      }
    ]
  },
  {
    path: '/vehicle',
    component: Layout,
    children: [
      {
        path: 'list',
        name: 'VehicleList',
        component: () => import('../views/vehicle/VehicleList.vue'),
        meta: { title: '车辆管理', requireAuth: true }
      }
    ]
  },
  {
    path: '/logistics',
    component: Layout,
    children: [
      {
        path: 'list',
        name: 'LogisticsList',
        component: () => import('../views/logistics/LogisticsList.vue'),
        meta: { title: '物流信息管理', requireAuth: true }
      }
    ]
  },
  {
    path: '/analysis',
    children: [
      {
        path: 'list',
        name: 'Analysis',
        component: () => import('../views/analysis/AnalysisList.vue'),
        meta: { title: '指标分析', requireAuth: true ,name: 'referrer', content:'no-referrer'}
      }
    ]
  },
  {
    path: '/logs',
    component: Layout,
    children: [
      {
        path: 'list',
        name: 'LogsList',
        component: () => import('../views/logs/LogsList.vue'),
        meta: { title: '日志管理', requireAuth: true }
      }
    ]
  },

  {
    path: '/bigdata',
    component: Layout,
    children: [
      {
        path: 'view',
        name: 'BigDataView',
        component: () => import('../views/bigdata/BigDataView.vue'),
        meta: { title: '大数据展示', requireAuth: true }
      },
      {
        path: 'screen',
        name: 'BigDataScreen',
        component: () => import('../views/bigdata/BigScreen.vue'),
        meta: { title: '大数据大屏', requireAuth: true }
      },
      {
        path: 'smart',
        name: 'SmartForecastScreen',
        component: () => import('../views/bigdata/SmartForecastScreen.vue'),
        meta: { title: '智慧预测分析平台', requireAuth: true }
      },
      {
        path: 'analysis',
        name: 'LogisticsAnalysisScreen',
        component: () => import('../views/bigdata/LogisticsAnalysisScreen.vue'),
        meta: { title: '物流运营总分析', requireAuth: true }
      },
      {
        path: 'order',
        name: 'OrderAnalysisScreen',
        component: () => import('../views/bigdata/OrderAnalysisScreen.vue'),
        meta: { title: '物流订单分析', requireAuth: true }
      }
    ]
  }

]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 导航守卫
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  
  // 如果是登录页或注册页，直接放行
  if (to.path === '/login' || to.path === '/register') {
    if (token) {
      next('/dashboard/index')
    } else {
      next()
    }
    return
  }

  // 其他页面需要验证登录状态
  if (!token) {
    next('/login')
  } else {
    next()
  }
})

export default router
