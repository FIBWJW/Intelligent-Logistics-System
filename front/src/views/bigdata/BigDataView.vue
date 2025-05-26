<template>
  <div class="bigdata-btn-group">

    <el-button class="bigdata-btn" type="default" @click="goToOrder">物流订单分析</el-button>
    <el-button class="bigdata-btn" type="default" @click="goToAnalysis">物流运营总分析</el-button>
    <el-button class="bigdata-btn" type="default" @click="goToSmart">智慧预测分析平台</el-button>
    <el-button class="bigdata-btn" type="default" @click="goToScreen">总数据</el-button>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
const router = useRouter()
const goToScreen = () => {
  router.push('/bigdata/screen')
}
const goToSmart = () => {
  router.push('/bigdata/smart')
}
const goToAnalysis = () => {
  router.push('/bigdata/analysis')
}
const goToOrder = () => {
  router.push('/bigdata/order')
}
</script>

<style scoped>
.bigdata-btn-group {
  min-height: 80vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.bigdata-btn {
  width: 260px;
  height: 48px;
  margin: 18px 0;
  font-size: 18px;
  border-radius: 8px;
  border: 1.5px solid #bbb;
  background: #fff;
  transition: box-shadow 0.2s, border-color 0.2s;
}
.bigdata-btn:hover {
  border-color: #409EFF;
  box-shadow: 0 2px 12px rgba(64,158,255,0.12);
  color: #409EFF;
}
</style> 