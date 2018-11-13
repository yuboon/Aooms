<template>
    <div>
        <el-form ref="form" :model="form" label-width="100px" size="small" style="width: 55%;margin: auto;">
            <input type="hidden" v-model="form.id">

            <el-form-item label-width="0px" style="margin: auto;text-align: center;">
                <img class="user-radius-image" :src="`${$baseUrl}image/user_default.png`" style="cursor: pointer"/>
            </el-form-item>

            <el-row>
                <el-col :span="12">
                    <el-form-item prop="user_name" label="用户姓名">
                        {{form.user_name}}
                    </el-form-item>
                </el-col>

                <el-col :span="12">
                    <el-form-item prop="account" label="用户账号">
                        {{ form.account }}
                    </el-form-item>
                </el-col>
            </el-row>

            <el-row>
                <el-col :span="24">
                    <el-form-item prop="old_password" label="旧密码" :rules="[{ required: true, message: '不能为空'}]">
                        <el-input type="password" v-model="form.old_password"></el-input>
                    </el-form-item>
                </el-col>

                <el-col :span="24">
                    <el-form-item prop="password" label="新密码" :rules="[{ required: true, message: '不能为空'}]">
                        <el-input type="password" v-model="form.password"></el-input>
                    </el-form-item>
                </el-col>

                <el-col :span="24">
                    <el-form-item prop="confirm_password" label="确认密码" :rules="[{ required: true, message: '不能为空'}]">
                        <el-input type="password" v-model="form.confirm_password"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-form-item style="text-align: center;" label-width="0">
                <el-button type="primary" :loading="loading" @click="handleUpdate">保存</el-button>
            </el-form-item>

        </el-form>
    </div>
</template>

<style lang="scss">
    .user-radius-image{
        margin-top: 10px;
        width:100px;
        height:100px;
        border-radius:20px;
    }
</style>

<script>
    import {httpGet,httpPost} from '@/api/sys/http'
    import common from '@/libs/common.js'

    export default {
        components: {

        },
        data() {
            return {
                user:{},
                form:{},
                loading:false
            }
        },
        watch: {

        },
        mounted() {
            this.user = common.userInfo(this);
            this.form = {
                id : this.user.id,
                account : this.user.account,
                user_name : this.user.userName
            };
        },
        methods: {
            handleUpdate: function () {
                var self = this;

                this.$refs.form.validate((valid, error) => {
                    if (valid) {
                        if(this.form.password != this.form.confirm_password){
                            this.$message({
                                type: 'error',
                                message: '两次密码不一致'
                            });
                            return;
                        }

                        let submitData = new FormData();
                        submitData.append("old_password",self.form.old_password);
                        submitData.append("password",self.form.password);
                        submitData.append("id",self.form.id);

                        this.loading = true;
                        httpPost('aooms/call/rbac/userService/updatePassword',submitData).then(res => {
                            this.loading = false;
                            if(res.$.code == -1){
                                this.$message({
                                    type: 'error',
                                    message: res.$.msg
                                });
                                return;
                            }

                            this.$message({
                                type: 'success',
                                message: '修改成功'
                            });
                        });
                    }
                });

            }
        }
    }
</script>
