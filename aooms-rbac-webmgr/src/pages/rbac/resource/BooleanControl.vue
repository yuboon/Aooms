<template>
    <el-switch
            v-model="currentValue"
            active-color="#67C23A"
            inactive-color="#F56C6C"
            :disabled="disabled"
            @change="handleChange">
    </el-switch>
</template>

<script>
    import {httpGet, httpPost} from '@/api/sys/http'

    export default {
        props: {
            value:{},
            id:{}
        },
        data () {
            return {
                currentValue: false,
                disabled: false
            }
        },
        watch: {
            value: {
                handler (val) {
                    this.currentValue = (val == 'Y');
                },
                immediate: true
            }
        },
        methods: {
            handleChange (val) {
                var self = this;
                var submitData = new FormData();
                submitData.append("id",this.id);
                submitData.append("status",val ? 'Y':'N');

                this.disabled = true;
                httpPost('aooms/rbac/resourceService/updateStatus',submitData).then(res => {
                    this.disabled = false;
                    self.$emit('change');
                });
            }
        }
    }
</script>
