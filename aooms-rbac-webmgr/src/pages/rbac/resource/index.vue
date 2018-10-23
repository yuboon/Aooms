<template>
    <d2-container>

        <page-main/>

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
    methods: {

    }
}
</script>
