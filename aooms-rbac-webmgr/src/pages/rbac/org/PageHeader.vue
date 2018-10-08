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

        <el-form-item label="机构名称">
            <el-input v-model="form.org_name" style="width: 100px;"/>
        </el-form-item>

        <el-form-item label="机构编码">
            <el-input v-model="form.account" style="width: 120px;"/>
        </el-form-item>

        <el-form-item label="机构简称">
            <el-input v-model="form.phone" style="width: 120px;"/>
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
                status: 'Y'
            }
        }
    },
    mounted() {
        this.$nextTick(() => {
            this.handleFormSubmit(true);
        })
    },
    methods: {
        getFormData() {
            return this.form;
        },
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
