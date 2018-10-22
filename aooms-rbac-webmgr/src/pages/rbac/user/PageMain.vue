<template>
    <div>
        <div :style="{height: mainHeight + 'px' }">
            <SplitPane :default-percent='20' split="vertical">
                <template slot="paneL" :style="{height: '100%' }" >
                    <el-scrollbar class="aooms-scrollbar">
                        <div class="aooms-tree-left">
                            <el-input size="mini" placeholder="输入关键字进行过滤" v-model="filterText" style="padding-bottom: 5px;"></el-input>
                            <el-tree
                                    ref="tree"
                                    :expand-on-click-node="false"
                                    :default-expanded-keys="['ROOT']"
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
                        <el-button type="primary" size="mini" icon="el-icon-plus" @click="handleForm({sex:'0',status:'Y',ordinal:0,org_id:org_id},'insert')">新增</el-button>
                        <el-button type="danger" size="mini" icon="el-icon-delete" :loading="delLoading"  @click="handleDelete('del', multipleSelection)">删除</el-button>
                    </div>

                    <el-table
                            :data="currentTableData"
                            v-loading="loading"
                            size="mini"
                            stripe
                            style="width: 100%;"
                            :height="mainHeight - 28"
                            @selection-change="handleSelectionChange">

                        <!-- Table 展开行 -->
                        <el-table-column type="expand" width="20">
                            <template slot-scope="scope">
                                <el-form label-position="left" inline class="aooms-table-expand">
                                    <el-form-item label="用户姓名">
                                        <span>{{ scope.row.user_name }}</span>
                                    </el-form-item>
                                    <el-form-item label="用户昵称">
                                        <span>{{ scope.row.user_nickname }}</span>
                                    </el-form-item>
                                    <el-form-item label="账号">
                                        <span>{{ scope.row.account }}</span>
                                    </el-form-item>
                                    <el-form-item label="性别">
                                        <span>{{ (scope.row.sex == '0') ? '男':'女' }}</span>
                                    </el-form-item>
                                    <el-form-item label="电话">
                                        <span>{{ scope.row.phone }}</span>
                                    </el-form-item>
                                    <el-form-item label="邮箱">
                                        <span>{{ scope.row.email }}</span>
                                    </el-form-item>
                                    <el-form-item label="创建时间">
                                        <span>{{ scope.row.create_time }}</span>
                                    </el-form-item>
                                    <el-form-item label="修改时间">
                                        <span>{{ scope.row.update_time }}</span>
                                    </el-form-item>
                                    <el-form-item label="备注" style="width: 100%;">
                                        <span>{{ scope.row.remark }}</span>
                                    </el-form-item>
                                </el-form>
                            </template>
                        </el-table-column>

                        <el-table-column
                                type="selection"
                                width="30">
                        </el-table-column>

                        <el-table-column label="用户姓名" prop="user_name"/>
                        <el-table-column label="账号" prop="account"/>
                        <el-table-column label="状态" align="center" width="50">
                            <template slot-scope="scope">
                                <boolean-control
                                        :value="scope.row.status"
                                        @change="(val) => {
                            handleSwitchChange(val, scope.$index)
                        }">
                                    <d2-icon
                                            name="check-circle"
                                            style="font-size: 20px; line-height: 32px; color: #67C23A;"
                                            slot="active"/>
                                    <d2-icon
                                            name="times-circle"
                                            style="font-size: 20px; line-height: 32px; color: #F56C6C;"
                                            slot="inactive"/>
                                </boolean-control>
                            </template>
                        </el-table-column>
                        <el-table-column label="性别" prop="sex" align="center">
                            <template slot-scope="scope">
                                {{ (scope.row.sex == '0') ? '男':'女' }}
                            </template>
                        </el-table-column>
                        <el-table-column label="电话" prop="phone"/>
                        <el-table-column label="序号" prop="ordinal" width="50"/>
                        <el-table-column label="创建时间" prop="create_time" align="center" width="150"/>

                        <el-table-column fixed label="操作" align="center" width="100">
                            <template slot-scope="scope">
                                <el-button type="primary" title="编辑" size="mini" icon="el-icon-edit" circle @click="handleForm(scope.row,'update')"></el-button>
                                <el-button type="danger" title="删除" size="mini" icon="el-icon-delete" circle :loading="scope.row.delLoading" @click="handleDelete('delOne',[scope.row])"></el-button>
                            </template>
                        </el-table-column>

                    </el-table>
                </template>
            </SplitPane>
        </div>


        <!-- 表单弹窗 -->
        <data-form
                :org_id="org_id"
                :org_name="org_name"
                @tableLoad="tableLoad"
                ref="dataForm">
        </data-form>

    </div>
</template>

<script>
import BooleanControl from './BooleanControl.vue'
import DataForm from './DataForm.vue'
import {httpGet,httpPost} from '@/api/sys/http'

export default {
    components: {
        BooleanControl,
        DataForm
    },
    props: {
        tableData: {
            default: () => []
        },
        loading: {
            default: false
        }
    },
    data() {
        return {
            delLoading:false,
            currentTableData: [],
            multipleSelection: [],
            mainHeight: 0,
            org_id: 'ROOT',
            org_name: '顶层机构',
            treeData: [{
                id:'ROOT',
                label: '顶层机构',
                icon:'el-icon-menu',
                children: []
            }],
            filterText:''
        }
    },
    watch: {
        filterText(val) {
            this.$refs.tree.filter(val);
        },
        tableData: {
            handler(val) {
                this.currentTableData = val
            },
            immediate: true
        }
    },
    mounted() {
        this.$nextTick(() => {
            let self = this;
            self.resetMainHeight();
            self.treeLoad();
            self.tableLoad();
            window.onresize = function () {
                //self.height = self.$refs.table.$el.offsetHeight
                self.resetMainHeight();
            }
        })
    },
    methods: {
        resetMainHeight:function(){
            this.mainHeight = window.innerHeight - 265;
        },
        handleSwitchChange(val, index) {
            const oldValue = this.currentTableData[index]
            this.$set(this.currentTableData, index, {
                ...oldValue,
                type: val
            })
        },
        tableLoad(){
            this.$emit('tableLoad',{});
        },
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        handleForm: function (row,method) {
            this.$refs.dataForm.open(row,method);
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
                httpPost('aooms/rbac/user/delete',submitData).then(res => {
                    this.$message({
                        type: 'success',
                        message: '成功删除' + selection.length + '条数据'
                    });

                    type == 'del' ? this.delLoading = false : this.$set(selection[0],'delLoading',false);
                    this.tableLoad();
                });
            })
        },
        treeLoad(){
            httpGet('aooms/rbac/org/findTree').then(res => {
                this.treeData[0].children = res.$tree;
            });
        },
        handleNodeClick(data) {
            this.org_id = data.id;
            this.org_name = data.label;
            this.$emit('tableLoad',{},true);
        },
        filterNode(value, data) {
            if (!value) return true;
            return data.label.indexOf(value) !== -1;
        }
    }
}
</script>
