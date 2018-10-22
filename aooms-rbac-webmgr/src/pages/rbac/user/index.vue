<template>
    <d2-container>
        <page-header
                ref="header"
                slot="header"
                @tableLoad="tableLoad"/>

        <page-main
                ref="main"
                @tableLoad="tableLoad"
                :table-data="table"
                :loading="loading"/>

        <page-footer
                slot="footer"
                :current="page.current"
                :size="page.size"
                :total="page.total"
                @change="handlePaginationChange"/>
    </d2-container>
</template>

<script>
import {BusinessTable1List} from '@/api/demo/business/table/1'
import {httpGet} from '@/api/sys/http'

export default {
    // name 值和本页的 $route.name 一致才可以缓存页面
    name: 'rbac-user',
    components: {
        'PageHeader': () => import('./PageHeader.vue'),
        'PageMain': () => import('./PageMain.vue'),
        'PageFooter': () => import('./PageFooter.vue')
    },
    data() {
        return {
            table: [],
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
            if(jumpFirst) this.page.current = 1;

            // 分页参数、查询条件拷贝
            Object.assign(params,this.$refs.header.getFormData(),{
                page: this.page.current,
                limit: this.page.size,
                org_id :this.$refs.main.org_id
            });

            this.loading = true;
            httpGet('aooms/rbac/user/findList', params).then(res => {
                this.loading = false;
                this.table = res.$data.list
                this.page.total = res.$data.total;
            });
        },
        handlePaginationChange(val) {
            this.page = val;
            this.$refs.header.handleFormSubmit();

            /*// nextTick 只是为了优化示例中 notify 的显示
            this.$nextTick(() => {

            })*/
        }
    }
}
</script>
