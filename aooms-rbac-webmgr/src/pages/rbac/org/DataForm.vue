<template>
    <div class="aooms-dialog" id="dialog">
        <el-dialog
                   title="机构信息" ref="dialog"
                   :visible.sync="dialogVisible">

            <el-form ref="form" :model="form" label-width="80px" size="small">
                <input type="hidden" v-model="form.id">
                <input type="hidden" v-model="form.parent_org_id">

                <el-form-item prop="org_name" label="上级机构">
                    <el-popover
                            placement="right"
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
                                        :data="currentTreeData"
                                        @node-click="handleNodeClick"
                                        :filter-node-method="filterNode">
                                    <span class="aooms-tree-node" slot-scope="{ node, data }">
                                       <i :class="node.icon"></i>{{ node.label }}
                                    </span>
                                </el-tree>
                            </el-scrollbar>
                        </div>

                        <span style="color: blue;cursor: pointer;" slot="reference" @click="popoverVisible = !popoverVisible" > [ {{ changeOrgName || parent_org_name }} ] </span>
                    </el-popover>

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
        parent_org_id: {},
        parent_org_name: {},
        treeData:{}
    },
    data() {
        return {
            loading:false,
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
            currentTreeData:[],
            dialogVisible: false,
            popoverVisible: false,
            filterText:'',
            changeOrgName:'',
            changeOrgId:''
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
        },
        popoverVisible(val){
            if(!val) this.currentTreeData = [];
            else this.currentTreeData = this.treeData;
        }
    },
    methods: {
        insert:function(){
            var self = this;
            this.$refs.form.validate((valid, error) => {
                if (valid) {
                    let submitData = new FormData();
                    var isChangeOrg = false;

                    if(this.changeOrgId == this.form.id){
                        this.$message({
                            showClose: true,
                            message: '不能调整父机构为自己',
                            type: 'warning'
                        });
                        return;
                    }
                    if(this.changeOrgId != this.form.parent_org_id){
                        self.form.parent_org_id = this.changeOrgId;
                        isChangeOrg = true;
                    }

                    submitData.append('formData',JSON.stringify(self.form));
                    this.loading = true;
                    httpPost('aooms/rbac/orgService/' + self.method,submitData).then(res => {
                        this.$message({
                            type: 'success',
                            message: '保存成功'
                        });

                        this.loading = false;
                        this.$emit('tableLoad',{});
                        if(isChangeOrg){
                            this.$emit('treeLoad');
                        }else{
                            this.$emit('treeUpdate',res.$vo, self.method);
                        }
                        this.dialogVisible = false;


                    });
                }
            });
        },
        open:function(row,method){
            this.changeOrgName = '';
            this.changeOrgId = row.parent_org_id;
            this.method = method;
            this.dialogVisible = true;
            this.form = Object.assign({},row);
        },
        close:function() {
            this.dialogVisible = false;
        },
        handleNodeClick(data) {
            var self = this;
            this.changeOrgName = data.org_name;
            this.changeOrgId = data.id;
        },
        filterNode(value, data) {
            if (!value) return true;
            return data.org_name.indexOf(value) !== -1;
        }
    }
}
</script>