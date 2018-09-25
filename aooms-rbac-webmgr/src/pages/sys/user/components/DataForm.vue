<template>
    <!--
    <el-dialog
            title="提示"
            :visible.sync="isShow"
            width="30%"
            :before-close="handleClose">
        <span>这是一段信息</span>
        <span slot="footer" class="dialog-footer">
            <el-button @click="ok">取 消</el-button>
            <el-button type="primary" @click="ok">确 定</el-button>
        </span>
    </el-dialog>
    -->

    <el-dialog
            title="用户信息"
            :visible.sync="dialogVisible">

        <el-form ref="form" :model="form" label-width="80px" size="small">
            <el-form-item label="活动名称">
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
                    <el-date-picker type="date" placeholder="选择日期" v-model="form.date1" style="width: 100%;"></el-date-picker>
                </el-col>
                <el-col :span="2">&nbsp;</el-col>
                <el-col :span="11">
                    <el-time-picker type="fixed-time" placeholder="选择时间" v-model="form.date2" style="width: 100%;"></el-time-picker>
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
                    <el-radio label="线上品牌商赞助"></el-radio>
                    <el-radio label="线下场地免费"></el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="活动形式">
                <el-input type="textarea" v-model="form.desc"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="close">立即创建</el-button>
                <el-button @click="close">取消</el-button>
            </el-form-item>
        </el-form>

    </el-dialog>

</template>

<style lang="scss">
    .el-dialog__body{
        padding: 30px 30px 5px 20px;
    }

    .el-dialog{
        margin-top: 10vh !important;
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
                    desc: ''
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
            onSubmit() {
                console.log('submit!');
            },
            open:function(){
                this.dialogVisible = true;
                //this.$emit('ee', 'cc12345')
            },
            close:function() {
                this.dialogVisible = false;
                //this.$emit('ee', 'cc12345')
            }
        }
    }
</script>