<template>
    <el-form
            ref="form"
            :inline="true"
            :model="form"
            size="mini"
            label-width="70px"
            style="margin-bottom: -18px;">

        <el-form-item label="状态">
            <el-select v-model="form.status" placeholder="状态" @change="handleFormSubmit()" style="width: 100px;">
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
            <el-button type="primary" @click="handleFormSubmit()">
                <d2-icon name="search"/>查询
            </el-button>
        </el-form-item>

        <el-form-item>
            <el-button @click="handleFormReset">
                <d2-icon name="refresh"/>重置
            </el-button>
        </el-form-item>

        <el-form-item>
            <el-popover
                    placement="bottom"
                    trigger="click"
                    width="480"
                    popper-class="aooms-popper-condition"
            >
                <!-- content -->
                <el-card class="box-card">
                    <div slot="header" class="clearfix">
                        <span>更多条件</span>
                    </div>

                    <el-row>
                        <el-col :span="24">
                            <el-form-item label="昵称" prop="user">
                                <el-input v-model="form.user_nickname" placeholder="昵称" style="width:130px;"/>
                            </el-form-item>
                        </el-col>

                        <el-col :span="24">
                            <el-form-item label="邮箱" prop="user">
                                <el-input v-model="form.email" placeholder="邮箱" style="width:130px;"/>
                            </el-form-item>
                        </el-col>
                    </el-row>

                    <el-row>
                        <el-col :span="11">
                            <el-form-item label="创建时间">
                                <el-date-picker type="date" value-format="yyyy-MM-dd" placeholder="选择日期" v-model="form.create_time" style="width:130px;"></el-date-picker>
                            </el-form-item>
                        </el-col>
                        <el-col :span="2" style="text-align: center;"><el-form-item>至</el-form-item></el-col>
                        <el-col :span="11">
                            <el-form-item>
                                <el-date-picker type="date" value-format="yyyy-MM-dd" placeholder="选择日期" v-model="form.create_time_end" style="width:130px;"></el-date-picker>
                            </el-form-item>
                        </el-col>
                    </el-row>

                    <el-row>
                        <el-col :span="11">
                            <el-form-item label="更新时间">
                                <el-date-picker type="date" value-format="yyyy-MM-dd" placeholder="选择日期" v-model="form.update_time" style="width:130px;"></el-date-picker>
                            </el-form-item>
                        </el-col>
                        <el-col :span="2" style="text-align: center"><el-form-item>至</el-form-item></el-col>
                        <el-col :span="11">
                            <el-form-item>
                                <el-date-picker type="date" value-format="yyyy-MM-dd" placeholder="选择日期" v-model="form.update_time_end" style="width:130px;"></el-date-picker>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-card>
                <!-- content -->

                <span class="el-dropdown-link" style="cursor:pointer;color:#409eff;" slot="reference">
                    更多条件
                    <i class="el-icon--right el-icon-arrow-down"></i>
                </span>
            </el-popover>

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

    },
    methods: {
        handleFormSubmit() {
            this.$emit('tableLoad', this.form , true);
        },
        handleFormReset() {
            this.form = {status: 'Y'};
            this.$refs.form.resetFields();
        }
    }
}
</script>
