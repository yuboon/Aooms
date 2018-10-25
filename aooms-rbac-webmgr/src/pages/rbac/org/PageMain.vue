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
                                   <!-- 自定义图标 : https://blog.csdn.net/qq_33242126/article/details/79365098-->
                                </span>
                            </el-tree>
                        </div>
                    </el-scrollbar>
                </template>

                <template slot="paneR">

                    <div style="padding-left: 5px;">
                        <el-button type="primary" size="mini" icon="el-icon-plus" @click="handleForm({status:'Y',ordinal:0,parent_org_id:parent_org_id},'insert')">新增</el-button>
                        <el-button :loading="delLoading"
                                   type="danger" size="mini" icon="el-icon-delete" @click="handleDelete('del', multipleSelection)">删除</el-button>

                        <el-switch
                                v-model="cascade"
                                active-text="开启级联"
                                style="float: right"
                                @change="cascadeChange"
                        />

                    </div>

                    <el-table
                            ref="table"
                            :data="currentTableData"
                            v-loading="loading"
                            size="mini"
                            stripe
                            style="margin-top: 5px"
                            :height="mainHeight - 72"
                            @selection-change="handleSelectionChange">

                        <!-- Table 展开行 -->
                        <el-table-column type="expand" width="20">
                            <template slot-scope="scope">
                                <el-form label-position="left" inline class="aooms-table-expand">
                                    <el-form-item label="上级机构" style="width: 100%;">
                                        <span>{{ $refs.tree.getNode(scope.row.parent_org_id).org_name }}</span>
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
                                        <span>{{ scope.row.ordinal }}</span>
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

                        <el-table-column type="selection" width="50" align="center" />
                        <el-table-column width="30" align="left" v-if="isMultipleSelection">
                                <template slot-scope="scope">
                                    <el-dropdown size="mini" placement="bottom" @command="dropdownCommand">
                                        <span class="el-dropdown-link">
                                            <i class="fa fa-angle-double-down" aria-hidden="true"></i>
                                        </span>
                                        <el-dropdown-menu slot="dropdown">
                                            <el-dropdown-item command="batchUpdateOrg">
                                                <i class="el-icon-menu"/>&nbsp;批量修改机构
                                            </el-dropdown-item>
                                            <el-dropdown-item>
                                                <i class="el-icon-menu"/>&nbsp;更多功能
                                            </el-dropdown-item>
                                        </el-dropdown-menu>
                                    </el-dropdown>
                                </template>
                        </el-table-column>

                        <!--<el-table-column label="头像" prop="photo"/>-->
                        <el-table-column label="机构名称" prop="org_name"/>
                        <el-table-column label="机构简称" prop="org_shortname">
                            <!--
                            <template slot-scope="scope">
                                <el-input v-model="scope.row.org_shortname" size="mini"></el-input>
                            </template>
                            -->
                        </el-table-column>
                        <el-table-column label="机构代码" prop="org_code"/>
                        <el-table-column label="状态" align="center" width="60">
                            <template slot-scope="scope">
                                <boolean-control
                                        :value="scope.row.status"
                                        :id="scope.row.id"
                                        @tableLoad="tableLoad"
                                        @change="(val) => {
                                            currentTableData[scope.$index].status = val
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
                                <el-button type="primary" title="编辑" size="mini" icon="el-icon-edit" circle @click="handleForm(scope.row,'update')"></el-button>
                                <el-button type="danger" title="删除" :loading="scope.row.delLoading" size="mini" icon="el-icon-delete" circle @click="handleDelete('delOne',[scope.row])"></el-button>
                            </template>
                        </el-table-column>
                    </el-table>

                    <ext-pagination ref="pagination" @change="tableLoad({},false)" style="padding:10px 0 0 10px; margin: 0px;" />
                </template>
            </SplitPane>
        </div>

        <!-- 表单弹窗 -->
        <data-form ref="dataForm"
                   :parent_org_id="parent_org_id"
                   :parent_org_name="parent_org_name"
                   :treeData="treeData"
                   @tableLoad="tableLoad"
                   @treeLoad="treeLoad"
                   @treeUpdate="treeUpdate">
        </data-form>

    </div>
</template>

<style lang="scss">

</style>

<script>
    import {httpGet, httpPost} from '@/api/sys/http'

    import BooleanControl from './BooleanControl.vue'
    import DataForm from './DataForm.vue'
    import ExtPagination from '@/components/ext-pagination'

    export default {
    components: {
        BooleanControl,
        DataForm,
        ExtPagination
    },
    data() {
        return {
            isMultipleSelection:false,
            loading:false,
            delLoading:false,
            currentTableData: [],
            multipleSelection: [],
            mainHeight: 0,
            filterText:'',
            parent_org_id: 'ROOT',
            parent_org_name: '顶层机构',
            data_permission:'',
            treeData: [{
                id:'ROOT',
                data_permission:'',
                org_name: '顶层机构',
                icon:'el-icon-menu',
                children: []
            }],
            cascade:false
        }
    },
    watch: {
        filterText(val) {
            this.$refs.tree.filter(val);
        }/*,
        treeData: {
            handler(newValue, oldValue) {
                //this.$ref.datForm.treeData = this.treeData;
                /!*for (let i = 0; i < newValue.length; i++) {
                    if (oldValue[i] != newValue[i]) {
                        console.log(newValue)
                    }
                }*!/
            },
            deep: true
        }*/
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
        handleSelectionChange(val) {
            this.isMultipleSelection = val.length > 1;
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
                httpPost('aooms/rbac/org/delete',submitData).then(res => {
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

                    ids.forEach((id) => {
                        var node = this.$refs.tree.getNode(id);
                        this.$refs.tree.remove(node);
                    });
                });
            })
        },
        treeLoad(){
            httpGet('aooms/rbac/org/findTree').then(res => {
                this.treeData[0].children = res.$tree;
                this.$refs.dataForm.treeData = this.treeData;
            });
        },
        treeUpdate(newData,method){
            if(method == 'insert'){
                var parentNode = this.$refs.tree.getNode(this.parent_org_id);
                //parentNode.data.children.push(newData);
                this.$refs.tree.append(newData,parentNode);
            }else{
                var node = this.$refs.tree.getNode(newData.id);
                Object.assign(node.data, newData);
            }
        },
        tableLoad(params,jumpFirst){
            var self = this;
            this.$emit('pageHeaderFormData',function(formData) {
                var pagination = self.$refs.pagination;

                if (jumpFirst) pagination.current = 1;

                var ext = {parent_org_id: self.parent_org_id};
                if (self.cascade){
                    ext = {data_permission: self.data_permission};
                };

                // 分页参数、查询条件拷贝
                Object.assign(params, formData, {
                    page: pagination.current,
                    limit: pagination.size
                },ext);

                self.loading = true;
                httpGet('aooms/rbac/org/findList', params).then(res => {
                    self.loading = false;
                    self.currentTableData = res.$data.list
                    pagination.total = res.$data.total;
                });
            });
        },
        handleNodeClick(data) {
            this.parent_org_id = data.id;
            this.parent_org_name = data.org_name;
            this.data_permission = data.org_permission;
            this.tableLoad({},true);
        },
        filterNode(value, data) {
            if (!value) return true;
            return data.org_name.indexOf(value) !== -1;
        },
        dropdownCommand(val){
            alert(val);
        },
        cascadeChange(val){
            this.tableLoad({});
        }
    }
}
</script>
