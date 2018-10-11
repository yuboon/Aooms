<template>
    <div class="aooms-dialog">
        <el-dialog
                title="资源信息"
                :visible.sync="dialogVisible">

            <el-form ref="form" :model="form" label-width="80px" size="small">
                <input type="hidden" v-model="form.id">
                <input type="hidden" v-model="form.parent_resource_id">

                <el-row>
                    <el-col :span="12">
                        <el-form-item prop="resource_name" label="资源名称" :rules="[{ required: true, message: '不能为空'}]">
                            <el-input v-model="form.resource_name"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="上级资源">
                            <span>[ {{parent_resource_name}} ]</span>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="12">
                        <el-form-item prop="resource_code" label="资源编码" :rules="[{ required: true, message: '不能为空'}]">
                            <el-input v-model="form.resource_code"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item prop="ordinal" label="序号" :rules="[{ type:'number', required: true, message: '请输入数字'}]">
                            <el-input v-model.number="form.ordinal"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="12">
                        <el-form-item prop="resource_type" label="资源类型" :rules="[{ required: true, message: '不能为空'}]">
                            <el-select v-model="form.resource_type" style="width: 100%;">
                                <el-option label="目录" value="0"/>
                                <el-option label="菜单" value="1"/>
                                <el-option label="按钮" value="2"/>
                                <el-option label="接口" value="3"/>
                            </el-select>
                        </el-form-item>
                    </el-col>

                    <el-col :span="12">
                        <el-form-item prop="open_type" label="打开方式">
                            <el-select v-model="form.open_type" style="width: 100%;">
                                <el-option label="默认" value="0"/>
                                <el-option label="iframe" value="1"/>
                                <el-option label="新窗口" value="2"/>
                            </el-select>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="12">
                        <el-form-item prop="icon" label="资源图标">
                            <el-input v-model="form.icon"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="12">
                        <el-form-item prop="permission" label="权限标识">
                            <el-input v-model="form.permission"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-form-item label="链接地址">
                    <el-input v-model="form.resource_url"></el-input>
                </el-form-item>

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
            parentRow:{},
            parent_resource_name:'',
            method:'',
            form: {},
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
                    let obj = Object.assign({},self.form);
                    // 删除树扩展的属性
                    delete obj._expanded;
                    delete obj._level;
                    delete obj._show;
                    delete obj.children;
                    delete obj.leaf;
                    delete obj.parent;

                    let submitData = new FormData();
                    submitData.append('formData',JSON.stringify(obj));
                    httpPost('aooms/rbac/resource/' + self.method,submitData).then(res => {
                        this.$message({
                            type: 'success',
                            message: '保存成功'
                        });
                        this.dialogVisible = false;
                        if(self.method == 'insert'){
                            this.$emit('tableUpdate', res.$vo, this.parentRow);
                        }
                    });
                }
            });
        },
        open:function(row,method,parentRow){
            this.method = method;
            this.dialogVisible = true;
            this.form = row;
            this.parent_resource_name = parentRow.resource_name;
            this.parentRow = parentRow;
        },
        close:function() {
            this.dialogVisible = false;
        }
    }
}
</script>