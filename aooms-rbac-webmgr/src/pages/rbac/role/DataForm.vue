<template>
    <div class="aooms-dialog">
        <el-dialog
                title="角色信息"
                :visible.sync="dialogVisible">

            <el-form ref="form" :model="form" label-width="80px" size="small">
                <input type="hidden" v-model="form.id">
                <input type="hidden" v-model="form.org_id">


                <el-form-item prop="org_name" label="所属机构">
                    <el-popover
                            placement="right-end"
                            title="选择机构"
                            width="400"
                            :visible-arrow="false"
                            trigger="click"
                            v-model="popoverVisible"
                    >

                        <el-input size="mini" placeholder="输入关键字进行过滤" v-model="filterText" style="padding-bottom: 5px;"></el-input>
                        <div style="height:300px;">
                            <el-scrollbar class="aooms-scrollbar">
                                <el-tree
                                        ref="tree"
                                        :expand-on-click-node="false"
                                        :default-expanded-keys="['ROOT']"
                                        :props="{
                                            label: 'org_name'
                                        }"
                                        highlight-current
                                        node-key="id"
                                        :data="treeData"
                                        @node-click="handleNodeClick"
                                        :filter-node-method="filterNode">
                                    <span class="aooms-tree-node" slot-scope="{ node, data }">
                                       <i :class="node.icon"></i>{{ node.label }}
                                    </span>
                                </el-tree>
                            </el-scrollbar>
                        </div>

                        <span style="color: blue;cursor: pointer;" slot="reference" @click="popoverVisible = !popoverVisible" > [ {{ changeOrgName || org_name }} ] </span>
                    </el-popover>

                </el-form-item>

                <el-row>
                    <el-col :span="12">
                        <el-form-item prop="role_name" label="角色名称" :rules="[{ required: true, message: '不能为空'}]">
                            <el-input v-model="form.role_name"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="12">
                        <el-form-item prop="role_code" label="角色编号">
                            <el-input v-model="form.role_code"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row>
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
        org_name: {},
        treeData:{}
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
            dialogVisible: false,
            popoverVisible: false,
            filterText:'',
            changeOrgName:''

        }
    },
    watch: {
        form(val){
            this.$nextTick(() => {
                this.$refs.form.clearValidate();
            });
        },
        filterText(val) {
            this.$refs.tree.filter(val);
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
                    httpPost('aooms/rbac/roleService/' + self.method,submitData).then(res => {
                        this.$message({
                            type: 'success',
                            message: '保存成功'
                        });
                        this.loading = false;
                        this.$emit('tableLoad',{},false);
                        this.dialogVisible = false;
                    });
                }
            });
        },
        open:function(row,method){
            this.changeOrgName = '';
            this.method = method;
            this.dialogVisible = true;
            this.form = Object.assign({},row);
        },
        close:function(){
            this.loading = false;
            this.dialogVisible = false;
        },
        handleNodeClick(data) {
            var self = this;
            this.form.org_id = data.id;
            this.changeOrgName = data.org_name;
        },
        filterNode(value, data) {
            if (!value) return true;
            return data.org_name.indexOf(value) !== -1;
        }
    }
}
</script>