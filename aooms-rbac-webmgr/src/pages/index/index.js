import page from './page'
import {menuAside, menuHeader} from "../../menu";
import router from "../../router";

// 加载菜单依赖
import { frameInRoutes } from '../../router/routes'
import layoutHeaderAside from '@/layout/header-aside'
import { httpGet } from '@/api/sys/http'

// 菜单动态查询加载
httpGet('aooms/rbac/resourceService/findTree',{parent_resource_id:'ROOT'}).then(res => {
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
        menuAside.push(item);
        menuHeader.push(item);
        frameInRoutes.push(item);
        router.app.$store.commit('d2admin/page/init', frameInRoutes);
    });

    // 追加动态路由
    router.addRoutes(list);
});


export default page
