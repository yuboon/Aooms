<template>
    <div>
        <el-form
                :inline="true"
                size="mini"
                class="demo"
        >
            <el-button type="primary" size="mini" icon="el-icon-plus" @click="handleForm()">新增</el-button>
            <!--<el-button type="primary" size="mini" icon="el-icon-edit" @click="handleForm()">编辑</el-button>-->
            <el-button type="danger" size="mini" icon="el-icon-delete" @click="handleDelete()">删除</el-button>
        </el-form>

        <el-table
                :data="currentTableData"
                v-loading="loading"
                size="mini"
                stripe
                style="width: 100%;"
                :height="tableHeight"
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
            <el-table-column label="邮箱" prop="email" width="150"/>
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

<style>
    .aooms-table-expand {
        font-size: 0;
    }

    .aooms-table-expand label {
        width: 90px;
        color: #99a9bf;
        text-align: right !important;
    }

    .aooms-table-expand .el-form-item {
        margin-right: 0;
        margin-bottom: 0;
        width: 50%;
    }
</style>

<script>
import BooleanControl from './BooleanControl.vue'
import DataForm from './DataForm.vue'

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
            downloadColumns: [
                {label: '卡密', prop: 'key'},
                {label: '面值', prop: 'value'},
                {label: '状态', prop: 'type'},
                {label: '管理员', prop: 'admin'},
                {label: '管理员备注', prop: 'adminNote'},
                {label: '创建时间', prop: 'dateTimeCreat'},
                {label: '使用状态', prop: 'used'},
                {label: '使用时间', prop: 'dateTimeUse'}
            ],
            isShow: false,
            tableHeight: window.innerHeight - 300
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
            window.onresize = function () {
                //self.height = self.$refs.table.$el.offsetHeight
                self.tableHeight = window.innerHeight - 300;
            }
        })
    },
    methods: {
        handleSwitchChange(val, index) {
            const oldValue = this.currentTableData[index]
            this.$set(this.currentTableData, index, {
                ...oldValue,
                type: val
            })
            // 注意 这里并没有把修改后的数据传递出去 如果需要的话请自行修改
        },
        handleSelectionChange(val) {
            this.multipleSelection = val
        },
        handleForm: function (row) {
            this.$refs.dataForm.open(row ? row : {sex:'0'});
        },
        handleDelete: function (row) {
            var self = this;
            this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                var len = self.multipleSelection.length;
                this.$message({
                    type: 'success',
                    message: '成功删除' + len + '条数据'
                });
            })
        }
    }
}
</script>
