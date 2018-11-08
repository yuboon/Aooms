<template>
    <div>
        <div :style="{height: mainHeight + 'px' }">
            <SplitPane :default-percent='20' split="vertical">
                <template slot="paneL" :style="{height: '100%' }" >
                    <el-scrollbar class="aooms-scrollbar">
                        <div class="aooms-tree-left">
                            <el-row>
                                <el-col :span="20">
                                    <el-input size="mini" placeholder="输入关键字进行过滤" v-model="filterText" style="padding-bottom: 5px;"></el-input>
                                </el-col>
                                <el-col :span="4">
                                    <el-button size="mini" title="刷新" @click="treeLoad" icon="el-icon-refresh" circle style="margin-left: 5px;"></el-button>
                                </el-col>
                            </el-row>

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
                        </div>
                    </el-scrollbar>
                </template>

                <template slot="paneR">

                    <div style="padding-left: 5px;">
                        <el-button type="primary" size="mini" icon="el-icon-plus" @click="handleForm({status:'Y',ordinal:1,org_id:org_id},'insert')">新增</el-button>
                        <el-button type="danger" size="mini" icon="el-icon-delete" :loading="delLoading"  @click="handleDelete('del', multipleSelection)">删除</el-button>

                        <el-switch
                                v-model="cascade"
                                active-text="开启级联"
                                style="float: right"
                                @change="cascadeChange"
                        />
                    </div>

                    <el-table
                            :data="currentTableData"
                            v-loading="loading"
                            size="mini"
                            stripe
                            style="margin-top: 5px"
                            :height="mainHeight - 72"
                            @selection-change="handleSelectionChange">

                        <el-table-column type="selection" width="50" align="center" />
                        <el-table-column label="角色名称" prop="role_name"/>
                        <el-table-column label="角色编码" prop="role_code"/>
                        <el-table-column label="状态" align="center" width="60">
                            <template slot-scope="scope">
                                <el-switch
                                        v-model="scope.row.status == 'Y'"
                                        :disabled="radioDisabled"
                                        active-color="#67C23A"
                                        inactive-color="#F56C6C"
                                        @change="handleStatusChange($event,scope.row)">
                                </el-switch>
                            </template>
                        </el-table-column>
                        <el-table-column label="序号" prop="ordinal" width="50" align="center"/>
                        <el-table-column label="创建时间" prop="create_time" align="center" width="150"/>

                        <el-table-column fixed label="操作" align="center" width="150">
                            <template slot-scope="scope">
                                <el-button type="info" title="分配权限" size="mini" icon="el-icon-view" circle @click="handleResourceAssign(scope.row)"></el-button>
                                <el-button type="primary" title="编辑" size="mini" icon="el-icon-edit" circle @click="handleForm(scope.row,'update')"></el-button>
                                <el-button type="danger" title="删除" size="mini" icon="el-icon-delete" circle :loading="scope.row.delLoading" @click="handleDelete('delOne',[scope.row])"></el-button>
                            </template>
                        </el-table-column>

                    </el-table>

                    <ext-pagination ref="pagination" @change="tableLoad({},false)" style="padding:10px 0 0 10px; margin: 0px;" />

                </template>
            </SplitPane>
        </div>

        <!-- 表单弹窗 -->
        <data-form
                :org_id="org_id"
                :org_name="org_name"
                :treeData="treeData"
                @tableLoad="tableLoad"
                ref="dataForm">
        </data-form>

        <!-- 权限分配窗口 -->
        <resource-assign
                :org_id="org_id"
                :org_name="org_name"
                :treeData="treeData"
                @tableLoad="tableLoad"
                ref="resourceAssign">
        </resource-assign>

    </div>
</template>

<script>
import DataForm from './DataForm.vue'
import ResourceAssign from './ResourceAssign.vue'

import {httpGet,httpPost} from '@/api/sys/http'
import ExtPagination from '@/components/ext-pagination'

export default {
    components: {
        DataForm,
        ResourceAssign,
        ExtPagination
    },
    data() {
        return {
            loading:false,
            delLoading:false,
            currentTableData: [],
            multipleSelection: [],
            mainHeight: 0,
            org_id: '',
            org_name: '',
            data_permission:'',
            treeData: [],
            filterText:'',
            cascade:true,
            radioDisabled:false
        }
    },
    watch: {
        filterText(val) {
            this.$refs.tree.filter(val);
        }
    },
    mounted() {
        this.$nextTick(() => {
            let self = this;
            self.resetMainHeight();
            self.treeLoad();
            self.tableLoad({},true);

            window.onresize = function () {
                self.resetMainHeight();
            }
        })
    },
    methods: {
        resetMainHeight:function(){
            this.mainHeight = window.innerHeight - 215;
        },
        tableLoad(params,jumpFirst){
            var self = this;
            this.$emit('pageHeaderFormData',function(formData){
                if(jumpFirst) self.$refs.pagination.current = 1;

                var ext = {org_id: self.org_id};
                if (self.cascade){
                    ext = {data_permission: self.data_permission};
                };

                // 分页参数、查询条件拷贝
                Object.assign(params,formData,{
                    page: self.$refs.pagination.current,
                    limit: self.$refs.pagination.size,
                },ext);
                self.loading = true;
                httpGet('aooms/rbac/role/findList', params).then(res => {
                    self.loading = false;
                    self.currentTableData = res.$data.list
                    self.$refs.pagination.total = res.$data.total;
                });
            });
        },
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        handleForm: function (row,method) {
            if(!this.org_id){
                this.org_id = this.treeData[0].id;
                this.org_name = this.treeData[0].org_name;
            }
            this.$refs.dataForm.open(row,method);
        },
        handleResourceAssign: function (row) {
            this.$refs.resourceAssign.open(row);
        },
        handleDelete: function (type , selection) {
            var self = this;
            if(selection.length == 0){
                this.$message({
                    message: '请至少选择一条数据',
                    type: 'warning'
                });
                return;
            }

            this.$confirm('确定删除选择的数据?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                var ids = new Array();
                selection.forEach(item => {
                    ids.push({id:item.id});
                });
                let submitData = new FormData();
                submitData.append("ids",JSON.stringify(ids));

                type == 'del' ? this.delLoading = true : this.$set(selection[0],'delLoading',true);
                httpPost('aooms/rbac/role/delete',submitData).then(res => {
                    this.$message({
                        type: 'success',
                        message: '成功删除' + selection.length + '条数据'
                    });

                    type == 'del' ? this.delLoading = false : this.$set(selection[0],'delLoading',false);

                    // 当前页全部删除，加载前一页
                    if(selection.length == this.currentTableData.length && this.$refs.pagination.current > 1){
                        this.$refs.pagination.current -= 1;
                    }
                    this.tableLoad({});
                });
            })
        },
        treeLoad(){
            httpGet('aooms/rbac/org/findTree').then(res => {
                this.treeData = res.$tree;
            });
        },
        handleNodeClick(data) {
            var self = this;
            this.org_id = data.id;
            this.org_name = data.org_name;
            this.data_permission = data.data_permission;
            self.tableLoad({},true);
        },
        filterNode(value, data) {
            if (!value) return true;
            return data.label.indexOf(value) !== -1;
        },
        cascadeChange(val){
            this.tableLoad({});
        },
        handleStatusChange(val,row){
            var status = val ? 'Y':'N';
            var submitData = new FormData();
            submitData.append("id",row.id);
            submitData.append("status",status);
            row.status = status;

            this.radioDisabled = true;
            httpPost('aooms/rbac/role/updateStatus',submitData).then(res => {
                this.radioDisabled = false;
                this.tableLoad({});
            });
        }

    }
}
</script>
