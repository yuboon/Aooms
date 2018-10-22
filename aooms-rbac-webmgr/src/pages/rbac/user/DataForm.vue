<template>
    <div class="aooms-dialog">
        <el-dialog
                title="用户信息"
                :visible.sync="dialogVisible">

            <el-form ref="form" :model="form" label-width="80px" size="small">
                <input type="hidden" v-model="form.id">
                <input type="hidden" v-model="form.org_id">

                <el-form-item prop="org_name" label="所属机构">
                    <span> [ {{ org_name }} ] </span>
                </el-form-item>

                <el-form-item prop="account" label="用户账号" :rules="[{ required: true, message: '不能为空'}]">
                    <el-input v-model="form.account"></el-input>
                </el-form-item>

                <el-form-item prop="password" label="密码">
                    <el-input v-model="form.password"></el-input>
                </el-form-item>

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

                <el-form-item label="性别">
                    <el-radio-group v-model="form.sex">
                        <el-radio label="0">男</el-radio>
                        <el-radio label="1">女</el-radio>
                    </el-radio-group>
                </el-form-item>

                <el-form-item label="备注">
                    <el-input type="textarea" class="aooms-form-textarea" v-model="form.remark"></el-input>
                </el-form-item>

                <!--<el-form-item label="活动时间">
                    <el-col :span="11">
                        <el-date-picker type="date" placeholder="选择日期" format="yyyy-MM-dd" v-model="form.date1" style="width: 100%;"></el-date-picker>
                    </el-col>
                    <el-col :span="2">&nbsp;</el-col>
                    <el-col :span="11">
                        <el-time-picker placeholder="选择时间" value-format="HH:mm:ss" v-model="form.date2" style="width: 100%;"></el-time-picker>
                    </el-col>
                </el-form-item>
                <el-form-item label="即时配送">
                    <el-switch v-model="form.delivery"></el-switch>
                </el-form-item>
                <el-form-item label="活动性质">
                    <el-checkbox-group v-model="form.type">
                        <el-checkbox label="美食/餐厅线上活动" name="type"></el-checkbox>
                        <el-checkbox label="地推活动" name="type"></el-checkbox>
                        <el-checkbox label="线下主题活动" name="type"></el-checkbox>
                        <el-checkbox label="单纯品牌曝光" name="type"></el-checkbox>
                    </el-checkbox-group>
                </el-form-item>
                <el-form-item label="特殊资源">
                    <el-radio-group v-model="form.resource">
                        <el-radio label="1">线上品牌商赞助2</el-radio>
                        <el-radio label="2">线下场地免费2</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="活动形式">
                    <el-input type="textarea" class="aooms-form-textarea" v-model="form.desc"></el-input>
                </el-form-item>-->
                <el-form-item>
                    <el-button type="primary" :loading="loading" @click="insert">保存</el-button>
                    <el-button @click="close">取消</el-button>
                </el-form-item>
            </el-form>

        </el-dialog>
    </div>

</template>


<script>

import {httpGet, httpPost} from '@/api/sys/http'

export default {
    props: {
        org_id: {},
        org_name: {}
    },
    data() {
        return {
            loading:false,
            method:'',
            form: {
                /*id:'',
                user_name: '',
                user_nickname: '',
                account: '',
                password: '',
                phone: '',
                email: '',
                sex: '0',
                remark: '',
                status:'',
                photo:''*/
            },
            dialogVisible: false
        }
    },
    watch: {
        form(val){
            this.$nextTick(() => {
                this.$refs.form.clearValidate();
            });
        }
    },
    methods: {
        insert:function(){
            var self = this;
            this.$refs.form.validate((valid, error) => {
                if (valid) {
                    // 推荐使用这种方式提交，保证RequestHeaders = Content-Type: multipart/form-databoss; boundary=----WebKitFormBoundaryzBe4gknCRJ5m3eaU
                    let submitData = new FormData();
                    submitData.append('formData',JSON.stringify(self.form));
                    this.loading = true;
                    httpPost('aooms/rbac/user/' + self.method,submitData).then(res => {
                        this.$message({
                            type: 'success',
                            message: '保存成功'
                        });
                        this.loading = false;
                        this.$emit('tableLoad');
                        this.dialogVisible = false;
                    });
                }
            });
        },
        open:function(row,method){
            /*this.dialogVisible = true;
            this.$nextTick(()=>{
                this.method = 'insert';
                if(row){
                    this.method = 'update';
                    this.form = row;
                }else{
                    this.form = {sex:'0'};
                    this.$refs.form.resetFields();
                }
            });*/

            this.method = method;
            this.dialogVisible = true;
            this.form = row;
        },
        close:function() {
            this.dialogVisible = false;
        }
    }
}
</script>