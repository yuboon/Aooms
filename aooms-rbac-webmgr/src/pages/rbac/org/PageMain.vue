<template>
    <div>
        <div :style="{height: mainHeight + 'px' }">
            <SplitPane :default-percent='20' split="vertical">
                <template slot="paneL" :style="{height: '100%' }" >
                    <el-scrollbar class="aooms-scrollbar">
                        <div class="aooms-tree-left">
                            <el-input size="mini" placeholder="输入关键字进行过滤" style="padding-bottom: 5px;"></el-input>
                            <el-tree :data="treeData" :props="defaultProps" @node-click="handleNodeClick">
                                <span class="aooms-tree-node" slot-scope="{ node, data }">
                                   <i :class="node.icon"></i>{{ node.label }}
                                   <!-- 自定义图标 : https://blog.csdn.net/qq_33242126/article/details/79365098-->
                                </span>
                            </el-tree>
                        </div>
                    </el-scrollbar>
                </template>

                <template slot="paneR">

                    <div style="padding-left: 5px;">
                        <el-button type="primary" size="mini" icon="el-icon-plus" @click="handleForm()">新增</el-button>
                        <el-button type="danger" size="mini" icon="el-icon-delete" @click="handleDelete()">删除</el-button>
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
                                    <el-form-item label="上级机构" style="width: 100%;">
                                        <span>{{ scope.row.parent_org_name }}</span>
                                    </el-form-item>
                                    <el-form-item label="机构名称">
                                        <span>{{ scope.row.org_name }}</span>
                                    </el-form-item>
                                    <el-form-item label="机构简称">
                                        <span>{{ scope.row.org_shortname }}</span>
                                    </el-form-item>
                                    <el-form-item label="机构编码">
                                        <span>{{ scope.row.org_code }}</span>
                                    </el-form-item>
                                    <el-form-item label="序号">
                                        <span>{{ scope.row.phone }}</span>
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

                        <el-table-column label="头像" prop="photo"/>
                        <el-table-column label="机构名称" prop="org_name"/>
                        <el-table-column label="机构简称" prop="org_shortname">
                            <template slot-scope="scope">
                                <el-input v-model="scope.row.org_shortname" size="mini"></el-input>
                            </template>
                        </el-table-column>
                        <el-table-column label="机构代码" prop="org_code"/>
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

                        <el-table-column label="序号" prop="ordinal" align="center"/>
                        <el-table-column label="创建时间" prop="create_time" align="center" width="150"/>

                        <el-table-column fixed label="操作" align="center" width="100">
                            <template slot-scope="scope">
                                <el-button type="primary" title="编辑" size="mini" icon="el-icon-edit" circle @click="handleForm(scope.row)"></el-button>
                                <el-button type="danger" title="删除" size="mini" icon="el-icon-delete" circle @click="handleDelete(scope.row)"></el-button>
                            </template>
                        </el-table-column>

                    </el-table>
                </template>
            </SplitPane>
        </div>

        <!-- 表单弹窗 -->
        <data-form ref="dataForm" @refreshTable="refreshTable"></data-form>
    </div>
</template>

<style lang="scss">

</style>

<script>
import BooleanControl from './BooleanControl.vue'
import DataForm from './DataForm.vue'
import {httpPost} from '@/api/sys/http'

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
            currentTableData: [],
            multipleSelection: [],
            mainHeight: 0,
            treeData: [{
                label: '一级 1',
                icon:'el-icon-menu',
                children: [{
                    label: '二级 1-1',
                    icon:'el-icon-news',
                    children: [{
                        icon:'el-icon-news',
                        label: '三级 1-1-1'
                    }]
                }]
            }, {
                label: '一级 2',
                icon:'el-icon-menu',
                children: [{
                    label: '二级 2-1',
                    children: [{
                        label: '三级 2-1-1'
                    }]
                }, {
                    label: '二级 2-2',
                    icon:'el-icon-menu',
                    children: [{
                        label: '三级 2-2-1'
                    }]
                }]
            }, {
                label: '一级 3',
                icon:'el-icon-news',
                children: [{
                    label: '二级 3-1',
                    children: [{
                        label: '三级 3-1-1'
                    }]
                }, {
                    label: '二级 3-2',
                    children: [{
                        label: '三级 3-2-1'
                    },{
                        label: '三级 3-2-2'
                    },{
                        label: '三级 3-2-3'
                    },{
                        label: '三级 3-2-4'
                    }]
                }]
            }],
            defaultProps: {
                children: 'children',
                label: 'label'
            }
        }
    },
    watch: {
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
            window.onresize = function () {
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
            // 注意 这里并没有把修改后的数据传递出去 如果需要的话请自行修改
        },
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        handleForm: function (row) {
            this.$refs.dataForm.open(row);
        },
        handleDelete: function (row) {
            var self = this;
            this.$confirm('确定删除选择的数据?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                var selection = [],ids = [];
                if(row){
                    selection.push(row);
                }else{
                    selection = self.multipleSelection;
                }

                selection.forEach(item => {
                    ids.push({id:item.id});
                });

                let submitData = new FormData();
                submitData.append("ids",JSON.stringify(ids));
                httpPost('aooms/rbac/user/delete',submitData).then(res => {
                    this.$message({
                        type: 'success',
                        message: '成功删除' + selection.length + '条数据'
                    });
                    refreshTable();
                });
            })
        },
        refreshTable(){
            this.$emit('getTableData',{});
        },
        handleNodeClick(data) {
            console.log(data);
        }
    }
}
</script>
