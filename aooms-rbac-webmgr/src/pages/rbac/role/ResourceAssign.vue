<template>
    <div class="aooms-dialog">
        <el-dialog
                title="权限分配"
                :visible.sync="dialogVisible">

            <el-form ref="form" :model="form" label-width="5px" size="small">
                <input type="hidden" v-model="form.id">
                <input type="hidden" v-model="form.org_id">

                <el-row style="padding: 0 0 5px 5px;font-weight: bold;">
                    当前角色：{{ form.role_name }}
                </el-row>

                <el-form-item>
                    <el-input size="mini" placeholder="输入关键字进行过滤" v-model="filterText" style="padding-bottom: 5px;"></el-input>
                    <div style="height: 300px;">
                        <el-scrollbar class="aooms-scrollbar">
                            <el-tree
                                    ref="tree"
                                    show-checkbox
                                    :expand-on-click-node="false"
                                    :default-expanded-keys="['ROOT']"
                                    :props="{
                                        label: 'resource_name'
                                    }"
                                    highlight-current
                                    node-key="id"
                                    :data="resourceData"
                                    :filter-node-method="filterNode">

                                <span class="aooms-tree-node" slot-scope="{ node, data }">
                                   <d2-icon :name="node.icon" style="width: 15px;text-align: center;"/>&nbsp;{{ node.label }}
                                </span>

                            </el-tree>
                        </el-scrollbar>
                    </div>
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
        org_id: {},
        org_name: {},
        treeData:{}
    },
    data() {
        return {
            loading:false,
            form: {},
            dialogVisible: false,
            filterText:'',
            resourceIds:[],
            selectResourceIds:[],
            halfSelectResourceIds:[],
            resourceData:[]

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
        }
    },
    mounted() {
        var self = this;
        httpGet('aooms/rbac/resourceService/findTree',{'status':'Y'}).then(res => {
            self.resourceData = res.$tree;
        })
    },
    methods: {
        insert:function(){
            var self = this;
            this.$refs.form.validate((valid, error) => {
                if (valid) {

                    var nodes = this.$refs.tree.getCheckedNodes();
                    var halfNodes = this.$refs.tree.getHalfCheckedNodes();
                    this.selectResourceIds = [];
                    this.halfSelectResourceIds = [];
                    nodes.forEach(node => {
                        this.selectResourceIds.push(node.id);
                    });
                    halfNodes.forEach(node => {
                        this.halfSelectResourceIds.push(node.id);
                    });

                    let submitData = new FormData();
                    submitData.append('role_id',self.form.id);
                    submitData.append("resourceIds",JSON.stringify(self.selectResourceIds));
                    submitData.append("halfResourceIds",JSON.stringify(self.halfSelectResourceIds));

                    this.loading = true;
                    httpPost('aooms/rbac/roleService/insertPermission',submitData).then(res => {
                        this.$message({
                            type: 'success',
                            message: '保存成功'
                        });
                        this.loading = false;
                    });
                }
            });
        },
        open:function(row){
            this.dialogVisible = true;
            this.form = Object.assign({},row);

            var self = this;
            this.$nextTick(() => {
                self.$refs.tree.setCheckedNodes([]);
                httpGet('aooms/rbac/roleService/findPermissionByRoleId', {'role_id': self.form.id,'is_halfselect':'N'}).then(res => {
                    self.resourceIds = res.resourceIds;
                    self.$refs.tree.setCheckedNodes(self.resourceIds);
                })
            });

        },
        close:function(){
            this.dialogVisible = false;
        },
        filterNode(value, data) {
            if (!value) return true;
            return data.resource_name.indexOf(value) !== -1;
        }
    }
}
</script>