<template>
    <div>

        <el-form ref="form" :model="form" label-width="80px" size="small" style="width: 60%;margin: auto;">
            <input type="hidden" v-model="form.id">

            <el-form-item label-width="0px" style="margin: auto;text-align: center;">
                <img class="user-radius-image" :src="`${$baseUrl}image/user-default.png`" style="cursor: pointer"/>
            </el-form-item>

            <el-row>
                <el-col :span="12">
                    <el-form-item prop="org_name" label="所属机构">
                       {{ org_name }}
                    </el-form-item>
                </el-col>

                <el-col :span="12">
                    <el-form-item prop="account" label="用户账号">
                        {{ form.account }}
                    </el-form-item>
                </el-col>
            </el-row>

            <el-row>
                <el-col :span="12">
                    <el-form-item prop="user_name" label="用户姓名" :rules="[{ required: true, message: '不能为空'}]">
                        <el-input v-model="form.user_name"></el-input>
                    </el-form-item>
                </el-col>

                <el-col :span="12">
                    <el-form-item prop="user_nickname" label="用户昵称">
                        <el-input v-model="form.user_nickname"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-row>
                <el-col :span="12">
                    <el-form-item prop="phone" label="电话">
                        <el-input v-model="form.phone"></el-input>
                    </el-form-item>
                </el-col>

                <el-col :span="12">
                    <el-form-item prop="email" label="邮箱" :rules="[{ type: 'email', message: '邮箱格式错误'}]">
                        <el-input v-model="form.email"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-row>
                <el-col :span="12">
                    <el-form-item label="性别">
                        <el-radio-group v-model="form.sex">
                            <el-radio label="0">男</el-radio>
                            <el-radio label="1">女</el-radio>
                        </el-radio-group>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-form-item style="text-align: center;" label-width="0">
                <el-button type="primary" :loading="loading" @click="handleUpdate">保存</el-button>
                <el-button @click="loading=false">取消</el-button>

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
            org_name:'',
            form:{},
            loading:false
        }
    },
    watch: {

    },
    mounted() {
        this.user = common.userInfo(this);
        this.org_name = this.user.orgName;
        this.form = {
            id : this.user.id,
            account : this.user.account,
            user_name : this.user.userName,
            user_nickname : this.user.userNickname,
            sex : this.user.sex,
            email : this.user.email,
            phone : this.user.phone
        };
    },
    methods: {
        handleUpdate: function () {
            var self = this;
            this.$refs.form.validate((valid, error) => {
                if (valid) {
                    let submitData = new FormData();
                    submitData.append("formData",JSON.stringify(this.form));

                    this.loading = true;
                    httpPost('aooms/call/rbac/userService/update',submitData).then(res => {
                        this.$message({
                            type: 'success',
                            message: '修改成功'
                        });
                        this.loading = false;

                        this.user.account = self.form.account;
                        this.user.userName = self.form.user_name;
                        this.user.userNickname = self.form.user_nickname;
                        this.user.sex = self.form.sex;
                        this.user.email = self.form.email;
                        this.user.phone = self.form.phone;
                        // 更新本地用户信息
                        self.$store.commit('d2admin/user/set',this.user,{ root: true });
                    });
                }
            });
        }
    }
}
</script>
