<template>
    <div>
        <d2-container>

            <page-main ref="main"/>

        </d2-container>
    </div>

</template>

<script>
import PageMain from './PageMain.vue'

export default {
    // name 值和本页的 $route.name 一致才可以缓存页面
    name: 'rbac-user-info',
    components: {
        PageMain
    },
    methods: {
        tableLoad(formData, jumpFirst){
            return this.$refs.main.tableLoad(formData, true);
        }
    }
}
</script>
