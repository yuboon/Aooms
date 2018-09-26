<template>
    <div class="aooms-dialog">
        <el-dialog
                title="用户信息"
                :visible.sync="dialogVisible">

            <el-form ref="userForm" :model="form" label-width="80px" size="small">
                <el-form-item prop="name" label="名称" :rules="[{ required: true, message: '请输入活动名称', trigger: 'blur' }]">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="活动区域">
                    <el-select v-model="form.region" placeholder="请选择活动区域" style="width: 100%;">
                        <el-option label="区域一" value="shanghai"></el-option>
                        <el-option label="区域二" value="beijing"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="活动时间">
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
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submit">立即创建</el-button>
                    <el-button @click="close">取消</el-button>
                </el-form-item>
            </el-form>

        </el-dialog>
    </div>

</template>

<style lang="scss">
    .aooms-dialog .el-dialog__body{
        padding: 30px 30px 5px 20px;
    }

    .aooms-dialog .el-dialog{
        margin-top: 10vh !important;
    }

    .aooms-form-textarea textarea{
        font-family: "Arial", "Microsoft YaHei", "\9ED1\4F53", "\5B8B\4F53", sans-serif;
        color: #030406;
    }
</style>

<script>
    export default {
        data() {
            return {
                form: {
                    name: '',
                    region: '',
                    date1: '',
                    date2: '',
                    delivery: false,
                    type: [],
                    resource: '',
                    desc: '',
                    email:''
                },
                dialogVisible: false
            }
        },
        props: {
            isShow: {
                default:false
            }
        },
        methods: {
            submit() {
                var self = this;
                this.$refs["userForm"].validate((valid, error) => {
                    if (valid) {
                        alert('submit2!');
                        alert(JSON.stringify(self.form));
                    } else {
                        console.log(error);
                        return false;
                    }
                });
            },
            open:function(formData){
                this.dialogVisible = true;
                if(formData){
                    this.form = formData;
                }
                //this.$emit('ee', 'cc12345')
            },

            close:function() {
                this.dialogVisible = false;
                //this.$emit('ee', 'cc12345')
            }
        }
    }
</script>