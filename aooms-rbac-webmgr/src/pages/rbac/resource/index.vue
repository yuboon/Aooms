<template>
    <d2-container>
       <!-- <page-header
            slot="header"
            @tableLoad="tableLoad"
            ref="header"/>-->

        <page-main
             @tableLoad="tableLoad"
            :table-data="tableData"
            :loading="loading"/>

    </d2-container>
</template>

<script>
import {BusinessTable1List} from '@/api/demo/business/table/1'
import {httpGet} from '@/api/sys/http'

export default {
    // name 值和本页的 $route.name 一致才可以缓存页面
    name: 'rbac-resource',
    components: {
        'PageMain': () => import('./PageMain.vue')
    },
    data() {
        return {
            tableData: [],
            loading: false,
            page: {
                current: 1,
                size: 5,
                total: 0
            }
        }
    },
    methods: {
        tableLoad(params,jumpFirst){
            //if(jumpFirst) this.page.current = 1;
            //Object.assign(params,{page:this.page.current,limit:this.page.size}); // 分页参数拷贝
            //this.loading = true;

            this.loading = true;
            httpGet('aooms/rbac/resource/findTree').then(res => {
                this.tableData = res.$tree;
                this.loading = false;
            });
        }
    }
}
</script>
