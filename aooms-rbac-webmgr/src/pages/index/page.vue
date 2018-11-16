<template>
  <d2-container class="page">
    <d2-page-cover
      title="Aooms 1.0.0-alpha.2"
      sub-title="极速微服务开发，不止像JFinal一样简单" style="">
      <!--<d2-icon-svg style="width: 120px;" name="d2-admin"/>-->
      <img :src="`${$baseUrl}image/logo.png`"/>
      <template slot="footer">
        <!--<div class="page__btn-group"></div>-->
        <!--<d2-badge/>-->
        <!-- <d2-help-btn/> -->
        <aooms-badge />
      </template>
    </d2-page-cover>
  </d2-container>
</template>

<script>
import D2HelpBtn from './components/d2-help-btn'
import D2Badge from './components/d2-badge'
import AoomsBadge from './components/aooms-badge'

export default {
  components: {
    D2HelpBtn,
    D2Badge,
    AoomsBadge
  },
  watch: {
      // 如果路由有变化，会再次执行该方法  注意：router中使用动态路由才能触发  index/:id
      '$route' (to, from) {
          //alert(111);
      }
  },
  mounted(){
  },
  methods:{

  }
}
</script>

<style lang="scss" scoped>
@import '~@/assets/style/public.scss';
.page {
  .page__btn-group {
    color: $color-text-placehoder;
    font-size: 12px;
    margin-top: -10px;
    margin-bottom: 20px;
    span {
      color: $color-text-sub;
      &:hover {
        color: $color-text-main;
      }
    }
  }
}
</style>
