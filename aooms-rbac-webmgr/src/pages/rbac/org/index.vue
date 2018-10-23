<template>
    <d2-container>
        <page-header
                slot="header"
                @tableLoad="tableLoad"
                ref="header"/>

        <page-main
                ref="main"
                @pageHeaderFormData="pageHeaderFormData"
        />

    </d2-container>
</template>

<script>
    import PageHeader from './PageHeader.vue'
    import PageMain from './PageMain.vue'

    export default {
        // name 值和本页的 $route.name 一致才可以缓存页面
        name: 'rbac-org',
        components: {
            PageHeader,
            PageMain
        },
        methods: {
            pageHeaderFormData(callback){
                callback(this.$refs.header.form);
            },

            tableLoad(formData, jumpFirst){
                return this.$refs.main.tableLoad(formData, true);
            }
        }
    }
</script>
