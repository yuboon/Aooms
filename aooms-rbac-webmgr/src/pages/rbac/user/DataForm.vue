<template>
    <div class="aooms-dialog">
        <el-dialog
                title="用户信息"
                :visible.sync="dialogVisible">

            <el-form ref="form" :model="form" label-width="80px" size="small">
                <input type="hidden" v-model="form.id">
                <input type="hidden" v-model="form.org_id">


                <el-form-item prop="org_name" label="所属机构">
                    <el-popover
                            placement="right-end"
                            title="选择机构"
                            width="400"
                            :visible-arrow="false"
                            trigger="click"
                            v-model="popoverVisible"
                    >

                        <el-input size="mini" placeholder="输入关键字进行过滤" v-model="filterText" style="padding-bottom: 5px;"></el-input>
                        <div style="height:300px;">
                            <el-scrollbar class="aooms-scrollbar">
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
                            </el-scrollbar>
                        </div>

                        <span style="color: blue;cursor: pointer;" slot="reference" @click="popoverVisible = !popoverVisible" > [ {{ changeOrgName || org_name }} ] </span>
                    </el-popover>

                </el-form-item>

                <el-form-item prop="account" label="用户账号" :rules="[{ required: true, message: '不能为空'}]">
                    <el-input v-model="form.account"></el-input>
                </el-form-item>

                <el-form-item prop="password" label="密码">
                    <el-input v-model="form.password"></el-input>
                </el-form-item>

                <el-row>
                    <el-col :span="12">
                        <el-form-item prop="user_name" label="用户姓名" :rules="[{ required: true, message: '不能为空'}]">
                            <el-input v-model="form.user_name"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="12">
                        <el-form-item prop="user_nickname" label="用户昵称">
                            <el-input v-model="form.user_nickname"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="12">
                        <el-form-item prop="phone" label="电话">
                            <el-input v-model="form.phone"></el-input>
                        </el-form-item>
                    </el-col>

                    <el-col :span="12">
                        <el-form-item prop="email" label="邮箱" :rules="[{ type: 'email', message: '邮箱格式错误'}]">
                            <el-input v-model="form.email"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="12">
                        <el-form-item label="性别">
                            <el-radio-group v-model="form.sex">
                                <el-radio label="0">男</el-radio>
                                <el-radio label="1">女</el-radio>
                            </el-radio-group>
                        </el-form-item>
                    </el-col>

                    <el-col :span="12">
                        <el-form-item prop="ordinal" label="序号" :rules="[{type:'number', required: true, message: '请输入数字'}]">
                            <el-input v-model.number="form.ordinal"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-form-item label="角色">
                    <el-select
                            v-model="roleIds"
                            multiple
                            filterable
                            placeholder="请选择" style="width: 100%;">
                        <el-option
                                v-for="item in roles"
                                :key="item.id"
                                :label="item.role_name"
                                :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>

                <el-form-item label="备注">
                    <el-input type="textarea" class="aooms-form-textarea" v-model="form.remark"></el-input>
                </el-form-item>

                <!--<el-form-item label="活动时间">
                    <el-col :span="11">
                        <el-date-picker type="date" placeholder="选择日期" format="yyyy-MM-dd" v-model="form.date1" style="width: 100%;"></el-date-picker>
                    </el-col>
                    <el-col :span="2">&nbsp;</el-col>
                    <el-col :span="11">
                        <el-time-picker placeholder="选择时间" value-format="HH:mm:ss" v-model="form.date2" style="width: 100%;"></el-time-picker>
                    </el-col>
                </el-form-item>
                <el-form-item label="即时配送">
                    <el-switch v-model="form.delivery"></el-switch>
                </el-form-item>
                <el-form-item label="活动性质">
                    <el-checkbox-group v-model="form.type">
                        <el-checkbox label="美食/餐厅线上活动" name="type"></el-checkbox>
                        <el-checkbox label="地推活动" name="type"></el-checkbox>
                        <el-checkbox label="线下主题活动" name="type"></el-checkbox>
                        <el-checkbox label="单纯品牌曝光" name="type"></el-checkbox>
                    </el-checkbox-group>
                </el-form-item>
                <el-form-item label="特殊资源">
                    <el-radio-group v-model="form.resource">
                        <el-radio label="1">线上品牌商赞助2</el-radio>
                        <el-radio label="2">线下场地免费2</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="活动形式">
                    <el-input type="textarea" class="aooms-form-textarea" v-model="form.desc"></el-input>
                </el-form-item>-->
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
            method:'',
            form: {
                /*id:'',
                user_name: '',
                user_nickname: '',
                account: '',
                password: '',
                phone: '',
                email: '',
                sex: '0',
                remark: '',
                status:'',
                photo:''*/
            },
            dialogVisible: false,
            popoverVisible: false,
            filterText:'',
            changeOrgName:'',
            //roleIds:["267321253608558592","267326095118831616"],
            roleIds:[],
            roles:[]
        }
    },
    mounted() {
        this.$nextTick(() => {
            var self = this;
            httpGet('aooms/rbac/roleService/findList',{status:'Y'}).then(res => {
                self.roles = res.$data.list;
            })
        });
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
    methods: {
        insert:function(){
            var self = this;
            this.$refs.form.validate((valid, error) => {
                if (valid) {
                    // 推荐使用这种方式提交，保证RequestHeaders = Content-Type: multipart/form-databoss; boundary=----WebKitFormBoundaryzBe4gknCRJ5m3eaU
                    let submitData = new FormData();
                    submitData.append('formData',JSON.stringify(self.form));
                    submitData.append('roleIds',JSON.stringify(self.roleIds));
                    this.loading = true;
                    httpPost('aooms/rbac/userService/' + self.method,submitData).then(res => {
                        this.$message({
                            type: 'success',
                            message: '保存成功'
                        });
                        this.loading = false;
                        this.$emit('tableLoad',{},false);
                        this.dialogVisible = false;
                    });
                }
            });
        },
        open:function(row,method){
            this.changeOrgName = '';
            this.method = method;
            this.dialogVisible = true;
            this.form = Object.assign({},row);

            var self = this;
            self.roleIds = [];
            httpGet('aooms/rbac/userService/findRoleByUserId', {user_id: this.form.id}).then(res => {
                if(res.roleIds) self.roleIds = res.roleIds;
            })
        },
        close:function(){
            this.loading = false;
            this.dialogVisible = false;
        },
        handleNodeClick(data) {
            var self = this;
            this.form.org_id = data.id;
            this.changeOrgName = data.org_name;
        },
        filterNode(value, data) {
            if (!value) return true;
            return data.org_name.indexOf(value) !== -1;
        }
    }
}
</script>