<template>
  <router-view></router-view>
</template>

<script setup >
</script>

<style>
html, body {
  margin: 0;
  padding: 0;
  height: 100%;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
}

#app {
  height: 100%;
}

/* 覆盖 Element Plus 的一些默认样式 */
:root {
  --el-color-primary: #1890ff;
  --el-color-primary-light-3: #40a9ff;
  --el-color-primary-light-5: #69c0ff;
  --el-color-primary-light-7: #91d5ff;
  --el-color-primary-light-8: #bae7ff;
  --el-color-primary-light-9: #e6f7ff;
  --el-color-primary-dark-2: #096dd9;
}

.el-button--primary {
  --el-button-hover-bg-color: var(--el-color-primary-light-3);
  --el-button-hover-border-color: var(--el-color-primary-light-3);
}

.el-input__wrapper {
  box-shadow: 0 0 0 1px #dcdfe6;
}

.el-input__wrapper:hover {
  box-shadow: 0 0 0 1px var(--el-color-primary) !important;
}

.el-input__wrapper.is-focus {
  box-shadow: 0 0 0 1px var(--el-color-primary) !important;
}
</style>
