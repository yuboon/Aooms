<template>
    <d2-container>
        <demo-page-header
            slot="header"
            @submit="handleSubmit"
            ref="header"/>

        <demo-page-main
            :table-data="table"
            :loading="loading"/>

        <demo-page-footer
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
    name: 'user',
    components: {
        'DemoPageHeader': () => import('./PageHeader.vue'),
        'DemoPageMain': () => import('./PageMain.vue'),
        'DemoPageFooter': () => import('./PageFooter.vue')
    },
    data() {
        return {
            table: [],
            loading: false,
            page: {
                current: 1,
                size: 100,
                total: 0
            }
        }
    },

    mounted() {
        this.$nextTick(() => {
            httpGet('aooms/rbac/user/findList', {
                id: '1'
            }).then(res => {
                this.loading = false
                this.$notify({
                    title: '数据请求完毕'
                })
                this.table = res.$data.list
                this.page = {
                    current: 1,
                    size: 100,
                    total: 1
                }
            });
        })
    },

    methods: {
        handlePaginationChange(val) {
            this.$notify({
                title: '分页变化',
                message: `当前第${val.current}页 共${val.total}条 每页${val.size}条`
            })
            this.page = val
            // nextTick 只是为了优化示例中 notify 的显示
            this.$nextTick(() => {
                this.$refs.header.handleFormSubmit()
            })
        },
        handleSubmit(form) {
            this.loading = true
            this.$notify({
                title: '开始请求模拟表格数据'
            })
            BusinessTable1List({
                ...form,
                page: this.page
            }).then(res => {
                this.loading = false
                this.$notify({
                    title: '模拟表格数据请求完毕'
                })
                this.table = res.list
                this.page = res.page
            }).catch(err => {
                this.loading = false
                this.$notify({
                    title: '模拟表格数据请求异常'
                })
                console.log('err', err)
            })
        }
    }
}
</script>
