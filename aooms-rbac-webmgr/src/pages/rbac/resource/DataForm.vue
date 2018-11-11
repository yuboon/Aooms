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
                        <el-form-item prop="org_name" label="上级资源">
                            <el-popover
                                    placement="bottom"
                                    title="选择上级资源"
                                    width="400"
                                    :visible-arrow="false"
                                    trigger="click"
                                    v-model="popoverVisible"
                            >

                                <el-input size="mini" placeholder="输入关键字进行过滤" v-model="filterText" style="padding-bottom: 5px;"></el-input>
                                <div style="height: 300px;">
                                    <el-scrollbar class="aooms-scrollbar">
                                        <el-tree
                                                ref="tree"
                                                :expand-on-click-node="false"
                                                :default-expanded-keys="['ROOT']"
                                                :props="{
                                                    label: 'resource_name'
                                                }"
                                                highlight-current
                                                node-key="id"
                                                :data="currentTableData"
                                                @node-click="handleNodeClick"
                                                :filter-node-method="filterNode">
                                            <span class="aooms-tree-node" slot-scope="{ node, data }">
                                               <i :class="node.icon"></i>{{ node.label }}
                                            </span>
                                        </el-tree>
                                    </el-scrollbar>
                                </div>

                                <span style="color: blue;cursor: pointer;" slot="reference" @click="popoverVisible = !popoverVisible" > [ {{ changeParentResourceName || parent_resource_name }} ] </span>
                            </el-popover>

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
                            <!--<el-input v-model="form.icon"></el-input>-->
                            <!--<i :class="'fa fa-' + icon2"></i>-->
                            <d2-icon-select v-model="form.icon" :user-input="true"/>
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
                    <el-button :loading="loading" type="primary" @click="insert">保存</el-button>
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
        tableData :{}
    },
    data() {
        return {
            currentTableData:[],
            loading:false,
            parentRow:{},
            parent_resource_name:'',
            method:'',
            form: {},
            dialogVisible: false,
            popoverVisible: false,
            filterText:'',
            changeParentResourceName:'',
            changeParentResource:{}
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
            this.currentTableData = [{id:'ROOT', resource_name: '顶层', icon:'el-icon-menu', children: []}];
            if(!val) this.currentTableData = [];
            else this.currentTableData[0].children = this.tableData;
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
                    delete obj.label;

                    var isChangeParent = (this.changeParentResourceName != '');
                    if(isChangeParent && this.changeParentResource.id == this.form.id){
                        this.$message({
                            showClose: true,
                            message: '不能调整父机构为自己',
                            type: 'warning'
                        });
                        return;
                    }

                    let submitData = new FormData();
                    submitData.append('formData',JSON.stringify(obj));

                    this.loading = true;
                    httpPost('aooms/rbac/resourceService/' + self.method,submitData).then(res => {
                        this.$message({
                            type: 'success',
                            message: '保存成功'
                        });
                        this.dialogVisible = false;
                        this.loading = false;
                        if(self.method == 'insert'){
                            this.$emit('tableAppend', res.$vo, this.parentRow);
                        }else{
                            this.$emit('tableUpdate', res.$vo);
                            // 调整了父资源
                            if(isChangeParent && this.changeParentResource.id != this.parentRow.id){
                                this.$emit('tableAppend', res.$vo, this.changeParentResource);
                                this.$emit('tableDelete', this.form);
                            }
                        }
                    });
                }
            });
        },
        open:function(row,method,parentRow){
            this.changeParentResource = {};
            this.changeParentResourceName = '';
            this.method = method;
            this.dialogVisible = true;
            this.form = Object.assign({},row);
            this.parent_resource_name = parentRow.resource_name;
            this.parentRow = parentRow;
        },
        close:function() {
            this.dialogVisible = false;
        },
        handleNodeClick(data) {
            var self = this;
            this.form.parent_resource_id = data.id;
            this.changeParentResourceName = data.resource_name;
            this.changeParentResource = data;
        },
        filterNode(value, data) {
            if (!value) return true;
            return data.label.indexOf(value) !== -1;
        }
    }
}
</script>