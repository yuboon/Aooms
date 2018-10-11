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

            <el-input size="mini" v-model="filterText" placeholder="资源名称" style="width: 100px;margin-left: 10px;"/>
        </el-form>

        <d2-treetable ref="treeTable" row-key="resource_name" :data="tableData" size="mini" stripe >
            <el-table-column label="资源名称" prop="resource_name" />
            <el-table-column label="资源编码" prop="resource_code" align="center"/>
            <el-table-column label="资源类型" prop="resource_type" align="center"/>
            <el-table-column label="链接地址" prop="resource_url" width="250"/>
            <el-table-column label="序号" prop="ordinal" align="center" width="50"/>
            <el-table-column label="状态" prop="status" align="center" width="80"/>

            <el-table-column fixed="right" label="操作" align="center" width="130">
                <template slot-scope="scope">
                    <el-button type="primary" title="添加子级" size="mini" icon="el-icon-plus" circle @click="handleForm({'status':'Y','ordinal':0, 'resource_type':'1','open_type':'0','parent_resource_id':scope.row.id},'insert',scope.row)"></el-button>
                    <el-button type="primary" title="编辑" size="mini" icon="el-icon-edit" circle @click="handleForm(scope.row,'update',scope.row)"></el-button>
                    <el-button type="danger" title="删除" :loading="scope.row.delLoading" size="mini" icon="el-icon-delete" circle @click="handleDelete(scope.row)"></el-button>
                </template>
            </el-table-column>

        </d2-treetable>
        <!--<el-table
                :data="currentTableData"
                v-loading="loading"
                size="mini"
                stripe
                style="width: 100%;"
                :height="tableHeight"
                @selection-change="handleSelectionChange">

            &lt;!&ndash; Table 展开行 &ndash;&gt;
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

            <el-table-column label="头像" prop="photo"/>
            <el-table-column label="用户姓名" prop="user_name"/>
            <el-table-column label="用户昵称" prop="user_nickname"/>
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
            <el-table-column label="邮箱" prop="email" width="150"/>
            <el-table-column label="创建时间" prop="create_time" align="center" width="150"/>

            <el-table-column fixed label="操作" align="center" width="100">
                <template slot-scope="scope">
                    <el-button type="primary" title="编辑" size="mini" icon="el-icon-edit" circle @click="handleForm(scope.row)"></el-button>
                    <el-button type="danger" title="删除" size="mini" icon="el-icon-delete" circle @click="handleDelete(scope.row)"></el-button>
                </template>
            </el-table-column>

        </el-table>-->

        <!-- 表单弹窗 -->
        <data-form @tableUpdate="tableUpdate" ref="dataForm"></data-form>
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
            filterText: ''
        }
    },
    watch: {
        tableData: {
            handler(val) {
                this.currentTableData = val
            },
            immediate: true
        },
        filterText(val) {
            this.$refs.treeTable.filter('resource_name',val);
        }
    },
    mounted() {
        this.$nextTick(() => {
            let self = this;
            self.resetMainHeight();
            self.$emit('tableLoad',{});
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
        handleForm: function (row,method,parentRow){
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
                httpPost('aooms/rbac/resource/delete',submitData).then(res => {
                    this.$refs.treeTable.remove(row);
                });
            })
        },
        tableUpdate(data,parentRow){
            this.$refs.treeTable.append(data,parentRow);
        }

    }
}
</script>
