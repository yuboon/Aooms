<template>
    <div>
        <div>
            <el-button type="primary" size="mini" icon="el-icon-plus" @click="handleForm()">新增</el-button>
            <!--<el-button type="primary" size="mini" icon="el-icon-edit" @click="handleForm()">编辑</el-button>-->
            <el-button type="danger" size="mini" icon="el-icon-delete" @click="handleDelete()">删除</el-button>
        </div>

        <el-table
                :data="currentTableData"
                v-loading="loading"
                size="mini"
                stripe
                style="width: 100%;"
                :height="mainHeight"
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
            <el-table-column label="序号" prop="ordinal"/>
            <el-table-column label="创建时间" prop="create_time" align="center" width="150"/>

            <el-table-column fixed label="操作" align="center" width="100">
                <template slot-scope="scope">
                    <el-button type="primary" title="编辑" size="mini" icon="el-icon-edit" circle @click="handleForm(scope.row)"></el-button>
                    <el-button type="danger" title="删除" size="mini" icon="el-icon-delete" circle @click="handleDelete(scope.row)"></el-button>
                </template>
            </el-table-column>

        </el-table>

        <!-- 表单弹窗 -->
        <data-form ref="dataForm"></data-form>
    </div>
</template>

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
            mainHeight: window.innerHeight - 300
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
                //self.height = self.$refs.table.$el.offsetHeight
                self.resetMainHeight();
            }
        })
    },
    methods: {
        resetMainHeight:function(){
            this.mainHeight = window.innerHeight - 293;
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
                    this.$emit('tableLoad',{});
                });
            })
        }
    }
}
</script>
