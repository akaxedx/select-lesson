const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8121',  // 后端服务地址
        changeOrigin: true,  // 允许改变源
        pathRewrite: {
          '^/api': ''  // 将路径中的 /api 替换为空
        }
      }
    }
  }
});