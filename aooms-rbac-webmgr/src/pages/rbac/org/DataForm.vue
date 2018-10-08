<template>
    <div class="aooms-dialog">
        <el-dialog
                title="机构信息"
                :visible.sync="dialogVisible">

            <el-form ref="form" :model="form" label-width="80px" size="small">
                <input type="hidden" v-model="form.id">
                <input type="hidden" v-model="form.parent_org_id">

                <el-form-item prop="org_name" label="上级机构">
                    <span> [ {{ parent_org_name }} ] </span>
                </el-form-item>

                <el-form-item prop="org_name" label="机构名称" :rules="[{ required: true, message: '不能为空'}]">
                    <el-input v-model="form.org_name"></el-input>
                </el-form-item>
                <el-form-item prop="org_code" label="机构编码" :rules="[{ required: true, message: '不能为空'}]">
                    <el-input v-model="form.org_code"></el-input>
                </el-form-item>

                <el-row>
                    <el-col :span="12">
                        <el-form-item label="机构简称">
                            <el-input v-model="form.org_shortname"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="12">
                        <el-form-item prop="ordinal" label="序号" :rules="[{type:'number', required: true, message: '请输入数字'}]">
                            <el-input v-model.number="form.ordinal"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-form-item label="备注">
                    <el-input type="textarea" class="aooms-form-textarea" v-model="form.remark"></el-input>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" @click="insert">保存</el-button>
                    <el-button @click="close">取消</el-button>
                </el-form-item>
            </el-form>

        </el-dialog>
    </div>

</template>


<script>

import {httpGet, httpPost} from '@/api/sys/http'

export default {
    data() {
        return {
            method:'',
            form: {
                /*id:'',
                org_name: '',
                org_shortname: '',
                org_code: '',
                status: '',
                parent_org_id: '',
                photo: '',
                remark: '',
                status:'',
                ordinal:''*/
            },
            parent_org_name:'根机构',
            dialogVisible: false
        }
    },
    methods: {
        insert:function(){
            var self = this;
            this.$refs.form.validate((valid, error) => {
                if (valid) {
                    // 推荐使用这种方式提交，保证RequestHeaders = Content-Type: multipart/form-data; boundary=----WebKitFormBoundaryzBe4gknCRJ5m3eaU
                    let submitData = new FormData();
                    submitData.append('formData',JSON.stringify(self.form));
                    httpPost('aooms/rbac/org/' + self.method,submitData).then(res => {
                        this.$message({
                            type: 'success',
                            message: '保存成功'
                        });

                        this.$emit('refreshTable');
                        this.dialogVisible = false;
                    });
                }
            });
        },
        open:function(row){
            this.dialogVisible = true;
            this.$nextTick(()=>{
                this.method = 'insert';
                if(row){
                    this.method = 'update';
                    this.form = row;
                }else{
                    this.form = {status:'Y',ordinal:0};
                }
                this.$refs.form.resetFields();
            })
        },
        close:function() {
            this.dialogVisible = false;
        }
    }
}
</script>