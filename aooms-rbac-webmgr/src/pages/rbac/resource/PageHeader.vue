<template>
    <el-form
            :inline="true"
            :model="form"
            ref="form"
            size="mini"
            label-width="70px"
            style="margin-bottom: -18px;">

        <el-form-item label="状态">
            <el-select v-model="form.status" placeholder="状态" @change="handleFormSubmit(true)" style="width: 100px;">
                <el-option label="全部" value=""/>
                <el-option label="正常" value="Y"/>
                <el-option label="禁用" value="N"/>
            </el-select>
        </el-form-item>

        <el-form-item label="姓名">
            <el-input v-model="form.user_name" placeholder="用户姓名" style="width: 100px;"/>
        </el-form-item>

        <el-form-item label="账号">
            <el-input v-model="form.account" placeholder="账号" style="width: 120px;"/>
        </el-form-item>

        <el-form-item label="电话">
            <el-input v-model="form.phone" placeholder="电话" style="width: 120px;"/>
        </el-form-item>

        <el-form-item>
            <el-button type="primary" @click="handleFormSubmit(true)">
                <d2-icon name="search"/>查询
            </el-button>
        </el-form-item>

        <el-form-item>
            <el-button @click="handleFormReset">
                <d2-icon name="refresh"/>重置
            </el-button>
        </el-form-item>

    </el-form>
</template>

<script>
export default {
    data() {
        return {
            form: {
                status: 'Y',
                user_name: '',
                user_nickname: '',
                account: '',
                phone: '',
                email: '',
                create_time:'',
                update_time:'',
                create_time_end:'',
                update_time_end:''
            }
        }
    },
    mounted() {
        this.$nextTick(() => {
           // this.handleFormSubmit(true);
        })
    },
    methods: {
        handleFormSubmit(jumpFirst) {
            this.$emit('tableLoad', this.form , jumpFirst);
        },
        handleFormReset() {
            this.form = {status: 'Y'};
            this.$refs.form.resetFields();
        }
    }
}
</script>
