<template>
  <div id="app">
    <!--<router-view/>-->

    <keep-alive>
      <router-view v-if="$route.meta.keepAlive">
        <!-- 这里是会被缓存的视图组件，比如 Home！ -->
      </router-view>
    </keep-alive>

    <router-view v-if="!$route.meta.keepAlive">
      <!-- 这里是不被缓存的视图组件，比如 Edit！ -->
    </router-view>

  </div>
</template>

<script>


export default {
    name: 'app',
}
</script>

<style lang="scss">
@import '~@/assets/style/public-class.scss';
</style>
