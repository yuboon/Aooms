<template>
    <d2-container>
        <page-header
                slot="header"
                @getTableData="getTableData"
                ref="header"/>

        <page-main
                slot="section"
                style="width: 200px"
                @getTableData="getTableData"
                :table-data="table"
                :loading="loading"/>

        <page-main
                @getTableData="getTableData"
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
            'PageFooter': () => import('../org/PageFooter.vue')
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
            getTableData(params, jumpFirst) {
                if (jumpFirst) this.page.current = 1;
                Object.assign(params, {page: this.page.current, limit: this.page.size}); // 分页参数拷贝
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
