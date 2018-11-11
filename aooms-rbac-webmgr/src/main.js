// polyfill
import 'babel-polyfill'
// Vue
import Vue from 'vue'
import App from './App'
// store
import store from '@/store/index'
// 模拟数据
import '@/mock'
// 多国语
import i18n from './i18n'
// 核心插件
import d2Admin from '@/plugin/d2admin'

// [ 可选插件组件 ]D2-Crud
import D2Crud from '@d2-projects/d2-crud'
// [ 可选插件组件 ] 图表
import VCharts from 'v-charts'
// [ 可选插件组件 ] 右键菜单
import contentmenu from 'v-contextmenu'
import 'v-contextmenu/dist/index.css'
// [ 可选插件组件 ] JSON 树状视图
import vueJsonTreeView from 'vue-json-tree-view'
// [ 可选插件组件 ] 网格布局组件
import { GridLayout, GridItem } from 'vue-grid-layout'
// [ 可选插件组件 ] 区域划分组件
import SplitPane from 'vue-splitpane'

// 菜单和路由设置
import router from './router'
import { menuHeader, menuAside } from '@/menu'
import { frameInRoutes } from '@/router/routes'
import layoutHeaderAside from '@/layout/header-aside'
import { httpGet } from '@/api/sys/http'

// 核心插件
Vue.use(d2Admin)

// 可选插件组件
Vue.use(D2Crud)
Vue.use(VCharts)
Vue.use(contentmenu)
Vue.use(vueJsonTreeView)
Vue.component('d2-grid-layout', GridLayout)
Vue.component('d2-grid-item', GridItem)
Vue.component('SplitPane', SplitPane)

// 发起Promise
new Promise(function(resolve, reject){
    // 菜单动态查询加载
    httpGet('aooms/rbac/resource/findTree',{parent_resource_id:'ROOT'}).then(res => {
        var list = res.$tree;

        function convertD2AdminMenu(li) {
            li.forEach(item => {
                // menu
                item.title = item.resource_name;
                item.path = item.resource_url;

                // router
                item.name = item.resource_url.replace(/\//g, "-").substring(1);
                item.meta = {requiresAuth: false, keepAlive: true, title: item.resource_name};
                if (item.parent_resource_id == 'ROOT') {
                    item.component = layoutHeaderAside;
                } else {
                    if (item.path) {
                        item.component = () => import('@/pages' + item.path);
                    }
                }
                convertD2AdminMenu(item.children || []);
            });
        }

        convertD2AdminMenu(list);
        list.forEach(item => {
            //menuAside.push(item);
            //menuHeader.push(item);
            //frameInRoutes.push(item);
        });

        // 追加路由
        router.addRoutes(list);
        // 继续 then
        resolve('');
    });

}).then(function(data) {

    // 加载Vue
    new Vue({
        router,
        store,
        i18n,
        render: h => h(App),
        created () {
            // 处理路由 得到每一级的路由设置
            this.$store.commit('d2admin/page/init', frameInRoutes)
            // 设置顶栏菜单
            this.$store.commit('d2admin/menu/headerSet', menuHeader)
            // 初始化菜单搜索功能
            this.$store.commit('d2admin/search/init', menuHeader)
        },
        mounted () {
            // 展示系统信息
            this.$store.commit('d2admin/releases/versionShow')
            // 检查最新版本
            this.$store.dispatch('d2admin/releases/checkUpdate')
            // 用户登录后从数据库加载一系列的设置
            this.$store.commit('d2admin/account/load')
            // 获取并记录用户 UA
            this.$store.commit('d2admin/ua/get')
            // 初始化全屏监听
            this.$store.commit('d2admin/fullscreen/listen')
        },
        watch: {
            // 监听路由 控制侧边栏显示
            '$route.matched' (val) {
                const _side = menuAside.filter(menu => menu.path === val[0].path)
                this.$store.commit('d2admin/menu/asideSet', _side.length > 0 ? _side[0].children : [])
            }
        }
    }).$mount('#app')
});

