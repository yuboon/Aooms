<template>
    <d2-container>
        <page-header
                slot="header"
                @tableLoad="tableLoad"
                ref="header"/>

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
        name: 'rbac-org',
        components: {
            'PageHeader': () => import('./PageHeader.vue'),
            'PageMain': () => import('./PageMain.vue'),
            'PageFooter': () => import('../user/PageFooter.vue')
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
            tableLoad(params, jumpFirst){
                if (jumpFirst) this.page.current = 1;

                // 分页参数、查询条件拷贝
                Object.assign(params,this.$refs.header.getFormData(),{
                    page: this.page.current,
                    limit: this.page.size,
                    parent_org_id :this.$refs.main.parent_org_id
                });

                this.loading = true;
                httpGet('aooms/rbac/org/findList', params).then(res => {
                    this.loading = false;
                    this.table = res.$data.list
                    this.page.total = res.$data.total;
                    if(res.$data.currentTotal == 0 && this.page.current > 1){
                        this.page.current = this.page.current - 1; // 当前页没有数据时，且不是第一页时，加载上一页
                        this.tableLoad(params);
                    }
                });
            },
            handlePaginationChange(val) {
                this.page = val;
                this.tableLoad({});
            }
        }
    }
</script>
