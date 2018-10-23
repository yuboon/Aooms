<template>
    <div>
        <el-form
                :inline="true"
                size="mini"
        >
            <el-button type="primary" size="mini" icon="el-icon-plus"
                       @click="handleForm({'status':'Y','ordinal':0,'resource_type':'1','open_type':'0','parent_resource_id':'ROOT'},'insert')">
                新增
            </el-button>

            <el-input size="mini" v-model="filterText" placeholder="名称或编码" style="width: 100px;margin-left: 10px;"/>
            <el-button size="mini" title="刷新" @click="tableLoad" icon="el-icon-refresh" circle style="margin-left: 5px;"></el-button>

        </el-form>

        <ext-treetable v-loading="loading" ref="treeTable" row-key="resource_name" :data="currentTableData" size="mini" stripe style="margin-top: 5px;">
            <el-table-column label="资源名称" prop="resource_name" />
            <el-table-column label="资源编码" prop="resource_code" align="center"/>
            <el-table-column label="资源类型" prop="resource_type" align="center"
                             :filters="[{ text: '目录', value: '0' }, { text: '模块', value: '1' }, { text: '按钮', value: '2' }, { text: '接口', value: '3' }]"
                             :filter-method="filterType">
                <template slot-scope="scope">
                    <el-tag v-if="scope.row.resource_type == '0'" size="mini" >目录</el-tag>
                    <el-tag v-if="scope.row.resource_type == '1'" size="mini" type="success">模块</el-tag>
                    <el-tag v-if="scope.row.resource_type == '2'" size="mini" type="info">按钮</el-tag>
                    <el-tag v-if="scope.row.resource_type == '3'" size="mini" type="warning">接口</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="链接地址" prop="resource_url" width="250"/>
            <el-table-column label="序号" prop="ordinal" align="center" width="50"/>
            <el-table-column label="状态" prop="status" align="center" width="80">
                <template slot-scope="scope">
                    <boolean-control
                            :value="scope.row.status"
                            :id="scope.row.id"
                            @tableLoad="tableLoad"
                            @change="(val) => {scope.row.status = val}"
                    >
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

            <el-table-column fixed="right" label="操作" align="center" width="130">
                <template slot-scope="scope">
                    <el-button type="primary" title="添加子级" size="mini" icon="el-icon-plus" circle @click="handleForm({'status':'Y','ordinal':0, 'resource_type':'1','open_type':'0','parent_resource_id':scope.row.id},'insert',scope.row)"></el-button>
                    <el-button type="primary" title="编辑" size="mini" icon="el-icon-edit" circle @click="handleForm(scope.row,'update',scope.row)"></el-button>
                    <el-button type="danger" title="删除" :loading="scope.row.delLoading" size="mini" icon="el-icon-delete" circle @click="handleDelete(scope.row)"></el-button>
                </template>
            </el-table-column>

        </ext-treetable>

        <!-- 表单弹窗 -->
        <data-form @tableUpdate="tableUpdate" @tableDelete="tableDelete" :currentTableData="currentTableData" ref="dataForm"></data-form>
    </div>
</template>

<script>
import BooleanControl from './BooleanControl.vue'
import DataForm from './DataForm.vue'
import {httpPost,httpGet} from '@/api/sys/http'

export default {
    components: {
        BooleanControl,
        DataForm
    },
    data() {
        return {
            loading:false,
            currentTableData: [],
            multipleSelection: [],
            mainHeight: 0,
            filterText: ''
        }
    },
    watch: {
        filterText(val) {
            this.$refs.treeTable.filter(['resource_name','resource_code'],val);
        }
    },
    mounted() {
        this.$nextTick(() => {
            let self = this;
            self.resetMainHeight();
            this.tableLoad();
            window.onresize = function () {
                self.resetMainHeight();
            }
        })
    },
    methods: {
        resetMainHeight:function(){
            this.mainHeight = window.innerHeight - 265;
        },
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },
        handleForm: function (row,method,parentRow){
            if(method == 'update'){
                parentRow = parentRow.parent;
            }

            if(!parentRow){
                parentRow = {resource_name:'无', children:this.tableData}
            }
            this.$refs.dataForm.open(row,method,parentRow);
        },
        handleDelete: function (row) {
            var self = this;
            this.$confirm('确定删除选择的数据?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                var ids = [{id:row.id}];
                let submitData = new FormData();
                submitData.append("ids",JSON.stringify(ids));

                this.$set(row,'delLoading',true);
                httpPost('aooms/rbac/resource/delete',submitData).then(res => {
                    this.$refs.treeTable.remove(row);
                    this.$set(row,'delLoading',false);
                });
            })
        },
        tableLoad(){
            this.loading = true;
            httpGet('aooms/rbac/resource/findTree').then(res => {
                this.currentTableData = res.$tree;
                this.loading = false;
            });
        },
        tableUpdate(data,parentRow){
            this.$refs.treeTable.append(data,parentRow);
        },
        tableDelete(row){
            this.$refs.treeTable.remove(row);
        },
        filterType(value, row, column) {
            const property = column['property'];
            return row[property] === value;
        }

    }
}
</script>
